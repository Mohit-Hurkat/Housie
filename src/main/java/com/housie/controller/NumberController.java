package com.housie.controller;

import com.housie.response.NumberResponse;
import com.housie.response.RoomResponse;
import com.housie.service.NumberService;
import com.housie.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.QueryParam;


@Controller
@RequestMapping("/number")
public class NumberController {


    private static final Logger logger = LogManager.getLogger(NumberController.class);

    @Autowired
    private NumberService numberService;

    @Autowired
    private RoomService roomService;


    @GetMapping(value = "/getNextRandom")
    public String getNextRandom(Model model, @QueryParam("code") String code) {
        NumberResponse numberResponse = numberService.getNextRandomNumber(code);
        model.addAttribute("numberResponse", numberResponse);
        return "numbers";
    }

    @GetMapping(value = "/getPrevious")
    public String getPrevious(Model model, @QueryParam("code") String code, @QueryParam("currentNumber") Integer currentNumber) {
        NumberResponse numberResponse = numberService.getPreviousNumber(code, currentNumber);
        model.addAttribute("numberResponse", numberResponse);
        return "numbers";
    }

    @GetMapping(value = "/getNext")
    public String getNext(Model model, @QueryParam("code") String code, @QueryParam("currentNumber") Integer currentNumber) {
        NumberResponse numberResponse = numberService.getNextNumber(code, currentNumber);
        model.addAttribute("numberResponse", numberResponse);
        return "numbers";
    }

    @GetMapping(value = "/getCalledNumbers")
    public String getRemaining(Model model, @QueryParam("code") String code) {
        RoomResponse roomResponse = roomService.getHousieRoom(code);
        model.addAttribute("roomResponse", roomResponse);
        return "calledNumbers";
    }

//    public String setNumberImage(){
//
//    }


}
