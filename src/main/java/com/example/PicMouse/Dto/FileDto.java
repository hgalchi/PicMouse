package com.example.PicMouse.Dto;

import com.example.PicMouse.Entity.DirectoryEntity;
import lombok.*;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {
    private String picture;
    private String word;
    private String kor;
    private String content;
    private String trans;

}
