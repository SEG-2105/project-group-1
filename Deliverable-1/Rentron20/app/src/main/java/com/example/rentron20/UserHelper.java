package com.example.rentron20;

import static com.example.rentron20.UserModel.UNASSIGNED_ADDRESS;
import static com.example.rentron20.UserModel.UNASSIGNED_ID;
import static com.example.rentron20.UserModel.UNNASSIGNED_BIRTHYEAR;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "user";
    public static final String ID_COLUMN = "userID";
    public static final String FIRSTNAME_COLUMN = "firstName";
    public static final String LASTNAME_COLUMN = "lastName";
    public static final String EMAILADDRESS_COLUMN = "emailAddress";
    public static final String TYPE_COLUMN = "type";

    public UserHelper(@Nullable Context context) {
        super(context, TABLE_NAME+"s.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreationStatement= "CREATE TABLE " + TABLE_NAME + "(" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," + TYPE_COLUMN + " TEXT," + FIRSTNAME_COLUMN + " TEXT, " +
                LASTNAME_COLUMN + " TEXT, " +EMAILADDRESS_COLUMN+"TEXT);";
        db.execSQL(tableCreationStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void addUser(UserModel user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(FIRSTNAME_COLUMN,user.getFirstName());
        cv.put(LASTNAME_COLUMN,user.getLastName());
        cv.put(EMAILADDRESS_COLUMN,user.getEmailAddress());
        cv.put(TYPE_COLUMN,user.getType());
        if(user.getId()!=UNASSIGNED_ID){
            cv.put(ID_COLUMN,user.getId());
        }
        db.insert(TABLE_NAME,null,cv);

    }
    public List<UserModel> getUsers(String query){
        SQLiteDatabase db=this.getReadableDatabase();
        List<UserModel> users=new ArrayList<>();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                UserModel user=new UserModel(cursor.getInt(0)//id
                        ,cursor.getString(1)//Type
                        , cursor.getString(2)//First Name
                        , cursor.getString(3)//Last Name
                        ,cursor.getString(4));// Email
                users.add(user);
            }while(cursor.moveToNext());
        }
        return users;
    }
    public List<UserModel> getAllUsers() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getUsers(query);
    }
}
