package vn.edu.ute.datphong.helper
import java.text.DecimalFormat
fun Long.dinhDangTien(): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(this).replace(",", ".") + " đ"
}