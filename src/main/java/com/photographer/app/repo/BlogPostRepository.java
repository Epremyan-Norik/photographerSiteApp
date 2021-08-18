package com.photographer.app.repo;

import com.photographer.app.models.BlogPost;
import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {

}
