package com.young.study.manager;

import android.content.Context;
import android.util.Log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.OkHttpClient;
import com.young.study.Bmob.Person;
import com.young.study.MyApplication;

/**
 * Created by yang.tan on 2017/11/1.
 */

public class UserManager {

    private static final String TAG = "UserManager";
    private static UserManager instance = null;

    private String user_name;
    private String user_pwd;
    private String user_nick;
    private BmobFile user_head;
    private String user_email;
    private String user_addr;
    private String yx_account;
    private String yx_token;

    private static Person myPerson = null;

    /**
     * 网易云信 app_key
     */
    private static final String YX_APP_KEY = "2e77d83708cbeae80b19a6505de83400";
    private static final String YX_APP_SECRET = "797a2d85a3e9";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String yx_create_url = "https://api.netease.im/nimserver/user/create.action";
    private static final String yx_update_url = "https://api.netease.im/nimserver/user/updateUinfo.action";

    private static OkHttpClient okHttpClient = new OkHttpClient();


    private UserManager(){
    }


    // 登录借口封装
    public void login(String name,String pwd,final UserListener listener){
        BmobUser.loginByAccount(name, pwd, new LogInListener<Person>() {
            @Override
            public void done(final Person person, BmobException e) {
                if (e == null){
                    init();
                    listener.onSuccess();
//                    loginYX(person.getYx_account(),person.getYx_token(),listener);
                } else {
                    release();
                    if (listener != null) {
                        listener.onFailed(e);
                    }
                }
            }
        });
    }

    public void loginYX(String accid,String token,final UserListener listener){
    }

    // 退出登录当前账号封装
    public void logOut(){
        BmobUser.logOut();
        release();
        MyApplication.getInstance().exit();
    }

    // 更新用户信息封装
    public void update(final UserListener listener){
        myPerson.setYx_token(yx_token);
        myPerson.setYx_account(yx_account);
        myPerson.setUser_addr(user_addr);
        myPerson.setUser_nick(user_nick);
        myPerson.setPassword(user_pwd);
        myPerson.setEmail(user_email);
        myPerson.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                } else {
                    if (listener != null){
                        listener.onFailed(e);
                    }
                }
            }
        });
    }

    public void upload(final Context context, final UserListener listener){
        user_head.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }

            @Override
            public void doneError(int code, String msg) {
                super.doneError(code, msg);
            }
        });
    }


    public class Yunxin{
        public String account;
        public String token;
    }



/*    public Yunxin createYX(String name,String nick){
        Log.d(TAG,"name = "+name);
        Yunxin yunxin = new Yunxin();
        String nonce = String.valueOf(new Random(99999).nextInt());
        String currentTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(YX_APP_SECRET,nonce,currentTime);

        String result = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("AppKey", YX_APP_KEY);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", currentTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        Log.d(TAG,"name 1 = "+name);
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", name));
        nvps.add(new BasicNameValuePair("name",nick));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 打印执行结果
        try {
            // 执行请求
            HttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            Log.d(TAG,"result = aaaa = " + result);
            JSONObject object = new JSONObject(result);
            String accid = object.getJSONObject("info").optString("accid");
            String token = object.getJSONObject("info").optString("token");
            yunxin.account = accid;
            yunxin.token = token;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"result = 11111"+result);

        return yunxin;
    }*/

    //创建网易云信账号和 token


    public interface UserListener{
        void onSuccess();
        void onFailed(BmobException e);
    }



    public boolean init(){
        myPerson = BmobUser.getCurrentUser(Person.class);
        if (myPerson == null) {
            return false;
        }
        user_name = myPerson.getUsername();
        user_addr = myPerson.getUser_addr();
        user_email = myPerson.getEmail();
        user_head = myPerson.getUser_headicon();
        user_nick = myPerson.getUser_nick();
        yx_account = myPerson.getYx_account();
        yx_token = myPerson.getYx_token();
        Log.d(TAG,"yx_account ="+yx_account+",,,yx_token= "+yx_token);
        return true;
    }

    public void release(){
        myPerson = null;
        user_name = null;
        user_nick = null;
        user_head = null;
        user_email = null;
        user_addr = null;
        user_pwd = null;
        yx_account = null;
        yx_token = null;
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setUser_name(String name){
        user_name = name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_head(BmobFile user_head) {
        this.user_head = user_head;
    }

    public BmobFile getUser_head() {
        return user_head;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_addr(String user_addr) {
        this.user_addr = user_addr;
    }

    public String getUser_addr() {
        return user_addr;
    }

    public void setYx_account(String yx_account) {
        this.yx_account = yx_account;
    }

    public String getYx_account() {
        return yx_account;
    }

    public void setYx_token(String yx_token) {
        this.yx_token = yx_token;
    }

    public String getYx_token() {
        return yx_token;
    }
}
