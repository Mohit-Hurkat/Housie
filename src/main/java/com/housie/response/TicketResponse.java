package com.housie.response;

import com.housie.models.Ticket;

public class TicketResponse {

    private Ticket data;
    private String message;
    private int status;

    public TicketResponse(Ticket data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public Ticket getData() {
        return data;
    }

    public void setData(Ticket data) {
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
