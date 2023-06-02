package com.example.PicMouse.Repository;

import com.example.PicMouse.Entity.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComRepository extends JpaRepository<DirectoryEntity,Long> {

    @Modifying
    @Query("update Community p set p.like = p.like + 1 where p.id = :id")
    int updateViews(@Param("id") Long id);


}
