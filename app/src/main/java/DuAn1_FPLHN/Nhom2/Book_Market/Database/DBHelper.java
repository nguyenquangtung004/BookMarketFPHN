package DuAn1_FPLHN.Nhom2.Book_Market.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){super(context, "BookMarket", null, 3);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        //bang san pham
        String dbSANPHAM = "create table SANPHAM(masp integer primary key autoincrement, tensp text, maloai integer, anhsp blob, motasp text, giasp integer)";
        db.execSQL(dbSANPHAM);
        //bang the loai
        String dbTHELOAI = "CREATE TABLE THELOAI(maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(dbTHELOAI);
        //bang gio hang
        String dbGIOHANG = "create table GIOHANG(id integer primary key autoincrement, tensp text, anhsp blob, giasp integer, soluong integer)";
        db.execSQL(dbGIOHANG);
        //bang hoa don
        String dbHOADON = "create table HOADON(mahd integer primary key autoincrement, ngaylap text, matk integer references TAIKHOAN(matk), hoten text, sdt text, diachi text, tongtien integer, tongsanpham text, trangthai integer)";
        db.execSQL(dbHOADON);
        //bang tai khoan
        String dbTAIKHOAN = "create table TAIKHOAN(matk integer primary key autoincrement, taikhoan text, matkhau text, hoten text, sdt text, diachi text, loaitaikhoan text)";
        db.execSQL(dbTAIKHOAN);

        //du lieu mau

        db.execSQL("insert into THELOAI values (1, 'Thiếu nhi'), (2, 'Tài liệu'), (3, 'Ngụ ngôn'), (4, 'Truyện hài')");

        db.execSQL("insert into HOADON values (1, '09/11/2023', 1, 'Vuong Dinh Hien', '0866815906', 'Kim Chung - Hoài Đức - Hà Nội', 90000, 'Cháo gà x1', 0), (2, '03/11/2023', 2, 'HienDinh', '0979991999', 'Duy Tiên - Hà Nam', 110000, 'Cơm bò x1', 1)");

        db.execSQL("insert into TAIKHOAN values (1, 'admin', '123', 'Vuong Dinh Hien', '0866815906', 'Kim Chung - Hoài Đức - Hà Nội','admin'), (2, 'tungnq', '123', 'QuangTung', '0877956178', 'Hoàng Mai - Hà Nội','khách hàng')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("drop table if exists SANPHAM");
            db.execSQL("drop table if exists THELOAI");
            db.execSQL("drop table if exists GIOHANG");
            db.execSQL("drop table if exists HOADON");
            db.execSQL("drop table if exists TAIKHOAN");
            onCreate(db);
        }

    }
}
