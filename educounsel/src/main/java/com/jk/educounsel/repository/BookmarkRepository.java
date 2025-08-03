package com.jk.educounsel.repository;

import com.jk.educounsel.model.Bookmark;
import com.jk.educounsel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByStudent(User student);
}

