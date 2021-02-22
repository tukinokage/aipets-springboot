package com.shay.aipets.controller;

import com.google.gson.Gson;
import com.shay.aipets.dto.DailyRecord;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.UserCommentItem;
import com.shay.aipets.entity.UserDailyRecordItem;
import com.shay.aipets.entity.UserInfo;
import com.shay.aipets.entity.params.*;
import com.shay.aipets.entity.response.*;
import com.shay.aipets.entity.responsedata.SetPwResponseData;
import com.shay.aipets.entity.responses.*;
import com.shay.aipets.mapper.DaillyRecordMapper;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.services.CommentService;
import com.shay.aipets.services.DailyRecordService;
import com.shay.aipets.services.UserService;
import com.shay.aipets.utils.MD5CodeCeator;
import com.shay.aipets.utils.TextUtil;
import com.shay.aipets.utils.TimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/usr")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DailyRecordService dailyRecordService;
    @Autowired
    CommentService commentService;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            response.setErrorMsg("服务器出错");
        }finally {
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
    @RequestMapping(value = "/getUserInfo")
    public BaseResponse<GetUserInfoResponse> getUserInfo(GetUserInfoParam getUserInfoParam){
        BaseResponse<GetUserInfoResponse> response = new BaseResponse<>();
        GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
        try {
            String userId = getUserInfoParam.getUserId();
            UserInfo userInfo = userService.getUserInfoById(userId);
            getUserInfoResponse.setUserInfo(userInfo);

            response.setData(getUserInfoResponse);

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
    @RequestMapping(value = "/getCommentList")
    public BaseResponse<GetUserCommentResponse> getCommentList(GetUserCommentParam getUserCommentParam){
        BaseResponse<GetUserCommentResponse> response = new BaseResponse<>();
        GetUserCommentResponse getUserCommentResponse = new GetUserCommentResponse();

        try {

           // getUserCommentResponse.setCommentItemList();
            List<UserCommentItem> commentItems = commentService.getCommentByUserId(getUserCommentParam.getUserId(),
                    Integer.valueOf(getUserCommentParam.getPerPagerCount()),
                    Integer.valueOf(getUserCommentParam.getCurrentPager()));
            getUserCommentResponse.setCommentItemList(commentItems);
            if(commentItems.size() < Integer.valueOf(getUserCommentParam.getPerPagerCount())){
                getUserCommentResponse.setHasMore(false);
            }else {
                getUserCommentResponse.setHasMore(true);
            }
            response.setData(getUserCommentResponse);
      /*  }catch (MyException e){
            response.setErrorMsg(e.getMessage());*/
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }




    @ResponseBody
    @RequestMapping(value = "/postDailyRecord")
    public BaseResponse<PostDaliyResponse> postDailyRecord(ConfrimDaliyRecord confrimDaliyRecord){
        BaseResponse<PostDaliyResponse> response = new BaseResponse<>();
        PostDaliyResponse postDaliyResponse = new PostDaliyResponse();

        try {
            if(userService.checkToken(confrimDaliyRecord.getUserId(), confrimDaliyRecord.getToken())){
                DailyRecord dailyRecord = new DailyRecord();
                dailyRecord.setContentText(confrimDaliyRecord.getContent());
                dailyRecord.setUserId(confrimDaliyRecord.getUserId());
                dailyRecord.setDateTime(TimeUntil.getDateTime());
                dailyRecord.setDRId(MD5CodeCeator.randomUUID());
                boolean b = dailyRecordService.insertDailyRecord(dailyRecord);
                if(b){
                    response.setData(postDaliyResponse);
                }else {
                    throw new MyException("发布失败");
                }

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

    @ResponseBody
    @RequestMapping(value = "/getDailyRecord")
    public BaseResponse<GetDailyRecordResponse> getDailyRecord(GetUserDailyRecordParam getUserDailyRecordParam){
        BaseResponse<GetDailyRecordResponse> response = new BaseResponse<>();
        GetDailyRecordResponse getDailyRecordResponse = new GetDailyRecordResponse();

        try {

            List<UserDailyRecordItem> query = dailyRecordService.query(getUserDailyRecordParam.getUserId(),
                    Integer.valueOf(getUserDailyRecordParam.getPerPagerCount()),
                    Integer.valueOf(getUserDailyRecordParam.getCurrentPager()));
            getDailyRecordResponse.setDailyRecordItemList(query);
            if(query.size() < Integer.valueOf(getUserDailyRecordParam.getPerPagerCount())){
                getDailyRecordResponse.setHasMore(false);
            }else {
                getDailyRecordResponse.setHasMore(true);
            }
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
    @RequestMapping(value = "/updateBg")
    public BaseResponse<UpdateUserInfoResponse> updateBg(@RequestParam("info") String json, @RequestParam("file") MultipartFile file){
        BaseResponse<UpdateUserInfoResponse> response = new BaseResponse<>();
      //  UpdateUserInfoResponse upLoadPicResponse = new UpdateUserInfoResponse();
        try {
            if (TextUtil.isEmpty(json)){
                throw new MyException("服务器：信息错误");
            }

            UpdateHeadImgParam upLoadPicParam = new Gson().fromJson(json, UpdateHeadImgParam.class);

          //  if (!userService.checkToken(upLoadPicParam.getUserId(), upLoadPicParam.getUserToken())){
            //    throw new MyException("服务器：非法操作");
          //  }
            String imgName = userService.uploadBgImg(file, upLoadPicParam.getUserId());
            boolean b = userService.updateBgImg(upLoadPicParam.getUserId(), imgName);
            if(!b){
                throw new MyException("服务器：修改失败");
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
    @RequestMapping(value = "/updateHeadImg")
    public BaseResponse<UpdateUserInfoResponse> updateHeadImg(@RequestParam("info") String json, @RequestParam("file") MultipartFile file){
        BaseResponse<UpdateUserInfoResponse> response = new BaseResponse<>();
        //  UpdateUserInfoResponse upLoadPicResponse = new UpdateUserInfoResponse();
        try {
            if (TextUtil.isEmpty(json)){
                throw new MyException("服务器：信息错误");
            }

            UpdateHeadImgParam upLoadPicParam = new Gson().fromJson(json, UpdateHeadImgParam.class);

            if (!userService.checkToken(upLoadPicParam.getUserId(), upLoadPicParam.getUserToken())){
                throw new MyException("服务器：非法操作");
            }
            String imgName = userService.uploadHeadImg(file, upLoadPicParam.getUserId());
            boolean b = userService.updateHeadImg(upLoadPicParam.getUserId(), imgName);
            if(!b){
                throw new MyException("服务器：修改失败");
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
