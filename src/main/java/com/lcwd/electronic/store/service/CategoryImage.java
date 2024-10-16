package com.lcwd.electronic.store.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface CategoryImage {


    public String uploadImage(MultipartFile file, String path) throws IOException;

    public InputStream viewImage(String path,String name) throws FileNotFoundException;

}
