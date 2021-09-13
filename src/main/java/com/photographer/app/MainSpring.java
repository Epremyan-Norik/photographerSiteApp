package com.photographer.app;

import com.photographer.app.modelsNew.Album;
import com.photographer.app.modelsNew.Product;
import com.photographer.app.modelsNew.User;
import com.photographer.app.repo.Repository;
import org.jboss.jandex.PrimitiveType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class MainSpring {


    public static void main(String[] args) {
        Repository repository = new Repository();
        List<Album> albums = repository.getAlbums();
        albums.forEach(System.out::println);

        SpringApplication.run(MainSpring.class, args);
    }

}
