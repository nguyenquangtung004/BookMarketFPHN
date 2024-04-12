package DuAn1_FPLHN.Nhom2.Book_Market.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Database.DBHelper;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.GioHang;

public class GioHangDAO {
    private final DBHelper dbHelper;

    public GioHangDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    // Lấy danh sách sản phẩm
    public ArrayList<GioHang> getDSGioHang(){
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM GIOHANG", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new GioHang(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        return list;
    }
    // Thêm sản phẩm
    public boolean themGioHang(GioHang gioHang){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensp", gioHang.getTensp());
        contentValues.put("anhsp", gioHang.getAnhsp());
        contentValues.put("giasp", gioHang.getGiasp());
        contentValues.put("soluong", gioHang.getSoluong());
        contentValues.put("solgtonkho", gioHang.getSoLuongTonKho());
        long check = db.insert("GIOHANG", null, contentValues);
        return check != -1;
    }

    // Xóa sạch Giỏ hàng
    public boolean clearGioHang(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long check = db.delete("GIOHANG", null, null);
        return check != -1;
    }

    // Xóa sản phẩm giỏ hàng
    public boolean xoaSPGioHang(int magh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long check = db.delete("GIOHANG", "id = ?", new String[]{String.valueOf(magh)});
        return check != -1;
    }

}
