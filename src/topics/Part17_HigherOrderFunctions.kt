package topics

/**
 * PHẦN 17: HIGHER-ORDER FUNCTION (HÀM BẬC CAO) TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Higher-Order Function (HOF) là hàm coi hàm khác như dữ liệu hạng nhất (First-Class Citizen):
 *   Có thể nhận một hoặc nhiều hàm làm tham số, hoặc trả về một hàm khác.
 * - Cú pháp Function Type: `(A, B) -> R` (nhận tham số A, B và trả về kiểu R).
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Tái sử dụng tối đa logic mẫu (Template Logic / Framework): Cho phép viết sẵn cấu trúc bao bọc
 *   (đo thời gian thực thi, log request, xử lý transaction, retry lỗi) và chỉ cần truyền hành vi lõi vào.
 * - Loại bỏ các Anonymous SAM Interface cồng kềnh thường thấy trong Java cổ điển.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - HOF vs Interface Callback của Java: HOF ngắn hơn, linh hoạt hơn, không cần tạo class mới.
 * - Tối ưu hiệu năng với `inline`:
 *   + Khi gọi HOF thông thường, JVM sẽ khởi tạo một object kiểu `FunctionN` trên Heap.
 *   + Nếu HOF được gọi lặp đi lặp lại nhiều lần (như duyệt collection, vòng lặp), hãy thêm từ khóa `inline`:
 *     Compiler sẽ chèn trực tiếp mã bytecode của lambda vào nơi gọi, triệt tiêu hoàn toàn chi phí cấp phát bộ nhớ.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================

// Ví dụ 1: HOF nhận hàm làm tham số để tùy biến phép tính toán
fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}
fun name(){
    val a = 2
    val b = 3
    val c = a + b
    println(c)
    calculate(2,4,{ a, b -> a+b})
}
// Ví dụ 2: HOF bọc logic đo thời gian thực thi (Benchmarking)
fun <T> measureExecutionTime(blockName: String, action: () -> T): T {
    val start = System.nanoTime()
    val result = action()
    val durationMs = (System.nanoTime() - start) / 1_000_000.0
    println("Khối '$blockName' hoàn thành trong: %.4f ms".format(durationMs))
    return result
}

// Ví dụ 3: HOF trả về một hàm khác (Function Factory)
fun createMultiplier(factor: Int): (Int) -> Int {
    return { number -> number * factor }
}

fun exampleHigherOrderFunctions() {
    println("--- 17.1 HOF nhận hàm làm tham số ---")
    val addResult = calculate(10, 5) { x, y -> x + y }
    val multiplyResult = calculate(10, 5) { x, y -> x * y }
    println("10 + 5 = $addResult")
    println("10 * 5 = $multiplyResult")

    println("\n--- 17.2 HOF trả về một hàm (Closure / Factory) ---")
    val triple = createMultiplier(3)
    println("Nhân 3 của 7: ${triple(7)}") // 21

    println("\n--- 17.3 HOF đo thời gian chạy ---")
    val total = measureExecutionTime("Tính tổng 1 đến 1.000.000") {
        (1L..1_000_000L).sum()
    }
    println("Tổng = $total")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 17.1: Tự viết hàm [customFilter] tương tự List.filter
 * Yêu cầu:
 * - Viết hàm mở rộng hoặc hàm thông thường [customFilter] nhận vào `list: List<T>`
 *   và một điều kiện lọc `predicate: (T) -> Boolean`.
 * - Duyệt qua từng phần tử, nếu `predicate(item) == true` thì thêm vào danh sách kết quả.
 * - Trả về `List<T>`.
 */
fun <T> customFilter(list: List<T>, predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in list) {
        if (predicate(item)) {
            result.add(item)
        }
    }
    return result
}

/**
 * BÀI TẬP 17.2: Viết HOF [retryOperation]
 * Yêu cầu:
 * - Nhận vào `maxAttempts: Int` (số lần thử tối đa) và khối `action: () -> T`.
 * - Lặp lại thử gọi `action()`. Nếu ném Exception, tiếp tục thử lại đến khi hết số lần.
 * - Nếu vượt quá maxAttempts mà vẫn lỗi, ném ngoại lệ lần cuối cùng.
 */
fun <T> retryOperation(maxAttempts: Int, action: () -> T): T {
    require(maxAttempts > 0) { "Số lần thử phải > 0" }
    var lastException: Throwable? = null
    for (attempt in 1..maxAttempts) {
        try {
            return action()
        } catch (t: Throwable) {
            lastException = t
        }
    }
    throw lastException ?: IllegalStateException("Lỗi không xác định")
}

fun runPart17() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 17: HIGHER-ORDER FUNCTION")
    println("==================================================")
    exampleHigherOrderFunctions()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Test customFilter
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val evens = customFilter(numbers) { it % 2 == 0 }
    check(evens == listOf(2, 4, 6, 8, 10)) { "Sai customFilter" }
    println("✅ Bài tập 17.1 (customFilter HOF): Đạt chuẩn!")

    // Test retryOperation thành công ở lần 3
    var attempts = 0
    val res = retryOperation(maxAttempts = 3) {
        attempts++
        if (attempts < 3) throw RuntimeException("Lỗi mạng giả định")
        "Thành công"
    }
    check(res == "Thành công" && attempts == 3) { "Sai retryOperation thành công" }

    // Test retryOperation thất bại khi quá lần
    var failCount = 0
    var threwExpected = false
    try {
        retryOperation(maxAttempts = 2) {
            failCount++
            throw RuntimeException("Luôn luôn lỗi")
        }
    } catch (e: RuntimeException) {
        threwExpected = true
    }
    check(threwExpected && failCount == 2) { "Sai retryOperation khi hết lượt" }
    println("✅ Bài tập 17.2 (retryOperation HOF): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 17!\n")
}

fun main() {
    runPart17()
}
