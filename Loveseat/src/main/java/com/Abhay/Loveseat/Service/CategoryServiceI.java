package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.CategoryDto;
import com.Abhay.Loveseat.Model.Category;
import com.Abhay.Loveseat.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public  Optional<Category> findById(long id){
        return categoryRepository.findById(id);
    }
    public  void editCategory( long id,Category category){
      Category updateCategory=null;
      try {
          updateCategory=categoryRepository.findById(id).get();
          updateCategory.setName(category.getName());
          updateCategory.setDescription(category.getDescription());
          updateCategory.setAvailable(category.isAvailable());
      }catch (Exception e){
          e.printStackTrace();
      }
      categoryRepository.save(updateCategory);

    }
    public List<Category>findAll(){
        return  categoryRepository.findByActive();
    }
}
