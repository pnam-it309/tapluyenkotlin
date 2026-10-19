package topics

/**
 * PHẦN 2: KIỂU DỮ LIỆU NGUYÊN THỦY (PRIMITIVE TYPES) TRONG KOTLIN
 *
 * Lý thuyết:
 * - Ở cấp độ ngôn ngữ Kotlin, mọi thứ đều là đối tượng (Int, Double, Boolean...).
 *   Người dùng không khai báo trực tiếp kiểu nguyên thủy như 'int' hay 'double' của Java.
 * - Khi biên dịch sang JVM Bytecode:
 *   + Nếu biến KHÔNG NULL (Int, Double...): Trình biên dịch tự động tối ưu hóa thành
 *     kiểu nguyên thủy của Java (primitive: int, double...) để tiết kiệm bộ nhớ và tăng tốc độ.
 *   + Nếu biến NULLABLE (Int?, Double?...): Bắt buộc phải biên dịch thành dạng
 *     Wrapper Object (java.lang.Integer, java.lang.Double) để có thể chứa giá trị null.
 *   + Khi dùng trong Generic (ví dụ List<Int>): JVM chỉ hỗ trợ Object trong Generic,
 *     nên Kotlin cũng tự động dùng Wrapper Object (Integer).
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
fun examplePrimitiveOptimization() {
    println("--- 2.1 Tối ưu hóa Primitive vs Nullable Wrapper ---")
    val nonNullInt: Int = 42      // Sẽ biên dịch thành 'int' nguyên thủy trên JVM
    val nullableInt: Int? = 42    // Sẽ biên dịch thành 'java.lang.Integer' trên JVM
    val nullValue: Int? = null    // Được biểu diễn bằng null trong java.lang.Integer

    println("nonNullInt: $nonNullInt (bytecode là primitive int)")
    println("nullableInt: $nullableInt (bytecode là java.lang.Integer object)")
    println("nullValue: $nullValue")

    // So sánh == (Structural equality - so sánh giá trị)
    // So sánh === (Referential equality - so sánh địa chỉ ô nhớ)
    val a: Int = 1000
    val b: Int? = a
    val c: Int? = a
    println("b == c: ${b == c} (bằng nhau về giá trị)")
    println("b === c: ${b === c} (có thể false vì nằm ở 2 Wrapper Object khác nhau trên heap)")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 2.1:
 * Viết hàm [sumNonNullNumbers] nhận vào một danh sách các số nguyên có thể null (`List<Int?>`).
 * Yêu cầu:
 * - Bỏ qua các phần tử bị null.
 * - Trả về tổng của các phần tử khác null dưới dạng kiểu nguyên thủy `Int` (không null).
 * - Nếu không có phần tử nào hoặc toàn null, trả về 0.
 *
 * Yêu cầu tư duy: Nhận biết khi nào từ kiểu Wrapper (Int?) chuyển đổi (unbox) về kiểu nguyên thủy (Int).
 */
fun sumNonNullNumbers(numbers: List<Int?>): Int {
    var total: Int = 0 // Biến nguyên thủy tích lũy
    for (item in numbers) {
        if (item != null) {
            // item tự động được smart-cast từ Int? sang Int (primitive int khi cộng)
            total += item
        }
    }
    return total
}

/**
 * BÀI TẬP 2.2:
 * Viết hàm [calculateAverageScore] nhận vào danh sách điểm số `List<Double?>`.
 * Yêu cầu:
 * - Lọc ra các điểm hợp lệ (khác null và từ 0.0 đến 10.0).
 * - Tính trung bình cộng của các điểm hợp lệ đó.
 * - Trả về kết quả kiểu `Double`. Nếu không có điểm hợp lệ nào, trả về 0.0.
 */
fun calculateAverageScore(scores: List<Double?>): Double {
    val validScores = scores.filterNotNull().filter { it in 0.0..10.0 }
    if (validScores.isEmpty()) return 0.0
    return validScores.sum() / validScores.size
}

fun runPart02() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 2: KIỂU DỮ LIỆU NGUYÊN THỦY")
    println("==================================================")
    examplePrimitiveOptimization()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Test Bài tập 2.1
    val listWithNulls = listOf(10, null, 25, null, 5)
    val sum = sumNonNullNumbers(listWithNulls)
    check(sum == 40) { "Sai bài tập 2.1: kết quả mong muốn 40, nhận được $sum" }
    check(sumNonNullNumbers(listOf(null, null)) == 0) { "Sai bài tập 2.1 khi toàn null" }
    println("✅ Bài tập 2.1 (Unboxing & Sum): Đạt chuẩn!")

    // Test Bài tập 2.2
    val testScores = listOf(8.5, null, 9.0, 15.0, -1.0, 7.5) // 15.0 và -1.0 là không hợp lệ
    val avg = calculateAverageScore(testScores)
    // Hợp lệ: 8.5, 9.0, 7.5 -> tổng = 25.0, số lượng = 3 -> avg = 8.333...
    check(kotlin.math.abs(avg - (25.0 / 3.0)) < 0.0001) { "Sai bài tập 2.2" }
    println("✅ Bài tập 2.2 (Lọc & Tính điểm trung bình): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 2!\n")
}

fun main() {
    runPart02()
}
