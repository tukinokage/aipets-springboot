package com.shay.aipets.entity;

import java.io.Serializable;

public class SelectPetCondition implements Serializable {
    int shapeLevel;
    int fetchLevel;
    int rankType;
    int petClass;
    int startNum;
    int endNum;
    String currentDay;

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getShapeLevel() {
        return shapeLevel;
    }

    public void setShapeLevel(int shapeLevel) {
        this.shapeLevel = shapeLevel;
    }

    public int getFetchLevel() {
        return fetchLevel;
    }

    public void setFetchLevel(int fetchLevel) {
        this.fetchLevel = fetchLevel;
    }

    public int getRankType() {
        return rankType;
    }

    public void setRankType(int rankType) {
        this.rankType = rankType;
    }

    public int getPetClass() {
        return petClass;
    }

    public void setPetClass(int petClass) {
        this.petClass = petClass;
    }


}
