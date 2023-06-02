package com.example.PicMouse.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Directory")
@Builder
public class DirectoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;


    //파일 리스트
    /*@OneToMany(mappedBy = "directoryEntity")
    private List<FileEntity> fileList = new ArrayList<>();*/

    String title;
}
