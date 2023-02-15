package com.rkbapps.contactapplicationwithdatabinding;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.rkbapps.contactapplicationwithdatabinding.databinding.ActivityContactViewBinding;
import com.rkbapps.contactapplicationwithdatabinding.databinding.AddContactDialogBinding;
import com.rkbapps.contactapplicationwithdatabinding.db.Contact;
import com.rkbapps.contactapplicationwithdatabinding.db.ContactDatabase;

public class ContactViewActivity extends AppCompatActivity {

    ActivityContactViewBinding activityContactViewBinding;
    private ContactDatabase contactDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contact_view);
        long id = getIntent().getLongExtra("id", 0);
        contactDatabase = Room.databaseBuilder(getApplicationContext(), ContactDatabase.class, "ContactDB")
                .allowMainThreadQueries()
                .build();


        Contact con = contactDatabase.getContactDao().getContact(id);


        activityContactViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_view);
        activityContactViewBinding.setContact(con);


        activityContactViewBinding.contactAvtar.setText("" + setFirstLetter(con));


        activityContactViewBinding.cardView.setCardBackgroundColor(Color.parseColor("#" + con.getContactColor()));


        activityContactViewBinding.btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(ContactViewActivity.this);
                AddContactDialogBinding addContactDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.add_contact_dialog, null, false);
                d.setContentView(addContactDialogBinding.getRoot());
                addContactDialogBinding.setContact(con);
                addContactDialogBinding.btnSave.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                contactDatabase.getContactDao().updateContact(con);
                                Toast.makeText(ContactViewActivity.this, "Contact Updated", Toast.LENGTH_SHORT).show();

                                activityContactViewBinding.contactAvtar.setText("" + setFirstLetter(con));

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

        activityContactViewBinding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+91" + con.getMobileNumber()));
                startActivity(Intent.createChooser(i, "Call"));
            }
        });

        activityContactViewBinding.btnMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("sms:+91" + con.getMobileNumber()));
                startActivity(Intent.createChooser(i, "Sent massage"));
            }
        });

        activityContactViewBinding.btnVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+91" + con.getMobileNumber()));
                startActivity(Intent.createChooser(i, "Video Call"));
            }
        });

    }

    private char setFirstLetter(Contact contact) {
        char[] ch = contact.getName().toCharArray();
        return ch[0];
    }

}