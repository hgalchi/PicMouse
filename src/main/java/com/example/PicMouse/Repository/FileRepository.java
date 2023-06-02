package com.example.PicMouse.Repository;

import com.example.PicMouse.Dto.FileDto;
import com.example.PicMouse.Entity.DirectoryEntity;
import com.example.PicMouse.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {
    Optional<FileEntity> findById(Long id);

    List<FileEntity> findAllByDir(DirectoryEntity directory);
}
