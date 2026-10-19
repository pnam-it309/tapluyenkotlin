package topics

/**
 * PHẦN 23: PHÂN BIỆT 5 SCOPE FUNCTIONS (LET, RUN, WITH, APPLY, ALSO)
 *
 * Lý thuyết tổng hợp:
 * ┌──────────┬─────────────────┬──────────────────┬────────────────────────────────────────────────────────┐
 * │ Function │ Context Object  │ Return Value     │ Trường hợp sử dụng tiêu biểu                           │
 * ├──────────┼─────────────────┼──────────────────┼────────────────────────────────────────────────────────┤
 * │ let      │ it (tham số)    │ Kết quả Lambda   │ Kiểm tra Null (obj?.let) và biến đổi (transform) kiểu  │
 * │ run      │ this (receiver) │ Kết quả Lambda   │ Cấu hình đối tượng và tính toán trả về kết quả         │
 * │ with     │ this (receiver) │ Kết quả Lambda   │ Gọi nhiều hàm trên đối tượng non-null không lặp tên    │
 * │ apply    │ this (receiver) │ Chính Context Obj│ Cấu hình thuộc tính cho đối tượng (Builder pattern)   │
 * │ also     │ it (tham số)    │ Chính Context Obj│ Tác vụ phụ trợ không đổi chuỗi gọi hàm (Log, Validate)│
 * └──────────┴─────────────────┴──────────────────┴────────────────────────────────────────────────────────┘
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
class ServerBuilder {
    var host: String = "localhost"
    var port: Int = 80
    var enableSsl: Boolean = false

    fun buildInfo(): String = "Server[$host:$port, SSL=$enableSsl]"
}

fun exampleScopeFunctions() {
    println("--- 23.1 Minh họa 5 Scope Functions ---")

    // 1. apply: Cấu hình thuộc tính (this) và trả về chính serverBuilder
    val server = ServerBuilder().apply {
        host = "api.production.com"
        port = 443
        enableSsl = true
    }
    println("Sau khi dùng apply: ${server.buildInfo()}")

    // 2. also: Thực hiện tác vụ phụ trợ (it) như log, rồi trả về chính đối tượng đó
    val verifiedServer = server.also {
        println("  -> [also - Logging] Kiểm tra port: ${it.port}")
    }

    // 3. let: Thường dùng với ?.let để kiểm tra null và trả về kết quả lambda
    val nullableText: String? = "Hello Kotlin"
    val length = nullableText?.let {
        println("  -> [let] Đang xử lý chuỗi: $it")
        it.length // Trả về Int
    }
    println("Kết quả từ let: $length")

    // 4. run: Tham chiếu bằng this, trả về kết quả lambda
    val serverStatus = server.run {
        "Status: $host đang chạy trên port $port"
    }
    println("Kết quả từ run: $serverStatus")

    // 5. with: Gọi nhiều phương thức mà không phải lặp lại tên biến
    val summary = with(server) {
        "with summary: host=$host, port=$port, ssl=$enableSsl"
    }
    println(summary)
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

data class Order(
    var id: String = "",
    var customerName: String = "",
    var totalAmount: Double = 0.0,
    var isPaid: Boolean = false
)

/**
 * BÀI TẬP 23.1:
 * Viết hàm [createAndConfigureOrder] sử dụng [apply]:
 * - Khởi tạo `Order()`
 * - Dùng `apply` cấu hình:
 *   + `id = "ORD-2026"`
 *   + `customerName = "Nguyễn Văn An"`
 *   + `totalAmount = 250.0`
 *   + `isPaid = true`
 * - Trả về đối tượng `Order` đã cấu hình.
 */
fun createAndConfigureOrder(): Order {
    return Order().apply {
        id = "ORD-2026"
        customerName = "Nguyễn Văn An"
        totalAmount = 250.0
        isPaid = true
    }
}

/**
 * BÀI TẬP 23.2:
 * Viết hàm [processOrderSafely] nhận vào `order: Order?`.
 * Yêu cầu kết hợp các Scope Functions:
 * - Nếu `order` khác null:
 *   + Dùng `order?.also { ... }` để in log kiểm tra mã đơn hàng.
 *   + Dùng `let` hoặc `run` để trả về chuỗi thông báo:
 *     "Đơn hàng ${it.id} của ${it.customerName}: ${if (it.isPaid) "ĐÃ THANH TOÁN" else "CHƯA THANH TOÁN"}"
 * - Nếu `order` là null, trả về "KHÔNG CÓ ĐƠN HÀNG".
 */
fun processOrderSafely(order: Order?): String {
    return order?.also {
        // Logging tác vụ phụ
        println("  [Audit log] Đang xử lý đơn: ${it.id}")
    }?.let {
        "Đơn hàng ${it.id} của ${it.customerName}: ${if (it.isPaid) "ĐÃ THANH TOÁN" else "CHƯA THANH TOÁN"}"
    } ?: "KHÔNG CÓ ĐƠN HÀNG"
}

fun runPart23() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 23: SCOPE FUNCTIONS")
    println("==================================================")
    exampleScopeFunctions()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val configuredOrder = createAndConfigureOrder()
    check(configuredOrder.id == "ORD-2026") { "Sai id" }
    check(configuredOrder.customerName == "Nguyễn Văn An") { "Sai customerName" }
    check(configuredOrder.totalAmount == 250.0) { "Sai totalAmount" }
    check(configuredOrder.isPaid) { "Sai isPaid" }
    println("✅ Bài tập 23.1 (apply Configuration): Đạt chuẩn!")

    val msgSuccess = processOrderSafely(configuredOrder)
    val msgNull = processOrderSafely(null)
    check(msgSuccess == "Đơn hàng ORD-2026 của Nguyễn Văn An: ĐÃ THANH TOÁN") { "Sai msgSuccess: $msgSuccess" }
    check(msgNull == "KHÔNG CÓ ĐƠN HÀNG") { "Sai msgNull" }
    println("✅ Bài tập 23.2 (also + let + elvis pipeline): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 23!\n")
}

fun main() {
    runPart23()
}
