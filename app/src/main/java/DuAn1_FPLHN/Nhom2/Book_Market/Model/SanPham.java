package DuAn1_FPLHN.Nhom2.Book_Market.Model;

public class SanPham {
    private int masp;
    private String tensp;
    private byte[] anhsp;
    private String motasp;
    private int giasp;
    private int maloai;
    private String tenloai;
    private int soLuongTonKho;


//    public SanPham(int masp, String tensp, byte[] anhsp, String motasp, int giasp) {
//        this.masp = masp;
//        this.tensp = tensp;
//        this.anhsp = anhsp;
//        this.motasp = motasp;
//        this.giasp = giasp;
//    }
//
//    public SanPham(String tensp, byte[] anhsp, String motasp, int giasp) {
//        this.tensp = tensp;
//        this.anhsp = anhsp;
//        this.motasp = motasp;
//        this.giasp = giasp;
//    }

    //Dòng Này Là SanPham DAO
    public SanPham(int masp, String tensp, byte[] anhsp, String tenloai, String motasp, int giasp, int soLuongTonKho) {
        this.masp = masp;
        this.tensp = tensp;
        this.anhsp = anhsp;
        this.tenloai = tenloai;
        this.motasp = motasp;
        this.giasp = giasp;
        this.soLuongTonKho = soLuongTonKho;
    }

    //Dòng Này Là QuanLySanPham
    public SanPham(String tensp, byte[] anhsp, String motasp, int giasp, int maloai,int soLuongTonKho) {
        this.masp = masp;
        this.tensp = tensp;
        this.anhsp = anhsp;
        this.motasp = motasp;
        this.giasp = giasp;
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.soLuongTonKho = soLuongTonKho;
    }

    //Dòng Này Là SanPhamAdapter
    public SanPham(int masp, String tensp, byte[] anhsp, String motasp, int giasp, int maloai, int soLuongTonKho) {
        this.masp = masp;
        this.tensp = tensp;
        this.anhsp = anhsp;
        this.motasp = motasp;
        this.giasp = giasp;
        this.maloai = maloai;
        this.soLuongTonKho = soLuongTonKho;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public byte[] getAnhsp() {
        return anhsp;
    }

    public void setAnhsp(byte[] anhsp) {
        this.anhsp = anhsp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
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

    public int getSoLuongTonKho() {
        return soLuongTonKho;
    }

    public void setSoLuongTonKho(int soLuongTonKho) {
        this.soLuongTonKho = soLuongTonKho;
    }
}
