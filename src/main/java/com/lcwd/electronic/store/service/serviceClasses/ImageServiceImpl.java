package com.lcwd.electronic.store.service.serviceClasses;

import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    Logger logger= LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        logger.info("Originale FileName {}",originalFilename);
        String fileName= UUID.randomUUID().toString();
        //String extenstion=originalFilename.substring(originalFilename.lastIndexOf("."));
        String extenstion=originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtenstion=fileName+extenstion;
        String fullPathWithFileName=path+ File.separator+fileNameWithExtenstion;

        if(extenstion.equalsIgnoreCase(".png") || extenstion.equalsIgnoreCase(".jpg") || extenstion.equalsIgnoreCase(".jpeg")){

            File folder=new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
           return fileNameWithExtenstion;
        }
        else{
            throw new  BadApiRequest("FileName with this " + extenstion+" is not supported. Please upload .png or .jpg formate");

        }
//        String originalFilename = file.getOriginalFilename();
//        String extenstion=originalFilename.substring(originalFilename.lastIndexOf("."));
//        if(extenstion.equalsIgnoreCase(".png") || extenstion.equalsIgnoreCase(".jpeg") || originalFilename.equalsIgnoreCase(".jpg")){
//            File folder=new File(path);
//            if(!folder.exists()){
//                folder.mkdirs();
//            }
//            Files.copy(file.getInputStream(),Paths.get(originalFilename));
//            return  originalFilename;
//        }
//        else {
//            throw  new BadApiRequest();
//        }


    }

    @Override
    public InputStream getReSource(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
