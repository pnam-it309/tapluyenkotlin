package topics

/**
 * PHẦN 13: SO SÁNH ENUM CLASS VÀ SEALED CLASS TRONG KOTLIN
 *
 * Lý thuyết:
 * ┌─────────────────────────────┬─────────────────────────────────┬─────────────────────────────────┐
 * │ Tiêu chí                    │ enum class                      │ sealed class                    │
 * ├─────────────────────────────┼─────────────────────────────────┼─────────────────────────────────┤
 * │ Bản chất                    │ Danh sách các HẰNG SỐ giá trị   │ Phân cấp các KIỂU DỮ LIỆU con   │
 * │ Cấu trúc đối tượng          │ Mọi hằng số chung 1 constructor │ Mỗi subclass có constructor và  │
 * │                             │ và danh sách thuộc tính giống hệt│ danh sách biến hoàn toàn khác   │
 * │ Lưu trữ State khác nhau     │ KHÔNG linh hoạt để mang state   │ CỰC KỲ linh hoạt để mang state  │
 * │                             │ động theo từng trường hợp       │ riêng biệt cho mỗi trạng thái   │
 * │ Kiểm tra 'when' exhaustive  │ Có kiểm tra đủ mọi enum case    │ Có kiểm tra đủ mọi subclass con │
 * │ Trường hợp sử dụng tiêu biểu│ Hướng (Đông, Tây...), Giới tính │ UI State (Loading, Success,     │
 * │                             │ Thứ trong tuần, HTTP Status Code│ Error), Result pattern, Event   │
 * └─────────────────────────────┴─────────────────────────────────┴─────────────────────────────────┘
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================

// Ví dụ 1: Enum class cho tập giá trị cố định cùng thuộc tính
enum class HttpStatus(val code: Int, val description: String) {
    OK(200, "Success"),
    BAD_REQUEST(400, "Client Error"),
    NOT_FOUND(404, "Resource Not Found"),
    INTERNAL_ERROR(500, "Server Crash");

    fun isSuccess(): Boolean = code in 200..299
}

// Ví dụ 2: Sealed class cho tập trạng thái nghiệp vụ (UI State Pattern)
sealed class NetworkResult<out T> {
    // Trạng thái Loading: không cần mang dữ liệu, có thể là singleton 'object'
    object Loading : NetworkResult<Nothing>()

    // Trạng thái Success: mang theo dữ liệu kiểu T
    data class Success<out T>(val data: T) : NetworkResult<T>()

    // Trạng thái Error: mang mã lỗi và thông báo lỗi riêng
    data class Error(val errorCode: Int, val message: String) : NetworkResult<Nothing>()
}

fun renderUi(result: NetworkResult<String>): String {
    // Điểm mạnh: 'when' trên sealed class là EXHAUSTIVE (không cần nhánh 'else')
    return when (result) {
        is NetworkResult.Loading -> "Đang tải dữ liệu, vui lòng đợi..."
        is NetworkResult.Success -> "Tải thành công: ${result.data}"
        is NetworkResult.Error -> "Lỗi [${result.errorCode}]: ${result.message}"
    }
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 13.1:
 * Thiết kế sealed class [OrderStatus] mô tả trạng thái đơn hàng:
 * - Subclass [Pending]: Trạng thái chờ xử lý (không cần tham số, dạng `object Pending : OrderStatus()`).
 * - Subclass [Shipping(val trackingCode: String, val estimatedDays: Int)]: Đang giao hàng.
 * - Subclass [Delivered(val deliveredAt: String)]: Đã giao thành công.
 * - Subclass [Cancelled(val reason: String)]: Bị hủy đơn.
 *
 * Viết hàm [getOrderStatusSummary(status: OrderStatus): String] sử dụng `when` exhaustive:
 * - Pending -> "Đơn hàng đang chờ xử lý."
 * - Shipping -> "Đang giao [Mã: $trackingCode] - Dự kiến $estimatedDays ngày."
 * - Delivered -> "Đã giao lúc $deliveredAt."
 * - Cancelled -> "Đã hủy do: $reason."
 */
sealed class OrderStatus {
    object Pending : OrderStatus()
    data class Shipping(val trackingCode: String, val estimatedDays: Int) : OrderStatus()
    data class Delivered(val deliveredAt: String) : OrderStatus()
    data class Cancelled(val reason: String) : OrderStatus()
}

fun getOrderStatusSummary(status: OrderStatus): String {
    return when (status) {
        is OrderStatus.Pending -> "Đơn hàng đang chờ xử lý."
        is OrderStatus.Shipping -> "Đang giao [Mã: ${status.trackingCode}] - Dự kiến ${status.estimatedDays} ngày."
        is OrderStatus.Delivered -> "Đã giao lúc ${status.deliveredAt}."
        is OrderStatus.Cancelled -> "Đã hủy do: ${status.reason}."
    }
}

fun runPart13() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 13: ENUM VS SEALED CLASS")
    println("==================================================")
    println("Enum HTTP OK: ${HttpStatus.OK.code} - ${HttpStatus.OK.description}")
    println(renderUi(NetworkResult.Loading))
    println(renderUi(NetworkResult.Success("Danh sách 50 sản phẩm")))
    println(renderUi(NetworkResult.Error(404, "Không tìm thấy user")))

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val s1 = getOrderStatusSummary(OrderStatus.Pending)
    val s2 = getOrderStatusSummary(OrderStatus.Shipping("VNPOST123", 3))
    val s3 = getOrderStatusSummary(OrderStatus.Delivered("14:30 18/10"))
    val s4 = getOrderStatusSummary(OrderStatus.Cancelled("Khách đổi ý"))

    check(s1 == "Đơn hàng đang chờ xử lý.") { "Sai Pending" }
    check(s2 == "Đang giao [Mã: VNPOST123] - Dự kiến 3 ngày.") { "Sai Shipping" }
    check(s3 == "Đã giao lúc 14:30 18/10.") { "Sai Delivered" }
    check(s4 == "Đã hủy do: Khách đổi ý.") { "Sai Cancelled" }

    println("✅ Bài tập 13.1 (OrderStatus Sealed Class Pattern): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 13!\n")
}

fun main() {
    runPart13()
}
