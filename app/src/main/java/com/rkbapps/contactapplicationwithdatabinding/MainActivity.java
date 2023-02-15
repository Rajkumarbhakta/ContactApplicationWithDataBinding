package com.rkbapps.contactapplicationwithdatabinding;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.rkbapps.contactapplicationwithdatabinding.databinding.ActivityMainBinding;
import com.rkbapps.contactapplicationwithdatabinding.databinding.AddContactDialogBinding;
import com.rkbapps.contactapplicationwithdatabinding.db.Contact;
import com.rkbapps.contactapplicationwithdatabinding.db.ContactDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    private ContactDatabase contactDatabase;
    private List<Contact> contactList = new ArrayList<>();

    private MyAdapter adapter;
    private final String[] colorList = {"ffbe0b", "fb5607", "ff006e", "8338ec", "3a86ff", "ffadad", "FFD6A5", "CAFFBF", "9BF6FF", "A0C4FF", "BDB2FF", "FFC6FF"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        in view binding we do setContentView in different way
        ///setContentView(R.layout.activity_main);
         */

        //setting the contentView using Binding class
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        /*
        now we can call the views without finding their ids.
         */

        setSupportActionBar(activityMainBinding.toolBar);


        contactDatabase = Room.databaseBuilder(getApplicationContext(), ContactDatabase.class, "ContactDB")
                .allowMainThreadQueries()
                .build();


        /*
        Invoking the recycler view by directly access by its XML id
         */
        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        recycler view adapter
         */

        adapter = new MyAdapter(this, contactList);
        setData();
        activityMainBinding.recyclerView.setAdapter(adapter);


        activityMainBinding.ebAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(MainActivity.this);
                AddContactDialogBinding addContactDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.add_contact_dialog, null, false);
                d.setContentView(addContactDialogBinding.getRoot());


                addContactDialogBinding.btnSave.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = addContactDialogBinding.etName.getText().toString().trim();
                                String mobileNumber = addContactDialogBinding.etMobileNumber.getText().toString().trim();
                                String email = addContactDialogBinding.etEmail.getText().toString().trim();
                                Random random = new Random();
                                int x = random.nextInt(colorList.length);

                                Contact c = new Contact(name, mobileNumber, email, colorList[x]);

                                addContactDialogBinding.setContact(new Contact());

                                contactDatabase.getContactDao().addContact(c);
                                setData();
                                d.dismiss();
                            }
                        });
                addContactDialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });

        setItemTouchHelper();

    }

    private void setItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                contactDatabase.getContactDao().deleteContact(contactList.get(position));
                contactList.remove(contactList.get(position));
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(activityMainBinding.recyclerView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        contactList.clear();
        contactList.addAll(contactDatabase.getContactDao().getAllContacts());
        adapter.notifyDataSetChanged();
    }
}