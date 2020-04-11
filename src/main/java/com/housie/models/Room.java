package com.housie.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "housie_room")
public class Room {

    @Id
    private String id;
    private String code;
    private String phoneNumber;
    private Set<Integer> calledNumbers;
    private Set<Integer> uncalledNumbers;

    private Long lastNumberRemovedAt;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Integer> getCalledNumbers() {
        return calledNumbers;
    }

    public void setCalledNumbers(Set<Integer> calledNumbers) {
        this.calledNumbers = calledNumbers;
    }

    public Set<Integer> getUncalledNumbers() {
        return uncalledNumbers;
    }

    public void setUncalledNumbers(Set<Integer> uncalledNumbers) {
        this.uncalledNumbers = uncalledNumbers;
    }

    public Long getLastNumberRemovedAt() {
        return lastNumberRemovedAt;
    }

    public void setLastNumberRemovedAt(Long lastNumberRemovedAt) {
        this.lastNumberRemovedAt = lastNumberRemovedAt;
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



