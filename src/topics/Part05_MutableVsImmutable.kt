package topics

/**
 * PHẦN 5: MUTABLE VÀ IMMUTABLE TRONG KOTLIN
 *
 * Lý thuyết:
 * - Immutable (Read-only):
 *   + Khai báo biến: 'val' (chỉ gán giá trị 1 lần, không thể gán lại).
 *   + Collections: 'List', 'Map', 'Set' (không có hàm add, remove, clear, put...).
 * - Mutable (Cho phép sửa):
 *   + Khai báo biến: 'var' (có thể gán lại giá trị mới).
 *   + Collections: 'MutableList', 'MutableMap', 'MutableSet' (có các hàm thay đổi dữ liệu).
 *
 * LƯU Ý QUAN TRỌNG:
 * - 'val list = mutableListOf(1, 2)' nghĩa là con trỏ 'list' không thể trỏ sang đối tượng khác,
 *   NHƯNG nội dung bên trong mutableList vẫn có thể bị thay đổi (add/remove)!
 * - Để bảo vệ tính đóng gói (encapsulation), class nên lưu trữ private MutableList
 *   nhưng chỉ để lộ ra ngoài dạng public Read-only List.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
fun exampleMutableVsImmutable() {
    println("--- 5.1 val vs var ---")
    val immutableValue = 100
    // immutableValue = 200 // Lỗi biên dịch: Val cannot be reassigned

    var mutableValue = 100
    mutableValue = 200 // Hợp lệ
    println("mutableValue sau khi gán lại: $mutableValue")

    println("\n--- 5.2 Read-only List vs MutableList ---")
    val readOnlyList: List<String> = listOf("Kotlin", "Java")
    // readOnlyList.add("Python") // Lỗi biên dịch: List không có hàm add()

    val mutableList: MutableList<String> = mutableListOf("Kotlin", "Java")
    mutableList.add("Python") // Hợp lệ
    println("mutableList sau khi thêm: $mutableList")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 5.1: Thiết kế Giỏ Hàng An Toàn (Safe Shopping Cart)
 *
 * Yêu cầu:
 * - Class [ShoppingCart] quản lý danh sách các món hàng (chuỗi String).
 * - Thuộc tính bên trong lưu trữ dạng `private val _items = mutableListOf<String>()`.
 * - Thuộc tính công khai [items]: Cung cấp dạng Read-only `List<String>` để bên ngoài
 *   chỉ có thể đọc danh sách mà không thể trực tiếp gọi .add() hay .clear().
 * - Hàm [addItem(item: String)]: Thêm sản phẩm vào giỏ (nếu item không rỗng/blank).
 * - Hàm [removeItem(item: String)]: Xóa sản phẩm khỏi giỏ.
 * - Hàm [itemCount]: Trả về số lượng sản phẩm hiện có.
 */
class ShoppingCart {
    private val _items: MutableList<String> = mutableListOf()

    // Expose dưới dạng Read-only List bằng custom getter hoặc toList()
    val items: List<String>
        get() = _items.toList() // Tạo snapshot bảo vệ hoặc trả về read-only interface

    fun addItem(item: String) {
        if (item.isNotBlank()) {
            _items.add(item.trim())
        }
    }

    fun removeItem(item: String): Boolean {
        return _items.remove(item.trim())
    }

    val itemCount: Int
        get() = _items.size
}

/**
 * BÀI TẬP 5.2:
 * Viết hàm [separateEvenOdd] nhận vào danh sách `List<Int>`.
 * Trả về một `Pair<List<Int>, List<Int>>` trong đó:
 * - first: danh sách chứa các số chẵn (sắp xếp tăng dần).
 * - second: danh sách chứa các số lẻ (sắp xếp tăng dần).
 * Cả hai danh sách trả về đều là Immutable List.
 */
fun separateEvenOdd(numbers: List<Int>): Pair<List<Int>, List<Int>> {
    val evens = numbers.filter { it % 2 == 0 }.sorted()
    val odds = numbers.filter { it % 2 != 0 }.sorted()
    return Pair(evens, odds)
}

fun runPart05() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 5: MUTABLE VS IMMUTABLE")
    println("==================================================")
    exampleMutableVsImmutable()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Test Bài tập 5.1
    val cart = ShoppingCart()
    cart.addItem("Bàn phím cơ")
    cart.addItem("Chuột không dây")
    cart.addItem("   ") // Không được thêm chuỗi rỗng
    check(cart.itemCount == 2) { "Sai số lượng item trong cart" }
    check(cart.items == listOf("Bàn phím cơ", "Chuột không dây")) { "Sai danh sách món hàng" }

    cart.removeItem("Bàn phím cơ")
    check(cart.itemCount == 1 && cart.items.first() == "Chuột không dây") { "Sai sau khi xóa" }
    println("✅ Bài tập 5.1 (Encapsulated ShoppingCart): Đạt chuẩn!")

    // Test Bài tập 5.2
    val (evens, odds) = separateEvenOdd(listOf(9, 2, 8, 3, 1, 4, 7, 6, 5))
    check(evens == listOf(2, 4, 6, 8)) { "Sai danh sách chẵn: $evens" }
    check(odds == listOf(1, 3, 5, 7, 9)) { "Sai danh sách lẻ: $odds" }
    println("✅ Bài tập 5.2 (Tách mảng chẵn lẻ Immutable): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 5!\n")
}

fun main() {
    runPart05()
}
