package top.caoxuan.tortoiseserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/14 18:07
 */
@Controller
public class MainController {

    @GetMapping(value = "/")
    private String index() {
        return "index";
    }

    @RequestMapping("/signIn")
    public String login() {
        return "signIn";
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }

   /* @RequestMapping("/error")
    public String error() {
        return "error";
    }*/
}
