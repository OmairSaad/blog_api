package com.omair.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class fileService {
    public String uploadFile(MultipartFile file) throws IOException{
        
        // Create path to store
        String path =new ClassPathResource("static/images").getFile().getAbsolutePath();

        //fileName;
         String name = file.getOriginalFilename();
         String random = UUID.randomUUID().toString();
         String content = file.getContentType();

         if(content==null || !content.equals("image/jpeg") || content.equals("image/png")){
           throw new IllegalArgumentException("JPG or Png allowed");
         }
         random = random.concat(name.substring(name.lastIndexOf(".")));
         String filePath = path+File.separator+random;      //images/abc.png

         //file copy
         Files.copy(file.getInputStream(), Paths.get(filePath));
        return random;
    }
}
