package com.example.PicMouse.Service;

import com.example.PicMouse.Dto.FileDto;
import com.example.PicMouse.Entity.DirectoryEntity;
import com.example.PicMouse.Repository.ComRepository;
import com.example.PicMouse.Repository.FileRepository;
import com.example.PicMouse.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirService {

    final ComRepository directoryRepository;
    final FileRepository fileRepository;
    final UserRepository userRepository;
    final EntityManager entityManager;


    public void createdir(String title) {

        DirectoryEntity directory = DirectoryEntity.builder()
                .userId(userRepository.findById(Long.parseLong("1")).get())
                .title(title)
                .build();

        directoryRepository.save(directory);

    }

    //디렉토리 이름 변경
    public void NameChangeDir(Long id, String title) {

        Optional<DirectoryEntity> entity = directoryRepository.findById(id);

        entity.ifPresent(t->{
            t.setTitle(title);
            this.directoryRepository.save(t);
        });
    }

    //현재 디렉토리 내에 있는 모든 파일 반환
    public List<FileDto> FindAllFile(long id) {
        Optional<DirectoryEntity> directory = directoryRepository.findById(id);

        List<FileDto> fileList =
                fileRepository.findAllByDir(directory.get()).stream().map(fileEntity -> new FileDto(fileEntity.getPicture(),fileEntity.getWord(),fileEntity.getKor(),fileEntity.getContent(),fileEntity.getTrans()
                )).toList();
        return fileList;
    }
    @Transactional
    public void copyDir(long id) {

        DirectoryEntity originalEntity = entityManager.find(DirectoryEntity.class, id);

        entityManager.detach(originalEntity);

        DirectoryEntity copiedEntity = new DirectoryEntity();

        copiedEntity.setUserId(userRepository.findById(Long.parseLong("2")).get());
        copiedEntity.setTitle(originalEntity.getTitle());

        entityManager.persist(copiedEntity);

    }

    //사용자가 저장한 모든 디렉토리 반환


}
