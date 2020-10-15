package top.caoxuan.tortoiseserver;

import com.alibaba.fastjson.JSON;
import top.caoxuan.tortoiseserver.entity.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/14 17:19
 */
public class Test {
    public static void main(String[] args) {
        Message message = new Message(Message.Type.AUTH, "this", "123", "hello","iii");
        System.out.println(JSON.toJSONString(message));
        List<String > list = new ArrayList<>();
        list.remove("dsad");
    }
}
