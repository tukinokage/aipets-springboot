package com.shay.aipets.controller;

import com.shay.aipets.dto.DailyRecord;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.UserDailyRecordItem;
import com.shay.aipets.entity.params.*;
import com.shay.aipets.entity.response.BaseResponse;
import com.shay.aipets.entity.response.CheckIsStarResponse;
import com.shay.aipets.entity.response.StarPetResponse;
import com.shay.aipets.entity.responsedata.SetPwResponseData;
import com.shay.aipets.entity.responses.GetDailyRecordResponse;
import com.shay.aipets.entity.responses.PostDaliyResponse;
import com.shay.aipets.entity.responses.UpdateUserInfoResponse;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.services.DailyRecordService;
import com.shay.aipets.services.UserService;
import com.shay.aipets.utils.MD5CodeCeator;
import com.shay.aipets.utils.TimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/usr")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DailyRecordService dailyRecordService;

    @ResponseBody
    @RequestMapping(value = "/starPet")
    public BaseResponse<StarPetResponse> starPet(StarPetParam starPetParam){
        BaseResponse<StarPetResponse> response = new BaseResponse<>();
        StarPetResponse starPetResponse = new StarPetResponse();
        try {
            boolean starPet = userService.isStarPet(starPetParam.getPetId(), starPetParam.getUserId());
            if(starPet){
                boolean b = userService.unStarPet(starPetParam.getPetId(), starPetParam.getUserId());
                if(b){

                }else {
                    throw new MyException("服务器：取消失败");
                }

            }else {
                boolean b = userService.starPet(starPetParam.getPetId(), starPetParam.getUserId());
                if(!b){
                    throw new MyException("服务器：收藏失败");
                }
            }

        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkPetStar")
    public BaseResponse<CheckIsStarResponse> checkPetStar(CheckIsStarParam checkIsStarParam){
        BaseResponse<CheckIsStarResponse> response = new BaseResponse<>();
        CheckIsStarResponse checkIsStarResponse = new CheckIsStarResponse();
        try {
            boolean starPet = userService.isStarPet(checkIsStarParam.getPetId(), checkIsStarParam.getUserId());
            checkIsStarResponse.setStar(starPet);
        } catch (MyException e){
            response.setErrorMsg(e.getMessage());
        } catch (Exception e){
            response.setErrorMsg("服务器出错");
        } finally {
            return response;
        }
    }

    /**修改信息
     *
     * @return  UpdateUserInfoResponse;
     *   通过token检查缓存中id是否与请求id相符
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/updateInfo")
    public BaseResponse<UpdateUserInfoResponse> updateUserInfo(UpdateUserInfoParam updateUserInfoParam){
        BaseResponse<UpdateUserInfoResponse> response = new BaseResponse<>();
        UpdateUserInfoResponse updateUserInfoResponse = new UpdateUserInfoResponse();
        try {
            String userId = userService.getIdByToken(updateUserInfoParam.getUserToken());
            if(userId.equals(updateUserInfoParam.getUserId())){
                User user = new User();
                user.setUserId(userId);
                // user.setSex(updateUserInfoParam.get);
                user.setUserName(updateUserInfoParam.getUserName());
                user.setSign(updateUserInfoParam.getSign());
                boolean b = userService.updateInfoById(user);
                if(!b) {
                    throw new MyException("修改失败");
                }
            }else {
                throw new MyException("非法操作");
            }
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }

    /**修改pw
     *
     * @return  UpdateUserInfoResponse;
     *   通过token检查缓存中id是否与请求id相符
     *
     * */
    @ResponseBody
    @RequestMapping(value = "/updatePassword")
    public BaseResponse<SetPwResponseData> updatePassword(SetPwRequestParam setPwRequestParam){
        BaseResponse<SetPwResponseData> response = new BaseResponse<>();
        SetPwResponseData setPwResponseData = new SetPwResponseData();
        response.setData(setPwResponseData);
        try {
            String phoneToken = setPwRequestParam.getPhoneToken();
            String phone = userService.getPhoneByPhoneToken(phoneToken);
            if(userService.isPhoneRg(phone)){
                User user = new User();
                user.setPassWord(setPwRequestParam.getPassword());
                user.setUserId(setPwRequestParam.getUserId());
                boolean b = userService.updateInfoById(user);
                if(!b) {
                    throw new MyException("修改失败");
                }
            }else {
                throw new MyException("非法操作");
            }
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDailyRecord")
    public BaseResponse<GetDailyRecordResponse> getDailyRecord(GetUserDailyRecordParam getUserDailyRecordParam){
        BaseResponse<GetDailyRecordResponse> response = new BaseResponse<>();
        GetDailyRecordResponse getDailyRecordResponse = new GetDailyRecordResponse();

        try {
            List<UserDailyRecordItem> queryList = dailyRecordService.query(getUserDailyRecordParam.getUserId(),
                    Integer.valueOf(getUserDailyRecordParam.getPerPagerCount()),
                    Integer.valueOf(getUserDailyRecordParam.getCurrentPager()));
            getDailyRecordResponse.setDailyRecordItemList(queryList);

            response.setData(getDailyRecordResponse);
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/insertDailyRecord")
    public BaseResponse<PostDaliyResponse> getDailyRecord(ConfrimDaliyRecord confrimDaliyRecord){
        BaseResponse<PostDaliyResponse> response = new BaseResponse<>();
        PostDaliyResponse postDaliyResponse = new PostDaliyResponse();

        try {
            if(userService.checkToken(confrimDaliyRecord.getUserId(), confrimDaliyRecord.getToken())){
                DailyRecord dailyRecord = new DailyRecord();
                dailyRecord.setContentText(confrimDaliyRecord.getContent());
                dailyRecord.setUserId(confrimDaliyRecord.getUserId());
                dailyRecord.setDateTime(TimeUntil.getDateTime());
                dailyRecord.setDRId(MD5CodeCeator.randomUUID());
                response.setData(postDaliyResponse);
            }else {
                throw new MyException("服务器：登录失效");
            }

        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }



}
