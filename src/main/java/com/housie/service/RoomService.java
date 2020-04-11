package com.housie.service;

import com.google.gson.Gson;
import com.housie.models.Room;
import com.housie.repository.RoomRepository;
import com.housie.response.RoomResponse;
import com.housie.util.HousieNumberUtils;
import com.housie.util.RoomMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StorageService storageService;


    private static final Logger logger = LogManager.getLogger(RoomService.class);


    public RoomResponse createRoom(String code, String phoneNumber) {
        if (getHousieRoom(code).getData()!=null) {
            return new RoomResponse(null, "ROOM_CODE_ALREADY_EXISTS", 1005);
        }
        Room room = new Room();
        room.setCode(code);
        room.setPhoneNumber(phoneNumber);
        room.setCalledNumbers(new HashSet<>());
        room.setUncalledNumbers(HousieNumberUtils.totalNumbersSet);
        Document document = storageService.saveDocument("housie_room",new Gson().toJson(room));
        return new RoomResponse(RoomMapper.extractRoomFromDocument(document),"",200);

    }

    public RoomResponse getHousieRoom(String code) {
        try {
            Room room = roomRepository.findRoomByCode(code);
            if (room != null) {
                return new RoomResponse(room, "", 200);
            }
            return new RoomResponse(null, "ROOM_NOT_FOUND", 1004);
        } catch (Exception e) {
            logger.info("Exception while fetching Room for Code : {}", code, e);
            return new RoomResponse(null, "EXCEPTION " + e.getMessage(), 500);
        }
    }

}
