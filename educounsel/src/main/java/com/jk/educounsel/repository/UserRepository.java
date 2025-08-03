package com.jk.educounsel.repository;

import com.jk.educounsel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);

    Page<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String usernameKeyword, String emailKeyword, Pageable pageable);

    Page<User> findByRole(String role, Pageable pageable);

    Page<User> findByRoleAndUsernameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCase(
            String role1, String usernameKeyword,
            String role2, String emailKeyword,
            Pageable pageable);
}
