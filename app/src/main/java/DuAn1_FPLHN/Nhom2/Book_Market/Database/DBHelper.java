package DuAn1_FPLHN.Nhom2.Book_Market.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){super(context, "BookMarket", null, 4);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Bảng Sản Phẩm Gồm 6 Thuộc Tính
        //masp,tensp,maloai,anhsp,motasp,giasp
        String dbSANPHAM = "CREATE TABLE SANPHAM(masp integer primary key autoincrement, tensp text, maloai integer, anhsp blob, motasp text, giasp integer)";
        db.execSQL(dbSANPHAM);
        //Cần Dữ Liệu Mẫu
        /*<----------------------------------------------->*/

        //Bảng Thể Loại Gồm 2 Thuộc Tính
        //maloai,tenloai
        String dbTHELOAI = "CREATE TABLE THELOAI(maloai integer PRIMARY KEY AUTOINCREMENT, tenloai text)";
        db.execSQL(dbTHELOAI);
        db.execSQL("INSERT INTO THELOAI VALUES (1, 'Giáo Khoa'), (2, 'Tài liệu'), (3, 'Bộ Đề'), (4, 'Truyện')");
        /*<----------------------------------------------->*/

        //Bảng Giỏ Hàng Gồm 5 Thuộc Tính
        //id,tensp,anhsp,giasp,soluong
        String dbGIOHANG = "create table GIOHANG(id integer primary key autoincrement, tensp text, anhsp blob, giasp integer, soluong integer)";
        db.execSQL(dbGIOHANG);
        /*<----------------------------------------------->*/

        //Bảng Hóa Đơn Gồm 9 Thuộc Tính
        //mahd,ngaylap,matk,hoten,sdt,diachi,tongtien,tongsanpham,trangthai
        String dbHOADON = "create table HOADON(mahd integer primary key autoincrement, ngaylap text, matk integer references TAIKHOAN(matk), hoten text, sdt text, diachi text, tongtien integer, tongsanpham text, trangthai integer)";
        db.execSQL(dbHOADON);
        /*<----------------------------------------------->*/

        //Bảng Tài Khoản Gồm
        //matk,taikhoan,matkhau,hoten,sdt,diachi,loaitaikhoan
        String dbTAIKHOAN = "create table TAIKHOAN(matk integer primary key autoincrement, taikhoan text, matkhau text, hoten text, sdt text, diachi text, loaitaikhoan text)";
        db.execSQL(dbTAIKHOAN);
        /*<----------------------------------------------->*/
        db.execSQL("insert into TAIKHOAN values " +
                "(1, 'admin', '123', 'Vương Đình Hiến', '0866815906', 'Kim Chung - Hoài Đức - Hà Nội','admin'), " +
                "(2, 'tungnq', '123', 'Nguyễn Quang Tùng', '0877956178', 'Hoàng Mai - Hà Nội','buyer')");
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
