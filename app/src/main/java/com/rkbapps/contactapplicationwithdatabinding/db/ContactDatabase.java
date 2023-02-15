package com.rkbapps.contactapplicationwithdatabinding.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    public abstract ContactDAO getContactDao();
}
