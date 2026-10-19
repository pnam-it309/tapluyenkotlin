package topics

/**
 * PHẦN 15: ƯU ĐIỂM CỦA DATA CLASS TRONG KOTLIN
 *
 * Lý thuyết:
 * - Data class được sinh ra chuyên để tạo các lớp lưu trữ dữ liệu (POKO/DTO),
 *   giúp loại bỏ hoàn toàn hàng chục dòng code mẫu (boilerplate) như trong Java.
 * - Trình biên dịch tự động sinh ra các phương thức từ các tham số trong Primary Constructor:
 *   1. equals(): So sánh bình đẳng giá trị (Structural Equality).
 *   2. hashCode(): Đảm bảo hai object có cùng giá trị sẽ có cùng hash (dùng trong HashMap/HashSet).
 *   3. toString(): Trả về chuỗi định dạng rõ ràng: "User(name=Alice, age=25)".
 *   4. copy(): Tạo bản sao mới với tùy chọn sửa đổi các trường mong muốn.
 *   5. component1(), component2(), ... componentN(): Hỗ trợ Destructuring Declaration.
 *
 * Điều kiện của Data Class:
 * - Primary Constructor phải có ít nhất 1 tham số.
 * - Mọi tham số trong Primary Constructor bắt buộc phải là 'val' hoặc 'var'.
 * - Không thể đánh dấu là abstract, open, sealed, hoặc inner.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
data class Book(
    val isbn: String,
    val title: String,
    val price: Double
)

fun exampleDataClassFeatures() {
    println("--- 15.1 Các tính năng tự động của Data Class ---")
    val book1 = Book("978-0134685991", "Effective Java", 45.0)
    val book2 = Book("978-0134685991", "Effective Java", 45.0)

    // 1. Tự động sinh toString()
    println("toString: $book1")

    // 2. Tự động sinh equals() và hashCode()
    println("book1 == book2: ${book1 == book2}") // true vì các thuộc tính bằng nhau
    println("book1.hashCode == book2.hashCode: ${book1.hashCode() == book2.hashCode()}") // true

    // 3. Tự động sinh componentN() -> Destructuring Declaration
    val (isbn, title, price) = book1
    println("Destructuring: ISBN=$isbn, Title='$title', Price=\$$price")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 15.1:
 * Thiết kế data class [Student(val id: String, val name: String, val grade: Double)].
 *
 * Viết hàm [extractTopStudentInfo(students: List<Student>): String]:
 * - Lọc ra học sinh có `grade` cao nhất trong danh sách.
 * - Dùng tính năng Destructuring Declaration của Data Class: `val (id, name, grade) = topStudent`
 * - Trả về chuỗi: "Thủ khoa: $name (Mã: $id) với điểm số $grade".
 * - Nếu danh sách rỗng, trả về "Không có sinh viên".
 */
data class Student(
    val id: String,
    val name: String,
    val grade: Double
)

fun extractTopStudentInfo(students: List<Student>): String {
    if (students.isEmpty()) return "Không có sinh viên"
    val topStudent = students.maxByOrNull { it.grade } ?: return "Không có sinh viên"
    // Destructuring declaration nhờ tự sinh component1(), component2(), component3()
    val (id, name, grade) = topStudent
    return "Thủ khoa: $name (Mã: $id) với điểm số $grade"
}

fun runPart15() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 15: DATA CLASS")
    println("==================================================")
    exampleDataClassFeatures()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val list = listOf(
        Student("SV01", "Nguyễn Văn An", 8.2),
        Student("SV02", "Trần Mai Lan", 9.5),
        Student("SV03", "Lê Quốc Bảo", 7.8)
    )
    val info = extractTopStudentInfo(list)
    check(info == "Thủ khoa: Trần Mai Lan (Mã: SV02) với điểm số 9.5") { "Sai extractTopStudentInfo: $info" }
    check(extractTopStudentInfo(emptyList()) == "Không có sinh viên") { "Sai khi danh sách rỗng" }

    println("✅ Bài tập 15.1 (Student Data Class & Destructuring): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 15!\n")
}

fun main() {
    runPart15()
}
