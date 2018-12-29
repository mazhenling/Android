package edu.bjtu.example.sportsdashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class RegisterFragment extends Fragment {
    private String result, message;
    private Button cancelBtn, registerBtn;
    private EditText usernameEditText, passwordEditText;
    private EditText passwordConfirmEditText, emailEditText, phoneEditText;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register, container, false);
        getActivity().setTitle("注册");

        cancelBtn = (Button) view.findViewById(R.id.no);
        registerBtn = (Button) view.findViewById(R.id.yes);
        usernameEditText = (EditText) view.findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
        passwordConfirmEditText = (EditText) view.findViewById(R.id.passwordConfirmEditText);
        emailEditText = (EditText) view.findViewById(R.id.passwordConfirmEditText);
        phoneEditText = (EditText) view.findViewById(R.id.phoneEditText);

        usernameEditText.requestFocus();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new LoginFragment())
                        .commit();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                // 检查用户是否输入信息
                if (validate()) {
                    // 检查是否注册成功
                    try {
                        if (register()) {
                            Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new LoginFragment())
                                    .commit();
                        } else Toast.makeText(getActivity(), "错误:" + message,
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }

    private boolean validate() {
        String username = usernameEditText.getText().toString();
        if (username.equals("")) {
            Toast.makeText(getActivity(), "用户名称是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        String pwd = passwordEditText.getText().toString();
        if (pwd.equals("")) {
            Toast.makeText(getActivity(), "用户密码是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        String pwdCon = passwordConfirmEditText.getText().toString();
        if (pwdCon.equals("")) {
            Toast.makeText(getActivity(), "密码验证是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        String email = emailEditText.getText().toString();
        if (email.equals("")) {
            Toast.makeText(getActivity(), "邮箱是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        String phone = phoneEditText.getText().toString();
        if (phone.equals("")) {
            Toast.makeText(getActivity(), "电话是必填项！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if(!pwd.equals(pwdCon)){
            Toast.makeText(getActivity(), "两次输入密码不一致！", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    // 注册方法
    private boolean register() throws JSONException {
        // 获得用户名称
        String username = usernameEditText.getText().toString().trim();
        // 获得密码
        String pwd = passwordEditText.getText().toString().trim();
        // 获得密码
        String pwdCon = passwordConfirmEditText.getText().toString().trim();
        // 获得邮箱地址
        String email = emailEditText.getText().toString().trim();
        // 获得电话
        String phone = phoneEditText.getText().toString().trim();

        // 获得注册结果
        query(username, pwd, pwdCon, email, phone);

        boolean flag = false;
        JSONObject object = null;
        while(true) {
            if(result != null){
                object = new JSONObject(result);
                long code = object.getLong("code");
                if (code != 0) {
                    message = object.getString("message");
                    flag = false;
                } else {
                    //System.out.println("LoginActivity result: " + result);
                    // 将此服务器返回的此用户信息保存起来
                    //saveUserMsg(result);
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
    public void query(String account, String password, String passwordConfirm, String email, String phone) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("account", account)
                .add("password", password)
                .add("passwordConfirm", password)
                .add("email", email)
                .add("phone", phone)
                .build();
        Request request = new Request.Builder()
                .url("http://47.94.252.84:80/site/register")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
