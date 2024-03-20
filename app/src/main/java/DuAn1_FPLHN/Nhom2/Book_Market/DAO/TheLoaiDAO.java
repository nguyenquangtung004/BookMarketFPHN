package DuAn1_FPLHN.Nhom2.Book_Market.DAO;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Database.DBHelper;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TheLoai;

public class TheLoaiDAO {
    private final DBHelper dbHelper;
    public TheLoaiDAO(Context context){
        dbHelper= new DBHelper(context);
    }
    // lay danh sach the loai
    public ArrayList<TheLoai> getDSTheLoai(){
        ArrayList<TheLoai> list= new ArrayList<>();
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THELOAI", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                list.add(new TheLoai(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    //them the loai
    public boolean themTheLoai(String tenloai){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = db.insert("THELOAI", null, contentValues);
        return check != -1;
    }

    //sua the loai
    public boolean suaTheLoai(int maloai, String tenloai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = db.update("THELOAI", contentValues, "maloai = ?", new String[]{String.valueOf(maloai)});
        return check != -1;
    }

    //xoa the loai: 1-xoa thanh cong, -1-xoa that bai, 0-co the loai ton tai trong muc san pham
    public int xoaTheLoai(int maloai){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SANPHAM WHERE maloai = ?", new String[]{String.valueOf(maloai)});
        if (cursor.getCount() !=0 ){
            return -1;
        }
        long check =db.delete("THELOAI", "maloai=?", new String[]{String.valueOf(maloai)});
        if (check == -1){
            return 0;
        }
        return 1;
    }
}
