package com.example.tadje.gpsproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tadje.gpsproject.Persistence.AppDatabase;
import com.example.tadje.gpsproject.ViewPager.UserViewAdapter;
import com.example.tadje.gpsproject.model.User;

import java.util.ArrayList;


/**
 * Created by tadje on 17.05.2018.
 */
@SuppressLint("ValidFragment")
public class UserFragment extends Fragment {

    protected MainActivity app;

    private Button addUserButton;
    private EditText userID;
    private EditText lastnameUser;
    private EditText firstnameUser;
    private EditText birthdayUser;

    public String title;
    public int page;

    private UserViewAdapter uAdapter;
    private RecyclerView uRecyclerView;
    private RecyclerView.LayoutManager uLayoutManager;
    private ArrayList<User> userList;
    FloatingActionButton floatingActionButton;


    private UserFragment.OnFragmentInteractionListener eListener;


    public UserFragment() {
        // Required empty public constructor
    }


    public static UserFragment newInstance(int page, String title) {

        UserFragment userfragment = new UserFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        userfragment.setArguments(args);
        return userfragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 1);
        title = getArguments().getString("someTitle");
  //      floatingActionButton = this.getActivity().findViewById(R.id
   //             .positionButton);
//        floatingActionButton.setVisibility(View.INVISIBLE);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);


        addUserButton = view.findViewById(R.id.addUserButton);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserDialog();
            }
        });

        userList = (ArrayList<User>) AppDatabase.getInstance().userDao().getAll();


        if (userList != null) {
            uRecyclerView = view.findViewById(R.id.recyclerviewUser);
            uRecyclerView.setHasFixedSize(true);

            uLayoutManager = new LinearLayoutManager(getActivity());
            uRecyclerView.setLayoutManager(uLayoutManager);

            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                uAdapter = new UserViewAdapter(userList, this);
            }

            uRecyclerView.setAdapter(uAdapter);
            uAdapter.notifyDataSetChanged();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        floatingActionButton.setVisibility(View.INVISIBLE);
    }

    public void addUserDialog() {

        AlertDialog.Builder addUserDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogViewUser = inflater.inflate(R.layout.add_user_dialog,
                null);
        addUserDialog.setView(dialogViewUser);

        lastnameUser = dialogViewUser.findViewById(R.id.userLastnameEdit);
        firstnameUser = dialogViewUser.findViewById(R.id.userFirstnameEdit);
        birthdayUser = dialogViewUser.findViewById(R.id.userBirthday);


        addUserDialog.setTitle(getString(R.string.addUser));

        addUserDialog.setPositiveButton((getString(R.string.addUser)), new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addUser();
                    }
                }
        );

        addUserDialog.setNegativeButton(getString(R.string.cancel), null);
        AlertDialog alertDialogEmp = addUserDialog.create();
        alertDialogEmp.show();
    }


    public void addUser() {

        String lastnameUserStr;
        String firstnameUserStr;
        String birthdayStr;


        lastnameUserStr = lastnameUser.getText().toString();
        firstnameUserStr = firstnameUser.getText().toString();
        birthdayStr = String.valueOf(birthdayUser.getText());


        if ((TextUtils.isEmpty(lastnameUser.getText()))
                || (TextUtils.isEmpty(firstnameUser.getText()))
                || (TextUtils.isEmpty(birthdayUser.getText()))) {

            Toast.makeText(getActivity(), (getString(R.string.fillFields)), Toast.LENGTH_LONG)
                    .show();

        }

        User user = new User(lastnameUserStr, firstnameUserStr, birthdayStr);

        AppDatabase.getInstance().userDao().insertAll(user);
        userList = (ArrayList<User>) AppDatabase.getInstance().userDao().getAll();
        listToAdapter(userList);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserFragment.OnFragmentInteractionListener) {
            eListener = (UserFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        eListener = null;
    }


    private void listToAdapter(ArrayList<User> userList) {
        uAdapter.setDataset(userList);
        uAdapter.notifyDataSetChanged();
    }

    public void selectUser(User userForList) {
        int selectUserID = userForList.getId();
        TripManager.getInstance().setSelectedUserID(selectUserID);
        Toast.makeText(getActivity(), (getString(R.string.selectedUser)+selectUserID+" "), Toast
                .LENGTH_LONG)
                .show();
    }


    public interface OnFragmentInteractionListener {
    }
}
