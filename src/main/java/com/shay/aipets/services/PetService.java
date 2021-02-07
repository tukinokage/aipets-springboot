package com.shay.aipets.services;

import com.shay.aipets.dto.Pet;
import com.shay.aipets.dto.PetPic;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.Hospital;
import com.shay.aipets.entity.PetIntroduce;
import com.shay.aipets.entity.Store;
import com.shay.aipets.entity.responsedata.CheckPhoneRepData;
import com.shay.aipets.entity.responsedata.LoginResponseData;

import java.util.List;

public interface PetService {
    List<Pet> getPetListByPetInfo(Pet pet) throws Exception;
    List<Pet> getPetListByCondition(int fetchLevel,
                                    int petClass,
                                    int shapeLevel,
                                    int rankType,
                                    int perPageCount,
                                    int currentPageNum) throws Exception;

    boolean updatePetViewNum(String petId, int vieNum, String date) throws Exception;
    int getPetTodayViewNum(String petId,  String date) throws Exception;
    List<Store> getPetStore(Pet pet) throws Exception;
    List<Hospital> getPetHospital(Pet pet) throws Exception;
    PetIntroduce getPetIntroduce(Pet pet) throws Exception;
    List<PetPic> getPetPic(Pet pet) throws Exception;
    boolean isExistCurDateNum(String petId, String date) throws Exception;
}
