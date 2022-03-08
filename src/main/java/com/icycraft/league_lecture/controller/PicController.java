package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.*;
import com.icycraft.league_lecture.service.*;
import com.icycraft.league_lecture.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@RestController
@Slf4j
public class PicController {

    @Autowired
    UserService userService;


    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Autowired
    LectureService lectureService;

    @Autowired
    ClazzService clazzService;

    @Autowired
    RecordService recordService;

    @Autowired
    MailService mailService;

    @PostMapping(value = "/upload/file/{imageName}/{userId}")
    public WebResult uploadFile(MultipartFile file, @PathVariable("imageName") String imageName, @PathVariable("userId") long userId) throws Exception {

        try {

            User user = userService.getUser(userId);

            if (user.getName()==null||"".equals(user.getName())){
                return WebResult.ERROR("检测到姓名为空，请重新编辑资料");
            }

            Lecture lastLecture = lectureService.getLastLecture();

            Clazz clazz = clazzService.getClazz(user.getClazzId());

            String filePath = fileUploadPath+File.separator+lastLecture.getName()+File.separator+clazz.getName()+File.separator+user.getName();

            judeDirExists(new File(fileUploadPath+File.separator+lastLecture.getName()));

            judeDirExists(new File(fileUploadPath+File.separator+lastLecture.getName()+File.separator+clazz.getName()));

            judeDirExists(new File(filePath));

            if (imageName.equals("img1")){
                filePath = filePath+File.separator+user.getName()+"学前统计页 1.jpg";
                File dest = new File(filePath);
                file.transferTo(dest);
            }
            if (imageName.equals("img2")){
                filePath = filePath+File.separator+user.getName()+"学前统计页 2.jpg";
                File dest = new File(filePath);
                file.transferTo(dest);
            }
            if (imageName.equals("img3")){

                filePath = filePath+File.separator+user.getName()+"信息页.jpg";
                File dest = new File(filePath);
                file.transferTo(dest);

            }
            if (imageName.equals("img4")){
                filePath = filePath+File.separator+user.getName()+"完成页.jpg";
                File dest = new File(filePath);
                file.transferTo(dest);

            }
            if (imageName.equals("img5")){
                filePath = filePath+File.separator+user.getName()+"转发页.jpg";
                File dest = new File(filePath);
                file.transferTo(dest);
            }


            return WebResult.SUCCESS(imageName);

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            return WebResult.ERROR(e.getMessage());

        }


    }

    @RequestMapping("/mail/{userId}")
    public WebResult mail(@PathVariable("userId") long userId, HttpServletRequest request, HttpServletResponse response){
        try {

            User user = userService.getUser(userId);

            Lecture lastLecture = lectureService.getLastLecture();

            Clazz clazz = clazzService.getClazz(user.getClazzId());

            ArrayList<String> list = new ArrayList<>();

            list.add(fileUploadPath+File.separator+lastLecture.getName()+File.separator+clazz.getName());

            String zipPath =  FileUtils.downloadAllAttachment(clazz.getName(),list,fileUploadPath,request,response);

            mailService.sendAttachmentsMail(user.getMail(),lastLecture.getName()+clazz.getName(),clazz.getName(),zipPath);

            return WebResult.SUCCESS(null);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping("/delete/{userId}")
    @Transactional
    public WebResult deleteFile(@PathVariable("userId") long userId){
        try {

            User user = userService.getUser(userId);

            Lecture lastLecture = lectureService.getLastLecture();

            Clazz clazz = clazzService.getClazz(user.getClazzId());

            String path =  fileUploadPath+File.separator+lastLecture.getName()+File.separator+clazz.getName()+File.separator+user.getName();

            FileUtils.deleteFile(new File(path));

            recordService.deleteLastRecordByUserId(userId);

            return WebResult.SUCCESS(null);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }


    @GetMapping("/download/{userId}")
    public byte[] download(@PathVariable("userId") long userId, HttpServletRequest request, HttpServletResponse response) throws Exception{


        User user = userService.getUser(userId);

        Lecture lastLecture = lectureService.getLastLecture();

        Clazz clazz = clazzService.getClazz(user.getClazzId());

        ArrayList<String> list = new ArrayList<>();

        list.add(fileUploadPath+File.separator+lastLecture.getName()+File.separator+clazz.getName()+File.separator+user.getName());

        String zipPath =  FileUtils.downloadAllAttachment(user.getName()+lastLecture.getName(),list,fileUploadPath,request,response);

        File file = new File(zipPath);

        // 获取文件名
        String filename = file.getName();
        //通过流把文件内容写入到客户端
        InputStream fis = new BufferedInputStream(new FileInputStream(zipPath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(),"ISO-8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();

        return buffer;

    }



    // 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
         //do nothing...........
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }
}