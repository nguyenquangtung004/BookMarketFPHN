package DuAn1_FPLHN.Nhom2.Book_Market.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import DuAn1_FPLHN.Nhom2.Book_Market.Database.DBHelper;
import DuAn1_FPLHN.Nhom2.Book_Market.Model.HoaDon;

public class ThongKeDAO {
    private final DBHelper dbHelper;

    public ThongKeDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    //thong ke doanh thu
    public int getDoanhThu(String ngaybatdau, String ngayketthuc) {// 2023/09/19
        String day_start = ngaybatdau.replace("/", ""); // bo dau '/'
        String day_end = ngayketthuc.replace("/", ""); // bo dau '/'
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(tongtien) FROM HOADON WHERE substr(ngaylap,7)||substr(ngaylap,4,2)||substr(ngaylap,1,2) BETWEEN ? AND ? ", new String[]{day_start, day_end});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            int doanhThu = cursor.getInt(0);
            Log.d("HoaDonDAO", "Doanh thu từ ngày " + ngaybatdau + " đến ngày " + ngayketthuc + " là: " + doanhThu);
            return doanhThu;
        }
        Log.e("HoaDonDAO", "Không tìm thấy hóa đơn nào từ ngày " + ngaybatdau + " đến ngày " + ngayketthuc);
        return 0;
    }

    //thong ke tong doanh thu
    public int getTongDoanhThu() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(tongtien) FROM HOADON", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            int tongDoanhThu = cursor.getInt(0);
            Log.d("HoaDonDAO", "Tổng doanh thu là: " + tongDoanhThu);
            return tongDoanhThu;
        }
        Log.e("HoaDonDAO", "Không tìm thấy hóa đơn nào trong cơ sở dữ liệu");
        return 0;
    }

    public ArrayList<HoaDon> getHoaDonTheoNgay(String ngaybatdau, String ngayketthuc) {
        ngaybatdau = ngaybatdau.replace("/", "");
        ngayketthuc = ngayketthuc.replace("/", "");

        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HOADON WHERE substr(ngaylap,7)||substr(ngaylap,4,2)||substr(ngaylap,1,2) BETWEEN ? AND ? ", new String[]{ngaybatdau, ngayketthuc});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new HoaDon(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)));
            } while (cursor.moveToNext());
            Log.d("HoaDonDAO", "Số lượng hóa đơn từ ngày " + ngaybatdau + " đến ngày " + ngayketthuc + " là: " + list.size());
        } else {
            Log.e("HoaDonDAO", "Không tìm thấy hóa đơn nào từ ngày " + ngaybatdau + " đến ngày " + ngayketthuc);  // Thêm log ở đây
        }
        return list;
    }
}
