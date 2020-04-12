package com.housie.controller;

import com.housie.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;


@RestController
@RequestMapping("/ticket")
public class TicketController {


    private static final Logger logger = LogManager.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;


    @RequestMapping(value = "/getResponse", method = RequestMethod.GET)
    public JSONObject getResponse(@QueryParam("ticketId") String ticketId, @QueryParam("groupId") String groupId, @QueryParam("code") String code) {
        return ticketService.checkTicket(ticketId, groupId, code).getResponse();
    }

}
