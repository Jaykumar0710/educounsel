package com.jk.educounsel.service;

import com.jk.educounsel.model.User;
import com.jk.educounsel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public List<User>getAllUser(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id, Long currentUserId) {
        if (!id.equals(currentUserId)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Admin cannot delete themselves");
        }
    }


    public User findByUsername(String username){
        return  userRepository.findByUsername(username);
    }

    public  User findByEmail(String email){
        return  userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return  userRepository.findById(id);
    }

    public Page<User> searchAndFilter(String keyword, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if ((keyword == null || keyword.isBlank()) && (role == null || role.isBlank())) {
            return userRepository.findAll(pageable);
        }

        if ((keyword == null || keyword.isBlank()) && role != null) {
            return userRepository.findByRole(role, pageable);
        }

        if (role == null || role.isBlank()) {
            return userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
        }

        // Both role and keyword present
        return userRepository.findByRoleAndUsernameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCase(
                role, keyword, role, keyword, pageable);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public Object countUsers() {
        return  userRepository.count();
    }
}
