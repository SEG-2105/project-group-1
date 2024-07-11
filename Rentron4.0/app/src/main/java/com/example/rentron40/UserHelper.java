package com.example.rentron40;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "user";
    public static final String ID_COLUMN = "id";
    public static final String TYPE_COLUMN = "type";
    public static final String FIRSTNAME_COLUMN = "firstname";
    public static final String LASTNAME_C0LUMN = "lastname";
    public static final String EMAILADDRESS_COLUMN = "emailaddress";
    public static final String PASSWORD_COLUMN = "password";
    public static final String BIRTHYEAR_COLUMN = "birthyear";
    public static final String ADDRESS_COLUMN = "address";

    public UserHelper(@Nullable Context context) {
        super(context, TABLE_NAME+"s.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreationStatement= "CREATE TABLE " + TABLE_NAME + "(" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TYPE_COLUMN + " TEXT," + FIRSTNAME_COLUMN + " TEXT, " + LASTNAME_C0LUMN + " TEXT, " + EMAILADDRESS_COLUMN + " TEXT UNIQUE, "
                + PASSWORD_COLUMN + " TEXT, " + BIRTHYEAR_COLUMN + " INTEGER, " + ADDRESS_COLUMN + " TEXT);";
        db.execSQL(tableCreationStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(UserModel user){
        if(findEmail(user.getEmailAddress()).isEmpty()){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(TYPE_COLUMN,user.getType());
            cv.put(FIRSTNAME_COLUMN,user.getFirstName());
            cv.put(LASTNAME_C0LUMN,user.getLastName());
            cv.put(EMAILADDRESS_COLUMN,user.getEmailAddress());
            cv.put(PASSWORD_COLUMN,user.getPassword());
            cv.put(BIRTHYEAR_COLUMN,user.getBirthYear());
            cv.put(ADDRESS_COLUMN,user.getAddress());
            if(UserModel.UNASSIGNED_ID != user.getId()){
                cv.put(ID_COLUMN,user.getId());
            }
            db.insert(TABLE_NAME,null,cv);
            return true;
        }
        return false;
    }

    public List<UserModel> getUsers(String query){
        SQLiteDatabase db=this.getReadableDatabase();
        List<UserModel> users=new ArrayList<UserModel>();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                UserModel user = new UserModel(
                        cursor.getInt(0), //ID
                        cursor.getString(1), //Type
                        cursor.getString(2), //First Name
                        cursor.getString(3),//lastname
                        cursor.getString(4),//email
                        cursor.getString(5),//password
                        cursor.getString(6),//address
                        cursor.getInt(7) //birthyear
                );
                users.add(user);
            }while(cursor.moveToNext());
        }
        return users;
    }
    public List<UserModel> getAllUser(){
        String query ="SELECT * FROM "+ TABLE_NAME;
        return getUsers(query);
    }
    public List<UserModel> findUsers(String firstname,String lastname,String email,String password,String type){
        String query="SELECT * FROM "+TABLE_NAME;
        boolean selectByFirstname=false;
        if(!firstname.isEmpty()){
            query +=" WHERE "+FIRSTNAME_COLUMN + " LIKE \"%"+firstname+"%\""+"--case-insensitive";
            selectByFirstname=true;
        }
        boolean selectBylastname=false;
        if(!lastname.isEmpty()){
            if(selectByFirstname){
                query+=" AND "+LASTNAME_C0LUMN+" LIKE \"%"+lastname+"%\""+"--case-insensitive";
            }
            else {
                query +=" WHERE "+LASTNAME_C0LUMN+" LIKE \"%"+lastname+"%\""+"--case-insensitive";
            }
            selectBylastname=true;
        }
        boolean selectByEmail=false;
        if(!email.isEmpty()){
            if (selectByFirstname || selectBylastname) {
                query+=" AND "+EMAILADDRESS_COLUMN+" LIKE \"%"+email+"%\""+"--case-insensitive";
            }
            else{
                query +=" WHERE "+EMAILADDRESS_COLUMN+" LIKE \"%"+email+"%\""+"--case-insensitive";
            }
            selectByEmail=true;
        }
        boolean selectByPassword=false;
        if(!password.isEmpty()){
            if(selectByEmail || selectBylastname || selectByFirstname){
                query+=" AND "+PASSWORD_COLUMN+" LIKE \"%"+password+"%\""+"--case-insensitive";
            }
            else{
                query+=" WHERE "+PASSWORD_COLUMN+ " LIKE \"%"+password+"%\""+"--case-insensitive";

            }
            selectByPassword=true;
        }
        if(!type.isEmpty()){
            if(selectBylastname || selectByFirstname || selectByEmail || selectByPassword){
                query+=" AND "+TYPE_COLUMN+" LIKE \"%"+type+"%\""+"--case-insensitive";
            }
            else{
                query+=" WHERE "+TYPE_COLUMN+ " LIKE \"%"+type +"%\""+"--case-insensitive";
            }
        }
        return getUsers(query);
    }
    public List<UserModel> getPms(){
        String type="PropertyManager";
        return findUserByType(type);
    }
    public List<UserModel> getClients(){
        String type="Client";
        return findUserByType(type);
    }
    public boolean login(String email,String password){
        try{
            UserModel user =getUserModel(email);
            return (password.equals(user.getPassword())) && (email.equals(user.getEmailAddress()));
        }catch(Exception e){
            return false;
        }

    }
    public UserModel getUserModel(String email){
        List<UserModel> users=findUsers("","",email,"","");
        if(users.isEmpty()){
            return null;
        }
        else{
            return users.get(0);
        }
    }
    public int getId(String email){
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+EMAILADDRESS_COLUMN+" LIKE \"%"+email+"%\""+"--case-insensitive";
        return getUsers(query).get(0).getId();
    }
    public UserModel getUserModel(int id){
        String query="SELECT * FROM "+TABLE_NAME+ " WHERE "+ID_COLUMN+" = "+id;
        return getUsers(query).get(0);
    }
    public String getName(int id){
        try{
        UserModel user=getUserModel(id);
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        return firstName+" "+lastName;}
        catch(Exception e){
            return "This isn't assigned";
        }
    }
    public int getBirthYear(int id){
        try{
            UserModel user=getUserModel(id);
            return user.getBirthYear();

        }catch(Exception e){
            return -1;
        }
    }
    public List<UserModel> findEmail(String email){
        return findUsers("","",email,"","");
    }
    public List<UserModel> findPassword(String password){
        return findUsers("","","",password,"");
    }
    public List<UserModel> findUserByType(String type){
        return findUsers("","","","",type);
    }
}

