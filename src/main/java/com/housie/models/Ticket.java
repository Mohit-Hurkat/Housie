package com.housie.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = "housie_ticket")
public class Ticket {

    @Id
    private String id;
    private String ticketId;
    private String groupId;
    private String roomCode;
    private List<Integer> ticketFirstLine;
    private List<Integer> ticketSecondLine;
    private List<Integer> ticketThirdLine;


    private String createDate;
    private String updateDate;
    private Date createdAt;
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public List<Integer> getTicketFirstLine() {
        return ticketFirstLine;
    }

    public void setTicketFirstLine(List<Integer> ticketFirstLine) {
        this.ticketFirstLine = ticketFirstLine;
    }

    public List<Integer> getTicketSecondLine() {
        return ticketSecondLine;
    }

    public void setTicketSecondLine(List<Integer> ticketSecondLine) {
        this.ticketSecondLine = ticketSecondLine;
    }

    public List<Integer> getTicketThirdLine() {
        return ticketThirdLine;
    }

    public void setTicketThirdLine(List<Integer> ticketThirdLine) {
        this.ticketThirdLine = ticketThirdLine;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}



