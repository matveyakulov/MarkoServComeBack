package ru.matveyakulov.markoservcomeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.matveyakulov.markoservcomeback.service.RestService;

import java.util.List;

@RestController
@RequestMapping("/holidays")
public class HolidayController {

    private final RestService restService;

    @Autowired
    public HolidayController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping
    public List<String> getAllByMouthAndDay(@RequestParam final String month, @RequestParam final Integer day){
        return restService.getAllByMouthAndDay(month, day);
    }

    @PutMapping
    public List<String> addByMouthAndDay(@RequestParam final String month, @RequestParam final Integer day){
        return restService.addByMouthAndDay(month, day);
    }
}
