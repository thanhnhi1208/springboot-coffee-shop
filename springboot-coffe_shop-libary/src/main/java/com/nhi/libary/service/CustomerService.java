package com.nhi.libary.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.OTP;
import com.nhi.libary.model.ShoppingCart;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.repository.OTPRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private JavaMailSender mailSender;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public Customer addProfile(Customer customer, MultipartFile imageOfCustomer) throws IOException {
		Customer checkPhone = this.customerRepository.findByPhoneNumber(customer.getPhoneNumber());
		Customer checkAccountNumber = this.customerRepository.findByAccountNumber(customer.getAccountNumber());
		if ((checkPhone != null && checkPhone.getId() != customer.getId())
				|| checkAccountNumber != null && customer.getAccountNumber().equals("")==false && checkAccountNumber.getId() != customer.getId() ) {
			return null;
		}

		if (imageOfCustomer.isEmpty()) {
			Customer customerTemp = customerRepository.findById(customer.getId()).get();
			customer.setImage(customerTemp.getImage());
		} else {
			customer.setImage(Base64.getEncoder().encodeToString(imageOfCustomer.getBytes()));
		}
		
		return customerRepository.save(customer);
	}

	public void sendOTPEmail(String email, String chucNang) throws UnsupportedEncodingException, MessagingException {
		String OTP = RandomString.make(8);
		OTP otp = this.otpRepository.findByEmailAndChucNang(email, chucNang);
		if (otp == null) {
			otp = new OTP();
			otp.setEmail(email);
			otp.setOtp(OTP);
			otp.setExpireTime(LocalDateTime.now().plusMinutes(1));
			otp.setChucNang(chucNang);
		} else {
			otp.setOtp(OTP);
			otp.setExpireTime(LocalDateTime.now().plusMinutes(1));
		}

		this.otpRepository.save(otp);

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("nguyenthanhnhi310718@gmail.com", "Hỗ trợ kĩ thuật");
		helper.setTo(email);

		String subject = "Đây là mã OTP của bạn và sẽ hết hạn sau 1 phút!";

		String content = "<p>Hello " + email + "</p>" + "<p><b>" + OTP + "</b></p>" + "<br>"
				+ "<p>Lưu ý: mã OTP này chỉ có hiệu lực trong vòng 1 phút.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

	public String confirmForgetPassword(String email, String password, String Otp) {
		Customer user = this.customerRepository.findByEmail(email);
		if (user == null) {
			OTP deleteOtp = this.otpRepository.findByEmailAndChucNang(email, "Quên mật khẩu");
			if (deleteOtp != null) {
				this.otpRepository.delete(deleteOtp);
			}
			return "Email sai";
		}

		OTP otpFromDatabase = this.otpRepository.findByEmailAndChucNang(email, "Quên mật khẩu");
		if (otpFromDatabase == null) {
			return "Bạn chưa gửi mã OTP";
		} else {
			if (otpFromDatabase.getOtp().equals(Otp) == false) {
				return "Sai mã OTP";
			}

			LocalDateTime currentTime = LocalDateTime.now();
			LocalDateTime expireTime = otpFromDatabase.getExpireTime();

			boolean isCurrentTimeAfterExpireTime = currentTime.isAfter(expireTime);
			if (isCurrentTimeAfterExpireTime) {
				return "Mã OTP đã hết hạn";
			} else {
				user.setPassword(bCryptPasswordEncoder.encode(password));
				this.customerRepository.save(user);
				this.otpRepository.deleteById(otpFromDatabase.getId());
				return "Thành công";
			}
		}
	}

	public String confirmRegister(Customer user, String Otp) throws IOException {
		if (this.customerRepository.findByEmail(user.getEmail()) != null) {
			OTP otpDelete = this.otpRepository.findByEmailAndChucNang(user.getEmail(), "Đăng kí");
			if (otpDelete != null) {
				this.otpRepository.delete(otpDelete);
			}
			return "Email đã được đăng kí";
		}

	
		OTP otpFromDatabase = this.otpRepository.findByEmailAndChucNang(user.getEmail(), "Đăng kí");
		if (otpFromDatabase == null) {
			return "Bạn chưa gửi mã OTP";
		} else {
			if (otpFromDatabase.getOtp().equals(Otp) == false) {
				return "Sai mã OTP";
			}

			LocalDateTime currentTime = LocalDateTime.now();
			LocalDateTime expireTime = otpFromDatabase.getExpireTime();

			boolean isCurrentTimeAfterExpireTime = currentTime.isAfter(expireTime);
			if (isCurrentTimeAfterExpireTime) {
				return "Mã OTP đã hết hạn";
			} else {
				String imagePath = "C:\\Users\\Admin\\Documents\\workspace-spring-tool-suite-4-4.20.1.RELEASE\\springboot-coffee_shop-root\\springboot-coffe_shop-customer\\src\\main\\resources\\static\\Hinh_Coffee\\avt-macdinh.jpg";
				File file = new File(imagePath);
				byte[] fileContent = Files.readAllBytes(file.toPath());
				user.setImage(Base64.getEncoder().encodeToString(fileContent));

				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				this.customerRepository.save(user);
				this.otpRepository.deleteById(otpFromDatabase.getId());
				return "Thành công";
			}
		}

	}
	
	public String conductChange(String email, String oldPassword, String newPassword) {
		Customer user = this.customerRepository.findByEmail(email);
		if(user == null) {
			return "Email sai";
		}
		
		if(bCryptPasswordEncoder.matches(oldPassword, user.getPassword())){
			if(oldPassword.equals(newPassword)) {
				return "Mật khẩu cũ và mới không thể giống nhau";
			}
			
			
			user.setPassword(bCryptPasswordEncoder.encode(newPassword));
			System.out.println("dadad");
			this.customerRepository.save(user);
			return "Thành công";
		}else {
			return "Sai mật khẩu cũ";
		}
		
		
	}
}
