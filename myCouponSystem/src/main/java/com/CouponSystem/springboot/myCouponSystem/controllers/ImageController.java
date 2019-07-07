package com.CouponSystem.springboot.myCouponSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.CouponSystem.springboot.myCouponSystem.exceptions.ApplicationException;
import com.CouponSystem.springboot.myCouponSystem.services.ImageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/images")
public class ImageController {

	// Attribute

	@Autowired
	private ImageService imageService;

	// Methods

	@PostMapping
	public Long uploadImage(@RequestParam("image") MultipartFile image) throws ApplicationException {
		return this.imageService.storeImage(image).getImageID();
	}

//	@GetMapping("/downloadImage/{imageID}")
//	public void downloadImage(@PathVariable long imageID) throws ApplicationException {
//		// Load file from database
//		Image dbImage = this.imageService.getImage(imageID);
//
////		return ResponseEntity.ok();
////				
////                .contentType(MediaType.parseMediaType(dbImage.getFileType()))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; imageName=\"" + dbImage.getImageName() + "\"")
////                .body(new ByteArrayResource(dbImage.getImage()));
//	}

}
