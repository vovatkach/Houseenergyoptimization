package com.vovatkach2427gmail.houseenergyoptimization.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vovatkach2427gmail.houseenergyoptimization.Model.Device;
import com.vovatkach2427gmail.houseenergyoptimization.Model.Set;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vovat on 16.05.2017.
 */

public class DataBaseWorker {
    private MyDataBaseHelper myDataBaseHelper;
    public DataBaseWorker(Context context)
    {
        myDataBaseHelper=new MyDataBaseHelper(context);
    }
    public void close(){myDataBaseHelper.close();}
    //----------------------------------------------
    public List<Set> getSets()
    {
        ArrayList<Set> sets=new ArrayList<>();
        SQLiteDatabase db=myDataBaseHelper.getReadableDatabase();
        Cursor cursor=db.query(Contact.TABLE_SET.TABLE_NAME,null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            int idColIndex=cursor.getColumnIndex(Contact.TABLE_SET.ID);
            int nameColIndex=cursor.getColumnIndex(Contact.TABLE_SET.NAME);
            int listOfDevice=cursor.getColumnIndex(Contact.TABLE_SET.LIST_OF_DEVICE);
            do
            {
               sets.add(new Set(cursor.getInt(idColIndex),cursor.getString(nameColIndex),Translator.fromJsonToListOfDevice(cursor.getString(listOfDevice))));
            }while (cursor.moveToNext());
        }
        db.close();
        return sets;
    }
    //--------------------------------------------------------------------
    public Set getSet(int idSet)
    {
        Set set;
        SQLiteDatabase db=myDataBaseHelper.getReadableDatabase();
        Cursor cursor=db.query(Contact.TABLE_SET.TABLE_NAME,null,Contact.TABLE_SET.ID+" = ?",new String[]{Integer.toString(idSet)},null,null,null);
        if(cursor.moveToFirst())
        {
            int idColIndex=cursor.getColumnIndex(Contact.TABLE_SET.ID);
            int nameColIndex=cursor.getColumnIndex(Contact.TABLE_SET.NAME);
            int listOfDevice=cursor.getColumnIndex(Contact.TABLE_SET.LIST_OF_DEVICE);
            set=new Set(cursor.getInt(idColIndex),cursor.getString(nameColIndex),Translator.fromJsonToListOfDevice(cursor.getString(listOfDevice)));
        } else set=new Set(0,null,null);
        return set;
    }
    //---------------------------------------------------------------------
    public List<Device> getAllDevices()
    {
        ArrayList<Device> devices=new ArrayList<>();
        SQLiteDatabase db=myDataBaseHelper.getReadableDatabase();
        Cursor cursor=db.query(Contact.TABLE_DEVICE.TABLE_NAME,null,null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            int idColIndex=cursor.getColumnIndex(Contact.TABLE_DEVICE.ID);
            int nameColIndex=cursor.getColumnIndex(Contact.TABLE_DEVICE.NAME);
            int extraInfoColIndex=cursor.getColumnIndex(Contact.TABLE_DEVICE.EXTRA_INFO);
            int powerConsumptionColIndex=cursor.getColumnIndex(Contact.TABLE_DEVICE.POWER_CONSUMPTION);
            int priorityColIndex=cursor.getColumnIndex(Contact.TABLE_DEVICE.PRIORITY);
            int tMinColIndex=cursor.getColumnIndex(Contact.TABLE_DEVICE.T_MIN);
            int tMaxColIndex=cursor.getColumnIndex(Contact.TABLE_DEVICE.T_MAX);
            do {
                devices.add(new Device(cursor.getInt(idColIndex),cursor.getString(nameColIndex),cursor.getString(extraInfoColIndex),cursor.getInt(powerConsumptionColIndex),cursor.getInt(priorityColIndex),cursor.getInt(tMinColIndex),cursor.getInt(tMaxColIndex)));
            }while (cursor.moveToNext());
        }
        db.close();
        return devices;
    }
    public void addDevice(Device device)
    {
        SQLiteDatabase database=myDataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contact.TABLE_DEVICE.NAME,device.getName());
        contentValues.put(Contact.TABLE_DEVICE.EXTRA_INFO,device.getExtraInfo());
        contentValues.put(Contact.TABLE_DEVICE.POWER_CONSUMPTION,device.getPowerConsumption());
        contentValues.put(Contact.TABLE_DEVICE.PRIORITY,device.getPriority());
        contentValues.put(Contact.TABLE_DEVICE.T_MIN,device.gettMin());
        contentValues.put(Contact.TABLE_DEVICE.T_MAX,device.gettMax());
        database.insert(Contact.TABLE_DEVICE.TABLE_NAME,null,contentValues);
    }
    public void addSet(Set set)
    {
        SQLiteDatabase database=myDataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contact.TABLE_SET.NAME,set.getName());
        contentValues.put(Contact.TABLE_SET.LIST_OF_DEVICE,Translator.listOfDevivesToJson(set.getListOfDevice()));
        database.insert(Contact.TABLE_SET.TABLE_NAME,null,contentValues);
    }
}
