  package topics

/**
 * PHẦN 15: ƯU ĐIỂM CỦA DATA CLASS TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Tạo các class chuyên biệt dùng để mô hình hóa và lưu trữ dữ liệu (DTO / Value Object / POKO).
 * - Trình biên dịch tự động sinh ra 5 nhóm phương thức cốt lõi dựa trên các thuộc tính trong Primary Constructor:
 *   1. `equals()`: So sánh theo nội dung giá trị (Structural Equality).
 *   2. `hashCode()`: Đảm bảo hai object có cùng thuộc tính sẽ có cùng mã băm (dùng trong HashMap/HashSet).
 *   3. `toString()`: Hiển thị chuỗi rõ ràng, tường minh ("User(id=1, name=Alice)").
 *   4. `copy()`: Tạo bản sao mới với tùy chọn chỉnh sửa một số thuộc tính mong muốn.
 *   5. `component1(), component2(), ... componentN()`: Hỗ trợ cú pháp phân rã Destructuring Declaration (`val (id, name) = user`).
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Trong Java, một class POJO đòi hỏi hơn 100 dòng mã boilerplate (getter, setter, equals, hashCode, toString)
 *   và rất dễ phát sinh lỗi khi người bảo trì thêm trường mới nhưng quên cập nhật lại `equals`/`hashCode`.
 * - Kotlin giải quyết toàn bộ sự phức tạp này chỉ với 1 dòng code duy nhất: `data class User(val id: Int, val name: String)`.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Data Class:
 *   + *Tối ưu nhất cho*: Các đối tượng mô tả dữ liệu thuần túy (DTO, API Response/Request, UI State trong ViewModel, Database Entities).
 * - Regular Class:
 *   + *Tối ưu cho*: Các lớp chứa logic nghiệp vụ, quản lý luồng (Service, Repository, ViewModel, Controller),
 *     hoặc khi cần kế thừa đa tầng (vì Data Class không thể đánh dấu là `open` hay `abstract`).
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

    println("\n--- KIỂM T   RA BÀI TẬP ---")
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
