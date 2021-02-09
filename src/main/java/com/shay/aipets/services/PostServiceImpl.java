package com.shay.aipets.services;

import com.shay.aipets.dto.DataTablePost;
import com.shay.aipets.entity.Post;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.utils.FileUrIUtil;
import com.shay.aipets.utils.MD5CodeCeator;
import javafx.geometry.Pos;
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
        return false;
    }

    @Override
    public List<DataTablePost> getPostListByUid(String uid, int perPaperNum, int currentPaperNum) throws Exception {
        return null;
    }

    @Override
    public List<DataTablePost> getPostListByCondition(int type, int perPaperNum, int currentPaperNum) throws Exception {
        return null;
    }

    @Override
    public List<DataTablePost> getPostListBySearch(String searchCondition, int perPaperNum, int currentPaperNum) throws Exception {
        return null;
    }

    @Override
    public List<String> getPostPicNameListByPostId(String postId) throws Exception {
        return null;
    }
}
