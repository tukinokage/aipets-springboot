package com.shay.aipets.entity.Conditions;

public class LoadPetHospitalCondition {


    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    private String petId;
    public LoadPetHospitalCondition(String petId) {
        this.petId = petId;
    }
}
