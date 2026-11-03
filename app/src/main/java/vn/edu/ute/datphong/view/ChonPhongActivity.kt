package vn.edu.ute.datphong.view
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.edu.ute.datphong.data.DuLieuMau
import vn.edu.ute.datphong.databinding.ActivityChonPhongBinding
import vn.edu.ute.datphong.helper.DatPhongHelper
import vn.edu.ute.datphong.helper.dinhDangTien
class ChonPhongActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChonPhongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChonPhongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val soKhach = intent.getIntExtra("so_khach", 1)
        val listPhong = DatPhongHelper.locTheoSoKhach(DuLieuMau.dsLoaiPhong, soKhach)
        for (phong in listPhong) {
            val rb = RadioButton(this).apply {
                text =
                    "${phong.ten} - Sức chứa: ${phong.sucChua} khách - Giá: ${phong.giaMotDem.dinhDangTien()}/đêm"
                tag = phong.ma
            }
            binding.rgLoaiPhong.addView(rb)
        }
        binding.btnChonXong.setOnClickListener {
            val checkedId = binding.rgLoaiPhong.checkedRadioButtonId
            if (checkedId == -1) {
                Toast.makeText(this, "Vui lòng chọn loại phòng", Toast.LENGTH_SHORT).show()
            } else {
                val selectedRb = findViewById<RadioButton>(checkedId)
                val maPhong = selectedRb.tag.toString()
                val resultIntent = Intent().apply {
                    putExtra("ma_loai_phong", maPhong)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}