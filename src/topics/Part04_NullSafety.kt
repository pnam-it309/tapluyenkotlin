package topics

/**
 * PHẦN 4: THỂ HIỆN CỦA NULL SAFETY TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Tách biệt rõ ràng hệ thống kiểu Non-null (`T`) và Nullable (`T?`) ở cấp độ biên dịch.
 * - Cung cấp bộ công cụ toán tử hoàn chỉnh để làm việc với null: Safe Call `?.`,
 *   Elvis `?:`, Not-null Assertion `!!`, Smart Cast và Scoping `let`.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Loại bỏ hoàn toàn lỗi crash kinh điển NullPointerException (NPE) làm sập app lúc runtime.
 * - Giải quyết vấn đề "Kim tự tháp if-null" (Pyramid of Doom) trong Java khi phải lồng
 *   nhiều tầng `if (user != null && user.getAddress() != null...)`.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Safe Call `?.`: Tối ưu khi đọc thuộc tính lồng nhau. Nếu có null ở bất kỳ mắt xích nào,
 *   toàn bộ biểu thức trả về `null` an toàn.
 * - Elvis `?:`: Tối ưu khi cần giá trị mặc định (Fallback value) hoặc dùng làm Guard Clause
 *   để ngắt hàm sớm (`val token = session?.token ?: return false`).
 * - `?.let { }`: Tối ưu khi cần thực thi một khối lệnh chỉ khi biến khác null.
 * - Not-null assertion `!!`: Cực kỳ rủi ro (Code Smell), chỉ nên dùng trong Unit Test (Fail-fast)
 *   hoặc khi 100% đảm bảo contract sau khi gọi thư viện Java.
 * - Smart Cast: Tự động biến `val x: String?` thành `String` sau khi qua khối kiểm tra `if (x != null)`.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
data class City(val name: String)
data class Address(val city: City?)
data class UserProfile(val id: Int, val name: String, val address: Address?)

fun exampleNullSafetyOperators() {
    println("--- 4.1 Minh họa các toán tử Null Safety ---")
    val nullableName: String? = "Kotlin Developer"
    val emptyName: String? = null
    // Safe Call (?.)
    println("nullableName length: ${nullableName?.length}") // In ra: 16
    println("emptyName length: ${emptyName?.length}")       // In ra: null (không hề crash NPE!)

    // Elvis Operator (?:)
    val nameToDisplay = emptyName ?: "Guest"
    println("Tên hiển thị: $nameToDisplay") // In ra: Guest

    // let block
    nullableName?.let {
        println("Thực hiện let block với giá trị không null: $it")
    }

    // Chuỗi Safe Call lồng nhau
    val user1 = UserProfile(1, "Alice", Address(City("Hà Nội")))
    val user2 = UserProfile(2, "Bob", null)

    val city1 = user1.address?.city?.name ?: "Chưa cập nhật thành phố"
    val city2 = user2.address?.city?.name ?: "Chưa cập nhật thành phố"
    println("Thành phố của user1: $city1")
    println("Thành phố của user2: $city2")
}

fun exampleOptimalGuardClause(input: String?): String {
    println("--- 4.2 Minh họa Guard Clause tối ưu bằng Elvis Operator ---")
    // Thay vì if-else lồng nhau phức tạp:
    val validText = input?.takeIf { it.isNotBlank() } ?: return "GIÁ TRỊ MẶC ĐỊNH (GUARD CLAUSE)"
    return "ĐÃ XỬ LÝ: ${validText.trim().uppercase()}"
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 4.1:
 * Viết hàm [formatUserCity] nhận vào `user: UserProfile?`.
 * Yêu cầu:
 * - Lấy tên thành phố (city name) từ user.
 * - Nếu thành phố tồn tại, chuyển thành chữ viết hoa (uppercase) và trả về.
 * - Nếu user bị null, hoặc address bị null, hoặc city bị null: trả về "UNKNOWN CITY".
 * - Tuyệt đối không dùng toán tử !!, chỉ dùng Safe Call (?.) và Elvis (?:).
 */
fun formatUserCity(user: UserProfile?): String {
    return user?.address?.city?.name?.uppercase() ?: "UNKNOWN CITY"
}

/**
 * BÀI TẬP 4.2:
 * Viết hàm [safeParsePositiveInt] nhận vào một chuỗi `rawInput: String?`.
 * Yêu cầu:
 * - Nếu `rawInput` là null hoặc rỗng (hoặc chỉ chứa khoảng trắng): trả về 0.
 * - Dùng `rawInput.trim().toIntOrNull()` để parse chuỗi sang số nguyên an toàn.
 * - Nếu số parse được lớn hơn 0: trả về số đó.
 * - Nếu parse thất bại (ra null) hoặc số <= 0: trả về 0.
 */
fun safeParsePositiveInt(rawInput: String?): Int {
    if (rawInput.isNullOrBlank()) return 0
    val parsed = rawInput.trim().toIntOrNull() ?: 0
    return if (parsed > 0) parsed else 0
}

fun runPart04() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 4: NULL SAFETY")
    println("==================================================")
    exampleNullSafetyOperators()
    println(exampleOptimalGuardClause("  kotlin pro  "))
    println(exampleOptimalGuardClause(null))

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Test Bài tập 4.1
    val u1 = UserProfile(10, "Nam", Address(City("Đà Nẵng")))
    val u2 = UserProfile(11, "Lan", Address(null))
    val u3 = UserProfile(12, "Tuan", null)
    val u4: UserProfile? = null

    check(formatUserCity(u1) == "ĐÀ NẴNG") { "Sai formatUserCity với u1" }
    check(formatUserCity(u2) == "UNKNOWN CITY") { "Sai formatUserCity với u2" }
    check(formatUserCity(u3) == "UNKNOWN CITY") { "Sai formatUserCity với u3" }
    check(formatUserCity(u4) == "UNKNOWN CITY") { "Sai formatUserCity với u4" }
    println("✅ Bài tập 4.1 (Safe call chain & Elvis): Đạt chuẩn!")

    // Test Bài tập 4.2
    check(safeParsePositiveInt("123") == 123) { "Sai với '123'" }
    check(safeParsePositiveInt("  50  ") == 50) { "Sai với '  50  '" }
    check(safeParsePositiveInt("-10") == 0) { "Sai với '-10'" }
    check(safeParsePositiveInt("abc") == 0) { "Sai với 'abc'" }
    check(safeParsePositiveInt(null) == 0) { "Sai với null" }
    check(safeParsePositiveInt("   ") == 0) { "Sai với chuỗi trắng" }
    println("✅ Bài tập 4.2 (toIntOrNull & Safe parsing): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 4!\n")
}

fun main() {
    runPart04()
}
