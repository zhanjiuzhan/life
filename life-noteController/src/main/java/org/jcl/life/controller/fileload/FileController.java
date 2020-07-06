package org.jcl.life.controller.fileload;

import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * @author chenglei
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws FileNotFoundException {
        String classPath = ResourceUtils.getURL("classpath:").getPath();
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String filePath = classPath + "view/file/upload/";
        fileName = filePath + UUID.randomUUID() + fileName;
        File dest = new File(fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    public static void main(String[] args) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath() + "view" +
                "/file";
        File file = new File(path);
        System.out.println(file.isDirectory());
        System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());

    }
}
