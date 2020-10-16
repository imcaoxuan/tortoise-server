package top.caoxuan.tortoiseserver.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import top.caoxuan.tortoiseserver.config.WebSecurityConfig;
import top.caoxuan.tortoiseserver.entity.Message;

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
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
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
            jsonObject.put("fileName", fileName);
            Message message = new Message(1, userId, "", "<a href=\"/files/" + fileName +"\">" +fileName + "</a>", WebSecurityConfig.uuid.toString());
            //向所有人广播。需要客户端自己判断该消息是否来自自己
            for (WebSocketSession session:
                 WebSocketHandler.SESSION_LIST) {
                session.sendMessage(new TextMessage(message.toJSONString()));
            }
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
