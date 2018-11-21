package edu.bjtu.example.sportsdashboard;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainMenuFragment extends Fragment {
    private RecyclerView mRecyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_menu, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recyclerView);
        //设置LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //绑定adapter
        MyAdapter myAdapter = new MyAdapter(getActivity());
        mRecyclerView.setAdapter(myAdapter);
        return view;
    }
    //adapter
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        LayoutInflater mInflater;
        List<MData> mList = addData();

        public MyAdapter(Activity context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_my_recyclerview,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mName.setText(mList.get(position).getUserName());
            holder.mDescribe.setText(mList.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mName;
            private TextView mDescribe;
            public ViewHolder(View itemView) {
                super(itemView);
                mName = itemView.findViewById(R.id.txt_name);
                mDescribe = itemView.findViewById(R.id.txt_describe);
            }
        }
    }
    //制造一个数据源
    private class MData{
        String userName;
        String description;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
    private List<MData> addData(){
        List<MData> list = new ArrayList();

        SharedPreferences pre = getActivity().getSharedPreferences("user_msg.txt", Context.MODE_PRIVATE);
        String []trainer={"","","","",""};
        for(int i = 0;i<5;i++){
            // 共享信息
            trainer[i]=pre.getString("trainer"+i, "");
        }

        MData mData = new MData();
        mData.setUserName(trainer[0]);
        mData.setDescription("一个很厉害的人");
        list.add(mData);

        mData = new MData();
        mData.setUserName(trainer[1]);
        mData.setDescription("呵呵");
        list.add(mData);

        mData = new MData();
        mData.setUserName(trainer[2]);
        mData.setDescription("嘻嘻");
        list.add(mData);

        mData = new MData();
        mData.setUserName(trainer[3]);
        mData.setDescription("呵呵");
        list.add(mData);

        mData = new MData();
        mData.setUserName(trainer[4]);
        mData.setDescription("地主家的傻儿子");
        list.add(mData);
        return list;
    }
}


