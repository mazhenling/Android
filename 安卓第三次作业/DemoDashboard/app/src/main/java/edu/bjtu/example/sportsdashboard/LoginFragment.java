package edu.bjtu.example.sportsdashboard;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_WRITEABLE;

public class LoginFragment extends Fragment {
    private Button cancelBtn, loginBtn, registerBtn;
    private EditText userEditText, pwdEditText;

    private String result;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login, container, false);
        getActivity().setTitle("登录");
        cancelBtn = (Button) view.findViewById(R.id.cancelButton);
        loginBtn = (Button) view.findViewById(R.id.loginButton);
        registerBtn = (Button) view.findViewById(R.id.register);
        userEditText = (EditText) view.findViewById(R.id.userEditText);
        pwdEditText = (EditText) view.findViewById(R.id.pwdEditText);

        userEditText.requestFocus();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new RegisterFragment(),null).commit();


            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                // 检查用户是否输入用户名密码
                if (validate()) {
                    // 检查是否登陆成功
                    try {
                        if (login()) {
                            Toast.makeText( getActivity(), "成功", Toast.LENGTH_SHORT).show();
                            //getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                             //       new IndexFragment()).commit();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new MainMenuFragment(),null)
                                    .commit();
                        } else {
                            Toast.makeText(getActivity(), "用户名称或者密码错误，请重新输入！",
                                    Toast.LENGTH_SHORT).show();
                            result = null;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }

    // 验证用户名密码是否为空
    private boolean validate() {
        String username = userEditText.getText().toString();
        if (username.equals("")) {
            Toast.makeText(getActivity(), "用户名称是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        String pwd = pwdEditText.getText().toString();
        if (pwd.equals("")) {
            Toast.makeText(getActivity(), "用户密码是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    // 登录方法
    private boolean login() throws JSONException {
        // 获得用户名称
        String username = userEditText.getText().toString().trim();
        // 获得密码
        String pwd = pwdEditText.getText().toString().trim();
        // 获得登录结果
        query(username, pwd);

        boolean flag = false;
        JSONObject object = null;
        while(true) {
            if (result != null) {
                object = new JSONObject(result);
                long code = object.getLong("code");
                if (code != 0) {
                    flag = false;
                } else {
                    //System.out.println("LoginActivity result: " + result);
                    // 将此服务器返回的此用户信息保存起来
                    //saveUserMsg(result);
                    object = new JSONObject(object.getString("data"));
                    object = new JSONObject(object.getString("info"));
                    long id = object.getLong("id");
                    String account = object.getString("account");
                    String email = object.getString("email");
                    String phone = object.getString("phone");
                    String trainerList = object.getString("trainer");
                    saveUserMsg(trainerList);
                    //可以继续...

                    flag = true;
                }
                return flag;
            }
        }
    }

    //请求后的回调接口
    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            //setResult(e.getMessage(), false);
            result = e.getMessage();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            //setResult(response.body().string(), true);
            result = response.body().string();
        }
    };

    //简单的带参数和Header的post请求
    public void query(String account, String password) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("account", account)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://47.94.252.84:80/site/index")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    // 将用户信息保存到配置文件
    private void saveUserMsg(String msg) {
        SharedPreferences pre = getActivity().getSharedPreferences("user_msg.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        // 获得信息数组
        String[] trainer = msg.split(";");
        for(int i = 0;i<trainer.length;i++){
            // 共享信息
            editor.putString("trainer"+i, trainer[i]);
            editor.commit();
        }
    }
}
