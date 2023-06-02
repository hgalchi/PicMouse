package com.example.PicMouse.Repository;

import com.example.PicMouse.Entity.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryEntity,Long> {


}
