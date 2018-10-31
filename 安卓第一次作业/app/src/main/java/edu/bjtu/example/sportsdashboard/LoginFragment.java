package edu.bjtu.example.sportsdashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Button se;
        //ac_fargment_a为fragment当前布局
        View view=inflater.inflate(R.layout.login,null);
        //绑定该LinearLayout的ID
        se=(Button) view.findViewById(R.id.button6);
        //设置监听
//        se.setOnClickListener(new View.OnClickListener()
//        {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onClick(View view) {
//                //android.support.v4.app.FragmentManager fm = getActivity().getFragmentManager();
//                //注意v4包的配套使用
//                Fragment fragment = new Fragment();
//                fm.beginTransaction().replace(R.layout.activity_register2, fragment).commit();
//            }
//        });

        return view;
    }
}

