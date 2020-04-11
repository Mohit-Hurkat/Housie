package com.housie.controller;

import com.housie.response.RoomResponse;
import com.housie.service.RoomService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;


@RestController
@RequestMapping("/room")
public class RoomController {


    private static final Logger logger = LogManager.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RoomResponse createRoom(@RequestBody JSONObject jsonBody) {
        String code = (String) jsonBody.getOrDefault("code", null);
        String phoneNumber = (String) jsonBody.getOrDefault("phoneNumber", null);
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(phoneNumber)) {
            return roomService.createRoom(code, phoneNumber);
        }
        return new RoomResponse(null, "BAD_REQUEST : MANDATORY PARAMS MISSING", 400);
    }

    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public RoomResponse getRoomData(@QueryParam("code") String code) {
        return roomService.getHousieRoom(code);
    }

}
