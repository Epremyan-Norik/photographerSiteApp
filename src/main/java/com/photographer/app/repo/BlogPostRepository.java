package com.photographer.app.repo;

import com.photographer.app.models.BlogPost;
import com.photographer.app.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
    Set<BlogPost> findAllByAuthor(User author);

}
