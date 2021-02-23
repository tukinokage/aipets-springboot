package com.shay.aipets.services;

import com.shay.aipets.dto.DataTablePost;
import com.shay.aipets.dto.PostPic;
import com.shay.aipets.entity.BBSPost;
import com.shay.aipets.entity.GetPostConditions;
import com.shay.aipets.entity.Picture;
import com.shay.aipets.entity.Post;
import com.shay.aipets.entity.params.GetPostListParam;
import com.shay.aipets.mapper.PostMapper;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.utils.MD5CodeCeator;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Service("postService")
public class PostServiceImpl implements PostService {

    @Value("${file.root.path}")
    String fileRootPath;

    @Value("${img.post.path}")
    String imgPostPath;

    @Autowired
    PostMapper postMapper;


    @Override
    public String uploadPic(MultipartFile file) throws Exception {

        if(file.isEmpty()){
            throw new MyException("服务器：文件为空");
        }else {
            String fileName = file.getOriginalFilename();  // 原文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = MD5CodeCeator.randomUUID() + suffixName;
            File dest = new File( fileRootPath + imgPostPath + newFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            try {
                file.transferTo(dest);
                return newFileName;
            } catch (IOException e) {
                e.printStackTrace();
                throw new MyException("图片上传保存失败");
            }
        }
    }

    @Override
    public boolean savePicName(String postId, String picName) throws Exception {
        PostPic postPic = new PostPic();
        postPic.setPicName(picName);
        postPic.setPostId(postId);
        return postMapper.savePostPic(postPic);
    }

    @Override
    public boolean insertPost(DataTablePost dataTablePost, List<Picture> pictures) throws Exception {
        int a = postMapper.insert(dataTablePost);
        if(a < 1){
            return false;
        }

        String postId = dataTablePost.getPostId();
        PostPic postPic = new PostPic();
        postPic.setPostId(postId);
        if(pictures != null){
            for(Picture pic: pictures){

                postPic.setPicName(pic.getPicName());
                postMapper.savePostPic(postPic);
            }
        }

        return true;
    }

    @Override
    public Post getPostListByPId(String postId) throws Exception {
        List<Post> postListByPId = postMapper.getPostListByPId(postId);
        return postListByPId.get(0);
    }

    @Override
    public List<BBSPost> getPostListByUid(String uid, int perPaperNum, int currentPaperNum) throws Exception {
        GetPostConditions conditions = new GetPostConditions();
        int start = (currentPaperNum- 1) * perPaperNum ;
        int end = start + perPaperNum ;
        conditions.setEndPaperNum(end);
        conditions.setStartPaperNum(start);
        conditions.setSearchUid(uid);
        return postMapper.query(conditions);
    }

    @Override
    public List<BBSPost> getPostListByType(int type, int perPaperNum, int currentPaperNum) throws Exception {
        GetPostConditions conditions = new GetPostConditions();
        int start = (currentPaperNum- 1) * perPaperNum ;
        int end = start + perPaperNum ;
        conditions.setEndPaperNum(end);
        conditions.setStartPaperNum(start);
        conditions.setType(type);
        return postMapper.query(conditions);
    }

    @Override
    public List<BBSPost> getPostListBySearch(String searchCondition, int perPaperNum, int currentPaperNum) throws Exception {
        GetPostConditions conditions = new GetPostConditions();
        int start = (currentPaperNum- 1) * perPaperNum ;
        int end = start + perPaperNum ;
        conditions.setEndPaperNum(end);
        conditions.setStartPaperNum(start);
        conditions.setSearchCondition(searchCondition);
        return postMapper.query(conditions);
    }

    @Override
    public List<String> getPostPicNameListByPostId(String postId) throws Exception {
        List<String> postPics = postMapper.getPostPic(postId);
        return postPics;
    }
}
