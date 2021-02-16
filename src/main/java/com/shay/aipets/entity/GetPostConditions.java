package com.shay.aipets.entity;

public class GetPostConditions {
    String searchCondition;
    int type;
    int startPaperNum;
    int endPaperNum;
    String searchUid;

    public String getSearchUid() {
        return searchUid;
    }

    public void setSearchUid(String searchUid) {
        this.searchUid = searchUid;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEndPaperNum() {
        return endPaperNum;
    }

    public void setEndPaperNum(int endPaperNum) {
        this.endPaperNum = endPaperNum;
    }

    public int getStartPaperNum() {
        return startPaperNum;
    }

    public void setStartPaperNum(int startPaperNum) {
        this.startPaperNum = startPaperNum;
    }
}
