package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.CategoryDto;
import com.Abhay.Loveseat.Model.Category;
import com.Abhay.Loveseat.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceI implements CategoryService{
    @Autowired
   private CategoryRepository categoryRepository;
    @Override
    public void saveCategory(CategoryDto categoryDto) {
        Category category=new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setAvailable(true);
        categoryRepository.save(category);

    }
    public Page<Category>AllCategory(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }
    @Transactional
    public  void ListOrUnList(long id,boolean status){
        categoryRepository.updateStatus(id,status);
    }
}
