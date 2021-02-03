package com.shay.aipets.dto;

public class DailyRecord {
    private String DRId;
    private String userId;
    private String contentText;
    private String dateTime;

    public DailyRecord() {
    }

    public DailyRecord(String DRId, String userId, String contentText, String dateTime) {
        this.DRId = DRId;
        this.userId = userId;
        this.contentText = contentText;
        this.dateTime = dateTime ;
    }

    public String getDRId() {
        return DRId;
    }

    public void setDRId(String DRId) {
        this.DRId = DRId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
