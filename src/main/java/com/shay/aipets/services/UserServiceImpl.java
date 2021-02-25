package com.shay.aipets.services;

import com.shay.aipets.app.AuthorizationInterceptor;
import com.shay.aipets.dto.Background;
import com.shay.aipets.dto.HeadImg;
import com.shay.aipets.dto.Pet;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.GetPostConditions;
import com.shay.aipets.entity.UserInfo;
import com.shay.aipets.entity.responsedata.CheckPhoneRepData;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import com.shay.aipets.mapper.DaillyRecordMapper;
import com.shay.aipets.mapper.PetMapper;
import com.shay.aipets.mapper.PostMapper;
import com.shay.aipets.mapper.UserMapper;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.redis.redisCache.RedisUtil;
import com.shay.aipets.utils.CloopenUtil;
import com.shay.aipets.utils.MD5CodeCeator;
import com.shay.aipets.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.soap.Text;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Service("userService")
public class UserServiceImpl implements UserService {

    @Value("${file.root.path}")
    String fileRootPath;

    @Value("${img.user.head.path}")
    String userHeadPath;
    @Value("${img.user.bg.path}")
    String userBgPath;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserMapper userMapper;
    @Autowired
    DaillyRecordMapper daillyRecordMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PetMapper petMapper;

    /**@return token str
     *  AuthorizationInterceptor.TOKEN_EXPIRE_TIME 为默认缓存存活时间
     * */

