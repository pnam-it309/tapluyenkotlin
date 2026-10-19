package topics

/**
 * PHẦN 8: SO SÁNH VAL VÀ CONST VAL TRONG KOTLIN
 *
 * Lý thuyết:
 * 1. val (Read-only):
 *    - Giá trị được xác định ở RUNTIME (khi chương trình đang chạy).
 *    - Có thể gán thông qua kết quả của một hàm hoặc biểu thức tính toán phức tạp:
 *      val currentTime = System.currentTimeMillis()
 *    - Có thể khai báo ở bất kỳ đâu (local trong hàm, thuộc tính của class, top-level).
 *    - Có thể có custom getter.
 *
 * 2. const val (Compile-time Constant):
 *    - Giá trị được xác định ở COMPILE-TIME (ngay khi trình biên dịch build mã nguồn).
 *    - Bắt buộc phải thỏa mãn 3 điều kiện ngặt nghèo:
 *      + Phải nằm ở Top-level hoặc bên trong một 'object' / 'companion object'.
 *      + Chỉ đi kèm kiểu dữ liệu nguyên thủy (Int, Double, Boolean...) hoặc 'String'.
 *      + Không được gán thông qua hàm runtime (ví dụ không thể gọi hàm lấy thời gian).
 *      + Không được có custom getter.
 *    - Tác dụng: Compiler sẽ inlined trực tiếp giá trị vào nơi sử dụng trong Bytecode,
 *      giúp tăng tốc độ xử lý tối đa (không cần overhead gọi hàm get).
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
