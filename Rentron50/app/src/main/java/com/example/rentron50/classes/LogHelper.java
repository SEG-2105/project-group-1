package com.example.rentron50.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LogHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "log" ;
    private static final String LOGID_COLUMN = "logId" ;

    private static final String DATE_COLUMN = "date" ;
    private static final String STATUS_COLUMN = "status" ;
    private static final String TICKETID_COLUMN = "ticketId";

    public LogHelper(@Nullable Context context) {
        super(context, TABLE_NAME+"s.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreationStatement="CREATE TABLE "+TABLE_NAME+"("+
                LOGID_COLUMN +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TICKETID_COLUMN+" INTEGER,"+ DATE_COLUMN+" TEXT,"+ STATUS_COLUMN+" INTEGER);";
        db.execSQL(tableCreationStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public void addLog(LogModel log){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TICKETID_COLUMN,log.getTicketId());
        cv.put(DATE_COLUMN,log.getDate());
        cv.put(STATUS_COLUMN,log.getStatus());
        if(log.getId()!=-1){
            cv.put(LOGID_COLUMN,log.getId());
        }
        db.insert(TABLE_NAME,null,cv);
    }
    public List<LogModel> getLogs(String query){
        SQLiteDatabase db=getReadableDatabase();
        List<LogModel> logs=new ArrayList<>();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                LogModel log=new LogModel(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getInt(3)
                );
                logs.add(log);
            }while (cursor.moveToNext());
        }
        return logs;
    }
    public List<LogModel> getAllLogs(){
        String query="SELECT*FROM "+TABLE_NAME;
        return getLogs(query);
    }
    public List<LogModel> getLogByTicket(int ticketId){
        String logByTicketStatement="SELECT*FROM "+TABLE_NAME+" WHERE "+
                TICKETID_COLUMN+"="+ticketId;
        return getLogs(logByTicketStatement);
    }

    public void acceptTicket(int ticketId) {
        LogModel log=new LogModel(ticketId,2);
        addLog(log);
    }

    public void rejectTicket(int ticketId) {
        LogModel log=new LogModel(ticketId,1);
        addLog(log);
    }
    public void resolveTicket(int ticketId){
        LogModel log=new LogModel(ticketId,0);
        addLog(log);
    }

    public void ticketInLimbo(int ticketId) {
        LogModel log=new LogModel(ticketId,-1);
        addLog(log);
    }
}

