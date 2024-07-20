package com.example.rentron50.classes;

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

    public TicketHelper(@Nullable Context context) {
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
    public void addTicket(TicketModel ticket){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(PROPERTYID_COLUMN,ticket.getPropertyId());
        cv.put(TYPE_COLUMN,ticket.getType());
        cv.put(URGENCY_COLUMN,ticket.getUrgency());
        cv.put(MESSAGE_COLUMN,ticket.getMessage());
        cv.put(DATE_COLUMN,ticket.getDate());
        cv.put(STATUS_COLUMN,ticket.getStatus());
        if(ticket.getId()!=-1){
            cv.put(TICKETID_COLUMN,ticket.getId());
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
    public List<TicketModel> getAllTickets() {
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
        String query="SELECT*FROM "+TABLE_NAME;
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
    public TicketModel getTicketModel(int id){
        String ticketModelStatement="SELECT*FROM "+
                TABLE_NAME+" WHERE "+TICKETID_COLUMN+"="+
                id;
        return getTickets(ticketModelStatement).get(0);
    }
    public void updateTicketStatus(int id,int status){
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+STATUS_COLUMN+"="+status
                +" WHERE "+TICKETID_COLUMN+"="+id;
        db.execSQL(query);
    }
    public TicketModel getTicketById(int id){
        String ticketModelStatement="SELECT*FROM "+TABLE_NAME+" WHERE "+TICKETID_COLUMN+"="+id;
        return getTickets(ticketModelStatement).get(0);
    }

    public List<TicketModel> getTicketByProperty(int id) {
        SQLiteDatabase db=getReadableDatabase();
        String ticketListStatement="SELECT*FROM "+TABLE_NAME+" WHERE "+PROPERTYID_COLUMN+"="+id;
        return getTickets(ticketListStatement);
    }

    public void resolveTicket(int ticketId) {
        SQLiteDatabase db=getWritableDatabase();
        String ticketResolutionStatement="UPDATE "+TABLE_NAME+
                " SET "+STATUS_COLUMN+"="+0+" WHERE "+TICKETID_COLUMN+"="+ticketId;
        db.execSQL(ticketResolutionStatement);
    }

    public List<TicketModel> getActiveTickets(int id) {
        String activeTicketStatement="SELECT*FROM "+TABLE_NAME+" WHERE "+PROPERTYID_COLUMN+"="+id+
                " AND "+STATUS_COLUMN+"="+2+" OR "+STATUS_COLUMN+"="+-1;
        return getTickets(activeTicketStatement);
    }

    public List<TicketModel> getClosedTickets(int id) {
        String activeTicketStatement="SELECT*FROM "+TABLE_NAME+" WHERE "+PROPERTYID_COLUMN+"="+id+
                " AND "+STATUS_COLUMN+"="+0;
        return getTickets(activeTicketStatement);
    }

    public void acceptTicket(int ticketId) {
        SQLiteDatabase db=getWritableDatabase();
        String acceptTicketStatement="UPDATE "+TABLE_NAME+" SET "+STATUS_COLUMN+"="+2+" WHERE "+TICKETID_COLUMN+"="+ticketId;
        db.execSQL(acceptTicketStatement);
    }
    public void RejectTicket(int ticketId) {
        SQLiteDatabase db=getWritableDatabase();
        String acceptTicketStatement="UPDATE "+TABLE_NAME+" SET "+STATUS_COLUMN+"="+1+" WHERE "+TICKETID_COLUMN+"="+ticketId;
        db.execSQL(acceptTicketStatement);
    }

    public int getMaxId(int propertyId) {
        List<TicketModel> tickets=getTicketByProperty(propertyId);
        return tickets.get(tickets.size()-1).getId();
    }
}
