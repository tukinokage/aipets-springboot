package com.shay.aipets.controller;

import com.shay.aipets.dto.Pet;
import com.shay.aipets.entity.Conditions.LoadPetCondition;
import com.shay.aipets.entity.Conditions.LoadPetIntroductionCondition;
import com.shay.aipets.entity.PetIntroduce;
import com.shay.aipets.entity.response.BaseResponse;
import com.shay.aipets.entity.responsedata.SmsResponse;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        System.out.println(loadPetCondition.getCurrentPageNum());
        BaseResponse<List<Pet>> response = new BaseResponse<>();
        if(loadPetCondition == null ){
            response.setErrorMsg("服务没有收到请求数据");
            return response;
        }

        try {
            List<Pet> petList = petService.getPetListByCondition(
                    loadPetCondition.getFetchLevel(),
                    loadPetCondition.getPetClass(),
                    loadPetCondition.getShapeLevel(),
                    loadPetCondition.getRankType(),
                    loadPetCondition.getPerPageCount(),
                    loadPetCondition.getCurrentPageNum());
            if(petList.size() == 0){
                throw new MyException("来自服务器：没有数据");
            }
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
    public BaseResponse<PetIntroduce> getPetList(LoadPetIntroductionCondition loadPetIntroductionCondition){
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

}