    @Override
    public LoginResponseData login(String name, String psw) throws  Exception {
        String token = "";
        LoginResponseData loginResponseData = null;
        User quser = new User();
        quser.setUserName(name);
        quser.setPassWord(psw);

        int nums = userMapper.queryNum(quser);

        if (nums > 0){
            User resultUser = userMapper.query(quser).get(0);
            //清除上次的token 记录
            String id = resultUser.getUserId();

            String oldToken = redisUtil.getString(id);
            if (oldToken != null && !oldToken.equals("")){
                redisUtil.del(oldToken);
            }

            String rtoken = MD5CodeCeator.randomUUID();

            //双向保存
            Boolean a = redisUtil.set(rtoken, id, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            Boolean b = redisUtil.set(id, rtoken, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            if (a && b){
                token = rtoken;
                loginResponseData = new LoginResponseData();
                loginResponseData.setToken(token);
                loginResponseData.setUserId(id);
                loginResponseData.setUserName(resultUser.getUserName());
            }
        }else {
            //查询不到
            throw new MyException("用户名或密码错误");
        }

        return  loginResponseData ;
    }



    /**@return userid str
     *  AuthorizationInterceptor.TOKEN_EXPIRE_TIME 为默认缓存存活时间
     * */
    @Override
    public String getIdByToken(String token) throws Exception{
        if ("".equals(token) || token == null){
            throw new MyException("token错误");
        }

        //如果 key 不存在时，id = null
        String id = redisUtil.getString(token);
        //更新存活时间
        if(id != null){
            redisUtil.expire(token, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            redisUtil.expire(id, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
        }else {
            throw new MyException("登录失效");
        }
        return id;
    }

    /**
     * @Describe 通过验证码生成的phonetoken获取redis中的 phonenum
     * @Return phoneNum str
     * */
    @Override
    public String getPhoneByPhoneToken(String phoneToken) throws Exception {
        if(TextUtil.isEmpty(phoneToken)){
            throw new MyException("错误号码数据");
        }

        String phone = (String) redisUtil.get(phoneToken);
        if(TextUtil.isEmpty(phone)){
            throw new MyException("操作失效");
        }else {
            return phone;
        }
    }

    /**使用phonenum注册新用户，密码随机生成 ，用户名随机生成
     * @return 注册后的用户*/
    @Override
    public CheckPhoneRepData regByPhone(String phonenum) throws Exception {
        User ruser = new User();
        ruser.setPhoneNum(phonenum);
        ruser.setUserName("用户" + phonenum);
        ruser.setPassWord(MD5CodeCeator.randomUUID16());
        ruser.setSex(0);
        ruser.setUserId(MD5CodeCeator.randomUUID16());
        String token = MD5CodeCeator.randomUUID();
        //双向保存

        int insert = userMapper.insert(ruser);
        if(insert > 0){
            Boolean a = redisUtil.set(token, ruser.getUserId(), AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            Boolean b = redisUtil.set(ruser.getUserId(), token, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);

            //初始化头像和背景
            Background background = new Background();
            HeadImg headImg = new HeadImg();
            background.setUserId(ruser.getUserId());
            headImg.setUserId(ruser.getUserId());
            userMapper.insertBackGroundName(background);
            userMapper.insertHeadImgName(headImg);

            CheckPhoneRepData checkPhoneRepData = new CheckPhoneRepData();
            checkPhoneRepData.setUserType(0);
            checkPhoneRepData.setUserName(ruser.getUserName());
            checkPhoneRepData.setUserId(ruser.getUserId());
            checkPhoneRepData.setToken(token);
            return checkPhoneRepData;
        }else {
            throw new MyException("服务器数据错误");
        }
    }

    @Override
    public CheckPhoneRepData getUserByPhone(String phonenum) throws Exception {
        User quser = new User();
        quser.setPhoneNum(phonenum);

        int nums = userMapper.queryNum(quser);
        CheckPhoneRepData checkPhoneRepData = null;
        if (nums > 0){
            User resultUser = userMapper.query(quser).get(0);

            //清除上次的token 键记录
            String id = resultUser.getUserId();

            String oldToken = redisUtil.getString(id);
            if (oldToken != null && !oldToken.equals("")){
                redisUtil.del(oldToken);
            }

            String rtoken = MD5CodeCeator.randomUUID();

            //双向保存
            Boolean a = redisUtil.set(rtoken, id, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            Boolean b = redisUtil.set(id, rtoken, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            if (a && b){
                checkPhoneRepData = new CheckPhoneRepData();
                checkPhoneRepData.setToken(rtoken);
                checkPhoneRepData.setUserId(id);
                checkPhoneRepData.setUserName(resultUser.getUserName());
                checkPhoneRepData.setUserType(1);
            }
        }else {
            //查询不到
            throw new MyException("号码获取用户信息错误");
        }

        return checkPhoneRepData;
    }

    /**
     * 检查phone是否已经注册*/
    @Override
    public boolean isPhoneRg(String phone) throws Exception {
        User quser = new User();
        quser.setPhoneNum(phone);
        int num = userMapper.queryNum(quser);
        if(num > 0){
            return true;
        }
        return false;
    }

    /**
     *验证输入的手机号码和 验证码匹配
     * 匹配就生成手机令牌用于识别用户
     *
     * @Return phonetoken str
     * */
    @Override
    public String getPhoneToken(String phoneNum, String code) throws Exception {
        String rcode = (String) redisUtil.get(phoneNum);
        if(code.equals(rcode)){
            String phoneToken = MD5CodeCeator.randomUUID16();
            redisUtil.set(phoneToken, phoneNum, AuthorizationInterceptor.PHONE_TOKEN_EXPIRE_TIME);
            return phoneToken;
        }else {
            throw new MyException("验证码错误");
        }

    }

    /**检查token匹配*/
    @Override
    public boolean checkToken(String userId, String token) throws Exception {
        if(TextUtil.isEmpty(userId) || TextUtil.isEmpty(token) ){
            throw new MyException("服务器通知：获取参数错误");
        }

        String rtoken = redisUtil.getString(userId).replace("\"", "");
        String ruid =  redisUtil.getString(token).replace("\"", "");
        if(token.equals(rtoken) &&  userId.equals(ruid)) {
            return true;
        }else {
            return false;
        }

    }

    /***
     * 向目标手机发送验证码，并生成缓存phonenum和code，用于验证
     *
     * */
    @Override
    public void sendMsg(String phoneNum) throws Exception {
        Random random = new Random();
        int codeInt = random.nextInt(8999) + 1000;
        //request params
        String code = String.valueOf(codeInt);
        CloopenUtil.send(phoneNum, code, "1");
        //String code = String.valueOf(1000);
        redisUtil.set(phoneNum, code, AuthorizationInterceptor.PHONE_TOKEN_EXPIRE_TIME);
    }


    @Override
    public User getUserById(String id) throws MyException {
        User quser = new User();
        quser.setUserId(id);
        List<User>  users= userMapper.query(quser);
        User user = users.get(0);

        if(user == null){
            throw new MyException("用户不存在");
        }
        return user;
    }

    @Override
    public UserInfo getUserInfoById(String id) throws Exception {
        UserInfo userInfo = new UserInfo();
        User user = this.getUserById(id);
        String bgName = this.getBgImgName(id);
        String headImgName = this.getHeadImgName(id);
        GetPostConditions postConditions = new GetPostConditions();
        postConditions.setSearchUid(id);
        int DRNum = daillyRecordMapper.queryNum(id);
        int postNum = postMapper.queryNum(postConditions);

        userInfo.setUserName(user.getUserName());
        userInfo.setUserSign(user.getSign());
        userInfo.setBgPicName(bgName);
        userInfo.setHeadPicName(headImgName);
        userInfo.setPostNum(String.valueOf(postNum));
        userInfo.setDailyNum(String.valueOf(DRNum));
        userInfo.setSex(user.getSex());
        return userInfo;
    }

    @Override
    public User getUserByName(String name) throws Exception{
        User quser  = new User();
        List<User>  users= userMapper.query(quser);
        User user = users.get(0);
        if(user == null){
            throw new MyException("用户不存在");
        }

        return user;
    }

    @Override
    public boolean isExistUserByName(String name) {
        User user = new User();
        user.setUserName(name);
        int num = userMapper.queryNum(user);
        if (num > 0){
            return true;
        }else {
            return false;
        }
    }

    /***
     *
     * 通过id修改信息*/
    @Override
    public boolean updateInfoById(User updaterInfo) throws Exception {
        if(userMapper.update(updaterInfo)){
            return true;
        }
            return false;
    }

    @Override
    public boolean updateHeadImg(String userIdString, String headImgName) throws Exception {
        HeadImg headImg = new HeadImg();
        headImg.setUserId(userIdString);
        headImg.setHeadImgName(headImgName);
        boolean b = userMapper.setHeadImgName(headImg);
        return b;
    }

    @Override
    public String uploadHeadImg(MultipartFile file, String userId) throws Exception {

        if(file.isEmpty()){
            throw new MyException("服务器：文件为空");
        }else {
            String fileName = file.getOriginalFilename();  // 原文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = userId + suffixName;
            File dest = new File( fileRootPath + userHeadPath + newFileName);


            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }else {
                if (dest.exists()) {
                    dest.delete();
                }
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
    public boolean updateBgImg(String userIdString, String bgImgName) throws Exception {
        Background background = new Background();
        background.setUserId(userIdString);
        background.setBgImgName(bgImgName);
        boolean b = userMapper.setBackGroundName(background);
        return b;
    }

    @Override
    public String uploadBgImg(MultipartFile file, String userId ) throws Exception {
        if(file.isEmpty()){
            throw new MyException("服务器：文件为空");
        }else {
            String fileName = file.getOriginalFilename();  // 原文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = "BG" + userId + suffixName;
            File dest = new File( fileRootPath + userBgPath + newFileName);


            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }else {
                if (dest.exists()) {
                    dest.delete();
                }
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
    public String getHeadImgName(String userId) throws Exception {
        HeadImg headImg = new HeadImg();
        headImg.setUserId(userId);

        List<String> stringList = userMapper.getHeadImgName(headImg);
        if(stringList.isEmpty()){
            return "";
        }else {
            String s = stringList.get(0);
            return s;
        }

    }

    @Override
    public String getBgImgName(String userId) throws Exception {
        Background background = new Background();
        background.setUserId(userId);
        List<String> stringList = userMapper.getBackGroundName(background);
        if(stringList.isEmpty()){
            return "";
        }else {
            String s = stringList.get(0);
            return s;
        }
    }

    @Override
    public boolean isStarPet(String petId, String userId) throws Exception {

        if(TextUtil.isEmpty(petId)){
            throw new MyException("参数错误");
        }

        int i = userMapper.queryStarPetNum(userId, petId);
        if(i > 0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean starPet(String petId, String userId) throws Exception {
        boolean b = userMapper.starPet( userId, petId);
        return b;
    }

    @Override
    public boolean unStarPet(String petId, String userId) throws Exception {
        boolean b = userMapper.unStarPet(userId, petId);
        return b;
    }

    @Override
    public List<Pet> getStartPet(String userId, int perPagerNum, int currentPagerNum) throws Exception {
        int starNum = (currentPagerNum -1) *perPagerNum ;
        int endNum = starNum + perPagerNum;
        List<String> list = userMapper.queryStarPetId(userId, starNum, endNum);
        if(list.isEmpty()){
            throw new MyException("没有收藏哦");
        }
        List<Pet> petList = new ArrayList<>();

        for (String petId:
             list) {
            Pet qPet = new Pet();
            qPet.setPetId(petId);
            petList.addAll(petMapper.query(qPet));
        }

        return petList;
    }


}
