package DuAn1_FPLHN.Nhom2.Book_Market.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Database.DBHelper;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.SanPham;

public class SanPhamDAO {
    private final DBHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Hàm thêm sản phẩm vào cơ sở dữ liệu
    // Nhận vào một đối tượng SanPham
    // Trả về true nếu thêm thành công, false nếu thêm thất bại
    public boolean themSanPham(SanPham sanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensp", sanPham.getTensp());
        contentValues.put("anhsp", sanPham.getAnhsp());
        contentValues.put("maloai", sanPham.getMaloai());
        contentValues.put("motasp", sanPham.getMotasp());
        contentValues.put("giasp", sanPham.getGiasp());
        contentValues.put("soluongtonkho",sanPham.getSoLuongTonKho());

        long check = db.insert("SANPHAM", null, contentValues);
        return check != -1;
    }

    // Hàm sửa thông tin sản phẩm trong cơ sở dữ liệu
    // Nhận vào một đối tượng SanPham
    // Trả về true nếu sửa thành công, false nếu sửa thất bại
    public boolean suaSanPham(SanPham sanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensp", sanPham.getTensp());
        contentValues.put("anhsp", sanPham.getAnhsp());
        contentValues.put("motasp", sanPham.getMotasp());
        contentValues.put("giasp", sanPham.getGiasp());
        contentValues.put("maloai", sanPham.getMaloai());
        contentValues.put("soluongtonkho",sanPham.getSoLuongTonKho());
        long check = db.update("SANPHAM", contentValues, "masp = ?", new String[]{String.valueOf(sanPham.getMasp())});
        return check != -1;
    }

    // Hàm xóa sản phẩm khỏi cơ sở dữ liệu
    // Nhận vào mã sản phẩm
    // Trả về 1 nếu xóa thành công, 0 nếu xóa thất bại
    public int xoaSanPham(int maSP) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("SANPHAM", "masp = ?", new String[]{String.valueOf(maSP)});
        if (check == -1) {
            return 0;
        }
        return 1;
    }

    // Hàm lấy danh sách sản phẩm từ cơ sở dữ liệu
    // Trả về một ArrayList chứa các đối tượng SanPham
    public ArrayList<SanPham> getDSSanPham() {
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sp.masp, sp.tensp, sp.anhsp, loai.tenloai, sp.motasp, sp.giasp, sp.soluongtonkho  FROM SANPHAM sp, THELOAI loai WHERE sp.maloai = loai.maloai", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5),cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Hàm lấy danh sách sản phẩm theo loại từ cơ sở dữ liệu
    // Nhận vào mã loại
    // Trả về một ArrayList chứa các đối tượng SanPham thuộc loại đó
    public ArrayList<SanPham> getDSSanPhamTheoLoai(Integer maloai) {
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sp.masp, sp.tensp, sp.anhsp, loai.tenloai, sp.motasp, sp.giasp,sp.soluongtonkho  FROM SANPHAM sp, THELOAI loai WHERE sp.maloai = loai.maloai AND sp.maloai = ?", new String[]{String.valueOf(maloai)});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getBlob(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        return list;

    }
}
