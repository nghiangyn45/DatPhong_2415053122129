package vn.edu.ute.datphong.view
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.edu.ute.datphong.databinding.ActivityXacNhanBinding
import vn.edu.ute.datphong.helper.DatPhongHelper
import vn.edu.ute.datphong.helper.dinhDangTien
import vn.edu.ute.datphong.model.DonDatPhong
class XacNhanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityXacNhanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityXacNhanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val don = intent.getSerializableExtra("don_dat_phong") as? DonDatPhong
        val tongTien = intent.getLongExtra("tong_tien", 0L)

        if (don != null) {
            binding.txtHoTen.text = "Họ tên: ${don.hoTen}"
            binding.txtSdt.text = "Số điện thoại: ${don.sdt}"
            binding.txtEmail.text = "Email: ${don.email}"
            val loai = DatPhongHelper.timLoaiPhong(don.maLoaiPhong)
            binding.txtLoaiPhong.text = "Loại phòng: ${loai?.ten ?: don.maLoaiPhong}"
            binding.txtNgayNhan.text = "Ngày nhận: ${don.ngayNhan}"
            binding.txtSoDem.text = "Số đêm: ${don.soDem}"
            binding.txtSoKhach.text = "Số khách: ${don.soKhach}"
            binding.txtTongTien.text = "Tổng tiền: ${tongTien.dinhDangTien()}"

            binding.btnXacNhan.setOnClickListener {
                val maDon = DatPhongHelper.taoMaDon(don)
                val resultIntent = Intent().apply {
                    putExtra("ma_don", maDon)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
        binding.btnSua.setOnClickListener {
            finish()
        }
    }
}