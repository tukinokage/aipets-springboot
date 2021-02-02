package com.shay.aipets.services;

import com.shay.aipets.app.AuthorizationInterceptor;
import com.shay.aipets.dto.Background;
import com.shay.aipets.dto.HeadImg;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.responsedata.CheckPhoneRepData;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import com.shay.aipets.mapper.UserMapper;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.redis.redisCache.RedisUtil;
import com.shay.aipets.utils.MD5CodeCeator;
import com.shay.aipets.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserMapper userMapper;

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

            String oldToken = (String) redisUtil.get(id);
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
        String id = (String) redisUtil.get(token);
        //更新存活时间
        if(id != null){
            redisUtil.expire(token,  AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
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

            String oldToken = (String) redisUtil.get(id);
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
    public boolean updateBgImg(String userIdString, String bgImgName) throws Exception {
        Background background = new Background();
        background.setUserId(userIdString);
        background.setBgImgName(bgImgName);
        boolean b = userMapper.setBackGroundName(background);
        return b;
    }


    @Override
    public String getHeadImgName(String userId) throws Exception {
        HeadImg headImg = new HeadImg();
        headImg.setUserId(userId);
        String s = userMapper.getHeadImgName(headImg);
        return s;
    }

    @Override
    public String getBgImgName(String userId) throws Exception {
        Background background = new Background();
        background.setUserId(userId);
        String s = userMapper.getBackGroundName(background);
        return s;
    }


}
