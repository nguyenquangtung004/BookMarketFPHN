package DuAn1_FPLHN.Nhom2.Book_Market.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Database.DBHelper;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.TaiKhoan;

public class TaiKhoanDAO {
    private final DBHelper dbHelper;
    private final SharedPreferences sharedPreferences;

    public TaiKhoanDAO(Context context) {
        dbHelper = new DBHelper(context);
        sharedPreferences = context.getSharedPreferences("ThongTinTaiKhoan", Context.MODE_PRIVATE);
    }

    //dangnhap
    public boolean checkDangNhap(String taikhoan, String matkhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAIKHOAN WHERE taikhoan = ? AND matkhau = ?", new String[]{taikhoan, matkhau});// Truyền 2 giá trị người dùng nhập vào dấu '?'
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            //luu sharedpreferences
            // matk integer primary key autoincrement, taikhoan text, matkhau text, hoten text, sdt text, diachi text
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("matk", cursor.getInt(0));
            editor.putString("taikhoan", cursor.getString(1));
            editor.putString("matkhau", cursor.getString(2));
            editor.putString("hoten", cursor.getString(3));
            editor.putString("sdt", cursor.getString(4));
            editor.putString("diachi", cursor.getString(5));
            editor.putString("loaitaikhoan", cursor.getString(6));
            editor.commit();
            Log.d("TaiKhoanDAO", "Đăng nhập thành công với tài khoản " + taikhoan);
            return true;
        } else {
            Log.e("TaiKhoanDAO", "Đăng nhập thất bại với tài khoản " + taikhoan);
            return false;
        }
    }

    //dang ki
    public boolean checkDangKi(TaiKhoan taiKhoan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taikhoan", taiKhoan.getTaikhoan());
        contentValues.put("matkhau", taiKhoan.getMatkhau());
        contentValues.put("hoten", taiKhoan.getHoten());
        contentValues.put("sdt", taiKhoan.getSdt());
        contentValues.put("diachi", taiKhoan.getDiachi());
        contentValues.put("loaitaikhoan", taiKhoan.getLoaitaikhoan());

        long check = db.insert("TAIKHOAN", null, contentValues);
        if (check != -1) {
            Log.d("TaiKhoanDAO", "Đăng ký tài khoản " + taiKhoan.getTaikhoan() + " thành công!");  // Thêm log ở đây
        } else {
            Log.e("TaiKhoanDAO", "Đăng ký tài khoản " + taiKhoan.getTaikhoan() + " thất bại!");  // Thêm log ở đây
        }
        return check != -1;
    }

    //lay danh sach tai khoan

    public ArrayList<TaiKhoan> getDSTaiKhoan() {
        ArrayList<TaiKhoan> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAIKHOAN", null);
        Log.d("TaiKhoanDAO", "Số lượng tài khoản trong cơ sở dữ liệu: " + cursor.getCount());
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new TaiKhoan(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    //lay thong tin tai khoan theo ma tk
    public TaiKhoan getThongTinTaiKhoan(int matk) {
        TaiKhoan taiKhoan = new TaiKhoan();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAIKHOAN WHERE matk = ?", new String[]{String.valueOf(matk)});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            taiKhoan = new TaiKhoan(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            Log.d("TaiKhoanDAO", "Lấy thông tin tài khoản với mã " + matk + " thành công!");
        } else {
            Log.e("TaiKhoanDAO", "Lấy thông tin tài khoản với mã " + matk + " thất bại!");  // Thêm log ở đây
        }
        return taiKhoan;
    }

    //sua tai khoan
    public boolean suaTaiKhoan(TaiKhoan taiKhoan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taikhoan", taiKhoan.getTaikhoan());
        contentValues.put("matkhau", taiKhoan.getMatkhau());
        contentValues.put("hoten", taiKhoan.getHoten());
        contentValues.put("sdt", taiKhoan.getSdt());
        contentValues.put("diachi", taiKhoan.getDiachi());
        long check = db.update("TAIKHOAN", contentValues, "matk =?", new String[]{String.valueOf(taiKhoan.getMatk())});
        if (check != -1) {
            Log.d("TaiKhoanDAO", "Cập nhật thông tin tài khoản " + taiKhoan.getTaikhoan() + " thành công!");  // Thêm log ở đây
        } else {
            Log.e("TaiKhoanDAO", "Cập nhật thông tin tài khoản " + taiKhoan.getTaikhoan() + " thất bại!");  // Thêm log ở đây
        }
        return check != -1;
    }

    //doi thong tin tai khoan
    public boolean doiThongTinTK(TaiKhoan taiKhoan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", taiKhoan.getHoten());
        contentValues.put("sdt", taiKhoan.getSdt());
        contentValues.put("diachi", taiKhoan.getDiachi());
        long check = db.update("TAIKHOAN", contentValues, "matk = ?", new String[]{String.valueOf(taiKhoan.getMatk())});
        if (check != -1) {
            Log.d("TaiKhoanDAO", "Đổi thông tin tài khoản " + taiKhoan.getTaikhoan() + " thành công!");  // Thêm log ở đây
        } else {
            Log.e("TaiKhoanDAO", "Đổi thông tin tài khoản " + taiKhoan.getTaikhoan() + " thất bại!");  // Thêm log ở đây
        }
        return check != -1;
    }

    //doi mat khau: -1 la mat khau cu khong dung
    public int doiMatKhau(int matk, String matkhaucu, String matkhaumoi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAIKHOAN WHERE matk = ? AND matkhau = ?", new String[]{String.valueOf(matk), matkhaucu});
        if (cursor.getCount() != 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", matkhaumoi);
            long check = db.update("TAIKHOAN", contentValues, "matk = ?", new String[]{String.valueOf(matk)});
            if (check == -1) {
                Log.e("TaiKhoanDAO", "Đổi mật khẩu tài khoản với mã " + matk + " thất bại!");
                return 0;
            }
            Log.d("TaiKhoanDAO", "Đổi mật khẩu tài khoản với mã " + matk + " thành công!");
            return 1;
        } else {
            Log.e("TaiKhoanDAO", "Mật khẩu cũ không đúng cho tài khoản với mã " + matk);
            return -1;
        }
    }

    //quen mat khau
    public int quenMatKhau(String taikhoan, String matkhaumoi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAIKHOAN WHERE taikhoan = ?", new String[]{taikhoan});
        if (cursor.getCount() != 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", matkhaumoi);
            long check = db.update("TAIKHOAN", contentValues, "taikhoan = ?", new String[]{String.valueOf(taikhoan)});
            if (check == -1) {
                Log.e("TaiKhoanDAO", "Đặt lại mật khẩu cho tài khoản " + taikhoan + " thất bại!");
                return 0;
            }
            Log.d("TaiKhoanDAO", "Đặt lại mật khẩu cho tài khoản " + taikhoan + " thành công!");
            return 1;
        } else {
            Log.e("TaiKhoanDAO", "Không tìm thấy tài khoản " + taikhoan + " trong cơ sở dữ liệu!");
            return -1;
        }
    }

    // Xóa tài khoản: 1: xóa thành công, -1: xóa thất bại, 0: có thể loại tồn tại trong mục sản phẩm
    public int xoaTaiKhoan(int maTK) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HOADON WHERE matk=?", new String[]{String.valueOf(maTK)});
        if (cursor.getCount() != 0) {
            Log.e("TaiKhoanDAO", "Không thể xóa tài khoản " + maTK + " vì nó tồn tại trong hóa đơn!");
            return -1;
        }
        long check = db.delete("TAIKHOAN", "matk = ?", new String[]{String.valueOf(maTK)});
        if (check == -1) {
            Log.e("TaiKhoanDAO", "Xóa tài khoản " + maTK + " thất bại!");
            return 0;
        }
        Log.d("TaiKhoanDAO", "Xóa tài khoản " + maTK + " thành công!");
        return 1;
    }

    // Thêm tài khoản: -1 là đã tồn tại, 0 thêm thất bại, 1 thêm thành công
    public int themTaiKhoan(TaiKhoan taiKhoan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taikhoan", taiKhoan.getTaikhoan());
        contentValues.put("matkhau", taiKhoan.getMatkhau());
        contentValues.put("hoten", taiKhoan.getHoten());
        contentValues.put("sdt", taiKhoan.getSdt());
        contentValues.put("diachi", taiKhoan.getDiachi());
        contentValues.put("loaitaikhoan", taiKhoan.getLoaitaikhoan());

        //check tai khoan ton tai hay chua
        Cursor cursor = db.rawQuery("SELECT * FROM TAIKHOAN WHERE taikhoan=?", new String[]{taiKhoan.getTaikhoan()});
        if (cursor.getCount() != 0) {
            Log.e("TaiKhoanDAO", "Tài khoản " + taiKhoan.getTaikhoan() + " đã tồn tại!");
            return -1;
        }
        long check = db.insert("TAIKHOAN", null, contentValues);
        if (check == -1) {
            Log.e("TaiKhoanDAO", "Thêm tài khoản " + taiKhoan.getTaikhoan() + " thất bại!");
            return 0;
        } else {
            Log.d("TaiKhoanDAO", "Thêm tài khoản " + taiKhoan.getTaikhoan() + " thành công!");
            return 1;
        }
    }
}
