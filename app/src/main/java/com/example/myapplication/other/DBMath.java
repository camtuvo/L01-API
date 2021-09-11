package com.example.myapplication.other;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.MathS;
import com.example.myapplication.model.People;

import java.util.ArrayList;

public class DBMath extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Lab07Math";
    private static final int VERSION = 1;
    public DBMath(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLQuery = "CREATE TABLE MATH (ID INTEGER primary key AUTOINCREMENT, CAUHOI VARCHAR(255), CAUA VARCHAR(255), CAUB VARCHAR(255), CAUC VARCHAR(255), CAUD VARCHAR(255), KQ VARCHAR(255))";
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    }

    public ArrayList<MathS> getAll() { //Lấy toàn bộ danh sách
        ArrayList<MathS> mathsArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MATH", null);
        cursor.moveToFirst(); //Đến dòng đầu của tập dữ liệu
        while (!cursor.isAfterLast()) {
            int stt = cursor.getInt(0);
            String cauhoi = cursor.getString(1);
            String caua = cursor.getString(2);
            String caub = cursor.getString(3);
            String cauc = cursor.getString(4);
            String caud = cursor.getString(5);
            String kq = cursor.getString(6);
            mathsArrayList.add(new MathS(stt, cauhoi, caua, caub, cauc, caud, kq));
            cursor.moveToNext();
        }
        cursor.close();
        return mathsArrayList;
    }

    //Lấy thông tin SV bằng ID
    public MathS getMathByID(int ids) {
        MathS pp = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MATH where ID = ?", new String[]{ids + ""});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int stt = cursor.getInt(0);
            String cauhoi = cursor.getString(1);
            String caua = cursor.getString(2);
            String caub = cursor.getString(3);
            String cauc = cursor.getString(4);
            String caud = cursor.getString(5);
            String kq = cursor.getString(6);
            pp = new MathS(stt, cauhoi, caua, caub, cauc, caud, kq);
        }
        cursor.close();
        return pp;
    }

    public void updateMath(MathS pp, int id) { //cập nhật
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE MATH SET CAUHOI=?, CAUA = ?, CAUB = ?, CAUC = ?, CAUD = ?, KQ = ? where ID = ?",
                new String[]{pp.getCauHoi(), pp.getcA(), pp.getcB(), pp.getcC(), pp.getcD(), pp.getKq(), String.valueOf(id) });
        db.close();
    }

    public void insertMath(MathS pp) { //thêm mới
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MATH (CAUHOI, CAUA, CAUB, CAUC, CAUD, KQ) VALUES (?,?,?,?,?,?)",
                new String[]{ pp.getCauHoi(), pp.getcA(), pp.getcB(), pp.getcC(), pp.getcD(), pp.getKq() });
        db.close();
    }

    public void deleteByIDMath(int id) { //Xoá SV bằng ID
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MATH where ID = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
