package com.example.tadje.gpsproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tadje on 02.05.2018.
 */

@SuppressLint("ValidFragment")
public class SettingFragment extends Fragment {

    public String title;
    public int page;

    public static SettingFragment newInstance(int page, String title) {

        SettingFragment settingFragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        settingFragment.setArguments(args);
        return settingFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 2);
        title = getArguments().getString("someTitle");
        FloatingActionButton floatingActionButton = this.getActivity().findViewById(R.id
                .positionButton);
        floatingActionButton.setVisibility(View.INVISIBLE);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        return view;
    }

    public interface OnFragmentInteractionListener {
    }

}
