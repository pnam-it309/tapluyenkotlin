package topics

/**
 * PHẦN 19: EXTENSION FUNCTION (HÀM MỞ RỘNG) TRONG KOTLIN
 *
 * Lý thuyết:
 * 1. Khái niệm:
 *    - Extension Function cho phép thêm các phương thức mới vào một Class đã tồn tại
 *      (kể cả các lớp có sẵn trong JDK như String, Int, List...) mà KHÔNG cần phải kế thừa
 *      (inherit) hay áp dụng các Design Pattern bọc phức tạp (như Decorator/Wrapper).
 * 2. Cú pháp:
 *    - fun ReceiverType.functionName(params): ReturnType { ... }
 *    - Bên trong thân hàm, từ khóa 'this' đại diện cho đối tượng đang gọi hàm (Receiver Object).
 * 3. Bản chất dưới Bytecode (JVM):
 *    - Trình biên dịch sinh ra một static method thông thường trong Java:
 *      public static boolean isEven(int $this$isEven) { ... }
 *    - Do đó, Extension Function HOÀN TOÀN KHÔNG thực sự sửa đổi Class gốc.
 *    - Nó KHÔNG THỂ truy cập các thành viên private hay protected của Class gốc!
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================

// Mở rộng kiểu Int có sẵn của Kotlin
fun Int.isEven(): Boolean = this % 2 == 0

// Mở rộng kiểu String để đảo ngược chữ và viết hoa
fun String.shoutReversed(): String = this.reversed().uppercase()

// Mở rộng kiểu List<Int> để tính trung bình cộng
fun List<Int>.customAverage(): Double {
    if (this.isEmpty()) return 0.0
    return this.sum().toDouble() / this.size
}

fun exampleExtensionFunctions() {
    println("--- 19.1 Gọi Extension Function như thể nó là hàm có sẵn của Class ---")
    val number = 10
    println("Số $number có chẵn không? ${number.isEven()}") // Gọi trực tiếp trên số Int!

    val text = "kotlin"
    println("Shout reversed của '$text': ${text.shoutReversed()}") // NILTOK

    val list = listOf(2, 4, 6, 8)
    println("Trung bình list: ${list.customAverage()}")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 19.1:
 * Viết một extension function [isValidEmail] cho kiểu [String].
 * Yêu cầu:
 * - Chuỗi phải chứa ký tự '@' và sau ký tự '@' phải có dấu chấm '.'.
 * - Ký tự '@' không được nằm ở đầu (index > 0).
 * - Ký tự '.' không được nằm ở cuối cùng (lastIndex > index của '@' + 1).
 * - Trả về `Boolean`.
 */
fun String.isValidEmail(): Boolean {
    val atIndex = this.indexOf('@')
    if (atIndex <= 0) return false
    val dotIndex = this.lastIndexOf('.')
    return dotIndex > atIndex + 1 && dotIndex < this.lastIndex
}

/**
 * BÀI TẬP 19.2:
 * Viết extension function [toTitleCaseWords] cho [String].
 * Chuyển các từ viết hoa chữ cái đầu:
 * Ví dụ: "lập trình kotlin core" -> "Lập Trình Kotlin Core"
 */
fun String.toTitleCaseWords(): String {
    if (this.isBlank()) return this
    return this.split(" ")
        .filter { it.isNotBlank() }
        .joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { it.uppercase() }
        }
}

fun runPart19() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 19: EXTENSION FUNCTION")
    println("==================================================")
    exampleExtensionFunctions()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Test isValidEmail
    check("user@example.com".isValidEmail()) { "Lỗi: user@example.com phải hợp lệ" }
    check(!"@example.com".isValidEmail()) { "Lỗi: @ ở đầu không hợp lệ" }
    check(!"user@com".isValidEmail()) { "Lỗi: thiếu dấu chấm sau @" }
    check(!"user@domain.".isValidEmail()) { "Lỗi: dấu chấm ở cuối" }
    println("✅ Bài tập 19.1 (String.isValidEmail): Đạt chuẩn!")

    // Test toTitleCaseWords
    val title = "lập trình KOTLIN căn bản".toTitleCaseWords()
    check(title == "Lập Trình Kotlin Căn Bản") { "Sai toTitleCaseWords: '$title'" }
    println("✅ Bài tập 19.2 (String.toTitleCaseWords): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 19!\n")
}

fun main() {
    runPart19()
}
