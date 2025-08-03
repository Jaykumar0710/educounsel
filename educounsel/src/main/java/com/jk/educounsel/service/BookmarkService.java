package com.jk.educounsel.service;

import com.jk.educounsel.model.Bookmark;
import com.jk.educounsel.model.User;
import com.jk.educounsel.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public List<Bookmark> getBookmarks(User student){
        return bookmarkRepository.findByStudent(student);
    }

    public void addBookmark(Bookmark bookmark){
        bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(Long id){
        bookmarkRepository.deleteById(id);
    }
}
