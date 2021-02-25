package com.shay.aipets.utils;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.gson.Gson;
import com.shay.aipets.myexceptions.MyException;

import java.util.HashMap;
import java.util.Set;

//
public class CloopenUtil {

    //生产环境请求地址：app.cloopen.com
    static final String serverIp = "app.cloopen.com";
    //请求端口
    static final String serverPort = "8883";
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    static final String accountSId = "8aaf0708762cb1cf0176b339f6dc31b2";
    static final String accountToken = "ca5ec2c69f2a48d7b49feb8d93a686c3";
    //请使用管理控制台中已创建应用的APPID
    static final String appId = "8aaf0708762cb1cf0176b339f7b631b8";
    static final  String templateId= "1";

    /**@Param phoneNum要接收的号码 code验证码 time分钟为单位
     * */
    public static void send(String phoneNum, String code, String time) throws MyException {
        String to = phoneNum;

        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);


        String[] datas = {code, time};
        //String subAppend="1234";  //可选 扩展码，四位数字 0~9999
        //String reqId="fadfafas";  //可选 第三方自定义消息id，最大支持32位英文数字，同账号下同一自然天内不允许重复
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        //HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas,subAppend,reqId);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息

            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            throw new MyException("短信服务失效");
        }
    }
}
