package cn.knightzz.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 王天赐
 * @title: IndexController
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-03-23 17:37
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String getHome(){
        return "views/websocket/index";
    }
}
