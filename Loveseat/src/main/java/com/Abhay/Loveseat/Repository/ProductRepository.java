package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Products,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Products p SET p.isAvailable = :status WHERE p.id = :productId")
    void updateStatus(@Param("productId") Long productId, @Param("status") boolean status);
    @Query("SELECT  p FROM Products p WHERE p.name LIKE ?1%")
    Page<Products> searchProducts(Pageable pageable, @Param("searchKey") String searchKey);
    @Query("SELECT p FROM Products p WHERE p.category.id = :searchId")
    Page<Products> filterByCategory(Pageable pageable,@Param("searchId") Long categoryId);
}
