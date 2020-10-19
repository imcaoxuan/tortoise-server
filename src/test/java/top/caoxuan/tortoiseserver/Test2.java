package top.caoxuan.tortoiseserver;

import com.alibaba.fastjson.JSON;
import top.caoxuan.tortoiseserver.entity.Message;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/15 17:19
 */
public class Test2 {
    public static void main(String[] args) {
        String s = "{\"id\":1603108988839,\"type\":1,\"from\":\"me\",\"content\":\"123\",\"roomId\":\"roomId\"}";
        Message message = JSON.parseObject(s, Message.class);
        System.out.println(message.toJSONString());
    }
}
