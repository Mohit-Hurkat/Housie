package com.housie.response;

import com.housie.models.Room;
import com.housie.models.Ticket;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TicketVerificationResponse {

    private Ticket ticket;
    private Room room;

    private boolean checkFirstLastAndMiddle;
    private boolean checkCornersAndMiddle;
    private boolean checkOnesAndZeros;
    private boolean checkFirstHalf;
    private boolean checkSecondHalf;

    private boolean checkFirstLine;
    private boolean checkSecondLine;
    private boolean checkThirdLine;

    private boolean checkFullHouse;

    private List<Integer> remainingNumber = new ArrayList<>();


    private String message;
    private int status;

    public TicketVerificationResponse(Ticket ticket, String message, int status) {
        this.ticket = ticket;
        this.message = message;
        this.status = status;
    }

    public TicketVerificationResponse(Ticket ticket, Room room, String message, int status) {
        this.ticket = ticket;
        this.room = room;
        this.message = message;
        this.status = status;
    }


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isCheckFirstLastAndMiddle() {
        return checkFirstLastAndMiddle;
    }

    public void setCheckFirstLastAndMiddle(boolean checkFirstLastAndMiddle) {
        this.checkFirstLastAndMiddle = checkFirstLastAndMiddle;
    }

    public boolean isCheckCornersAndMiddle() {
        return checkCornersAndMiddle;
    }

    public void setCheckCornersAndMiddle(boolean checkCornersAndMiddle) {
        this.checkCornersAndMiddle = checkCornersAndMiddle;
    }

    public boolean isCheckOnesAndZeros() {
        return checkOnesAndZeros;
    }

    public void setCheckOnesAndZeros(boolean checkOnesAndZeros) {
        this.checkOnesAndZeros = checkOnesAndZeros;
    }

    public boolean isCheckFirstHalf() {
        return checkFirstHalf;
    }

    public void setCheckFirstHalf(boolean checkFirstHalf) {
        this.checkFirstHalf = checkFirstHalf;
    }

    public boolean isCheckSecondHalf() {
        return checkSecondHalf;
    }

    public void setCheckSecondHalf(boolean checkSecondHalf) {
        this.checkSecondHalf = checkSecondHalf;
    }

    public boolean isCheckFirstLine() {
        return checkFirstLine;
    }

    public void setCheckFirstLine(boolean checkFirstLine) {
        this.checkFirstLine = checkFirstLine;
    }

    public boolean isCheckSecondLine() {
        return checkSecondLine;
    }

    public void setCheckSecondLine(boolean checkSecondLine) {
        this.checkSecondLine = checkSecondLine;
    }

    public boolean isCheckThirdLine() {
        return checkThirdLine;
    }

    public void setCheckThirdLine(boolean checkThirdLine) {
        this.checkThirdLine = checkThirdLine;
    }

    public boolean isCheckFullHouse() {
        return checkFullHouse;
    }

    public void setCheckFullHouse(boolean checkFullHouse) {
        this.checkFullHouse = checkFullHouse;
    }

    public List<Integer> getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(List<Integer> remainingNumber) {
        this.remainingNumber = remainingNumber;
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

    public JSONObject getResponse(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Temperature and Bulls Eye",this.isCheckFirstLastAndMiddle());
        jsonObject.put("Star Parivaar",this.isCheckCornersAndMiddle());
        jsonObject.put("Anda Danda",this.isCheckOnesAndZeros());
        jsonObject.put("Young Family",this.isCheckFirstHalf());
        jsonObject.put("Old Family",this.isCheckSecondHalf());
        jsonObject.put("1st Line",this.isCheckFirstLine());
        jsonObject.put("2nd Line",this.isCheckSecondLine());
        jsonObject.put("3rd Line",this.isCheckThirdLine());
        jsonObject.put("Full House",this.isCheckFullHouse());
        jsonObject.put("Muflis",this.getRemainingNumber());
        jsonObject.put("ticket",this.ticket);
        jsonObject.put("message",this.message);
        jsonObject.put("status",this.status);
        return jsonObject;
    }
}
