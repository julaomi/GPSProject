package com.example.tadje.gpsproject.ViewPager;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tadje.gpsproject.R;
import com.example.tadje.gpsproject.UserFragment;
import com.example.tadje.gpsproject.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tadje on 17.05.2018.
 */
 public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.ViewHolder> {
        ArrayList<User> uDataset;
        UserFragment uParent;


        public UserViewAdapter(ArrayList<User> userList, UserFragment parent) {
            this.uDataset = userList;
            this.uParent = parent;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R
                            .layout.view_holder_user,
                    parent, false);


            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final User userForList = uDataset.get(position);
            holder.uUserID.setText( String.format("%d", userForList.getId()));
            holder.uFirstname.setText(userForList.getFirstname());
            holder.uLastname.setText(userForList.getLastname());
            holder.uBirthday.setText(userForList.getBirthday());


            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.itemView.setTag(position);
                    uParent.selectUser(userForList);
                }
            });

            holder.container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    holder.itemView.setTag(position);
                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return this.uDataset.size();
        }

        public void setDataset(ArrayList<User> userList) {
            uDataset = userList;
        }

        public interface OnItemRemoveListener {
        }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView uUserID;
        TextView uLastname;
        TextView uFirstname;
        TextView uBirthday;
        ViewGroup container;

        ViewHolder(View itemView) {
            super(itemView);
            uUserID = itemView.findViewById(R.id.viewID);
            uLastname = itemView.findViewById(R.id.viewLastname);
            uFirstname = itemView.findViewById(R.id.viewFirstname);
            uBirthday = itemView.findViewById(R.id.viewBirthday);
            container = itemView.findViewById(R.id.viewHolderContainer);
        }
    }
}


