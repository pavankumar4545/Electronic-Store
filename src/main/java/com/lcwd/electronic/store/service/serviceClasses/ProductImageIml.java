package com.lcwd.electronic.store.service.serviceClasses;

import com.lcwd.electronic.store.service.ProductImage;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ProductImageIml implements ProductImage {
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String randomName = UUID.randomUUID().toString();
        String extenstion=originalFilename.substring(originalFilename.lastIndexOf("."));
        String randomNameWithExtenstion = randomName + extenstion;
        String fullPath=path+ File.separator+randomNameWithExtenstion;

        if(extenstion.equalsIgnoreCase(".png") || extenstion.equalsIgnoreCase(".jpg") || extenstion.equalsIgnoreCase(".jpeg")){
            File folder=new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullPath));
            return randomNameWithExtenstion;
        }
        else {
            throw new BadRequestException("File Name with this "+extenstion+" extenstion is not valid ");
        }

    }

    @Override
    public InputStream viewImage(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
