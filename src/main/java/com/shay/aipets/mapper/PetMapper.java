package com.shay.aipets.mapper;

import com.shay.aipets.dto.*;
import com.shay.aipets.entity.Conditions.LoadPetCondition;
import com.shay.aipets.entity.Hospital;
import com.shay.aipets.entity.PetIntroduce;
import com.shay.aipets.entity.SelectPetCondition;
import com.shay.aipets.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PetMapper {
    List<Pet> query(Pet pet);
    List<String> queryIdByLimit(@Param("start") int start, @Param("end") int end);
    int queryAllPetNum();


    //条件分页查询
    List<Pet> queryByNoneRank( SelectPetCondition selectPetCondition);
    List<Pet> queryByRank(SelectPetCondition selectPetCondition);

    List<Store> getPetStore(Store store);
    List<Hospital> getPetHospital(Hospital hospital);
    List<String> getPetStoreIdList(@Param("petId") String petId);
    List<String> getPetHospitalIdList(@Param("petId") String petId);
    PetIntroduce getPetIntroduce(Pet pet);

    List<PetPic> getPetPic(Pet pet);

    boolean updateDateViewNum(PetDayViewNum petDayViewNum);
    int selectDateViewNum(PetDayViewNum petDayViewNum);

    boolean insertDateViewNum(PetDayViewNum petDayViewNum);
    int countDateViewColumn(@Param("currentDay") String currentDay,
                            @Param("rankType") int rankType,
                            @Param("petId") String petId);
}
