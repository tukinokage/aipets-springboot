package com.shay.aipets.services;

import com.shay.aipets.app.AuthorizationInterceptor;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import com.shay.aipets.mapper.UserMapper;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.redis.redisCache.RedisUtil;
import com.shay.aipets.utils.MD5CodeCeator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    /**@return username str
     *         null为无结果
     *  AuthorizationInterceptor.TOKEN_EXPIRE_TIME 为默认缓存存活时间
     * */
    @Override
    public String loginByToken(String token) {
        if ("".equals(token) || token == null){
            return null;
        }

        //如果 key 不存在时，返回 null
        String result = (String) redisUtil.get(token);
        //更新存活时间
        if(result != null){
            redisUtil.expire(token,  AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            redisUtil.expire(result, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
        }
        return result;
    }

    @Override
    public User loginByPhoneToken(String phoneToken) throws Exception {
        return null;
    }

    @Override
    public User regByPhoneToken(String phoneToken) throws Exception {
        return null;
    }

    @Override
    public boolean isPhoneRg(String phoneToken) throws Exception {
        return false;
    }

    @Override
    public String getPhoneToken(String phoneNum, String code) throws Exception {
        return null;
    }

    @Override
    public String sendMsg(String phoneNum) throws Exception {
        return null;
    }


    @Override
    public User getUserById(String id) {
        User quser = new User();
        quser.setUserId(id);
        return ;
    }

    @Override
    public User getUserByName(String name) {
        return null;
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

}
