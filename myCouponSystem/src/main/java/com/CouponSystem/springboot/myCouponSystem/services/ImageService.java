package com.CouponSystem.springboot.myCouponSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.CouponSystem.springboot.myCouponSystem.entities.Image;
import com.CouponSystem.springboot.myCouponSystem.enums.ErrorType;
import com.CouponSystem.springboot.myCouponSystem.enums.RepoType;
import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.repositories.ImageRepository;
import com.CouponSystem.springboot.myCouponSystem.utils.JpaUtils;

@Service
public class ImageService {
	
	@Autowired
	private ImageRepository imageRepo;
	
	@Autowired
	private JpaUtils jpaUtils;
	
	public Image storeImage(MultipartFile image) throws ApplicationException {
	        // Normalize file name
	        String imageName = StringUtils.cleanPath(image.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(imageName.contains("..")) {
	            	throw new ApplicationException(ErrorType.IMAGE_NAME_INVALID,
	    					ErrorType.IMAGE_NAME_INVALID.getExternalMessage());
	            }

	            Image dbImage = new Image(imageName, image.getContentType(), image.getBytes());
	    
	            return this.imageRepo.saveAndFlush(dbImage);
	        } catch (Exception e) {
	        	throw new ApplicationException(ErrorType.IMAGE_FILE_NOT_STORED,
    					ErrorType.IMAGE_FILE_NOT_STORED.getExternalMessage());
	        }
	    }

	    public Image getImageByID(long imageID) throws ApplicationException {
	    	this.jpaUtils.validateResourceIsPresent(this.imageRepo, imageID, RepoType.IMAGE);
	        return this.imageRepo.findById(imageID).get();
	    }

}
