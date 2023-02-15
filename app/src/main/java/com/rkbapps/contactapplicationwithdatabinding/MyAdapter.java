package com.rkbapps.contactapplicationwithdatabinding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rkbapps.contactapplicationwithdatabinding.databinding.ContactViewBinding;
import com.rkbapps.contactapplicationwithdatabinding.db.Contact;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Contact> contactList = new ArrayList<>();

    public MyAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ContactViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact c = contactList.get(position);

        /*
        setting the data binding variable
         */
        holder.contactViewBinding.setContact(c);

        String name = contactList.get(position).getName();
        char ch[]=name.toCharArray();
        holder.contactViewBinding.fristLetter.setText(""+ch[0]);

        holder.contactViewBinding.cardView.setCardBackgroundColor(Color.parseColor("#"+c.getContactColor()));

        holder.contactViewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context,ContactViewActivity.class);
                i.putExtra("id",c.getId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ContactViewBinding contactViewBinding;
        public MyViewHolder(@NonNull ContactViewBinding itemView) {
            super(itemView.getRoot());
            contactViewBinding = itemView;
            /*
            As we using data binding we do not need to call the findViewById.
            we can access the views by using the ContactViewBinding class
             */
        }
    }
}
