package com.photographer.app.controllers;


import com.photographer.app.model.Image;
import com.photographer.app.model.User;
import com.photographer.app.repo.Repository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class UploadFileController {

    @Autowired
    Repository repository;


    @GetMapping("/uploadUserPhoto")
    public String getUploadFotoPage(

            Model model
            ) {
        model.addAttribute("title", "Загруженное изображение");
        return "uploadFile";
    }
    @PostMapping("/uploadUserPhoto")
    public String uploadUserPhoto(
            @AuthenticationPrincipal User user,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        String result = "redirect:/profile";
        List<Image> existImage = repository.getImagesByEnId(user.getId());
        if(file.isEmpty()){
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data:image/jpeg;base64,");
        stringBuilder.append(StringUtils.newStringUtf8(Base64.encodeBase64(file.getBytes(), false)));

        if(existImage.isEmpty()) {

            Image image = new Image();
            image.setEn_id(user.getId());
            image.setImagebyte64(stringBuilder.toString());
            image.setId(repository.insertImage(image));
            //model.addAttribute("title", "Загруженное изображение");
            //model.addAttribute("imageData", image.getImagebyte64());
        } else{
            existImage.get(0).setImagebyte64(stringBuilder.toString());
            repository.updateImage(existImage.get(0));

        }


        return result;
    }


}
