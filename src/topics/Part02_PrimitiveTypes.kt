package topics

/**
 * PHẦN 2: KIỂU DỮ LIỆU NGUYÊN THỦY (PRIMITIVE TYPES) TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Thống nhất hệ thống kiểu dữ liệu: Mọi kiểu dữ liệu nhìn bề ngoài đều là Object (như `Int`, `Double`, `Boolean`...),
 *   cho phép lập trình viên gọi hàm trực tiếp (ví dụ: `10.toString()`, `5.toLong()`).
 * - Trình biên dịch tự động tối ưu: Biên dịch thành kiểu nguyên thủy (primitive `int`, `double`...)
 *   của Java trong JVM Bytecode mà không cần phải khai báo từ khóa primitive riêng biệt.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Trong Java, việc phân chia rạch ròi giữa primitive (`int`) và wrapper object (`Integer`)
 *   dễ dẫn đến lỗi Autoboxing/Unboxing ngầm, làm suy giảm hiệu năng bộ nhớ nghiêm trọng và nguy cơ NullPointerException khi unbox một giá trị null.
 * - Kotlin đơn giản hóa: Bạn chỉ cần khai báo `Int`, trình biên dịch sẽ tự quyết định xem
 *   khi nào nên dùng kiểu primitive thô (siêu nhanh) và khi nào bắt buộc phải dùng wrapper object (đóng gói).
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - `Int` (Non-null):
 *   + Biên dịch thành primitive `int` lưu trực tiếp trên Stack, 0 overhead bộ nhớ, tốc độ tính toán CPU tối đa.
 *   + *Tối ưu*: Dùng mặc định cho mọi vòng lặp, tính toán số học, biến đếm, thuộc tính nội bộ.
 * - `Int?` (Nullable):
 *   + Bắt buộc đóng gói thành `java.lang.Integer` lưu trên Heap (overhead ~16-24 bytes/object) và chi phí Garbage Collector.
 *   + *Tối ưu*: Chỉ dùng khi dữ liệu thực sự có thể vắng mặt (nhận từ REST API, cột NULL trong Database).
 * - `IntArray` vs `Array<Int>`:
 *   + `IntArray` biên dịch thành primitive `int[]` thuần túy (siêu nhẹ, không boxing).
 *   + `Array<Int>` biên dịch thành `Integer[]` (mảng chứa các con trỏ trỏ tới từng object Integer).
 *   + *Tối ưu*: Luôn dùng `IntArray`, `DoubleArray`, `ByteArray` khi xử lý tập dữ liệu số lớn để tránh tràn bộ nhớ.
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

fun examplePrimitiveArrayOptimization() {
    println("--- 2.2 So sánh tối ưu IntArray (Primitive) vs Array<Int> (Boxed) ---")
    // IntArray: byte code là int[] -> Không bị boxing từng phần tử
    val primitiveArr = intArrayOf(1, 2, 3, 4, 5)
    // Array<Int>: byte code là Integer[] -> Mỗi phần tử bị boxing thành object java.lang.Integer
    val boxedArr = arrayOf(1, 2, 3, 4, 5)

    println("primitiveArr (int[]): kích thước tối thiểu trên RAM")
    println("boxedArr (Integer[]): tốn thêm overhead con trỏ đối tượng")
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
    examplePrimitiveArrayOptimization()

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
