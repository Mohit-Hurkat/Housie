package com.housie.response;

import com.housie.models.Room;

public class RoomResponse {

    private Room data;
    private String message;
    private int status;

    public RoomResponse(Room data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public Room getData() {
        return data;
    }

    public void setData(Room data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
