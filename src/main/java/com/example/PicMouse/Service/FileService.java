package com.example.PicMouse.Service;


import com.example.PicMouse.ChatGpt.ChatGptService;
import com.example.PicMouse.ChatGpt.QuestionRequest;
import com.example.PicMouse.Entity.FileEntity;
import com.example.PicMouse.Repository.ComRepository;
import com.example.PicMouse.Repository.FileRepository;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {

    final FileRepository fileRepository;
    final ComRepository directoryRepository;

    public  Object create(String filePath,String Ndir) throws IOException {


        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();


            //이미지, 결과값 db에 저장하기

            ArrayList<Object> list=new ArrayList<>();


            responses.get(0).getLabelAnnotationsList().get(0)
                    .getAllFields().forEach((k,v)-> list.add(v));


            String word = list.get(1).toString();

            ChatGptService chatGptService = new ChatGptService();
            QuestionRequest question = new QuestionRequest(word);
            Map<String, String> map = new HashMap<>();

            map=chatGptService.askQuestion(question);

            FileEntity fileEntity = FileEntity.builder()
                    .picture(filePath)
                    .word(word)
                    .kor(map.get("번역"))
                    .content(map.get("\"영어"))
                    .trans(map.get("한글"))
                    .dir(directoryRepository.findById(Long.parseLong(Ndir)).get())
                    .build();


            fileRepository.save(fileEntity);

            return responses;


        }
    }

    public void delete(String Id) {
        fileRepository.deleteById(Long.parseLong(Id));
    }


    public void MoveDir(long Id, long dirId) {
        Optional<FileEntity> entity = fileRepository.findById(Id);

        entity.ifPresent(t-> {
            t.setDir(directoryRepository.findById(dirId).get());
            this.fileRepository.save(t);
        });


    }
}