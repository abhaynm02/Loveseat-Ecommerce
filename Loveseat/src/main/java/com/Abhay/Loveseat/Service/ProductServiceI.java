package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Dto.ProductsDto;
import com.Abhay.Loveseat.ImageUtil.ImageUpload;
import com.Abhay.Loveseat.Model.Products;
import com.Abhay.Loveseat.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceI implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
   private ImageUpload imageUpload;
    @Override
    public Products save(ProductsDto productsDto, MultipartFile multipartFile1,MultipartFile multipartFile2,
                     MultipartFile multipartFile3) {


            Products products=new Products();
            products.setName(productsDto.getName());
            products.setDescription(productsDto.getDescription());
            products.setPrice(productsDto.getPrice());
            products.setSellingPrice(productsDto.getSellingPrice());
            products.setQuantity(productsDto.getQuantity());
            products.setStock(productsDto.getStock());
            products.setAvailable(true);
            products.setCategory(productsDto.getCategory_id());
            imageUpload.uploadImage(multipartFile1);
            products.setFrontImage(StringUtils.cleanPath(multipartFile1.getOriginalFilename()));
            imageUpload.uploadImage(multipartFile2);
            products.setSideImage(StringUtils.cleanPath(multipartFile2.getOriginalFilename()));
            imageUpload.uploadImage(multipartFile3);
            products.setTopImage(StringUtils.cleanPath(multipartFile3.getOriginalFilename()));


            return productRepository.save(products);



    }

    @Override
    public Page<Products> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Products updateProduct(ProductsDto productsDto) {
        return null;
    }

    @Override
    public void listOrUnList(long id, boolean status) {
        productRepository.updateStatus(id,status);
    }

    @Override
    public Optional<Products> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void updateProduct(long id, Products products , MultipartFile multipartFile1,MultipartFile multipartFile2,
                              MultipartFile multipartFile3) {
        Products productEdit=null;
        try {
            productEdit=findById(id).get();
            productEdit.setName(products.getName());
            productEdit.setCategory(products.getCategory());
            productEdit.setPrice(products.getPrice());
            productEdit.setSellingPrice(products.getSellingPrice());
            productEdit.setQuantity(products.getQuantity());
            productEdit.setStock(products.getStock());
            productEdit.setDescription(products.getDescription());

            if (!multipartFile1.isEmpty()){
                imageUpload.uploadImage(multipartFile1);
                productEdit.setFrontImage(StringUtils.cleanPath(multipartFile1.getOriginalFilename()));
            }
            if (!multipartFile2.isEmpty()){
                imageUpload.uploadImage(multipartFile2);
                productEdit.setSideImage(StringUtils.cleanPath(multipartFile2.getOriginalFilename()));
            }
            if (!multipartFile3.isEmpty()) {
                imageUpload.uploadImage(multipartFile3);
                productEdit.setTopImage(StringUtils.cleanPath(multipartFile3.getOriginalFilename()));
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        productRepository.save(productEdit);
    }
    public Page<Products> findAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

}
