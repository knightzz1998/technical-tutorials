package cn.knightzz.controller.upload;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 王天赐
 * @title: FileUploadController
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-03-23 17:51
 */
@Controller
@RequestMapping("upload")
public class FileUploadController {

    @PostMapping("/uploadByOne")
    public String uploadByOne(@RequestParam("file") MultipartFile file) {
        try (InputStream is = file.getInputStream(); ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            IOUtils.copy(is, os);
            byte[] bytes = os.toByteArray();
        } catch (IOException e) {
            System.out.println("error... ");
        }
        return "views/success/index";
    }
}
