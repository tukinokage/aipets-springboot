package com.shay.aipets.services;

import com.shay.aipets.dto.DataTablePost;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.responsedata.CheckPhoneRepData;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    String uploadPic(MultipartFile file) throws Exception;
    boolean savePicName(String postId, String picName) throws Exception;

    /**@Param uid ：用户id pernum：每页数量， currentpapernum：当前页码
     * */
    List<DataTablePost> getPostListByUid(String uid, int perPaperNum, int currentPaperNum) throws Exception;
    /**@Param type：post类型  pernum：每页数量， currentpapernum：当前页码
     * */
    List<DataTablePost> getPostListByCondition(int type, int perPaperNum, int currentPaperNum) throws Exception;
    /**@Param searchCondition:搜索关键字  用户id pernum：每页数量， currentpapernum：当前页码
     * */
    List<DataTablePost> getPostListBySearch(String searchCondition, int perPaperNum, int currentPaperNum) throws Exception;
    List<String> getPostPicNameListByPostId(String postId) throws Exception;

}
