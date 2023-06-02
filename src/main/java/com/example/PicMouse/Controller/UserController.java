package com.example.PicMouse.Controller;

import com.example.PicMouse.ChatGpt.ChatGptService;
import com.example.PicMouse.ChatGpt.QuestionRequest;
import com.example.PicMouse.Dto.FileDto;
import com.example.PicMouse.Entity.FileEntity;
import com.example.PicMouse.Repository.FileRepository;
import com.example.PicMouse.Service.DirService;
import com.example.PicMouse.Service.FileService;
import com.example.PicMouse.Enum.Constant;
import com.example.PicMouse.Oauth.GoogleOAuth;
import com.example.PicMouse.Oauth.GoogleUser;
import com.example.PicMouse.Oauth.OAuthService;

import com.example.PicMouse.google.SessionUser;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final OAuthService oAuthService;
    private final GoogleOAuth googleOAuth;
    private final FileService fileService;
    private final DirService dirService;


    @RequestMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String index(Model model) {

        return "login_success";
    }


    //소셜미디어 로그인
    @GetMapping("/auth/{socialLoginType}")
    public void SocialLoginTypeRedirect(@PathVariable(name = "socialLoginType") String SocialLoginPath) throws IOException {

        System.out.println("SocialLoginPath=" + SocialLoginPath);
        Constant.SocialLoginType socialLoginType = Constant.SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.request(socialLoginType);
    }

    // 소셜미디어 callback
    @ResponseBody
    @GetMapping(value = "/auth/{socailLoginType}/callback")
    public void callback(
            @PathVariable(name = "socailLoginType") String SocialLoginPath,
            @RequestParam(name = "code") String code) throws Exception {
        System.out.println("소셜로그인 api서버로부터 받은 code: " + code);
        Constant.SocialLoginType socialLoginType1 = Constant.SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        GoogleUser getSocialOAuthRes = oAuthService.oAuthLogin(socialLoginType1, code);
        System.out.println(getSocialOAuthRes);
    }


    // 파일 생성
    @PostMapping("/create_file/{dirId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String file(@PathVariable("dirId") String dirId,@RequestPart(value = "file",required=false) MultipartFile file) throws IOException {

        String originalfileName = file.getOriginalFilename();
        File dest = new File("C:\\Springboot\\PicMouse\\src\\main\\resources\\static\\" + originalfileName);
        file.transferTo(dest);


        Object result = fileService.create(dest.getPath(),dirId);

        return "create_file";
    }

    //파일 삭제
    @PostMapping("/delete_file/{Id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String DeleteFile(@PathVariable("Id")String Id){
        fileService.delete(Id);
        return "Delete_file";
    }

    //파일 이동
    @PostMapping("/Move_dir/{Id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String MoveDir(@PathVariable("Id") String Id, @RequestBody String dirId) {
        fileService.MoveDir(Long.parseLong(Id), Long.parseLong(dirId));

        return "move_dir";

    }


    //디렉토리 생성
    @PostMapping("/create_dir")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String directory(@RequestBody String title) {

        dirService.createdir("동물");

        return "Create_dir";
    }

    // 디렉토리 이름 변경
    @PostMapping("/ChangeName_dir/{Id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String NameChangeFile(@PathVariable("Id") String Id, @RequestBody String title) {
        dirService.NameChangeDir(Long.parseLong(Id), title);

        return "ChangeName_dir";

    }


    //디렉토리 모든 파일 보기
    @GetMapping("/FindAllFile/{Id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<FileSystemResource> List_file(@PathVariable("Id") String Id) throws IOException {
        List<FileDto> list= dirService.FindAllFile(Long.parseLong(Id));

        List<Resource> resources = new ArrayList<>();
        List<File> imageFiles = new ArrayList<>();
        list.stream().map(l -> imageFiles.add(new File(l.getPicture())));

        for (File imageFile : imageFiles) {
            Resource resource = new FileSystemResource(imageFile);
            resources.add(resource);
        }


        System.out.println(list.get(0).getPicture());
        String filePath=list.get(0).getPicture();
        String filePath2 = list.get(1).getPicture();
        List<FileSystemResource> PathList = new ArrayList<>();

        PathList.add(new FileSystemResource(filePath));
        PathList.add(new FileSystemResource(filePath2));

        HttpHeaders header = new HttpHeaders();
        Path path = Paths.get(filePath);
        Path path2 = Paths.get(filePath2);


        header.add("Content-Type", Files.probeContentType(path));
        header.add("content-Type", Files.probeContentType(path2));

        return new ResponseEntity<FileSystemResource>(new FileSystemResource(filePath),header, HttpStatus.OK);
    }


    //디렉토리 복사
    @PostMapping("/Copy_Dir/{Id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void CopyDir(@PathVariable("Id") String Id){
        dirService.copyDir(Long.parseLong(Id));

    }





    @GetMapping("/imagesend")
    public ResponseEntity<Resource> detected(@RequestParam int Ndir) throws IOException {

        String filePath = "C:\\Springboot\\PicMouse\\src\\main\\resources\\static\\wakeupcat.jpg";
        FileSystemResource resource = new FileSystemResource(filePath);

        FileEntity file1 = new FileEntity();
        file1.setWord("monkety");
        file1.setPicture(filePath);
        fileRepository.save(file1);

        HttpHeaders header = new HttpHeaders();
        Path path = Paths.get(filePath);
        header.add("Content-Type", Files.probeContentType(path));
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

    //test - 파일 전송


    @Autowired
    FileRepository fileRepository;
    @PostMapping("/upload")
    public void updateImages(@RequestBody MultipartFile files) {
        FileEntity file = new FileEntity();

        String fileUrl = "C:\\Springboot\\PicMouse\\src\\main\\resources\\static";

        file.setPicture(fileUrl);

        fileRepository.save(file);

    }

    @Autowired
    ChatGptService chatGptService;

    @PostMapping("/question")
    public void sendQuestion(@RequestBody String word) {
        QuestionRequest question = new QuestionRequest(word);

        chatGptService.askQuestion(question);
    }
}

