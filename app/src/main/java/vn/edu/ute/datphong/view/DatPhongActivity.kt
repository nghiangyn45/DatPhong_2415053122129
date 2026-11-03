package vn.edu.ute.datphong.view
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import vn.edu.ute.datphong.data.DuLieuMau
import vn.edu.ute.datphong.databinding.ActivityDatPhongBinding
import vn.edu.ute.datphong.helper.DatPhongHelper
import vn.edu.ute.datphong.helper.KiemTraHelper
import vn.edu.ute.datphong.helper.dinhDangTien
import vn.edu.ute.datphong.model.LoaiPhong
import com.google.android.material.snackbar.Snackbar
class DatPhongActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatPhongBinding
    var selectedLoaiPhong: LoaiPhong = DuLieuMau.dsLoaiPhong[0]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatPhongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateLoaiPhongUI()

        val updateTamTinh = {
            val soDem = binding.edtSoDem.text.toString().toIntOrNull() ?: 0
            val soKhach = binding.edtSoKhach.text.toString().toIntOrNull() ?: 0
            val tien = DatPhongHelper.tinhTienPhong_2415053122129(selectedLoaiPhong, soDem, soKhach)
            binding.txtTamTinh.text = "Tạm tính: ${tien.dinhDangTien()}"
        }
        binding.edtSoDem.doAfterTextChanged { updateTamTinh() }
        binding.edtSoKhach.doAfterTextChanged { updateTamTinh() }

        binding.btnDatPhong.setOnClickListener {
            
        }
