package com.shay.aipets.services;


import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.app.AuthorizationInterceptor;
import com.example.chaterserver.entity.User;
import com.example.chaterserver.mapper.UserMapper;
import com.example.chaterserver.redisCache.RedisUtil;
import com.example.chaterserver.util.MD5CodeCeator;
import com.shay.aipets.mapper.UserMapper;
import com.shay.aipets.redis.redisCache.RedisUtil;
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
    public JSONObject login(String name, String psw) {
        String token = "";

        User user = new User();
        user.setPassword(psw);
        user.setUserName(name);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "");
        jsonObject.put("userId", "");
        try {
            int nums = userMapper.queryNum(user);

            if (nums > 0){
                User resultUser = userMapper.query(user).get(0);
                //清除上次的token 记录
                String oldToken = (String) redisUtil.get(name);
                if (oldToken != null && !oldToken.equals("")){
                    redisUtil.del(oldToken);
                }

                String rtoken = MD5CodeCeator.randomUUID();

                Boolean a = redisUtil.set(rtoken, name, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
                Boolean b = redisUtil.set(name, rtoken, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
                if (a && b){
                    token = rtoken;
                    jsonObject.put("userId", resultUser.getUserId());
                    jsonObject.put("token", token);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return jsonObject;
        }
    }

    /**@return token str
     *  AuthorizationInterceptor.TOKEN_EXPIRE_TIME 为默认缓存存活时间
     * */
    @Override
    public JSONObject register(String name, String psw) {

        String token = "";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "");
        jsonObject.put("userId", "");

        User user = new User();
        user.setUserName(name);
        user.setPassword(psw);
        user.setUserId(MD5CodeCeator.randomUUID());

        int result = userMapper.insert(user);
        if(result > 0){
            String rtoken = MD5CodeCeator.randomUUID();

            Boolean a = redisUtil.set(rtoken, name, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            Boolean b = redisUtil.set(name, rtoken, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
            if (a && b){
                token = rtoken;
                jsonObject.put("token", token);
                jsonObject.put("userId", user.getUserId());
            }
        }

        return jsonObject;
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
    public User getUserById(String id) {
        return null;
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
