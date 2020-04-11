package com.housie.service;

import com.housie.models.Room;
import com.housie.response.NumberResponse;
import com.housie.response.RoomResponse;
import com.housie.util.HousieConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class NumberService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private RoomService roomService;


    private static final Logger logger = LogManager.getLogger(NumberService.class);


    public NumberResponse getNextRandomNumber(String code) {
        RoomResponse response = roomService.getHousieRoom(code);
        if (response.getData() == null) {
            return new NumberResponse(null, null, response.getMessage(), response.getStatus());
        }
        if(response.getData().getLastNumberRemovedAt()!=null && System.currentTimeMillis() - response.getData().getLastNumberRemovedAt() < 10000 ){
            Room room = new Room();
            room.setCode(code);
            return new NumberResponse(room, null,"TOO_EARLY || Try Again in 10 seconds" , 1006);

        }
        if (response.getData().getUncalledNumbers().size() == 0) {
            return new NumberResponse(response.getData(), null, "GAME_COMPLETED", 1000);
        }
        int randomNumber = new Random().nextInt(response.getData().getUncalledNumbers().size());
        NumberResponse numberResponse = new NumberResponse(response.getData());

        Optional<Integer> optionalInteger =  response.getData().getUncalledNumbers().stream().skip(randomNumber).findFirst();
        if(optionalInteger.isPresent()) {
            Integer number = optionalInteger.get();
            numberResponse.getRoom().getUncalledNumbers().remove(number);
            numberResponse.getRoom().getCalledNumbers().add(number);
            numberResponse.getRoom().setLastNumberRemovedAt(System.currentTimeMillis());
            JSONObject jsonObject = new JSONObject(numberResponse.getRoom());
            Query query = storageService.getUpsertQueries(jsonObject, HousieConstants.upsertKeysForRoom);
            if (storageService.upsertDocument("housie_room", query, jsonObject, HousieConstants.tobeUpdatedKeysForRoomNumber)) {
                numberResponse.setNumber(number);
            }
        }
        return numberResponse;
    }

    public NumberResponse getPreviousNumber(String code,Integer currentNumber) {
        RoomResponse response = roomService.getHousieRoom(code);
        if (response.getData() == null) {
            return new NumberResponse(null, null, response.getMessage(), response.getStatus());
        }

        Integer responseNumber = null;
        NumberResponse numberResponse = new NumberResponse(response.getData());

        if(currentNumber==null){
            long count = response.getData().getCalledNumbers().size();
            Stream<Integer> integerStream = response.getData().getCalledNumbers().stream();

            Optional<Integer> optionalInteger =  integerStream.skip(count - 1).findFirst();
            if(optionalInteger.isPresent()){
                numberResponse.setNumber(optionalInteger.get());
                return numberResponse;
            }
        }

        for (Integer number : response.getData().getCalledNumbers()) {
            if(number.equals(currentNumber)){
                numberResponse.setNumber(responseNumber);
                break;
            }
            responseNumber = number;
        }
        return numberResponse;
    }

    public NumberResponse getNextNumber(String code,Integer currentNumber) {
        RoomResponse response = roomService.getHousieRoom(code);
        if (response.getData() == null) {
            return new NumberResponse(null, null, response.getMessage(), response.getStatus());
        }

        if(currentNumber==null || currentNumber==0){
            return getNextRandomNumber(code);
        }

        NumberResponse numberResponse = new NumberResponse(response.getData());
        Iterator<Integer> iterator = response.getData().getCalledNumbers().iterator();
        while(iterator.hasNext()) {
                if (iterator.next().equals(currentNumber)) {
                    if(iterator.hasNext()){
                        numberResponse.setNumber(iterator.next());
                    }else{
                       return getNextRandomNumber(code);
                    }
                    break;
                }
            }
        return numberResponse;
    }

}
