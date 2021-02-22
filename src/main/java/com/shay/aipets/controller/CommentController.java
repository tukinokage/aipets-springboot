package com.shay.aipets.controller;

import com.google.gson.Gson;
import com.shay.aipets.dto.Comment;
import com.shay.aipets.dto.DailyRecord;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.Picture;
import com.shay.aipets.entity.UserDailyRecordItem;
import com.shay.aipets.entity.params.*;
import com.shay.aipets.entity.response.*;
import com.shay.aipets.entity.responsedata.SetPwResponseData;
import com.shay.aipets.entity.responses.GetDailyRecordResponse;
import com.shay.aipets.entity.responses.PostDaliyResponse;
import com.shay.aipets.entity.responses.UpdateUserInfoResponse;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/getCommentList")
    public BaseResponse<GetCommentResponse> getCommentList(GetCommentParam getCommentParam){
        BaseResponse<GetCommentResponse> response = new BaseResponse<>();
        GetCommentResponse getCommentResponse = new GetCommentResponse();
        try {
            List<Comment> commentList = commentService.getCommentByPostId(getCommentParam.getPostId());
            getCommentResponse.setCommentsList(commentList);
            response.setData(getCommentResponse);
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
    @RequestMapping(value = "/postComment")
    public BaseResponse<CommitCommentResponse> postComment(CommitCommentParam commitCommentParam){
        BaseResponse<CommitCommentResponse> response = new BaseResponse<>();
        CommitCommentResponse commitCommentResponse = new CommitCommentResponse();
        try {
            String commentText = commitCommentParam.getCommentText();

            String postId = commitCommentParam.getPostId();
            String userId = commitCommentParam.getUserId();
            String token = commitCommentParam.getToken();
            if(!userService.checkToken(userId, token)){
                throw new MyException("登录失效");
            }
            List<String> picList = commitCommentParam.getPicList();
            List<String> picNameList = new ArrayList<>();
            if(picList != null){
                picNameList = picList;
            }

            commentService.insertComment(userId, postId, commentText, picNameList);
        } catch (MyException e){
            e.printStackTrace();
            response.setErrorMsg(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器出错");
        } finally {
            return response;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/uploadPic")
    public BaseResponse<UpLoadPicResponse> uploadPic(@RequestParam("info") String json, @RequestParam("file") MultipartFile file){
        BaseResponse<UpLoadPicResponse> response = new BaseResponse<>();
      UpLoadPicResponse upLoadPicResponse  = new UpLoadPicResponse();
        try {
            if (TextUtil.isEmpty(json)){

                throw new MyException("服务器：信息错误");
            }

            UpLoadPicParam upLoadPicParam = new Gson().fromJson(json, UpLoadPicParam.class);
            String token = upLoadPicParam.getToken();
            String userId = upLoadPicParam.getUserId();
            if(!userService.checkToken(userId, token)){

                throw new MyException("登录失效");
            }

            String imgName = commentService.uploadCommentPic(file);
            upLoadPicResponse.setPicName(imgName);
            upLoadPicResponse.setIndex(upLoadPicParam.getIndex());
            response.setData(upLoadPicResponse);
        }catch (MyException e){
            e.printStackTrace();
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }




}
