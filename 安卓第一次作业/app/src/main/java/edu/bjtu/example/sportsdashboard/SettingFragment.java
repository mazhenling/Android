package edu.bjtu.example.sportsdashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.bjtu.example.sportsdashboard.dummy.DummyContent;
import edu.bjtu.example.sportsdashboard.dummy.DummyContent.DummyItem;

import java.util.List;


public class SettingFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}
