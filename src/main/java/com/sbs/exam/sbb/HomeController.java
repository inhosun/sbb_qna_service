package com.sbs.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class HomeController {
    @RequestMapping("/sbb")
    @ResponseBody
    public String showHome() {
        return "첫 시작2";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String showTest() {
        return """
                <h1>안녕하세요</h1>
                <input type="text" placeholder="입력해주세요"/>
                """;
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showGet() {
        return """
                <form method="post" action="/page2">
                <input type="text" name="age" placeholder="나이를 입력하세요">
                <input type="submit" value="post방식으로 이동">
                </form>                
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPost(@RequestParam(defaultValue = "0") int age ) {
        return """
                <h1>나이 : %d </h1>
                <h1>나이는 POST방식으로 입력되었습니다</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showGet1(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>나이 : %d </h1>
                <h1>나이는 GET방식으로 입력되었습니다</h1>
                """.formatted(age);

    }
}