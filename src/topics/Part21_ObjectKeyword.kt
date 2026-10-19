package topics

/**
 * PHẦN 21: TÁC DỤNG CỦA TỪ KHÓA 'OBJECT' TRONG KOTLIN
 *
 * Lý thuyết:
 * Từ khóa 'object' trong Kotlin giải quyết 3 bài toán lớn:
 * 1. Singleton Object (object Declaration):
 *    - Tạo ra MỘT VÀ CHỈ MỘT instance duy nhất trong toàn bộ vòng đời ứng dụng.
 *    - Được khởi tạo Thread-safe ngay lần đầu truy cập nhờ cơ chế ClassLoader của JVM.
 *    - Ví dụ: object AppDatabase { ... }
 *
 * 2. Companion Object:
 *    - Nằm bên trong một Class thông thường, tạo các phương thức/thuộc tính ở cấp độ Class
 *      (thay thế từ khóa 'static' của Java).
 *    - Rất phổ biến khi làm Factory Method (ví dụ: User.createGuest()).
 *    - Có thể kế thừa class khác hoặc implement Interface.
 *
 * 3. Anonymous Object Expression (object : Interface/Class):
 *    - Tạo đối tượng ẩn danh tại chỗ (thay thế Anonymous Inner Class của Java).
 *    - Thường dùng làm Listener, Callback hoặc Event Handler.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================

// 1. Singleton Object
object AppSettings {
    var theme: String = "DARK"
    var language: String = "vi"

    fun printSettings() {
        println("Settings: Theme=$theme, Language=$language")
    }
}

// 2. Companion Object (Static-like & Factory Pattern)
class Account private constructor(val id: String, val type: String) {
    companion object {
        // Factory Method
        fun createStandard(id: String): Account {
            return Account(id, "STANDARD")
        }

        fun createVip(id: String): Account {
            return Account(id, "VIP")
        }
    }
}

// 3. Anonymous Object Expression
interface OnClickListener {
    fun onClick(viewId: String): String
}

fun triggerClick(listener: OnClickListener): String {
    return listener.onClick("btn_submit")
}

fun exampleObjectUsages() {
    println("--- 21.1 Singleton Object ---")
    AppSettings.printSettings()
    AppSettings.theme = "LIGHT"
    println("Theme mới của Singleton: ${AppSettings.theme}")

    println("\n--- 21.2 Companion Object ---")
    val vipAcc = Account.createVip("ACC-999")
    println("Tạo tài khoản qua Companion Factory: ${vipAcc.id} - ${vipAcc.type}")

    println("\n--- 21.3 Anonymous Object Expression ---")
    val result = triggerClick(object : OnClickListener {
        override fun onClick(viewId: String): String {
            return "Đã click vào view: $viewId tại thời điểm ${System.currentTimeMillis()}"
        }
    })
    println(result)
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 21.1:
 * Thiết kế class [Money] có constructor private `(val amount: Double, val currency: String)`:
 * - Tạo `companion object` cung cấp 2 Factory Methods:
 *   + `fun usd(amount: Double): Money`: tạo đối tượng với currency = "USD".
 *   + `fun vnd(amount: Double): Money`: tạo đối tượng với currency = "VND".
 * - Hàm `fun formatted(): String` trả về "$amount $currency" (ví dụ "100.0 USD").
 */
class Money private constructor(val amount: Double, val currency: String) {
    companion object {
        fun usd(amount: Double): Money = Money(amount, "USD")
        fun vnd(amount: Double): Money = Money(amount, "VND")
    }

    fun formatted(): String = "$amount $currency"
}

/**
 * BÀI TẬP 21.2:
 * Thiết kế interface [DataFilter<T>] có hàm `fun isValid(item: T): Boolean`.
 * Viết hàm [createPositiveNumberFilter()] trả về một anonymous object của [DataFilter<Int>]
 * chấp nhận các số nguyên > 0.
 */
interface DataFilter<T> {
    fun isValid(item: T): Boolean
}

fun createPositiveNumberFilter(): DataFilter<Int> {
    return object : DataFilter<Int> {
        override fun isValid(item: Int): Boolean {
            return item > 0
        }
    }
}

fun runPart21() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 21: OBJECT KEYWORD")
    println("==================================================")
    exampleObjectUsages()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val m1 = Money.usd(50.0)
    val m2 = Money.vnd(500000.0)
    check(m1.formatted() == "50.0 USD") { "Sai Money USD" }
    check(m2.formatted() == "500000.0 VND") { "Sai Money VND" }
    println("✅ Bài tập 21.1 (Companion Object Factory): Đạt chuẩn!")

    val filter = createPositiveNumberFilter()
    check(filter.isValid(10)) { "Lỗi: 10 phải là số dương" }
    check(!filter.isValid(-5)) { "Lỗi: -5 không thể hợp lệ" }
    check(!filter.isValid(0)) { "Lỗi: 0 không thể là số dương" }
    println("✅ Bài tập 21.2 (Anonymous Object Expression): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 21!\n")
}

fun main() {
    runPart21()
}
