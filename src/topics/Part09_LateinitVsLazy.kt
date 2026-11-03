package topics

import kotlin.isInitialized

/**
 * PHẦN 9: SO SÁNH LATEINIT VS BY LAZY TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Cung cấp hai cơ chế trì hoãn việc cấp phát / khởi tạo giá trị cho thuộc tính:
 *   1. `lateinit var`: Khởi tạo muộn thủ công (Manual late initialization) cho biến non-null.
 *   2. `val ... by lazy`: Khởi tạo lười tự động (Automatic lazy initialization) ở lần truy cập đầu tiên.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Loại bỏ việc phải khai báo biến dạng Nullable (`var prop: Type? = null`) chỉ để chờ khởi tạo sau,
 *   giúp tránh phải dùng toán tử `?.` hoặc `!!` ở khắp mọi nơi trong mã nguồn.
 * - Tiết kiệm tài nguyên CPU & RAM: Tránh khởi tạo các đối tượng nặng (Database, Network, File Parser)
 *   lúc ứng dụng khởi động khi người dùng có thể chưa cần dùng tới chức năng đó.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * ┌──────────────────────┬───────────────────────────────────┬───────────────────────────────────┐
 * │ Tiêu chí             │ lateinit                          │ by lazy                           │
 * ├──────────────────────┼───────────────────────────────────┼───────────────────────────────────┤
 * │ Từ khóa đi kèm       │ Bắt buộc dùng với 'var'           │ Bắt buộc dùng với 'val'           │
 * │ Kiểu dữ liệu         │ Không hỗ trợ Primitive (Int...)   │ Áp dụng cho MỌI kiểu dữ liệu      │
 * │ Cơ chế khởi tạo      │ Khởi tạo thủ công (bằng code gán) │ Tự động chạy lambda ở lần đọc đầu │
 * │ Kiểm tra khởi tạo    │ Dùng ::prop.isInitialized         │ Tự động cache kết quả sau lần 1   │
 * │ Thread Safety        │ Không an toàn đa luồng            │ Mặc định Thread-safe (SYNCHRONIZED│
 * └──────────────────────┴───────────────────────────────────┴───────────────────────────────────┘
 * - lateinit tối ưu nhất khi:
 *   + Dùng với Dependency Injection framework (Dagger, Hilt, Spring `@Autowired`).
 *   + Khởi tạo theo vòng đời của framework (Android `onCreate()`, JUnit `@Before setUp()`).
 * - by lazy tối ưu nhất khi:
 *   + Khởi tạo các tài nguyên nặng (Heavy Object, Database connection, Bitmap loading, Service client)
 *     chỉ đọc (Read-only) và cần nạp theo nhu cầu (On-demand).
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

class lateinitexample(){
    lateinit var apiService :String
    fun setup(){
        apiService = "aeorstride.com"
    }
    fun doST(){
        if (::apiService.isInitialized) {
            println("Service: $apiService")
        } else {
            println("apiService chưa được khởi tạo!")
        }
    }
    fun updateService() {
        // Có thể gán lại giá trị mới vì là 'var'
        apiService = "https://api.v2.example.com"
    }
}
class LazyExample {
    // 1. Bắt buộc dùng 'val' (không dùng được với 'var')
    // 2. Đi kèm block code chứa logic khởi tạo
    // 3. Áp dụng được cho mọi kiểu dữ liệu (kể cả Int, Double, Class...)
    // 4. Mặc định Thread-safe (an toàn khi dùng đa luồng)
    val heavyDatabase: String by lazy {
        println("--> Đang khởi tạo Database (chỉ chạy duy nhất 1 lần khi gọi tới)...")
        "MySQL_Connection_Object"
    }

    fun executeQuery() {
        println("Chưa dùng đến heavyDatabase...")

        // Lần đầu tiên truy cập: Block code trong 'by lazy' mới chạy
        println("Lần 1 truy cập: $heavyDatabase")

        // Lần thứ hai trở đi: Trả về luôn kết quả đã lưu trước đó, không chạy lại block code
        println("Lần 2 truy cập: $heavyDatabase")
    }
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
