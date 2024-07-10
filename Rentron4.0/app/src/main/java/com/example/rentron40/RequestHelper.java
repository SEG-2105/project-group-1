package com.example.rentron40;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;

public class RequestHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "request" ;
    private static final String REQUESTID_COLUMN = "id" ;
    private static final String SENDERID_COLUMN = "sender" ;
    private static final String PROPERTYID_COLUMN = "property" ;
    private static final String RECIPIENTID_COLUMN = "recipient" ;
    private static final String ISACTIVE_COLUMN = "active" ;


    public RequestHelper(@Nullable Context context) {
        super(context, TABLE_NAME+"s.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreationStatement="CREATE TABLE "+ TABLE_NAME+
                "("+REQUESTID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT , " +
                SENDERID_COLUMN+" INTEGER ,"+PROPERTYID_COLUMN +"INTEGER ,"+
                RECIPIENTID_COLUMN +" INTEGER ,"+ ISACTIVE_COLUMN+" INTEGER);";
        db.execSQL(tableCreationStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void addRequest(RequestModel request){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(SENDERID_COLUMN,request.getSenderId());
        cv.put(PROPERTYID_COLUMN,request.getPropertyId());
        cv.put(RECIPIENTID_COLUMN,request.getRecipientId());
        cv.put(ISACTIVE_COLUMN,request.getIsActive());
        if(request.getRequestId()!=-1){
            cv.put(RECIPIENTID_COLUMN,request.getRequestId());
        }
        db.insert(TABLE_NAME,null,cv);

    }
    public List<RequestModel> getRequests(String query){
        SQLiteDatabase db=this.getReadableDatabase();
        List<RequestModel> requests=new ArrayList<>();
        Cursor cursor =db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                RequestModel property=new RequestModel(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                );
                requests.add(property);
            }while(cursor.moveToNext());
        }
        return requests;
    }
    public List<RequestModel> getAllRequests() {
        String query="SELECT * FROM "+TABLE_NAME;
        return getRequests(query);
    }
    public List<RequestModel> getPropertyClientRequests(int landlordId,int propertyId){
        findRequests(-1,-1,propertyId,landlordId,1);
    }
    public List<RequestModel> findRequests(int requestId,int senderId, int propertyId,int recipientId,int active){
        String query="SELECT * FROM "+TABLE_NAME;
        boolean selectById=false;
        if(requestId!=-1){
            query +=" WHERE "+REQUESTID_COLUMN +"="+requestId;
            selectById=true;
        }
        boolean selectBySender=false;
        if(senderId!=-1){
            if(selectById){
                query+=" AND "+SENDERID_COLUMN+"="+senderId;
            }
            else {
                query +=" WHERE "+SENDERID_COLUMN+"="+senderId;
            }
            selectBySender=true;
        }
        boolean selectByProperty=false;
        if(propertyId!=-1){
            if (selectById || selectBySender) {
                query+=" AND "+PROPERTYID_COLUMN+"="+propertyId;
            }
            else{
                query +=" WHERE "+PROPERTYID_COLUMN+"="+propertyId;
            }
            selectByProperty=true;
        }
        boolean selectByRecipient=false;
        if(recipientId!=-1){
            if(selectById || selectByProperty || selectBySender){
                query+=" AND "+RECIPIENTID_COLUMN+"="+recipientId;
            }
            else{
                query+=" WHERE "+RECIPIENTID_COLUMN+"="+recipientId;

            }
            selectByRecipient=true;
        }
        if(active!=-1){
            if(selectById || selectByProperty || selectBySender || selectByRecipient){
                query+=" AND "+ISACTIVE_COLUMN+"="+active;
            }
            else{
                query+=" WHERE "+ISACTIVE_COLUMN+"="+active;
            }
        }
        return getRequests(query);
    }
}
