package com.example.rentron50.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.rentron50.activities.MyPropertyActivity;

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
    private static final String CITY_COLUMN = "city";
    private static final String COUNTRY_COLUMN="country";

    public PropertyHelper(@Nullable Context context) {
        super(context, TABLE_NAME + "s.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreationStatement = "CREATE TABLE " + TABLE_NAME + "(" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RENT_COLUMN + " INTEGER, " + TYPE_COLUMN + " TEXT, " + ADDRESS_COLUMN + " TEXT, " + ROOMS_COLUMN + " DOUBLE, " + BATHROOM_COLUMN + " DOUBLE, " + FLOORS_COLUMN + " DOUBLE," +
                AREA_COLUMN + " INTEGER, " + PARKING_COLUMN + " INTEGER, " + HYDRO_COLUMN + " INTEGER, " + HEATING_COLUMN + " INTEGER, " +
                WATER_COLUMN + " INTEGER, " + OCCUPIED_COLUMN + " INTEGER," + LANDLORD_COLUMN + " INTEGER, " + CLIENT_COLUMN + " INTEGER, " +
                PROPERTYMANAGER_COLUMN + " INTEGER,"+CITY_COLUMN+" TEXT, "+COUNTRY_COLUMN+" TEXT);";
        db.execSQL(tableCreationStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addProperty(PropertyModel property) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RENT_COLUMN, property.getRent());
        cv.put(TYPE_COLUMN, property.getType());
        cv.put(ADDRESS_COLUMN, property.getAddress());
        cv.put(ROOMS_COLUMN, property.getRooms());
        cv.put(BATHROOM_COLUMN, property.getBathrooms());
        cv.put(FLOORS_COLUMN, property.getFloors());
        cv.put(AREA_COLUMN, property.getArea());
        cv.put(PARKING_COLUMN, property.getParking());
        cv.put(HYDRO_COLUMN, property.isHydro());
        cv.put(HEATING_COLUMN, property.isHeating());
        cv.put(WATER_COLUMN, property.isWater());
        cv.put(OCCUPIED_COLUMN, property.isOccupied());
        cv.put(CLIENT_COLUMN, property.getClientId());
        cv.put(LANDLORD_COLUMN, property.getLandlordId());
        cv.put(PROPERTYMANAGER_COLUMN, property.getPropertyManagerId());
        cv.put(CITY_COLUMN,property.getCity());
        cv.put(COUNTRY_COLUMN,property.getCountry());
        if (property.getId() != PropertyModel.UNASSIGNED_ID) {
            cv.put(ID_COLUMN, property.getId());
        }
        db.insert(TABLE_NAME, null, cv);
    }

    public List<PropertyModel> getProperties(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<PropertyModel> properties = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PropertyModel property = new PropertyModel(
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
            } while (cursor.moveToNext());
        }
        return properties;
    }

    public List<PropertyModel> getAllProperty() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getProperties(query);
    }

    public void updatePropertyManager(int id, int pm) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + PROPERTYMANAGER_COLUMN + " = " + pm + " WHERE " + ID_COLUMN + "=" + id;
        db.execSQL(query);
    }

    public void updateClient(int id, int client) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + CLIENT_COLUMN + " = " + client +" ,"+OCCUPIED_COLUMN+"="+1+ " WHERE " + ID_COLUMN + "=" + id;
        db.execSQL(query);
        updateOcuppancy(id);
    }

    public void removeClient(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + CLIENT_COLUMN + "="
                + PropertyModel.UNASSIGNED_CLIENTID + " WHERE " + ID_COLUMN + "=" + id;
    }

    private void updateOcuppancy(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + OCCUPIED_COLUMN + " = " + 1 + " WHERE " + ID_COLUMN + "=" + id;
        db.execSQL(query);
    }

    public List<PropertyModel> getOwnedProperties(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + LANDLORD_COLUMN + " = " + id;
        return getProperties(query);
    }

    public void updateProperty(int rent, double rooms, double bathrooms
            , double floors, int area, int parking, int id, int occupied) {
        updateRent(rent, id);
        updateFloors(floors, id);
        updateRooms(rooms, id);
        updateBathrooms(bathrooms, id);
        updateArea(area, id);
        updateParking(parking, id);
    }

    private void updateBathrooms(double bathrooms, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + BATHROOM_COLUMN + " = " + bathrooms + " WHERE " + ID_COLUMN + "=" + id+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);
    }

    private void updateParking(int parking, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + PARKING_COLUMN + " = " + parking + " WHERE " + ID_COLUMN + "=" + id+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);
    }

    private void updateArea(int area, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + AREA_COLUMN + " = " + area + " WHERE " + ID_COLUMN + "=" + id+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);
    }

    private void updateRooms(double rooms, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + ROOMS_COLUMN + " = " + rooms + " WHERE " + ID_COLUMN + "=" + id+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);
    }

    private void updateFloors(double floors, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + FLOORS_COLUMN + " = " + floors + " WHERE " + ID_COLUMN + "=" + id+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);
    }

    private void updateRent(int rent, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + RENT_COLUMN + " = " + rent + " WHERE " + ID_COLUMN + "=" + id+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);

    }

    public List<PropertyModel> findPropertyForClients(PropertyModel property) {
        PropertyModel propertyModel = new PropertyModel();
        if (property != null) {
            return findProperty(property);
        }
        return findProperty();
    }

    public List<PropertyModel> findProperty() {
        String activePropertyStatement = "SELECT*FROM " + TABLE_NAME + " WHERE " + OCCUPIED_COLUMN + "=" + 0 + " AND NOT " +
                PROPERTYMANAGER_COLUMN + " = " + -1;
        return getProperties(activePropertyStatement);
    }

    public List<PropertyModel> findProperty(PropertyModel property) {
        boolean selectByRent = false, selectByArea = false, selectByRooms = false,
                selectByBathrooms = false, selectByFloors = false, selectByHydro = false,
                selectByHeating = false, selectByWater = false, selectByType = false, selectByParking = false;
        String query = "SELECT*FROM " + TABLE_NAME;
        if (property.getRent() != -1) {
            query += " WHERE " + RENT_COLUMN + "<=" + property.getRent();
            selectByRent = true;
        }
        if (property.getArea() != -1) {
            if (selectByRent) {
                query += " AND " + AREA_COLUMN + "<=" + property.getArea();

            } else {
                query += " WHERE " + AREA_COLUMN + "<=" + property.getArea();
            }
            selectByArea = true;
        }
        if (property.getRooms() != -1) {

            if (selectByRent || selectByArea) {
                query += " AND " + ROOMS_COLUMN + "<=" + property.getRooms();
            } else {
                query += " WHERE " + ROOMS_COLUMN + "<=" + property.getRooms();
            }
            selectByRooms = true;
        }
        if (property.getBathrooms() != -1) {
            if (selectByRent || selectByArea || selectByRooms) {
                query += " AND " + BATHROOM_COLUMN + "<=" + property.getBathrooms();
            } else {
                query += " WHERE " + BATHROOM_COLUMN + "<=" + property.getBathrooms();
            }
            selectByBathrooms = true;
        }
        if (property.getFloors() != -1) {
            if (selectByRent || selectByArea || selectByRooms || selectByBathrooms) {
                query += " AND " + FLOORS_COLUMN + "<=" + property.getFloors();
            } else {
                query += " WHERE " + FLOORS_COLUMN + "<=" + property.getFloors();
            }
            selectByFloors = true;
        }
        if (property.getType().equals("Client") || property.getType().equals("Landlord") || property.getType().equals("PropertyManager")) {
            if (selectByRent || selectByArea || selectByRooms || selectByBathrooms || selectByFloors) {
                query += " AND " + TYPE_COLUMN + "=" + property.getType();
            } else {
                query += " WHERE " + TYPE_COLUMN + "=" + property.getType();
            }
            selectByType = true;
        }
        if (property.getParking() != -1) {
            if (selectByRent || selectByArea || selectByRooms || selectByBathrooms || selectByFloors || selectByType) {
                query += " AND " + PARKING_COLUMN + "<=" + property.getParking();
            } else {
                query += " WHERE " + PARKING_COLUMN + "<=" + property.getParking();
            }
            selectByParking = true;
        }
        if (property.getHydro() != -1) {
            if (selectByRent || selectByArea || selectByRooms || selectByBathrooms || selectByFloors || selectByType || selectByParking) {
                query += " AND " + HYDRO_COLUMN + "=" + property.getHydro();
            } else {
                query += " WHERE " + HYDRO_COLUMN + "=" + property.getHydro();
            }
            selectByHydro = true;
        }
        if (property.getHeating() != -1) {
            if (selectByRent || selectByArea || selectByRooms
                    || selectByBathrooms || selectByFloors
                    || selectByType || selectByParking || selectByHydro) {
                query += " AND " + HEATING_COLUMN + "=" + property.getHeating();
            } else {
                query += " WHERE " + HEATING_COLUMN + "=" + property.getHeating();
            }
            selectByHeating = true;
        }
        if (property.getWater() != -1) {
            if (selectByRent || selectByArea || selectByRooms
                    || selectByBathrooms || selectByFloors
                    || selectByType || selectByParking || selectByHydro
                    || selectByHeating) {
                query += " AND " + WATER_COLUMN + "=" + property.getWater();
            } else {
                query += " WHERE " + WATER_COLUMN + "=" + property.getWater();
            }
            selectByWater = true;
        }
        boolean selectByCity=false;
        try{
            if(property.getCity()!=null){
                if (selectByRent || selectByArea || selectByRooms
                        || selectByBathrooms || selectByFloors
                        || selectByType || selectByParking || selectByHydro
                        || selectByHeating||selectByWater) {
                    query += " AND " + CITY_COLUMN + " LIKE " + property.getCity()+"%";
                } else {
                    query += " WHERE " + CITY_COLUMN + "LIKE " + property.getCity()+"%";
                }
                selectByCity = true;
            }
        }catch (Exception e){

        }
        boolean selectByCountry=false;

        try{
            if(property.getCountry()!=null){
                if (selectByRent || selectByArea || selectByRooms
                        || selectByBathrooms || selectByFloors
                        || selectByType || selectByParking || selectByHydro
                        || selectByHeating||selectByWater||selectByCity) {
                    query += " AND " + COUNTRY_COLUMN + "LIKE " + property.getCountry()+"%";
                } else {
                    query += " WHERE " + COUNTRY_COLUMN + "LIKE " + property.getCountry()+"%";
                }
                selectByCountry = true;
            }
        }catch (Exception e){}
        return getProperties(query);
    }

    public PropertyModel getPropertyModel(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + "=" + id;
        return getProperties(query).get(0);
    }

    public PropertyModel getPropertyModel(int clientId, int m) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + CLIENT_COLUMN + "=" + clientId;
        return getProperties(query).get(0);
    }

    public List<PropertyModel> getProperties(int clientId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + CLIENT_COLUMN + "=" + clientId;
        return getProperties(query);
    }

    public boolean evictClient(int id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String evictionNotice = "UPDATE " + TABLE_NAME + " SET " + CLIENT_COLUMN + "=" + -1 +" , "+OCCUPIED_COLUMN+"="+0+ " WHERE " + ID_COLUMN + "=" + id;
            db.execSQL(evictionNotice);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    public List<PropertyModel> getPropertiesToManage(int id) {
        String managePropertiesStatement = "SELECT*FROM " + TABLE_NAME + " WHERE " + PROPERTYMANAGER_COLUMN + "=" + id;
        return getProperties(managePropertiesStatement);
    }

    public void editProperty(PropertyModel editedProperty, int propertyId) {
        if(editedProperty.getRent()!=-1){
            updateRent(editedProperty.getRent(),propertyId);
        }
        if(editedProperty.getArea()!=-1){
            updateArea(editedProperty.getArea(),propertyId);
        }
        if(editedProperty.getRooms()!=-1){
            updateRooms(editedProperty.getRooms(),propertyId);
        }
        if(editedProperty.getFloors()!=-1){
            updateFloors(editedProperty.getFloors(),propertyId);
        }
        if(editedProperty.getParking()!=-1){
            updateFloors(editedProperty.getParking(),propertyId);
        }
        updateHydro(editedProperty.getHydro(), propertyId);
        updateHeating(editedProperty.getHeating(), propertyId);
        updateWater(editedProperty.getWater(), propertyId);
    }

    private void updateWater(int water, int propertyId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + WATER_COLUMN + " = " + water + " WHERE " + ID_COLUMN + "=" + propertyId+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);
    }

    private void updateHeating(int heating, int propertyId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + HEATING_COLUMN + " = " + heating + " WHERE " + ID_COLUMN + "=" + propertyId+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);
    }
    private void updateHydro(int hydro, int propertyId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + HYDRO_COLUMN + " = " + hydro + " WHERE " + ID_COLUMN + "=" + propertyId+" AND "+OCCUPIED_COLUMN+"="+0;
        db.execSQL(query);
    }
}
