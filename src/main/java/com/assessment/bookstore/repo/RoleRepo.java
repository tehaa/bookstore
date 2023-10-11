package com.assessment.bookstore.repo;

import com.assessment.bookstore.entity.Role;
import com.assessment.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
