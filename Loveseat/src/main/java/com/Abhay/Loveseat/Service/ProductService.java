package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.ProductsDto;
import com.Abhay.Loveseat.Model.Category;
import com.Abhay.Loveseat.Model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Products save(ProductsDto productsDto, MultipartFile multipartFile1,
                  MultipartFile multipartFile2, MultipartFile multipartFile3);
    Page<Products> findAll(Pageable pageable);
    Products updateProduct(ProductsDto productsDto);
    void  listOrUnList(long id,boolean status);
    Optional<Products>findById(long id);
    void updateProduct(long id,Products products , MultipartFile multipartFile1,MultipartFile multipartFile2,
                       MultipartFile multipartFile3);
}
