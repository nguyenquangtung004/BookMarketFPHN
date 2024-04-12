package DuAn1_FPLHN.Nhom2.Book_Market.DAO;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Database.DBHelper;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TheLoai;

public class TheLoaiDAO {
    private final DBHelper dbHelper;

    public TheLoaiDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Hàm thêm thể loại
    // Nhận vào tham số là tên thể loại
    // Trả về true nếu thêm thành công, false nếu thêm thất bại
    public boolean themTheLoai(String tenloai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = db.insert("THELOAI", null, contentValues);
        if (check != -1) {
            Log.d("TheLoaiDAO", "Thêm thể loại " + tenloai + " thành công!");  // Thêm log ở đây
        } else {
            Log.e("TheLoaiDAO", "Thêm thể loại " + tenloai + " thất bại!");  // Thêm log ở đây
        }
        return check != -1;
    }

    // Hàm sửa thể loại
    // Nhận vào tham số là mã thể loại và tên thể loại mới
    // Trả về true nếu sửa thành công, false nếu sửa thất bại
    public boolean suaTheLoai(int maloai, String tenloai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = db.update("THELOAI", contentValues, "maloai = ?", new String[]{String.valueOf(maloai)});
        if (check != -1) {
            Log.d("TheLoaiDAO", "Sửa thể loại có mã " + maloai + " thành công!");  // Thêm log ở đây
        } else {
            Log.e("TheLoaiDAO", "Sửa thể loại có mã " + maloai + " thất bại!");  // Thêm log ở đây
        }
        return check != -1;
    }

    // Hàm xóa thể loại
    // Nhận vào tham số là mã thể loại
    // Trả về 1 nếu xóa thành công, -1 nếu xóa thất bại, 0 nếu thể loại tồn tại trong mục sản phẩm
    public int xoaTheLoai(int maloai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SANPHAM WHERE maloai = ?", new String[]{String.valueOf(maloai)});
        if (cursor.getCount() != 0) {
            Log.e("TheLoaiDAO", "Không thể xóa thể loại có mã " + maloai + " vì nó tồn tại trong sản phẩm!");
            return -1;
        }
        long check = db.delete("THELOAI", "maloai=?", new String[]{String.valueOf(maloai)});
        if (check == -1) {
            Log.e("TheLoaiDAO", "Xóa thể loại có mã " + maloai + " thất bại!");
            return 0;
        }
        Log.d("TheLoaiDAO", "Xóa thể loại có mã " + maloai + " thành công!");
        return 1;
    }

    // Hàm lấy danh sách thể loại
    // Trả về danh sách các thể loại có trong cơ sở dữ liệu
    public ArrayList<TheLoai> getDSTheLoai() {
        ArrayList<TheLoai> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THELOAI", null);
        Log.d("TheLoaiDAO", "Số lượng thể loại trong cơ sở dữ liệu: " + cursor.getCount());
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new TheLoai(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }
}
