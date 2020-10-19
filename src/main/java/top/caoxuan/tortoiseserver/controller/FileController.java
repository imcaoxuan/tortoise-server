package top.caoxuan.tortoiseserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import top.caoxuan.tortoiseserver.config.WebSecurityConfig;
import top.caoxuan.tortoiseserver.entity.Message;

import java.io.File;
import java.io.IOException;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/14 12:40
 */
@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);


    @PostMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("message") String messageJSONString) {
        JSONObject jsonObject;
        if (file.isEmpty()) {
            return "empty file";
        }
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        assert originalFilename != null;
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("/") + 1);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.warn("上传的文件名为：" + fileName + "后缀名为:" + suffixName);
        File dest;
        dest = new File(System.getProperty("user.dir") + "/share/files/" + fileName);
        if (!dest.getParentFile().getParentFile().exists()) {
            dest.getParentFile().getParentFile().mkdir();
        }
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        jsonObject = new JSONObject();
        try {
            file.transferTo(dest);

        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put("result", "failed");
            return jsonObject.toJSONString();
        }
        Message message = JSON.parseObject(messageJSONString, Message.class);

        //这个id是客户端生成并发送过来的。
        jsonObject.put("messageId", message.getId());
        //为了预防客户端生成的id被篡改而带来的不良后果，在转发之前，在服务器重新生成一个id
        message.setId(System.currentTimeMillis());
        message.setType(Message.Type.MSG);
        message.setContent("<a href='/files/" + fileName + "' target='_blank' download='" + fileName + "'>" + fileName + "</a>");
        message.setRoomId(WebSecurityConfig.uuid.toString());
        //向所有人广播。需要客户端自己判断该消息是否来自自己
        for (WebSocketSession session :
                WebSocketHandler.SESSION_LIST) {
            try {
                session.sendMessage(new TextMessage(message.toJSONString()));
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("在Session：" + session.getId() + "上发送失败");
            }
        }
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }


}
