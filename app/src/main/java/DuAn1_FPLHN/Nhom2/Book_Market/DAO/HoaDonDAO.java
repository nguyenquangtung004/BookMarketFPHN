package DuAn1_FPLHN.Nhom2.Book_Market.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Database.DBHelper;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;

public class HoaDonDAO {
    private final DBHelper dbHelper;
    Context context;
    public HoaDonDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    // Lấy danh sách hóa đơn theo matk
    public ArrayList<HoaDon> getDSHoaDonTheoKH(int matk){
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HOADON WHERE matk = ? ORDER BY mahd DESC", new String[]{String.valueOf(matk)} );
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new HoaDon(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6),cursor.getString(7), cursor.getInt(8)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Lấy toàn bộ DS hóa đơn
    public ArrayList<HoaDon> getDSHoaDon(){
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HOADON ORDER BY mahd DESC", null );
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new HoaDon(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Thêm hoá đơn
    public boolean themHoaDon(HoaDon hoaDon){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngaylap", hoaDon.getNgaylap());
        contentValues.put("matk", hoaDon.getMatk());
        contentValues.put("hoten", hoaDon.getHoten());
        contentValues.put("sdt", hoaDon.getSdt());
        contentValues.put("diachi", hoaDon.getDiachi());
        contentValues.put("tongtien", hoaDon.getTongtien());
        contentValues.put("tongsanpham", hoaDon.getTongsanpham());
        contentValues.put("trangthai", hoaDon.getTrangthai());

        long check = db.insert("HOADON", null, contentValues);
        return check != -1;
    }

    // Xóa hóa đơn theo khách hàng
    public boolean xoaHoaDonTheoKH(int mahd, int matk){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("HOADON", "mahd = ? AND matk =?", new String[]{String.valueOf(mahd), String.valueOf(matk)});
        return check != -1;
    }

    // Xóa hóa đơn
    public boolean xoaHoaDon(int mahd){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("HOADON", "mahd = ?", new String[]{String.valueOf(mahd), String.valueOf(mahd)});
        return check != -1;
    }

    // Thay đổi trạng thái hóa đơn
    public boolean thayDoiTrangThai(HoaDon hoaDon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Cập nhật với giá trị ngược lại của trạng thái hiện tại
        contentValues.put("trangthai", hoaDon.getTrangthai() == 0 ? 1 : 0);

        long check = db.update("HOADON", contentValues, "mahd = ?", new String[]{String.valueOf(hoaDon.getMahd())});
        return check != -1;
    }
}
