package vn.edu.ute.datphong.data
import vn.edu.ute.datphong.model.LoaiPhong
object DuLieuMau {
    val dsLoaiPhong: List<LoaiPhong> = listOf(
        LoaiPhong("P129", "Phòng Tiêu Chuẩn", 500000L, 2),
        LoaiPhong("P201", "Phòng Deluxe Cao Cấp", 1200000L, 3),
        LoaiPhong("P305", "Phòng Suite Gia Đình", 2500000L, 5)
    )
}