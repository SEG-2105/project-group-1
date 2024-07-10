package com.example.rentron40;

import static com.example.rentron40.RequestHelper.PROPERTYID_COLUMN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TicketHelper extends SQLiteOpenHelper {

    private static final String TICKETID_COLUMN = "id" ;
    private static final String TABLE_NAME = "ticket" ;
    private static final String MESSAGE_COLUMN ="message" ;
    private static final String DATE_COLUMN = "date";
    private static final String TYPE_COLUMN = "type" ;
    private static final String PROPERTYID_COLUMN = "property";
    private static final String URGENCY_COLUMN = "urgency";
    private static final String STATUS_COLUMN = "status";

    public TicketHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "s.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreationStatement="CREATE TABLE "+ TABLE_NAME+"("+TICKETID_COLUMN+
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+PROPERTYID_COLUMN+" INTEGER, "
                +TYPE_COLUMN +" TEXT,"+URGENCY_COLUMN+" INTEGER, "+MESSAGE_COLUMN+" TEXT, "
                +DATE_COLUMN +" TEXT, "+ STATUS_COLUMN +" INTEGER)";
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
        cv.put(TYPE_COLUMN,request.getType());
        if(request.getRequestId()!=-1){
            cv.put(RECIPIENTID_COLUMN,request.getRequestId());
        }
        db.insert(TABLE_NAME,null,cv);

    }
    public List<TicketModel> getTickets(String query){
        SQLiteDatabase db=this.getReadableDatabase();
        List<TicketModel> tickets=new ArrayList<>();
        Cursor cursor =db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                TicketModel ticket=new TicketModel(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                );
                tickets.add(ticket);
            }while(cursor.moveToNext());
        }
        return tickets;
    }
    public List<TicketModel> getAllRequests() {
        String query="SELECT * FROM "+TABLE_NAME;
        return getTickets(query);
    }
    public List<TicketModel> getPropertyClientRequests(int propertyId){
        return findTickets(-1,propertyId,-1);
    }
    public List<TicketModel> getPropertyClientRequests(int ticketId,int propertyId,int urgency){
        return findTickets(ticketId,propertyId,urgency);
    }
    public List<TicketModel> findTickets(int ticketId,int propertyId,int urgency){
        String query="SELECT * FROM "+TABLE_NAME;
        boolean selectById=false;
        if(ticketId!=-1){
            query +=" WHERE "+TICKETID_COLUMN +"="+ticketId;
            selectById=true;
        }
        boolean selectByProperty=false;
        if(propertyId!=-1){
            if(selectById){
                query+=" AND "+PROPERTYID_COLUMN+"="+propertyId;
            }
            else {
                query +=" WHERE "+PROPERTYID_COLUMN+"="+propertyId;
            }
            selectByProperty=true;
        }
        boolean selectbyUrgency=false;
        if(propertyId!=-1){
            if (selectById || selectByProperty) {
                query+=" AND "+URGENCY_COLUMN+"="+urgency;
            }
            else{
                query +=" WHERE "+URGENCY_COLUMN+"="+urgency;
            }
            selectbyUrgency=true;
        }
        return getTickets(query);
    }
    public void updateTicketStatus(int id,int status){
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+STATUS_COLUMN+"="+status
                +"WHERE "+TICKETID_COLUMN+"="+id;
        db.execSQL(query);
    }
}
