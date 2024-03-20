package DuAn1_FPLHN.Nhom2.Book_Market.Model;

public class TheLoai {
    private int maloai;
    private String tenloai;

    public TheLoai() {
    }

    public TheLoai(int maloai, String tenloai) {
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}
