package com.shevcov.notes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Tag(name = "MVC контроллер")
@Controller
public class WebController {

    @Operation(summary = "Метод для отображения странички index.html")
    @GetMapping("/")
    public String index(){
        return "index";
    }

}
