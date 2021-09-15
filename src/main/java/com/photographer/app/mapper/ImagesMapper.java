package com.photographer.app.mapper;

import com.photographer.app.model.Image;

import java.util.List;

public interface ImagesMapper {
    int insertImage(Image image);
    List<Image> getImagesByEnId(long id);
    int deleteImageById(long id);
    int updateImage(Image image);
}
