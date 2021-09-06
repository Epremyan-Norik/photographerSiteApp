package com.photographer.app.mapper;

import java.util.List;

public interface BlogTextMapper {

    BlogText getBlogTextById(long id);

    List<BlogText> getAllFromBlogText();

    int insertBlogText(BlogText blogText);

    int updateBlogText(BlogText blogText);

    int deleteBlogTextById(long id);
}
