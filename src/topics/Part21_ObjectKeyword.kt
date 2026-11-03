package topics

/**
 * PHẦN 21: TÁC DỤNG CỦA TỪ KHÓA 'OBJECT' TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Từ khóa đa năng `object` trong Kotlin cung cấp 3 giải pháp kiến trúc cốt lõi:
 *   1. Singleton Object (`object Name`): Khởi tạo một đối tượng duy nhất trên toàn ứng dụng.
 *   2. Companion Object (`companion object`): Thành viên gắn liền với Class (thay thế `static` của Java).
 *   3. Anonymous Object Expression (`object : Interface`): Tạo đối tượng ẩn danh sử dụng tức thời tại chỗ.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Trong Java, việc cài đặt một Singleton an toàn đa luồng đòi hỏi viết nhiều code phức tạp
 *   (Double-Checked Locking, biến `volatile`, private constructor...). Kotlin giải quyết việc này với duy nhất 1 từ khóa `object`.
 * - Kotlin loại bỏ từ khóa `static`, thay vào đó `companion object` là một đối tượng thực sự có thể kế thừa và implement Interface.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Singleton Object:
 *   + Tối ưu cho: Dịch vụ chia sẻ toàn cục, quản lý cấu hình (Configuration), bộ nhớ đệm (Cache), Converter utils.
 *   + Cơ chế: Được JVM khởi tạo Lazy tự động khi được truy cập lần đầu và đảm bảo an toàn Thread-safe tuyệt đối.
 * - Companion Object:
 *   + Tối ưu nhất cho: **Factory Method Pattern** (`User.createDefault()`), nơi khai báo hằng số `const val` gắn với Class.
 * - Anonymous Object Expression:
 *   + Tối ưu cho: UI ClickListener, Network Callback. Vượt trội hơn Java vì có thể implement cùng lúc nhiều interface
 *     và truy cập trực tiếp các biến bên ngoài mà không cần biến đó phải là `final`.
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
