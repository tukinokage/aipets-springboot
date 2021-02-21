package com.shay.aipets.services;

import com.shay.aipets.dto.Pet;
import com.shay.aipets.dto.PetDayViewNum;
import com.shay.aipets.dto.PetPic;
import com.shay.aipets.entity.Hospital;
import com.shay.aipets.entity.PetIntroduce;
import com.shay.aipets.entity.Store;
import com.shay.aipets.entity.SelectPetCondition;
import com.shay.aipets.mapper.PetMapper;
import com.shay.aipets.redis.redisCache.RedisUtil;
import com.shay.aipets.utils.TimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service("petService")
public class PetServiceImpl implements  PetService {
    @Autowired
    PetMapper petMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<Pet> getPetListByPetInfo(Pet pet) throws Exception {
        return petMapper.query(pet);
    }

    @Override
    public List<Pet> getPetListByCondition(int fetchLevel, int petClass, int shapeLevel, int rankType, int perPageCount, int currentPageNum) throws Exception{
        SelectPetCondition condition = new SelectPetCondition();
        String currentDay = TimeUntil.getDate();
        condition.setCurrentDay(currentDay);
        condition.setFetchLevel(fetchLevel);
        condition.setPetClass(petClass);
        condition.setRankType(rankType);
        condition.setShapeLevel(shapeLevel);
        condition.setStartNum(perPageCount *(currentPageNum - 1) );
        condition.setEndNum(perPageCount *currentPageNum );
        if(rankType == 0){
            return  petMapper.queryByNoneRank(condition);
        }else {
           return petMapper.queryByRank(condition);
        }
    }

    @Override
    public boolean updatePetViewNum(String petId, int num, String date) throws Exception {
        PetDayViewNum conditon = new PetDayViewNum();
        conditon.setPetId(petId);
        conditon.setStime(date);
        conditon.setViewNum(num);
        return petMapper.updateDateViewNum(conditon);
    }

    @Override
    public int getPetTodayViewNum(String petId, String date) throws Exception {
        PetDayViewNum petDayViewNum = new PetDayViewNum();
        petDayViewNum.setStime(date);
        petDayViewNum.setPetId(petId);
        int num = petMapper.selectDateViewNum(petDayViewNum);
        return num;
    }

    @Override
    public List<Store> getPetStore(Pet pet) throws Exception {
        List<String> idList = petMapper.getPetStoreIdList(pet.getPetId());
        List<Store> petStores = new ArrayList<>();
        for(String id:idList){
            Store qStore = new Store();
            qStore.setStoreId(id);
            List<Store> petStore = petMapper.getPetStore(qStore);
            petStores.addAll(petStore);
        }

        return petStores;
    }

    @Override
    public List<Hospital> getPetHospital(Pet pet) throws Exception {
        List<String> idList = petMapper.getPetHospitalIdList(pet.getPetId());
        List<Hospital> petHospitals = new ArrayList<>();
        for(String id:idList){
            Hospital qHospital = new Hospital();
            qHospital.setHospitalId(id);
            List<Hospital> petStore = petMapper.getPetHospital(qHospital);
            petHospitals.addAll(petStore);
        }
        return petHospitals;
    }

    @Override
    public PetIntroduce getPetIntroduce(Pet pet) throws Exception {
        PetIntroduce petIntroduce = petMapper.getPetIntroduce(pet);
        return petIntroduce;
    }

    @Override
    public List<PetPic> getPetPic(Pet pet) throws Exception {
        List<PetPic> petPic = petMapper.getPetPic(pet);
        return petPic;
    }

    @Override
    public boolean isExistCurDateNum(String petId,  String date) throws Exception {
        int num = petMapper.countDateViewColumn(date, 1, petId);
        if(num > 0){
            return true;
        }else {
            return false;
        }
    }


}
