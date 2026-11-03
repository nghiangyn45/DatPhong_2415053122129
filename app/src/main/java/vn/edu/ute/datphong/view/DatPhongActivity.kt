package vn.edu.ute.datphong.view
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import vn.edu.ute.datphong.data.DuLieuMau
import vn.edu.ute.datphong.databinding.ActivityDatPhongBinding
import vn.edu.ute.datphong.helper.DatPhongHelper
import vn.edu.ute.datphong.helper.KiemTraHelper
import vn.edu.ute.datphong.helper.dinhDangTien
import vn.edu.ute.datphong.model.DonDatPhong
import vn.edu.ute.datphong.model.LoaiPhong
import com.google.android.material.snackbar.Snackbar
class DatPhongActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatPhongBinding
    var selectedLoaiPhong: LoaiPhong = DuLieuMau.dsLoaiPhong[0]

    private val chonPhongLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val maPhong = result.data?.getStringExtra("ma_loai_phong")
            if (maPhong != null) {
                val found = DatPhongHelper.timLoaiPhong(maPhong)
                if (found != null) {
                    selectedLoaiPhong = found
                    updateLoaiPhongUI()
                    triggerTamTinh()
                }
            }
        }
    }
    private val xacNhanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val maDon = result.data?.getStringExtra("ma_don") ?: ""
            Snackbar.make(binding.root, "Đặt phòng thành công - Mã đơn: $maDon", Snackbar.LENGTH_LONG).show()
            clearForm()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatPhongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            val savedMa = savedInstanceState.getString("selected_ma_phong")
            if (savedMa != null) {
                DatPhongHelper.timLoaiPhong(savedMa)?.let { selectedLoaiPhong = it }
            }
        }

        updateLoaiPhongUI()
        binding.edtSoDem.doAfterTextChanged { triggerTamTinh() }
        binding.edtSoKhach.doAfterTextChanged { triggerTamTinh() }

        binding.btnChonPhong.setOnClickListener {
            val soKhach = binding.edtSoKhach.text.toString().toIntOrNull() ?: 1
            val intent = Intent(this, ChonPhongActivity::class.java).apply {
                putExtra("so_khach", soKhach)
            }
            chonPhongLauncher.launch(intent)
        }
        binding.btnDatPhong.setOnClickListener {
            val hoTen = binding.edtHoTen.text.toString()
            val sdt = binding.edtSdt.text.toString()
            val email = binding.edtEmail.text.toString()
            val ngay = binding.edtNgayNhan.text.toString()
            val soDem = binding.edtSoDem.text.toString().toIntOrNull() ?: 0
            val soKhach = binding.edtSoKhach.text.toString().toIntOrNull() ?: 0
            var isValid = true
            if (!KiemTraHelper.kiemTraHoTen(hoTen)) {
                binding.tilHoTen.error = "Họ tên không hợp lệ"
                isValid = false
            } else binding.tilHoTen.error = null

            if (!KiemTraHelper.kiemTraSdt(sdt)) {
                binding.tilSdt.error = "SĐT phải 10 số, bắt đầu 09 hoặc 03"
                isValid = false
            } else binding.tilSdt.error = null

            if (!KiemTraHelper.kiemTraEmail(email)) {
                binding.tilEmail.error = "Email phải đúng đuôi @ute.udn.vn"
                isValid = false
            } else binding.tilEmail.error = null
            if (!KiemTraHelper.kiemTraNgay(ngay)) {
                binding.tilNgayNhan.error = "Ngày định dạng dd/MM/yyyy (2026-2029)"
                isValid = false
            } else binding.tilNgayNhan.error = null
            if (soDem !in 1..19) {
                binding.tilSoDem.error = "Số đêm từ 1 đến 19"
                isValid = false
            } else binding.tilSoDem.error = null
            if (!DatPhongHelper.hopLeSoKhach(selectedLoaiPhong, soKhach)) {
                binding.tilSoKhach.error = "Số khách vượt quá sức chứa cho phép"
                isValid = false
            } else binding.tilSoKhach.error = null
            if (!isValid) {
                Snackbar.make(binding.root, "Vui lòng kiểm tra lại thông tin", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                val don = DonDatPhong(
                    hoTen.trim(),
                    sdt.trim(),
                    email.trim(),
                    selectedLoaiPhong.ma,
                    ngay.trim(),
                    soDem,
                    soKhach
                )
                val tongTien =
                    DatPhongHelper.tinhTienPhong_2415053122129(selectedLoaiPhong, soDem, soKhach)
                val intent = Intent(this, XacNhanActivity::class.java).apply {
                    putExtra("don_dat_phong", don)
                    putExtra("tong_tien", tongTien)
                }
                xacNhanLauncher.launch(intent)
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("selected_ma_phong", selectedLoaiPhong.ma)
    }
    fun updateLoaiPhongUI() {
        binding.txtLoaiPhong.text = "Loại phòng: ${selectedLoaiPhong.ten} - ${selectedLoaiPhong.giaMotDem.dinhDangTien()}/đêm"
    }
    private fun triggerTamTinh() {
        val soDem = binding.edtSoDem.text.toString().toIntOrNull() ?: 0
        val soKhach = binding.edtSoKhach.text.toString().toIntOrNull() ?: 0
        val tien = DatPhongHelper.tinhTienPhong_2415053122129(selectedLoaiPhong, soDem, soKhach)
        binding.txtTamTinh.text = "Tạm tính: ${tien.dinhDangTien()}"
    }

    private fun clearForm() {
        binding.edtHoTen.text?.clear()
        binding.edtSdt.text?.clear()
        binding.edtEmail.text?.clear()
        binding.edtNgayNhan.text?.clear()
        binding.edtSoDem.text?.clear()
        binding.edtSoKhach.text?.clear()
        binding.txtTamTinh.text = "Tạm tính: 0 đ"
    }
    }
