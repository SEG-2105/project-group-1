package com.example.rentron40;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PropertyHelper extends SQLiteOpenHelper {


    public static final String ID_COLUMN = "id";
    public static final String TABLE_NAME = "property";
    public static final String RENT_COLUMN = "rent";
    public static final String TYPE_COLUMN = "type";
    public static final String ADDRESS_COLUMN = "address";
    public static final String ROOMS_COLUMN = "rooms";
    public static final String BATHROOM_COLUMN = "bathroom";
    public static final String FLOORS_COLUMN = "floors";
    public static final String AREA_COLUMN = "area";
    public static final String PARKING_COLUMN = "parking";
    public static final String HYDRO_COLUMN = "hydro";
    public static final String HEATING_COLUMN = "heating";
    public static final String WATER_COLUMN = "water";
    public static final String OCCUPIED_COLUMN = "occupied";
    public static final String CLIENT_COLUMN = "client";
    public static final String LANDLORD_COLUMN = "landlord";
    public static final String PROPERTYMANAGER_COLUMN = "propertymanager";

    public PropertyHelper(@Nullable Context context) {
        super(context, TABLE_NAME+"s.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreationStatement= "CREATE TABLE " + TABLE_NAME + "(" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RENT_COLUMN + " INTEGER, " + TYPE_COLUMN + " TEXT, " + ADDRESS_COLUMN + " TEXT, " + ROOMS_COLUMN + " DOUBLE, " + BATHROOM_COLUMN + " DOUBLE, " + FLOORS_COLUMN + " DOUBLE," +
                AREA_COLUMN + " INTEGER, " + PARKING_COLUMN + " INTEGER, " + HYDRO_COLUMN + " INTEGER, " + HEATING_COLUMN + " INTEGER, " +
                WATER_COLUMN + " INTEGER, " + OCCUPIED_COLUMN + " INTEGER," + LANDLORD_COLUMN + " INTEGER, " + CLIENT_COLUMN + " INTEGER, " + PROPERTYMANAGER_COLUMN + " INTEGER);";
        db.execSQL(tableCreationStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void addProperty(PropertyModel property){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(RENT_COLUMN,property.getRent());
        cv.put(TYPE_COLUMN,property.getType());
        cv.put(ADDRESS_COLUMN,property.getAddress());
        cv.put(ROOMS_COLUMN,property.getRooms());
        cv.put(BATHROOM_COLUMN,property.getBathrooms());
        cv.put(FLOORS_COLUMN,property.getFloors());
        cv.put(AREA_COLUMN,property.getArea());
        cv.put(PARKING_COLUMN,property.getParking());
        cv.put(HYDRO_COLUMN,property.isHydro());
        cv.put(HEATING_COLUMN,property.isHeating());
        cv.put(WATER_COLUMN,property.isWater());
        cv.put(OCCUPIED_COLUMN,property.isOccupied());
        cv.put(CLIENT_COLUMN,property.getClientId());
        cv.put(LANDLORD_COLUMN,property.getLandlordId());
        cv.put(PROPERTYMANAGER_COLUMN,property.getPropertyManagerId());
        if(property.getId()!=PropertyModel.UNASSIGNED_ID){
            cv.put(ID_COLUMN,property.getId());
        }
        db.insert(TABLE_NAME,null,cv);
    }
    public List<PropertyModel> getProperties(String query){
        SQLiteDatabase db=this.getReadableDatabase();
        List<PropertyModel> properties=new ArrayList<>();
        Cursor cursor =db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                PropertyModel property=new PropertyModel(
                        cursor.getInt(0), // id
                        cursor.getInt(1), // rent
                        cursor.getString(2),// type
                        cursor.getString(3),// address
                        cursor.getDouble(4),// rooms
                        cursor.getDouble(5), //bathrooms
                        cursor.getDouble(6),//floors
                        cursor.getInt(7),//area
                        cursor.getInt(8),//parking
                        cursor.getInt(9),//hydro
                        cursor.getInt(10),//heating
                        cursor.getInt(11),//water
                        cursor.getInt(12),//occupied
                        cursor.getInt(13),//landlordid
                        cursor.getInt(14),//clientid
                        cursor.getInt(15)//propertymanagerid
                );
                properties.add(property);
            }while(cursor.moveToNext());
        }
        return properties;
    }

    public List<PropertyModel> getAllProperty() {
        String query="SELECT * FROM "+TABLE_NAME;
        return getProperties(query);
    }
    public void updatePropertyManager(int id,int pm){
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+PROPERTYMANAGER_COLUMN+" = "+pm+ " WHERE "+ID_COLUMN+"="+id;
        db.execSQL(query);
    }
    public void updateClient(int id,int client){
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+CLIENT_COLUMN+" = "+client+ " WHERE "+ID_COLUMN+"="+id;
        db.execSQL(query);
        updateOcuppancy(id);
    }
    public void removeClient(int id){
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+CLIENT_COLUMN+"="
                +PropertyModel.UNASSIGNED_CLIENTID+" WHERE "+ID_COLUMN+"="+id;
    }

    private void updateOcuppancy(int id) {
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+OCCUPIED_COLUMN+" = "+1+ " WHERE "+ID_COLUMN+"="+id;
        db.execSQL(query);
    }

    public List<PropertyModel> getOwnedProperties(int id) {
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+LANDLORD_COLUMN+" = "+id;
        return getProperties(query);
    }

    public void updateProperty(int rent, double rooms,double bathrooms
            ,double floors,int area, int parking,int id,int occupied) {
        updateRent(rent,id,occupied);
        updateFloors(floors,id);
        updateRooms(rooms,id);
        updateBathrooms(bathrooms,id);
        updateArea(area,id);
        updateParking(parking,id);
    }

    private void updateBathrooms(double bathrooms, int id) {
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+BATHROOM_COLUMN+" = "+bathrooms+ " WHERE "+ID_COLUMN+"="+id;
        db.execSQL(query);
    }

    private void updateParking(int parking, int id) {
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+PARKING_COLUMN+" = "+parking+ " WHERE "+ID_COLUMN+"="+id;
        db.execSQL(query);
    }

    private void updateArea(int area, int id) {
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+AREA_COLUMN+" = "+area+ " WHERE "+ID_COLUMN+"="+id;
        db.execSQL(query);
    }

    private void updateRooms(double rooms, int id) {
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+ROOMS_COLUMN+" = "+rooms+ " WHERE "+ID_COLUMN+"="+id;
        db.execSQL(query);
    }

    private void updateFloors(double floors,int id) {
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+TABLE_NAME+" SET "+FLOORS_COLUMN+" = "+floors+ " WHERE "+ID_COLUMN+"="+id;
        db.execSQL(query);
    }

    private void updateRent(int rent,int id,int occupied) {
        if(occupied==0){
            SQLiteDatabase db=getWritableDatabase();
            String query="UPDATE "+TABLE_NAME+" SET "+RENT_COLUMN+" = "+rent+ " WHERE "+ID_COLUMN+"="+id;
            db.execSQL(query);
        }
    }

    public PropertyModel getPropertyModel(int id) {
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+ID_COLUMN+"="+id;
        return getProperties(query).get(0);
    }
}
