package com.shay.aipets.entity.params;

public class GetPostListParam {
    String searchCondition;
    int type;
    int perPaperNum;
    int currentPaperNum;
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

    public int getPerPaperNum() {
        return perPaperNum;
    }

    public void setPerPaperNum(int perPaperNum) {
        this.perPaperNum = perPaperNum;
    }

    public int getCurrentPaperNum() {
        return currentPaperNum;
    }

    public void setCurrentPaperNum(int currentPaperNum) {
        this.currentPaperNum = currentPaperNum;
    }
}
