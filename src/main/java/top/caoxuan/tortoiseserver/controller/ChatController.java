package top.caoxuan.tortoiseserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/14 14:47
 */
@Controller
public class ChatController {

    @RequestMapping("/chat")
    public String chat() {
        return "multiplayer_sport";
    }
}
