package topics

/**
 * PHẦN 1: KOTLIN VÀ JAVA - MỐI QUAN HỆ & ƯU ĐIỂM VƯỢT TRỘI
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Tương thích 100% (100% Interoperability) với hệ sinh thái Java: Cả Kotlin và Java đều
 *   biên dịch thành JVM Bytecode và có thể gọi qua lại lẫn nhau trong cùng một dự án mà không cần chuyển đổi.
 * - Hiện đại hóa lập trình hướng đối tượng kết hợp lập trình hàm (Functional Programming),
 *   cung cấp Null Safety, Smart Cast, Type Inference, Extension Functions và cú pháp súc tích.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Triệt tiêu lỗi "tỉ đô" NullPointerException (NPE) ngay tại Compile-time nhờ hệ thống kiểu non-nullable.
 * - Loại bỏ mã rập khuôn thừa thãi (Boilerplate code): Không còn phải tự viết Getter/Setter,
 *   constructor rườm rà, `findViewById` hay class POJO dài hàng trăm dòng.
 * - Khắc phục tính rườm rà khi ép kiểu: Java phải dùng `instanceof` rồi cast tường minh `((String) obj)`,
 *   còn Kotlin tự động ép kiểu thông minh (Smart Cast).
 * - Kế thừa toàn bộ thư viện Java khổng lồ có sẵn (Spring, Android SDK, Apache Commons, Jackson...).
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Kotlin tối ưu hơn khi:
 *   + Phát triển ứng dụng mới (Android, Spring Boot, KMP đa nền tảng, Server-side).
 *   + Cần tốc độ phát triển nhanh, bảo trì code ít lỗi, giảm tới 30-40% số dòng code.
 *   + Xử lý luồng dữ liệu bất đồng bộ với Coroutines & Flow (vượt trội so với RxJava/Thread trong Java).
 * - Java tối ưu hơn khi:
 *   + Bảo trì dự án legacy lớn đã chạy ổn định hàng chục năm.
 *   + Xây dựng các thư viện cấp thấp (Low-level JVM library) cần giữ tương thích tuyệt đối
 *     với các phiên bản JDK rất cũ (Java 6, 7, 8) mà không muốn thêm dependency `kotlin-stdlib`.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
fun exampleKotlinJavaInteroperability() {
    println("--- 1.1 Khả năng tương thích 100% với thư viện Java ---")
    // Sử dụng trực tiếp các class từ JDK Java trong Kotlin một cách tự nhiên
    val javaCalendar = java.util.Calendar.getInstance()
    val year = javaCalendar.get(java.util.Calendar.YEAR)
    println("Năm hiện tại từ java.util.Calendar: $year")

    val javaArrayList = java.util.ArrayList<String>()
    javaArrayList.add("Java")
    javaArrayList.add("Kotlin")
    println("Java ArrayList trong Kotlin: $javaArrayList")
}

fun exampleSmartCast(data: Any) {
    println("--- 1.2 Smart Cast trong Kotlin ---")
    // Trong Java: phải dùng instanceof rồi ép kiểu tường minh: ((String) data).length()
    // Trong Kotlin: Sau khi kiểm tra 'is', compiler tự động smart-cast sang kiểu đó!
    if (data is String) {
        println("Độ dài chuỗi '$data' là: ${data.length} (Tự ép kiểu sang String)")
    } else if (data is Int) {
        println("Bình phương của số '$data' là: ${data * data} (Tự ép kiểu sang Int)")
    } else {
        println("Kiểu dữ liệu khác: ${data::class.simpleName}")
    }
}

/**
 * Minh họa so sánh cú pháp Java truyền thống vs Kotlin tối ưu
 */
fun exampleComparisonAndOptimalUsage() {
    println("--- 1.3 So sánh trực quan: Java Boilerplate vs Kotlin Concise ---")
    // Giả sử trong Java ta phải kiểm tra null và ép kiểu:
    // Object obj = "Hello World";
    // if (obj instanceof String) {
    //     String str = (String) obj;
    //     System.out.println(str.toUpperCase());
    // }

    // Trong Kotlin: Ngắn gọn, an toàn Compile-time và tự Smart Cast
    val obj: Any = "Hello World"
    if (obj is String) {
        println("Kotlin Smart Cast: ${obj.uppercase()}")
    }
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 1.1:
 * Viết hàm [processMixedData] nhận vào một tham số `item: Any`.
 * Yêu cầu:
 * - Nếu item là String: trả về "String: " kèm nội dung viết hoa (uppercase).
 * - Nếu item là Int: trả về "Int: " kèm giá trị nhân đôi (x * 2).
 * - Nếu item là List<*>: trả về "List size: " kèm số lượng phần tử của list.
 * - Ngược lại: trả về "Unknown".
 *
 * Gợi ý: Dùng biểu thức `when (item)` kết hợp Smart Cast (`is String`, `is Int`, `is List<*>`).
 */
fun processMixedData(item: Any): String {
    return when (item) {
        is String -> "String: ${item.uppercase()}"
        is Int -> "Int: ${item * 2}"
        is List<*> -> "List size: ${item.size}"
        else -> "Unknown"
    }
}

/**
 * BÀI TẬP 1.2:
 * Viết hàm [calculateJavaMathStats] nhận vào một mảng số nguyên `IntArray`.
 * Sử dụng thư viện `java.lang.Math` để tìm giá trị lớn nhất và nhỏ nhất,
 * trả về một `Pair<Int, Int>` dạng (min, max).
 * Nếu mảng rỗng, ném ngoại lệ IllegalArgumentException.
 */
fun calculateJavaMathStats(numbers: IntArray): Pair<Int, Int> {
    require(numbers.isNotEmpty()) { "Mảng không được rỗng" }
    var minVal = numbers[0]
    var maxVal = numbers[0]
    for (num in numbers) {
        minVal = java.lang.Math.min(minVal, num)
        maxVal = java.lang.Math.max(maxVal, num)
    }
    return Pair(minVal, maxVal)
}

fun runPart01() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 1: KOTLIN VS JAVA")
    println("==================================================")
    exampleKotlinJavaInteroperability()
    exampleSmartCast("Học Kotlin Core")
    exampleSmartCast(10)
    exampleComparisonAndOptimalUsage()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Test Bài tập 1.1
    val r1 = processMixedData("hello")
    val r2 = processMixedData(25)
    val r3 = processMixedData(listOf(1, 2, 3, 4))
    val r4 = processMixedData(true)
    check(r1 == "String: HELLO") { "Sai bài tập 1.1 - String" }
    check(r2 == "Int: 50") { "Sai bài tập 1.1 - Int" }
    check(r3 == "List size: 4") { "Sai bài tập 1.1 - List" }
    check(r4 == "Unknown") { "Sai bài tập 1.1 - Unknown" }
    println("✅ Bài tập 1.1: Đạt chuẩn!")

    // Test Bài tập 1.2
    val stats = calculateJavaMathStats(intArrayOf(5, 12, -3, 8, 20))
    check(stats.first == -3 && stats.second == 20) { "Sai bài tập 1.2" }
    println("✅ Bài tập 1.2: Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 1!\n")
}

fun main() {
    runPart01()
}
