package ru.matveyakulov.markoservcomeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.matveyakulov.markoservcomeback.service.RestService;

import java.util.List;

@RestController
@RequestMapping("/holidays")
public class GetController {

    private final RestService restService;

    @Autowired
    public GetController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping
    public List<String> getAllByMouthAndDay(@RequestParam final String mouth, @RequestParam final Integer day){
        return restService.getAllByMouthAndDay(mouth, day);
    }
}
