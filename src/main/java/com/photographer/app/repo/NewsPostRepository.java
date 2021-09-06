package com.photographer.app.repo;

import com.photographer.app.models.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsPostRepository extends JpaRepository<NewsPost, Long> {
}
