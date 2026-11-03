package vn.edu.ute.datphong
import java.io.Serializable
data class DonDatPhong(
    val hoTen: String,
    val sdt: String,
    val email: String,
    val maLoaiPhong: String,
    val ngayNhan: String,
    val soDem: Int,
    val soKhach: Int,
    val ghiChu: String = ""
) : Serializable {
    fun tomTat(): String {
        return "Họ tên: $hoTen, Mã phòng: $maLoaiPhong, Ngày nhận: $ngayNhan, Số đêm: $soDem"
    }
}
