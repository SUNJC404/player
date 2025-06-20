package org.example.springboot_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index"; // 返回 index.html 模板
    }

    @GetMapping("/upload")
    public String upload() { return "upload"; }
}