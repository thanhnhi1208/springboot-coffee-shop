package com.nhi.customer.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhi.libary.model.Customer;
import com.nhi.libary.service.CustomerService;

import jakarta.mail.MessagingException;

@Controller
public class LoginController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("/login")
	public String login(Authentication authentication) {
		if (authentication != null) {
			return "redirect:/index";
		}
		return "LogIn";
	}

	@GetMapping("/signup")
	public String signUpPage(Model model, Authentication authentication, String email, String password, String fullName,
			String notification, String Otp) {
		if (authentication != null) {
			return "redirect:/index";
		}

		if (email != null) {
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			model.addAttribute("fullName", fullName);
			model.addAttribute("otp", Otp);
		}

		if (notification != null) {
			model.addAttribute("notification", notification);
		}
		return "Singup";
	}

	@GetMapping("/signup/sendOtpToEmail")
	@ResponseBody
	public void sendOtpToEmailSignUp(String email, Model model, Authentication authentication)
			throws UnsupportedEncodingException, MessagingException {
		this.customerService.sendOTPEmail(email, "Đăng kí");
	}

	@PostMapping("/signup/confirmRegister")
	public String confirmRegister(Customer user, String Otp, Model model, RedirectAttributes attributes)
			throws IOException {
		String notification = this.customerService.confirmRegister(user, Otp);
		if (notification.equals("Thành công") == false) {
			attributes.addAttribute("email", user.getEmail());
			attributes.addAttribute("password", user.getPassword());
			attributes.addAttribute("fullName", user.getFullName());
			attributes.addAttribute("Otp", Otp);
		}

		attributes.addAttribute("notification", notification);
		return "redirect:/signup";
	}

	@GetMapping("/change-pass")
	public String changePasswordPage(Model model, Authentication authentication, String email , String oldPassword, String newPassword, String notification) {
		if(authentication != null) {
			return "redirect:/index";
		}
		
		if(email != null) {
			model.addAttribute("email", email);
			model.addAttribute("oldPassword", oldPassword);
			model.addAttribute("newPassword", newPassword);
		}
		
		if(notification != null) {
			model.addAttribute("notification", notification);
		}
		
		return "DoiMK";
	}
	
	@PostMapping("/change-pass/conductChange")
	public String conductChange(String email, String oldPassword, String newPassword, RedirectAttributes attributes)  {
		String notification = this.customerService.conductChange(email, oldPassword, newPassword );
		if(notification.equals("Thành công") == false) {
			attributes.addAttribute("email", email);
			attributes.addAttribute("oldPassword", oldPassword);
			attributes.addAttribute("newPassword", newPassword);
		}
		
		attributes.addAttribute("notification", notification);
		return "redirect:/change-pass";
	}

	@GetMapping("/forgot-password")
	public String forgetPasswordPage(Model model, Authentication authentication, String email, String password,
			String Otp, String notification) {
		if (authentication != null) {
			return "redirect:/index";
		}

		if (email != null) {
			model.addAttribute("email", email);
		}

		if (password != null) {
			model.addAttribute("password", password);
		}

		if (Otp != null) {
			model.addAttribute("otp", Otp);
		}

		if (notification != null) {
			model.addAttribute("notification", notification);
		}

		return "FotgotPassword";
	}

	@GetMapping("/forgot-password/sendOtpToEmail")
	@ResponseBody
	public void sendOtpToEmailForgetPassword(String email, Model model, Authentication authentication)
			throws UnsupportedEncodingException, MessagingException {
		this.customerService.sendOTPEmail(email, "Quên mật khẩu");
	}

	@PostMapping("/forgot-password/confirmForgetPassword")
	public String confirmForgetPassword(String email, String password, String Otp, Model model,
			RedirectAttributes attributes) {
		String notification = this.customerService.confirmForgetPassword(email, password, Otp);
		if (notification.equals("Thành công") == false) {
			attributes.addAttribute("email", email);
			attributes.addAttribute("password", password);
			attributes.addAttribute("Otp", Otp);

		}

		attributes.addAttribute("notification", notification);
		return "redirect:/forgot-password";
	}
}
