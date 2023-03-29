package com.liu.org.controller;

import com.liu.org.common.aop.LogAnnotation;
import com.liu.org.service.FlightService;
import com.liu.org.vo.Result;
import com.liu.org.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    @LogAnnotation(module = "航程",operator = "获取航程列表")
    public Result listFlight(@RequestBody PageParams pageParams){
        System.out.println("6666");
        return flightService.listFlight(pageParams);
    }

    @PostMapping("multiPass")
    @LogAnnotation(module = "航程",operator = "获取航程列表")
    public Result listFlightByAirlineName(@RequestBody PageParams pageParams){
        return flightService.listFlightByAirlineName(pageParams);
    }


}
