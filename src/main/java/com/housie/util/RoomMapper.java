package com.housie.util;

import com.housie.models.Room;
import org.bson.Document;

import java.util.HashSet;

public class RoomMapper {

    public static Room extractRoomFromDocument(Document document) {
        Room housieRoom = new Room();

        housieRoom.setPhoneNumber(document.getString("phoneNumber"));
        housieRoom.setCode(document.getString("code"));
        housieRoom.setLastNumberRemovedAt(document.getLong("lastNumberRemovedAt"));
        housieRoom.setUncalledNumbers(new HashSet<>(document.getList("uncalledNumbers", Integer.class)));
        housieRoom.setCalledNumbers(new HashSet<>(document.getList("calledNumbers", Integer.class)));

        housieRoom.setCreateDate(document.getString("createDate"));
        housieRoom.setUpdateDate(document.getString("updateDate"));
        housieRoom.setCreatedAt(document.getDate("createdAt"));
        housieRoom.setUpdatedAt(document.getDate("updatedAt"));

        return housieRoom;

    }


}
