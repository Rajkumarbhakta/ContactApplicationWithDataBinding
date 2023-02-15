package com.rkbapps.contactapplicationwithdatabinding.db;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.rkbapps.contactapplicationwithdatabinding.BR;

@Entity(tableName = "Contact")
public class Contact extends BaseObservable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "mobile_num")
    private String mobileNumber;

    @ColumnInfo(name = "email")
    private String emailId;

    @ColumnInfo(name = "contactColor")
    private String contactColor;

    @Ignore
    public Contact() {
    }

    public Contact(String name, String mobileNumber, String emailId, String contactColor) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.contactColor = contactColor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        notifyPropertyChanged(BR.mobileNumber);
    }

    @Bindable
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
        notifyPropertyChanged(BR.emailId);
    }

    public String getContactColor() {
        return contactColor;
    }

    public void setContactColor(String contactColor) {
        this.contactColor = contactColor;
    }

}
