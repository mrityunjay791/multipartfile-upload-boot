package com.mrityunjay.multipartupload.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mrityunjay.multipartupload.DTO.Response;
import com.mrityunjay.multipartupload.service.FileStorageService;

@RestController
public class FileUploadController {
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/upload")
	public Response uploadFile(@RequestParam("file") MultipartFile file) {
		
		String fileName = fileStorageService.storeFile(file);
		
		String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/").path(fileName).toUriString();
		
		return new Response(fileName, downloadUri, file.getContentType(), file.getSize());
	}
	
	@PostMapping("/upload-multiplefile")
	public List<Response> uploadMutipleFiles(@RequestParam("files") MultipartFile files[]) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}
}
