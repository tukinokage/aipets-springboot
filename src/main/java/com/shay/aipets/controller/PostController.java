package com.shay.aipets.controller;

import com.google.gson.Gson;
import com.shay.aipets.dto.DataTablePost;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.BBSPost;
import com.shay.aipets.entity.Post;
import com.shay.aipets.entity.params.*;
import com.shay.aipets.entity.response.*;
import com.shay.aipets.entity.responsedata.SetPwResponseData;
import com.shay.aipets.entity.responses.PostResponse;
import com.shay.aipets.entity.responses.UpdateUserInfoResponse;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.services.PostService;
import com.shay.aipets.services.UserService;
import com.shay.aipets.utils.MD5CodeCeator;
import com.shay.aipets.utils.TextUtil;
import com.shay.aipets.utils.TimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @ResponseBody
    @RequestMapping(value = "/postlist")
    public BaseResponse<GetPostListResponse> postlist(GetPostListParam getPostListParam){
        BaseResponse<GetPostListResponse> response = new BaseResponse<>();
        GetPostListResponse getPostListResponse = new GetPostListResponse();
        try {
            List<BBSPost> posts =new ArrayList<>();
            Float currentNum =   Float.valueOf(getPostListParam.getCurrentPaperNum());
            Float perNum =  Float.valueOf(getPostListParam.getPerPaperNum());
            Float type =  Float.valueOf(getPostListParam.getType());

            if(!TextUtil.isEmpty(getPostListParam.getSearchCondition())){
                posts = postService.getPostListBySearch(
                        getPostListParam.getSearchCondition(),
                        perNum.intValue(),
                        currentNum.intValue());
            }else  if(!TextUtil.isEmpty(getPostListParam.getSearchUid())){
                posts = postService.getPostListByUid(
                        getPostListParam.getSearchUid(),
                        perNum.intValue(),
                        currentNum.intValue());
            }else if(!TextUtil.isEmpty(String.valueOf(getPostListParam.getType()))){
                posts = postService.getPostListByType(
                        type.intValue(),
                        perNum.intValue(),
                        currentNum.intValue());
            }

            for(int i = 0; i < posts.size(); i++){
                String postId = posts.get(i).getPostId();
                List<String> postPicNameList= postService.getPostPicNameListByPostId(postId);
                if(!postPicNameList.isEmpty()){
                    posts.get(i).setPicName(postPicNameList.get(0));
                }
            }
            if(posts.size() < getPostListParam.getPerPaperNum()){
                getPostListResponse.setHasMore(false);
            }else {
                getPostListResponse.setHasMore(true);
            }
            getPostListResponse.setBbsPostList(posts);
            response.setData(getPostListResponse);
//        }catch (MyException e){
//            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/postInfo")
    public BaseResponse<GetPostInfoResponse> postInfo(GetPostInfoParam getPostInfoParam){
        BaseResponse<GetPostInfoResponse> response = new BaseResponse<>();
        GetPostInfoResponse getPostInfoResponse = new GetPostInfoResponse();
        try {

            String postId = getPostInfoParam.getPostId();
            Post post = postService.getPostListByPId(postId);
            List<String> postPicNameList= postService.getPostPicNameListByPostId(postId);
            User userById = userService.getUserById(post.getUserId());
            String userName = userById.getUserName();
            post.setUserName(userName);
            if(!postPicNameList.isEmpty()){
                post.setPicNameList(postPicNameList);
            }

            getPostInfoResponse.setPost(post);
            response.setData(getPostInfoResponse);
       /*} catch (MyException e){
            response.setErrorMsg(e.getMessage());*/
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
    @RequestMapping(value = "/uploadPic")
    public BaseResponse<UpLoadPicResponse> uploadPic(@RequestParam("info") String json, @RequestParam("file")MultipartFile file){
        BaseResponse<UpLoadPicResponse> response = new BaseResponse<>();
        UpLoadPicResponse upLoadPicResponse = new UpLoadPicResponse();
        try {
            if (TextUtil.isEmpty(json)){
                throw new MyException("信息错误");
            }

            UpLoadPicParam upLoadPicParam = new Gson().fromJson(json, UpLoadPicParam.class);

            if (!userService.checkToken(upLoadPicParam.getUserId(), upLoadPicParam.getToken())){
                throw new MyException("非法操作");
            }

            String picName = postService.uploadPic(file);
            upLoadPicResponse.setIndex(upLoadPicParam.getIndex());
            upLoadPicResponse.setPicName(picName);

            response.setData(upLoadPicResponse);
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
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
    @RequestMapping(value = "/commitPost")
    public BaseResponse<PostResponse> commitPost(PostParam postParam){
        BaseResponse<PostResponse> response = new BaseResponse<>();
        PostResponse postResponse = new PostResponse();
        try {

            boolean b = userService.checkToken(postParam.getUserId(), postParam.getToken());
            if(b){
                DataTablePost dataTablePost = new DataTablePost();
                dataTablePost.setContextText(postParam.getContentText());
                dataTablePost.setDateTime(TimeUntil.getDateTime());
                dataTablePost.setPostId(MD5CodeCeator.randomUUID());
                dataTablePost.setTitle(postParam.getTitle());
                dataTablePost.setType(postParam.getType());
                dataTablePost.setUserId(postParam.getUserId());
                postService.insertPost(dataTablePost, postParam.getPicList());
            }else {
                throw new MyException("服务器：身份失效");
            }
        /*}catch (MyException e){
            response.setErrorMsg(e.getMessage());*/

        }catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }



}
