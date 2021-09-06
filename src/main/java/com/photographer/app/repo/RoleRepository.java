package com.photographer.app.repo;

import com.photographer.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<User, Long> {
}
