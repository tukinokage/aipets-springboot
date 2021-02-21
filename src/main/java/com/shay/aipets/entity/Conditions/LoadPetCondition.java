package com.shay.aipets.entity.Conditions;

import java.io.Serializable;

public class LoadPetCondition implements Serializable {
    String shapeLevel;
    String fetchLevel;
    String rankType;
    String petClass;
    String perPageCount;
    String currentPageNum;

    public String getShapeLevel() {
        return shapeLevel;
    }

    public void setShapeLevel(String shapeLevel) {
        this.shapeLevel = shapeLevel;
    }

    public String getFetchLevel() {
        return fetchLevel;
    }

    public void setFetchLevel(String fetchLevel) {
        this.fetchLevel = fetchLevel;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public String getPetClass() {
        return petClass;
    }

    public void setPetClass(String petClass) {
        this.petClass = petClass;
    }

    public String getPerPageCount() {
        return perPageCount;
    }

    public void setPerPageCount(String perPageCount) {
        this.perPageCount = perPageCount;
    }

    public String getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(String currentPageNum) {
        this.currentPageNum = currentPageNum;
    }
}
