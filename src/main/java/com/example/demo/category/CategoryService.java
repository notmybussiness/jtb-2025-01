package com.example.demo.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    //게시판 카테고리 조회
    public List<Category> getList(){
        return this.categoryRepository.findAll();
    }
    //게시판 카테고리 생성
    public void createCategory(String category){
        Category category1 = new Category();
        category1.setCategory(category);
        this.categoryRepository.save(category1);
    }


}
