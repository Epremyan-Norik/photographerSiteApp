package com.photographer.app.repo;

import com.photographer.app.models.BlogPostOld;
import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPostOld, Long> {

}
