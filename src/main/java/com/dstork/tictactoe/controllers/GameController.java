package com.dstork.tictactoe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/game")
public class GameController {

    @GetMapping("/hello-world")
    public String HelloWorld(){
        return "Hello World";
    }

}
