package top.caoxuan.tortoiseserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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
        JSONObject jsonObject;
        if (file.isEmpty()) {
            return "empty file";
        }
        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.warn("上传的文件名为：" + fileName + "后缀名为:" + suffixName);
        File dest = null;
        dest = new File(System.getProperty("user.dir") + "\\public\\files\\" + fileName);
        if (!dest.getParentFile().getParentFile().exists()) {
            dest.getParentFile().getParentFile().mkdir();
        }
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            jsonObject = new JSONObject();
            jsonObject.put("result", "success");
            return jsonObject.toJSONString();
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject = new JSONObject();
            jsonObject.put("result", "failure");
            return jsonObject.toJSONString();
        }
    }

    public String download() {
        return "";
    }


}
