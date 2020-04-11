package com.housie.response;

import com.housie.models.Room;

public class NumberResponse {

    private Room room;
    private Integer number;
    private String numberImage;
    private String message;
    private int status;

    public NumberResponse(Room room, Integer number, String message, int status) {
        this.room = room;
        this.number = number;
        this.message = message;
        this.status = status;
    }

    public NumberResponse(Room room, Integer number, String numberImage, String message, int status) {
        this.room = room;
        this.number = number;
        this.numberImage = numberImage;
        this.message = message;
        this.status = status;
    }

    public NumberResponse(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNumberImage() {
        return numberImage;
    }

    public void setNumberImage(String numberImage) {
        this.numberImage = numberImage;
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
