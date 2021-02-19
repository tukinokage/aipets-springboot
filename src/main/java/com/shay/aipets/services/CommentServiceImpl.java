package com.shay.aipets.services;

import com.shay.aipets.dto.Comment;
import com.shay.aipets.dto.CommentPic;
import com.shay.aipets.dto.HeadImg;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.Post;
import com.shay.aipets.entity.UserCommentItem;
import com.shay.aipets.mapper.CommentMapper;
import com.shay.aipets.mapper.PostMapper;
import com.shay.aipets.mapper.UserMapper;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.utils.MD5CodeCeator;
import com.shay.aipets.utils.TimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    UserMapper userMapper;

    @Value("${img.comment.path}")
    String commentPicPath;

    @Value("${file.root.path}")
    String fileRootPath;

    @Override
    public boolean insertComment(String userId, String postId, String contentText, List<String> picList) throws Exception {
        Comment comment = new Comment();
        String commentId = MD5CodeCeator.randomUUID();
        comment.setContentText(contentText);
        comment.setDateTime(TimeUntil.getDateTime());
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setCommentId(commentId);
        commentMapper.insert(comment);
        for(int i = 0; i < picList.size(); i++){
            CommentPic commentPic = new CommentPic();
            commentPic.setCommentId(commentId);
            commentPic.setPicName(picList.get(i));
            commentMapper.insertPic(commentPic);
        }

        return true;
    }

    @Override
    public boolean insertCommentPic(String commentId, String picName) throws Exception {
        return false;
    }

    @Override
    public String uploadCommentPic(MultipartFile file) throws Exception {
        if(file.isEmpty()){
            throw new MyException("服务器：文件为空");
        }else {
            String fileName = file.getOriginalFilename();  // 原文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = MD5CodeCeator.randomUUID() + suffixName;
            File dest = new File( fileRootPath + commentPicPath + newFileName);
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
    public List<Comment> getCommentByPostId(String postId) throws Exception {
        Comment qcomment = new Comment();
        qcomment.setPostId(postId);
        List<Comment> queryList = commentMapper.query(qcomment);
        for(int i = 0; i < queryList.size(); i++){
            String commentid = queryList.get(i).getCommentId();
            List<CommentPic> picList = commentMapper.getPicListByCId(commentid);

            String userId = queryList.get(i).getUserId();
            HeadImg headImg = new HeadImg();
            headImg.setUserId(userId);
            String headImgStr = userMapper.getHeadImgName(headImg);

            User quser = new User();
            quser.setUserId(userId);
            String userName = userMapper.query(quser).get(0).getUserName();

            List<String> picNames = new ArrayList<>();
            for(CommentPic commentPic:picList){
                picNames.add(commentPic.getPicName());
            }

            queryList.get(i).setUserName(userName);
            queryList.get(i).setHeadPicName(headImgStr);
            queryList.get(i).setPicList(picNames);
        }

        return queryList;
    }

    @Override
    public List<UserCommentItem> getCommentByUserId(String userId,  int perNum, int currentNum) throws Exception {
        int starNum = currentNum * perNum ;
        int endNum = starNum + perNum;
        List<Comment> queryList = commentMapper.queryById(userId, starNum, endNum );
        List<UserCommentItem> userCommentItems = new ArrayList<>();
        for(int i = 0; i < queryList.size(); i++){
            UserCommentItem userCommentItem = new UserCommentItem();
            Comment comment = queryList.get(i);
            String postId = queryList.get(i).getPostId();
            Post post = postMapper.getPostListByPId(postId).get(0);
            userCommentItem.setCommentId(comment.getCommentId());
            userCommentItem.setCommentText(comment.getContentText());
            userCommentItem.setDateTime(comment.getDateTime());
            userCommentItem.setPostId(postId);
            userCommentItem.setPostTitle(post.getTitle());
            userCommentItem.setUserId(comment.getUserId());

            userCommentItems.add(userCommentItem);
        }

        return userCommentItems;
    }
}
