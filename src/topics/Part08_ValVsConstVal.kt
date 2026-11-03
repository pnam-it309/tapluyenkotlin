package topics

import java.io.File
import java.lang.annotation.RetentionPolicy

/**
 * PHẦN 8: SO SÁNH VAL VÀ CONST VAL TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Phân định rõ ràng giữa hai cấp độ hằng số:
 *   1. `val` (Runtime Constant): Biến chỉ đọc, giá trị được gán khi chương trình thực thi (Runtime).
 *   2. `const val` (Compile-time Constant): Hằng số thực sự, giá trị được xác định ngay khi biên dịch (Compile-time)
 *      và được Compiler chèn thẳng giá trị thô (Inlined) vào nơi sử dụng trong Bytecode.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Tối ưu hóa hiệu năng: `const val` loại bỏ chi phí gọi hàm getter và chi phí cấp phát vùng nhớ.
 * - Yêu cầu bắt buộc của Annotation: Các tham số truyền vào Annotation (ví dụ `@Retention`, `@Target`, `@Named`)
 *   bắt buộc phải là `const val` vì Annotation processor cần biết giá trị trước khi build.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - `const val`:
 *   + *Điều kiện*: Phải ở Top-level hoặc trong `object`/`companion object`; chỉ áp dụng cho kiểu nguyên thủy (`Int`, `Double`...) hoặc `String`.
 *   + *Tối ưu nhất cho*: Cấu hình hệ thống cố định (Base URL, Timeout, Database Version, Action Keys, Annotations).
 * - `val`:
 *   + *Linh hoạt*: Khai báo được ở mọi nơi, áp dụng cho mọi kiểu đối tượng phức tạp, có thể gán từ kết quả hàm (`val now = System.currentTimeMillis()`).
 *   + *Tối ưu cho*: Các đối tượng chỉ đọc nhưng dữ liệu phụ thuộc vào trạng thái lúc ứng dụng đang chạy.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================

// Hợp lệ: const val ở top-level với kiểu String và Int
const val APP_VERSION = "2.5.0"
const val MAX_RETRY_COUNT = 3

object ApiConfig {
    const val BASE_URL = "https://api.example.com/v1"
    const val TIMEOUT_SECONDS = 30
}

class ServerConfig {
    companion object {
        const val PORT = 8080
    }
    // val runtime: Được xác định khi tạo instance của ServerConfig
    val startedAt: Long = System.currentTimeMillis()
}

fun exampleValVsConstVal() {
    println("--- 8.1 Minh họa val vs const val ---")
    println("APP_VERSION: $APP_VERSION (Compile-time constant)")
    println("BASE_URL: ${ApiConfig.BASE_URL}")
    println("PORT: ${ServerConfig.PORT}")

    val server = ServerConfig()
    println("Server started at: ${server.startedAt} (Runtime val)")
}

fun exampleConstValInliningComparison() {
    println("--- 8.2 So sánh cơ chế Inlining của const val ---")
    // Ở cấp độ bytecode:
    // val count = MAX_RETRY_COUNT -> Biên dịch thành: bipush 3 (không hề gọi getter hay load biến tĩnh)
    val retries = MAX_RETRY_COUNT
    println("Số lần thử lại tối đa (Inlined constant): $retries")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 8.1:
 * Thiết kế object [DiscountPolicy] quản lý chính sách giảm giá:
 * - Khai báo các hằng số compile-time:
 *   + `const val VIP_DISCOUNT_PERCENT = 20` (20%)
 *   + `const val REGULAR_DISCOUNT_PERCENT = 5` (5%)
 *   + `const val MIN_ORDER_AMOUNT_FOR_VIP = 1000.0`
 * - Cung cấp hàm `fun calculateFinalPrice(isVip: Boolean, rawAmount: Double): Double`:
 *   + Nếu `rawAmount <= 0.0`, ném IllegalArgumentException.
 *   + Nếu isVip == true và rawAmount >= MIN_ORDER_AMOUNT_FOR_VIP:
 *       giảm giá theo VIP_DISCOUNT_PERCENT.
 *   + Ngược lại: giảm giá theo REGULAR_DISCOUNT_PERCENT.
 *   + Trả về số tiền sau giảm giá.
 */
object DiscountPolicy {
    const val VIP_DISCOUNT_PERCENT = 20
    const val REGULAR_DISCOUNT_PERCENT = 5
    const val MIN_ORDER_AMOUNT_FOR_VIP = 1000.0

    fun calculateFinalPrice(isVip: Boolean, rawAmount: Double): Double {
        require(rawAmount > 0.0) { "Số tiền đơn hàng phải lớn hơn 0" }
        val discountRate = if (isVip && rawAmount >= MIN_ORDER_AMOUNT_FOR_VIP) {
            VIP_DISCOUNT_PERCENT
        } else {
            REGULAR_DISCOUNT_PERCENT
        }
        return rawAmount * (1.0 - discountRate / 100.0)
    }
}

fun runPart08() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 8: VAL VS CONST VAL")
    println("==================================================")
    exampleValVsConstVal()
    exampleConstValInliningComparison()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val p1 = DiscountPolicy.calculateFinalPrice(isVip = true, rawAmount = 2000.0)
    // 2000 * 0.8 = 1600.0
    check(p1 == 1600.0) { "Sai giá VIP đủ điều kiện: $p1" }

    val p2 = DiscountPolicy.calculateFinalPrice(isVip = true, rawAmount = 500.0)
    // Chưa đủ 1000.0 -> chỉ giảm 5% -> 500 * 0.95 = 475.0
    check(p2 == 475.0) { "Sai giá VIP chưa đủ đơn tối thiểu: $p2" }

    val p3 = DiscountPolicy.calculateFinalPrice(isVip = false, rawAmount = 1000.0)
    // Regular giảm 5% -> 1000 * 0.95 = 950.0
    check(p3 == 950.0) { "Sai giá Regular: $p3" }

    println("✅ Bài tập 8.1 (DiscountPolicy với const val): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 8!\n")
}

fun main() {
    runPart08()
}
