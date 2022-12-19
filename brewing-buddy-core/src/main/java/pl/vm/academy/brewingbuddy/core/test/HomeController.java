package pl.vm.academy.brewingbuddy.core.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "<h1>Hello, champ!<h1>";
    }

}
