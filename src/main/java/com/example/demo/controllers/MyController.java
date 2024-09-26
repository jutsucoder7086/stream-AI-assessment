package com.example.demo.controllers;

import com.example.demo.dto.RequestDto;
import com.example.demo.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MyController {

    private final MyService myService;

    @Autowired
    public MyController(MyService myService) {
        this.myService = myService;
    }

    @PostMapping("/find-path")
    public List<int[]> findPath(@RequestBody RequestDto requestDto) {
        return this.myService.search(requestDto);

    }


}
