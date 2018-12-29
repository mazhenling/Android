package edu.bjtu.example.sportsdashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class QQActivity extends AppCompatActivity {
    public Button bt,zhuce;
    private EditText edit_user,edit_pass;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String username,userpass,userId;
    private Tencent mTencent;
    private Button QQ,map;

    private String openidString;
    private Button QQlogin;
    private TextView nickname,gender,province,year;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qq);
//QQ第三方登录
        mTencent = Tencent.createInstance("1107992991",getApplicationContext());//这里的“123123123”改为自己的Appid
        QQlogin = (Button) findViewById(R.id.qqLogin);
        QQlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get_simple_userinfo
                mTencent.login(QQActivity.this,"all",new BaseUiListener());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());

        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }

    }

    private class BaseUiListener implements IUiListener {
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            /*
             * 下面隐藏的是用户登录成功后 登录用户数据的获取的方法
             * 共分为两种  一种是简单的信息的获取,另一种是通过UserInfo类获取用户较为详细的信息
             *有需要看看
             * */
          try {
                //获得的数据是JSON格式的，获得你想获得的内容
                //如果你不知道你能获得什么，看一下下面的LOG
                Log.v("----TAG--", "-------------"+response.toString());
                openidString = ((JSONObject) response).getString("openid");
                mTencent.setOpenId(openidString);

                mTencent.setAccessToken(((JSONObject) response).getString("access_token"),((JSONObject) response).getString("expires_in"));


                Log.v("TAG", "-------------"+openidString);
                //access_token= ((JSONObject) response).getString("access_token");              //expires_in = ((JSONObject) response).getString("expires_in");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /**到此已经获得OpneID以及其他你想获得的内容了
             QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
             sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
             如何得到这个UserInfo类呢？  */

            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(getApplicationContext(), qqToken);

            //    info.getUserInfo(new BaseUIListener(this,"get_simple_userinfo"));
            info.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    //用户信息获取到了

                    try {

                        Toast.makeText(getApplicationContext(), ((JSONObject) o).getString("nickname")+((JSONObject) o).getString("gender"), Toast.LENGTH_SHORT).show();
                        Log.v("UserInfo",o.toString());
                        SharedPreferences pre = getSharedPreferences("user_msg.txt", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pre.edit();
                        editor.putString("nickname", ((JSONObject) o).getString("nickname"));
                        editor.putString("gender", ((JSONObject) o).getString("gender"));
                        editor.putString("province", ((JSONObject) o).getString("province"));
                        editor.putString("city", ((JSONObject) o).getString("city"));
                        editor.putString("year", ((JSONObject) o).getString("year"));
                        editor.commit();
                        nickname = (TextView) findViewById(R.id.nickname);
                        nickname.setText(((JSONObject) o).getString("nickname"));
                        nickname.setVisibility(View.VISIBLE);
                        gender = (TextView) findViewById(R.id.gender);
                        gender.setText(((JSONObject) o).getString("gender"));
                        gender.setVisibility(View.VISIBLE);
                        province = (TextView) findViewById(R.id.province);
                        province.setText(((JSONObject) o).getString("province"));
                        province.setVisibility(View.VISIBLE);
                        year = (TextView) findViewById(R.id.year);
                        year.setText(((JSONObject) o).getString("year"));
                        year.setVisibility(View.VISIBLE);
//                        Intent intent1 = new Intent(QQActivity.this,MainActivity.class);
//                        startActivity(intent1);
//                        finish();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    Log.v("UserInfo","onError");
                }

                @Override
                public void onCancel() {
                    Log.v("UserInfo","onCancel");
                }
            });

        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "onError", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "onCancel", Toast.LENGTH_SHORT).show();
        }


    }

}
