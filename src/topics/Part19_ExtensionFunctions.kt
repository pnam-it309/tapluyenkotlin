package topics

/**
 * PHẦN 19: EXTENSION FUNCTION (HÀM MỞ RỘNG) TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Cho phép thêm các phương thức/thuộc tính mới vào một Class đã tồn tại (kể cả class từ JDK, Android SDK
 *   hoặc thư viện bên thứ ba đã đóng gói jar) mà không cần kế thừa (Inheritance) hay sửa đổi mã nguồn gốc.
 * - Cú pháp: `fun ReceiverType.functionName(params): ReturnType` (từ khóa `this` trỏ tới đối tượng gọi hàm).
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Thay thế các lớp tiện ích "xấu xí" trong Java như `StringUtils.capitalize(str)` hay `DateUtils.format(date)`.
 * - Đem lại cú pháp gọi hàm tự nhiên như method thành viên: `str.capitalize()`, `date.format()`,
 *   kết hợp mượt mà với gợi ý code (IntelliSense/AutoComplete) của IDE.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Bản chất Bytecode: Extension function chỉ là một Java **Static Method** nhận đối tượng gọi làm tham số đầu tiên
 *   (`public static void myFunc(Receiver $this)`).
 * - Phân giải tĩnh (Static Dispatch): Được phân giải dựa trên kiểu khai báo tại Compile-time, KHÔNG hỗ trợ Đa hình (Polymorphism).
 * - Không phá vỡ tính bao đóng: Extension function KHÔNG THỂ truy cập các thành viên `private` hay `protected` của Class gốc.
 * - Trường hợp tối ưu nhất: Viết tiện ích cho View/UI Android, format chuỗi, bóc tách Collections, chuyển đổi kiểu dữ liệu.
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
