package com.shay.aipets.controller;

import com.shay.aipets.dto.Pet;
import com.shay.aipets.dto.PetPic;
import com.shay.aipets.entity.Conditions.*;
import com.shay.aipets.entity.Hospital;
import com.shay.aipets.entity.PetInfoImg;
import com.shay.aipets.entity.PetIntroduce;
import com.shay.aipets.entity.Store;
import com.shay.aipets.entity.response.BaseResponse;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.services.PetService;
import com.shay.aipets.utils.TimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;


    //...根据筛选条件加载列表
    @RequestMapping(value = "/petlist")
    @ResponseBody
    public BaseResponse<List<Pet>> getPetList(LoadPetCondition loadPetCondition){

        BaseResponse<List<Pet>> response = new BaseResponse<>();
        if(loadPetCondition == null ){
            response.setErrorMsg("服务没有收到请求数据");
            return response;
        }

        try {
            Float fetchLevel = Float.valueOf(loadPetCondition.getFetchLevel());
            Float petClass =Float.valueOf(loadPetCondition.getPetClass());
            Float shapeLevel = Float.valueOf(loadPetCondition.getShapeLevel());
            Float rankType =Float.valueOf( loadPetCondition.getRankType());
            Float per =Float.valueOf(loadPetCondition.getPerPageCount());
            Float current =Float.valueOf(loadPetCondition.getCurrentPageNum());
            List<Pet> petList = petService.getPetListByCondition(
                    fetchLevel.intValue(),
                    petClass.intValue(),
                    shapeLevel.intValue(),
                    rankType.intValue(),
                    per.intValue(),
                    current.intValue());
            /*if(petList.size() == 0){
                throw new MyException("来自服务器：没有数据");
            }*/
            response.setData(petList);

        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器端处理出错");
        }finally {

            return response;
        }
    }


    //加载介绍列表
    @RequestMapping(value = "/loadPetIntroduction")
    @ResponseBody
    public BaseResponse<PetIntroduce> loadPetIntroduction(LoadPetIntroductionCondition loadPetIntroductionCondition){
        System.out.println(loadPetIntroductionCondition.getPetId());
        BaseResponse<PetIntroduce> response = new BaseResponse<>();
        if(loadPetIntroductionCondition == null ){
            response.setErrorMsg("服务没有收到请求数据");
            return response;
        }

        try {
            Pet pet = new Pet();
            pet.setPetId(loadPetIntroductionCondition.getPetId());
            PetIntroduce petIntroduce = petService.getPetIntroduce(pet);
            if(petIntroduce == null){
                petIntroduce = new PetIntroduce();
                petIntroduce.setPetAttention("没有相关信息");
                petIntroduce.setPetStory("没有相关信息");
            }
            new Thread(){
                @Override
                public void run() {
                    try {
                        int viewNum = petService.getPetTodayViewNum(loadPetIntroductionCondition.getPetId(), TimeUntil.getDate());
                        petService.updatePetViewNum(loadPetIntroductionCondition.getPetId(), viewNum +1, TimeUntil.getDate());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            response.setData(petIntroduce);
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器端处理出错");
        }finally {

            return response;
        }
    }

    //加载商店列表
    @RequestMapping(value = "/loadPetStore")
    @ResponseBody
    public BaseResponse<List<Store>> getPetStoreList(LoadPetStoreCondition loadPetStoreCondition){
        System.out.println(loadPetStoreCondition.getPetId());
        BaseResponse<List<Store>> response = new BaseResponse<>();
        if(loadPetStoreCondition == null ){
            response.setErrorMsg("服务没有收到请求数据");
            return response;
        }

        try {
            Pet pet = new Pet();
            pet.setPetId(loadPetStoreCondition.getPetId());
            List<Store> storeList = petService.getPetStore(pet);
            response.setData(storeList);
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器端处理出错");
        }finally {

            return response;
        }
    }


    //加载医院列表
    @RequestMapping(value = "/loadPetHospital")
    @ResponseBody
    public BaseResponse<List<Hospital>> getPetHospitalList(LoadPetHospitalCondition loadPetHospitalCondition){
        System.out.println(loadPetHospitalCondition.getPetId());
        BaseResponse<List<Hospital>> response = new BaseResponse<>();
        if(loadPetHospitalCondition == null ){
            response.setErrorMsg("服务没有收到请求数据");
            return response;
        }

        try {
            Pet pet = new Pet();
            pet.setPetId(loadPetHospitalCondition.getPetId());
            List<Hospital> hospitalList = petService.getPetHospital(pet);
            response.setData(hospitalList);
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器端处理出错");
        }finally {

            return response;
        }
    }

    //加载图片列表
    @RequestMapping(value = "/loadPetImage")
    @ResponseBody
    public BaseResponse<PetInfoImg> getPetLoadPetImage(LoadPetPicCondition loadPetPicCondition){
        System.out.println(loadPetPicCondition.getPetId());
        BaseResponse<PetInfoImg> response = new BaseResponse<>();
        if(loadPetPicCondition == null ){
            response.setErrorMsg("服务没有收到请求数据");
            return response;
        }

        try {
            Pet pet = new Pet();
            PetInfoImg petInfoImg = new PetInfoImg();
            pet.setPetId(loadPetPicCondition.getPetId());
            List<PetPic> petPics = petService.getPetPic(pet);
            List<String> pciNames = new ArrayList<>();
            for(PetPic p:petPics){
                pciNames.add(p.getPicName());
            }
            petInfoImg.setImgList(pciNames);
            response.setData(petInfoImg);
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器端处理出错");
        }finally {

            return response;
        }
    }

}
