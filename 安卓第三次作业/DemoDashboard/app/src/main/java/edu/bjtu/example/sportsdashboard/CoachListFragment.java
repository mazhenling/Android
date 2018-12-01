package edu.bjtu.example.sportsdashboard;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CoachListFragment extends ListFragment {

    String[] players = {"Michael Porter"};
    String[] experience = {
            "Perfection is not attainable, but if we chase perfection we can catch excellence",

    };
    int[] images = {R.drawable.michael_porter};

    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        //MAP
        HashMap<String, String> map = new HashMap<String, String>();

        //FILL
        for (int i = 0; i < players.length; i++) {
            map = new HashMap<String, String>();
            map.put("Player", players[i]);
            map.put("Info",experience[i]);
            map.put("Image", Integer.toString(images[i]));

            data.add(map);
        }

        //KEYS IN MAP
        String[] from = {"Player","Info", "Image"};

        //IDS OF VIEWS
        int[] to = {R.id.nameTxt, R.id.infoTxt, R.id.imageView1};

        //ADAPTER
        adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.trainerlist_item, from, to);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        getListView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub

                Toast.makeText(getActivity(), data.get(pos).get("Player"), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
