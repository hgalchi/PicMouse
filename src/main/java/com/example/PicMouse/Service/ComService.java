package com.example.PicMouse.Service;

import com.example.PicMouse.Entity.Community;
import com.example.PicMouse.Repository.ComRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComService {
    final ComRepository comRepository;

    public void create_comm() {
        Community community = Community.builder()
                .dirId(Long.parseLong("1"))
                .userId(Long.parseLong("2"))
                .like(0)
                .build();

        Community c = new Community();

        //comRepository.save(c);

    }
}
