package com.housie.repository;

import com.housie.models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface RoomRepository extends MongoRepository<Room, String> {

    @Query("{ 'code' : ?0 }")
    Room findRoomByCode(String code);

}
