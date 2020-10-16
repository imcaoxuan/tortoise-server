package top.caoxuan.tortoiseserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/14 14:47
 */
@Controller
public class ChatController {

    @RequestMapping("/chat")
    public String chat(Model model) throws UnknownHostException {
        InetAddress inetAddress = Inet4Address.getLocalHost();
        String ip = inetAddress.getHostAddress();
        model.addAttribute("ip", ip);
        return "multiplayer_sport";
    }
}
