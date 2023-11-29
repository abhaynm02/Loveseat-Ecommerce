package com.Abhay.Loveseat.Repository;

import com.Abhay.Loveseat.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Modifying
    @Query("UPDATE Category c SET c.isAvailable = :status WHERE c.id = :categoryId")
    void updateStatus(@Param("categoryId") Long categoryId, @Param("status") boolean status);

}
