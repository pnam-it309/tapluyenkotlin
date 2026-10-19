package topics

/**
 * PHẦN 9: SO SÁNH LATEINIT VS BY LAZY TRONG KOTLIN
 *
 * Lý thuyết so sánh:
 * ┌──────────────────────┬───────────────────────────────────┬───────────────────────────────────┐
 * │ Tiêu chí             │ lateinit                          │ by lazy                           │
 * ├──────────────────────┼───────────────────────────────────┼───────────────────────────────────┤
 * │ Từ khóa đi kèm       │ Bắt buộc dùng với 'var'           │ Bắt buộc dùng với 'val'           │
 * │ Kiểu dữ liệu         │ Không hỗ trợ Primitive (Int...)   │ Áp dụng cho MỌI kiểu dữ liệu      │
 * │ Cơ chế khởi tạo      │ Khởi tạo thủ công (Manual init)   │ Tự động khởi tạo ở lần gọi đầu    │
 * │ Kiểm tra khởi tạo    │ Dùng ::prop.isInitialized         │ Tự động cache kết quả sau lần 1   │
 * │ Thread Safety        │ Không an toàn đa luồng            │ Mặc định Thread-safe (SYNCHRONIZED│
 * └──────────────────────┴───────────────────────────────────┴───────────────────────────────────┘
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
class DatabaseConnection(val url: String) {
    fun query(sql: String): String = "Kết quả cho '$sql' từ $url"
}

class ServiceContainer {
    // 1. lateinit var: Thường dùng trong Dependency Injection (Dagger/Hilt/Spring) hoặc Android onCreate()
    lateinit var db: DatabaseConnection

    // 2. by lazy: Khởi tạo chậm khi có ai đó lần đầu tiên đọc giá trị
    val heavyCalculationResult: Int by lazy {
        println("  -> [Lazy] Đang tính toán dữ liệu nặng nề lần đầu tiên...")
        // Giả lập phép tính tốn tài nguyên
        (1..100).sum()
    }

    fun setupDependencies() {
        db = DatabaseConnection("jdbc:mysql://localhost:3306/app_db")
    }

    fun isDatabaseReady(): Boolean {
        return ::db.isInitialized
    }
}

fun exampleLateinitVsLazy() {
    println("--- 9.1 Minh họa lateinit ---")
    val container = ServiceContainer()
    println("Database đã khởi tạo chưa? ${container.isDatabaseReady()}") // false

    container.setupDependencies()
    println("Database đã khởi tạo chưa? ${container.isDatabaseReady()}") // true
    println(container.db.query("SELECT * FROM users"))

    println("\n--- 9.2 Minh họa by lazy ---")
    println("Trước khi truy cập heavyCalculationResult")
    // Lần 1: Khối lambda bên trong lazy { ... } sẽ được thực thi và cache lại
    println("Lần truy cập 1: ${container.heavyCalculationResult}")
    // Lần 2: Không tính toán lại nữa, lấy ngay từ cache!
    println("Lần truy cập 2: ${container.heavyCalculationResult}")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 9.1:
 * Thiết kế class [UserProfileManager]:
 * - Thuộc tính `lateinit var token: String`: Token xác thực người dùng.
 * - Thuộc tính `val cachedUserData: Map<String, String> by lazy`:
 *   Mô phỏng việc đọc dữ liệu người dùng từ ổ đĩa hoặc API khi truy cập lần đầu.
 *   Trả về map chứa `mapOf("status" to "ACTIVE", "token" to token)`.
 * - Hàm `fun login(generatedToken: String)`: Gán token vào `token`.
 * - Hàm `fun hasToken(): Boolean`: Kiểm tra xem `token` đã được khởi tạo hay chưa
 *   mà không làm văng UninitializedPropertyAccessException.
 */
class UserProfileManager {
    lateinit var token: String

    val cachedUserData: Map<String, String> by lazy {
        check(::token.isInitialized) { "Không thể tải cachedUserData khi chưa đăng nhập (token chưa khởi tạo)!" }
        mapOf("status" to "ACTIVE", "token" to token)
    }

    fun login(generatedToken: String) {
        require(generatedToken.isNotBlank()) { "Token không được để trống" }
        token = generatedToken
    }

    fun hasToken(): Boolean {
        return ::token.isInitialized
    }
}

fun runPart09() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 9: LATEINIT VS BY LAZY")
    println("==================================================")
    exampleLateinitVsLazy()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val manager = UserProfileManager()
    check(!manager.hasToken()) { "Lỗi: hasToken() phải là false trước khi login" }

    manager.login("JWT_SECRET_TOKEN_999")
    check(manager.hasToken()) { "Lỗi: hasToken() phải là true sau khi login" }
    check(manager.token == "JWT_SECRET_TOKEN_999") { "Sai giá trị token" }

    // Test Lazy caching
    val data = manager.cachedUserData
    check(data["status"] == "ACTIVE" && data["token"] == "JWT_SECRET_TOKEN_999") { "Sai cachedUserData" }
    println("✅ Bài tập 9.1 (lateinit token & lazy cache): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 9!\n")
}

fun main() {
    runPart09()
}
