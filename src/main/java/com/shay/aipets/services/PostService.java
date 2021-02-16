package com.shay.aipets.services;

import com.shay.aipets.dto.DataTablePost;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.BBSPost;
import com.shay.aipets.entity.Picture;
import com.shay.aipets.entity.Post;
import com.shay.aipets.entity.responsedata.CheckPhoneRepData;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    String uploadPic(MultipartFile file) throws Exception;
    boolean savePicName(String postId, String picName) throws Exception;
    boolean insertPost(DataTablePost dataTablePost, List<Picture> pictures) throws Exception;

    /**@Param uid ：用户id pernum：每页数量， currentpapernum：当前页码
     * */
    Post getPostListByPId((String postId) throws Exception;
    /**@Param uid ：用户id pernum：每页数量， currentpapernum：当前页码
     * */
    List<BBSPost> getPostListByUid(String uid, int perPaperNum, int currentPaperNum) throws Exception;
    /**@Param type：post类型  pernum：每页数量， currentpapernum：当前页码
     * */
    List<BBSPost> getPostListByType(int type, int perPaperNum, int currentPaperNum) throws Exception;
    /**@Param searchCondition:搜索关键字  用户id pernum：每页数量， currentpapernum：当前页码
     * */
    List<BBSPost> getPostListBySearch(String searchCondition, int perPaperNum, int currentPaperNum) throws Exception;

    List<String> getPostPicNameListByPostId(String postId) throws Exception;

}
