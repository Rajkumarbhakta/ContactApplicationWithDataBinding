package com.rkbapps.contactapplicationwithdatabinding.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert
    public long addContact(Contact contact);

    @Update
    public void updateContact(Contact contact);

    @Delete
    public void deleteContact(Contact contact);

    @Query("select * from Contact")
    public List<Contact> getAllContacts();

    @Query("select * from Contact where id==:enterId")
    public  Contact getContact(long enterId);
}
