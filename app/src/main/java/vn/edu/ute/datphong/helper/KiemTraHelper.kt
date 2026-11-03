package vn.edu.ute.datphong.helper

object KiemTraHelper {
    private val HO_TEN_REGEX = Regex("^[\\p{L}]+( [\\p{L}]+)+$")
    private val SDT_REGEX = Regex("^(09|03)\\d{8}$")
    private val EMAIL_REGEX = Regex("^[a-zA-Z][a-zA-Z0-9._]{2,29}@ute\\.udn\\.vn$")
    private val NGAY_REGEX = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(202[6-9])$")
    fun kiemTraHoTen(hoTen: String): Boolean = HO_TEN_REGEX.matches(hoTen.trim())
    fun kiemTraSdt(sdt: String): Boolean = SDT_REGEX.matches(sdt.trim())
    fun kiemTraEmail(email: String): Boolean = EMAIL_REGEX.matches(email.trim())
    fun kiemTraNgay(ngay: String): Boolean = NGAY_REGEX.matches(ngay.trim())
}