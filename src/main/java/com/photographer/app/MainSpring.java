package com.photographer.app;

import com.photographer.app.modelsNew.User;
import com.photographer.app.repo.Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class MainSpring {


    public static void main(String[] args) {
        Repository repository = new Repository();
        User user = repository.findUserByUsername("admin123");
        System.out.println(user);
        SpringApplication.run(MainSpring.class, args);
    }

}
