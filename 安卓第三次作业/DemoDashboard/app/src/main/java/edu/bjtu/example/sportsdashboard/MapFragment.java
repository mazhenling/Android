package edu.bjtu.example.sportsdashboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

public class MapFragment extends Fragment {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        getActivity().setTitle("地图");
        view = inflater.inflate(R.layout.map_activity, container, false);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现


        //初始化控件
        initView();
        return view;
    }

    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.bmapView);

        //获取百度地图
        mBaiduMap = mMapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //开启交通图
        mBaiduMap.setTrafficEnabled(true);

        //卫星地图
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }*/

}
