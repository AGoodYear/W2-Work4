package com.ivmiku.W4R3.controller;


import com.ivmiku.W4R3.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    @Autowired
    private VideoService service;

    @GetMapping
    public void todo(){

    }
}
