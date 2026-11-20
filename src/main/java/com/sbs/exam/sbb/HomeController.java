package com.sbs.exam.sbb;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller

public class HomeController {

    private int num;

    private HomeController homeController;

    public HomeController getHomeController() {
        return new HomeController();
    }

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

    @GetMapping("/plus")
    @ResponseBody
    public String showplus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        return "합계 : %d".formatted(a + b);
    }

    @GetMapping("/plus2")
    @ResponseBody
    public void showplus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));
        //return "합계 : %d".formatted(a + b);
        resp.getWriter().append(a + b +"");
    }

    @GetMapping("/minus")
    @ResponseBody
    public String showminus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        return "합계 : %d".formatted(b - a);
    }
    @GetMapping("/increase")
    @ResponseBody
    public String showIncrease() {
        num++;
        return "숫자 : %d".formatted(num);
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showgugudan(@RequestParam int a, @RequestParam int b) {
        StringBuffer sb = new StringBuffer();
        for(int i= a; i <= b; i++ ) {
            for(int j=1; j<=9; j++) {
                sb.append(i+"×"+j +" = "+ i*j +"<br>");
            }
        }
        return sb.toString();
    }

    @GetMapping("/gugudan2")
    @ResponseBody
    public String showgugudan2(@RequestParam int dan, @RequestParam int limit) {
        //final int dan = dan;
        //return IntStream.rangeClosed(1, limit)
          //      .mapToObj(i ->"%d × %d = %d".formatted(dan, i, dan * i))
            //    .collect(Collectors.joining("<br>"));
        return IntStream.rangeClosed(2, 9)
                .mapToObj(i ->
                        IntStream.rangeClosed(1,9)
                                .mapToObj(j-> "%d * %d = %d".formatted(i, j, i*j))
                                .collect(Collectors.joining("<br>"))
                ).collect(Collectors.joining("<br><br>"));


    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showmbti(@PathVariable String name) {
        String rs =
                switch(name) {
                    case "홍길동" -> "ENFP";
                    case "홍길순" -> "FNFP";
                    default -> "모름";
                };
        return rs;
    }



}