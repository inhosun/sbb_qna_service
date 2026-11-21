package com.sbs.exam.sbb;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller

public class HomeController {

    private int num;


    private List<Article> articles;

    public HomeController() {
        this.num = -1;
        this.articles = new ArrayList<>() {{
            add(new Article("제목입니다1", "내용입니다1"));
            add(new Article("제목입니다2", "내용입니다2"));
            add(new Article("제목입니다3", "내용입니다3"));
            add(new Article("제목입니다4", "내용입니다4"));
            add(new Article("제목입니다5", "내용입니다5"));
            add(new Article("제목입니다6", "내용입니다6"));
        }};
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

        return switch(name) {
            //여러줄은 써야 하는경우 {  }
            case "홍길동" -> {
                char k = 'J';
                yield "ENF"+k;
            }
            case "홍길순" -> "FNFP";
            case "임꺽정","신짱구" -> "FNFP";
            default -> "모름";
        };
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name,@PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(name, value);
        return "세션 변수의 %s 값이 %s로 설정 되었습니다".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name,HttpSession session) {
        String age = (String) session.getAttribute("age");
        return "세션의 %s값은 %s입니다".formatted(name,age);

    }

    @GetMapping("/home/returnList")
    @ResponseBody
    public List<String> showList() {
        List<String> list = new ArrayList<>() {{
             add("안녕");
             add("반가원");
             add("어서와");
        }};

        List<String> list2 = new ArrayList<>(List.of("안녕","반가워","어서와"));
        return list;
    }

    @GetMapping("/home/returnMap")
    @ResponseBody
    public Map<String, Object> showReturnMap() {
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("id", 1);
        map.put("age", 5);
        map.put("name", "푸바오");
        map.put("related", new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }});
    return map;
    }

    @GetMapping("/home/returnAnimal")
    @ResponseBody
    public Animal showAnimal() {
        Animal animal = new Animal(1, 5, "포비",
                new ArrayList<>() {{
                    add(1);
                    add(2);
                    add(3);
                }}
        );
        System.out.println(animal+"aaaa");
        return animal;
    }

    @GetMapping("/home/returnAnimalV2")
    @ResponseBody
    public AnimalV2 showAnimal2() {
        AnimalV2 animal = new AnimalV2(1, 5, "포비",
                new ArrayList<>() {{
                    add(1);
                    add(2);
                    add(3);
                }}
        );
        animal.setName(animal.getName());
        System.out.println(animal);
        return animal;
    }

    @GetMapping("/home/returnAnimalMapList")
    @ResponseBody
    public List<Map<String,Object>> showMapList() {
        Map<String,Object> animalmap1 = new LinkedHashMap<>();
        animalmap1.put("id", 1);
        animalmap1.put("age", 5);
        animalmap1.put("name", "푸바오");
        animalmap1.put("related", new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }});

        Map<String,Object> animalmap2 = new LinkedHashMap<>();
        animalmap2.put("id", 2);
        animalmap2.put("age", 10);
        animalmap2.put("name", "푸바오2");
        animalmap2.put("related", new ArrayList<>(){{
            add(4);
            add(5);
            add(6);
        }});

        List<Map<String,Object>> mapList = new ArrayList<>();
        mapList.add(animalmap1);
        mapList.add(animalmap2);


        mapList.forEach(l -> System.out.println(l.get("id")));
        return mapList;
    }

    @GetMapping("/home/returnAnimalListV2")
    @ResponseBody
    public List<AnimalV2> showAnimalList2() {
        AnimalV2 animal = new AnimalV2(1, 5, "포비",
                new ArrayList<>() {{
                    add(1);
                    add(2);
                    add(3);
                }}
        );

        AnimalV2 anima2 = new AnimalV2(2, 10, "포비2",
                new ArrayList<>() {{
                    add(4);
                    add(5);
                    add(6);
                }}
        );
        List<AnimalV2> animalV2List = new ArrayList<>();
        animalV2List.add(animal);
        animalV2List.add(anima2);

        return animalV2List;
    }

    @GetMapping("/home/addArticle")
    @ResponseBody
    public String addArticle(String title, String body) {
        Article article = new Article(title, body);
        articles.add(article);
        return "%d 게시물이 추가 되었습니다".formatted(article.getId());
    }

    @GetMapping("/home/getArticles")
    @ResponseBody
    public List<Article> getArticles() {
        return articles;
    }


    @GetMapping("/article/detail/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable int id) {
        System.out.println("여기오나?1");
        Article article = articles.stream()
                .filter(s-> s.getId() == id)
                .findFirst()
                .orElse(null);
        System.out.println("여기오나?2");
        return article;
    }
    @GetMapping("/article/modify/{id}")
    @ResponseBody
    public String editArticle(@PathVariable int id,  String title,  String body) {

        Article article = articles.stream()
                .filter(s-> s.getId() == id)
                .findFirst()
                .orElse(null);

        if(article == null) {
            return "%d 게시물은 존재하지 않습니다".formatted(id);
        }
        if(title.length() == 0 || title == null) {
            return "제목을 입력하세요";
        }
        if(body.length() == 0 || body == null) {
            return "내용을 입력하세요";
        }
        article.setTitle(title);

        article.setBody(body);

        return "%d 게시물이 수정 되었습니다".formatted(article.getId());
    }

    @GetMapping("/article/delete/{id}")
    @ResponseBody
    public String removeArticle(@PathVariable int id) {

        /*
        Article article = articles.stream()
                .filter(s-> s.getId() == id)
                .findFirst()
                .orElse(null);

        articles.remove(article);
        */
        boolean deleted =articles
                .removeIf(s->s.getId() == id);
        return deleted ? "%d 게시물이 삭제 되었습니다".formatted(id) : "%d 게시물은 존재하지 않습니다".formatted(id);
    }

    @GetMapping("/person/addpersonoldway")
    @ResponseBody
    public Person addPerson(int id, String name, int age) {
        Person p = new Person(2, "홍길동", 25);
        return p;

    }

    @GetMapping("/person/addperson")
    @ResponseBody
    public Person addPerson(Person p) {

        return p;

    }

}




class Animal {
    private final int id;
    private final int age;
    private final String name;
    private final List related;

    public Animal(int id, int age, String name, List related) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.related = related;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", related=" + related +
                '}';
    }
}

@AllArgsConstructor
@Data
@ToString
class AnimalV2 {
    private  int id;
    private  int age;
    private  String name;
    private  List related;

}

@AllArgsConstructor
@Data
@ToString
class Article {
    private static int lastId;
    private int id;
    private String title;
    private String body;

    static {
        lastId = 0;
    }

    public Article(String title, String body) {
        this(++lastId, title, body);
    }

}

@Getter
@Setter
@AllArgsConstructor
@ToString
class Person {
    private int id;
    private String name;
    private int age;

}

