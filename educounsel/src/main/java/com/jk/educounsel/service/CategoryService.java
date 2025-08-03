package com.jk.educounsel.service;

import com.jk.educounsel.model.Category;
import com.jk.educounsel.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category>getCategoryById(String id){
        return categoryRepository.findById(id);
    }

    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    public  void deleteCategory( String code){
        categoryRepository.deleteById(code);
    }

    public Page<Category>findAll(Pageable pageable){
        return  categoryRepository.findAll(pageable);
    }

    public Page<Category>findByCategoryCodeContainingIgnoreCase(String keyword, Pageable pageable){
        return  categoryRepository.findByCategoryCodeContainingIgnoreCase(keyword, pageable);
    }


    public Object countCategories() {
        return categoryRepository.count();
    }
}
