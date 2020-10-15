package top.caoxuan.tortoiseserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author CX
 * @date 2020/10/14 12:40
 * @version 1.0
 */
@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping("/transfer")
    public String transfer() {
        return "transfer";
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "empty file";
        }
        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.warn("上传的文件名为：" + fileName + "后缀名为:" + suffixName);
        String filePath = "D:\\CaoXuan\\Public\\files\\";
        String path = filePath + fileName;
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    public String download() {
        return "";
    }


}
