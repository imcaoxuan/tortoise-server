package top.caoxuan.tortoiseserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

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

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return System.getProperty("user.dir");
    }

   /* @RequestMapping("/error")
    public String error() {
        return "error";
    }*/
}