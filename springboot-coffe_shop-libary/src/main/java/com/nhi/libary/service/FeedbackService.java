package com.nhi.libary.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhi.libary.model.Customer;
import com.nhi.libary.model.FeedBack;
import com.nhi.libary.repository.CustomerRepository;
import com.nhi.libary.repository.FeedBackRepository;

@Service
public class FeedbackService {

	@Autowired
	private FeedBackRepository feedBackRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<FeedBack> findAllFeedBack(int productId, String email) {
		if(email != null) {
			Customer customer= this.customerRepository.findByEmail(email);
			List<FeedBack> findByCustomerAndProductSortDate = this.feedBackRepository.findByCustomerAndProduct(customer.getId(), productId);
			List<FeedBack> findAllByProductSortDate = this.feedBackRepository.findAllSortByProduct(productId, customer.getId());
			
			
			if(findAllByProductSortDate != null && findAllByProductSortDate.isEmpty() == false) {
				for (FeedBack feedBack : findAllByProductSortDate) {
					findByCustomerAndProductSortDate.add(feedBack);
				}
			}
			
			for (FeedBack feedBack : findByCustomerAndProductSortDate) {
				if(feedBack.getImage() != null) {
					List<String> imageTemp = new ArrayList<>();
					for (String image : feedBack.getImage().split("conHinhNua")) {
						imageTemp.add(image);
					}
					
					feedBack.setImageTemp(imageTemp);
				}
			}
			
			return findByCustomerAndProductSortDate;
		}else {
			
			List<FeedBack> findByCustomerAndProductSortDate = new ArrayList<>();
			List<FeedBack> findAllByProductSortDate = this.feedBackRepository.findAllSortByProduct(productId, 0);
			
			
			if(findAllByProductSortDate != null && findAllByProductSortDate.isEmpty() == false) {
				for (FeedBack feedBack : findAllByProductSortDate) {
					findByCustomerAndProductSortDate.add(feedBack);
				}
			}
			
			for (FeedBack feedBack : findByCustomerAndProductSortDate) {
				if(feedBack.getImage() != null) {
					List<String> imageTemp = new ArrayList<>();
					for (String image : feedBack.getImage().split("conHinhNua")) {
						imageTemp.add(image);
					}
					
					feedBack.setImageTemp(imageTemp);
				}
			}
			
			return findByCustomerAndProductSortDate;
		}

		
	}
	
	public void addFeedback(FeedBack feedBack, MultipartFile hinhAnh1, MultipartFile hinhAnh2) throws IOException {
		if(!hinhAnh1.isEmpty() ) {
			feedBack.setImage(Base64.getEncoder().encodeToString(hinhAnh1.getBytes()));
		}
		
		if(!hinhAnh2.isEmpty()) {
			if(feedBack.getImage() != null) {
				feedBack.setImage(feedBack.getImage() + "conHinhNua" + Base64.getEncoder().encodeToString(hinhAnh2.getBytes()));
			}else {
				feedBack.setImage(Base64.getEncoder().encodeToString(hinhAnh2.getBytes()));
			}
		}
		
		feedBack.setFeedBack_date(LocalDate.now());
		this.feedBackRepository.save(feedBack);
		
	}
}
