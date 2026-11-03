package topics

/**
 * PHẦN 5: MUTABLE VÀ IMMUTABLE TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Phân định rõ ràng giữa dữ liệu chỉ đọc (Immutable / Read-only) và dữ liệu có thể sửa đổi (Mutable).
 * - Cung cấp ở cả 2 cấp độ:
 *   + Cấp độ biến: `val` (chỉ gán 1 lần) vs `var` (cho phép gán lại).
 *   + Cấp độ Collections: `List`/`Map`/`Set` (Read-only interface) vs `MutableList`/`MutableMap`/`MutableSet`.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Dữ liệu bị thay đổi tùy tiện (Mutable State) là thủ phạm hàng đầu gây ra lỗi tương tranh (Race Conditions)
 *   trong môi trường đa luồng (Multi-threading) và tác dụng phụ (Side-effects) khó kiểm soát trong ứng dụng.
 * - Ưu tiên tính bất biến (Immutability by default) giúp code dễ dự đoán (Predictable),
 *   dễ viết Unit Test và an toàn khi chia sẻ giữa các luồng.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - `val` vs `var`:
 *   + *`val`*: Tối ưu và là lựa chọn mặc định cho 95% trường hợp khai báo biến.
 *   + *`var`*: Chỉ dùng khi thực sự cần cập nhật giá trị (biến tích lũy trong vòng lặp, trạng thái thay đổi theo thời gian).
 * - `List` vs `MutableList`:
 *   + *Kém tối ưu*: Expose trực tiếp `MutableList` ra public. Bất kỳ thành phần nào bên ngoài cũng có thể gọi
 *     `.clear()` hoặc `.add()` làm hỏng toàn vẹn dữ liệu của đối tượng.
 *   + *Tối ưu nhất (Backing Property Pattern)*: Lưu trữ bên trong là `private val _list = mutableListOf<T>()`
 *     nhưng để lộ ra ngoài là `val list: List<T> get() = _list`. Bảo vệ toàn diện tính bao đóng (Encapsulation).
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

fun exampleBackingPropertyPattern() {
    println("\n--- 5.3 So sánh tối ưu: Mô hình Backing Property (Encapsulation) ---")
    class DataRepository {
        // Nội bộ thay đổi được
        private val _users = mutableListOf("Admin", "Moderator")
        // Bên ngoài chỉ đọc được
        val users: List<String> get() = _users

        fun addUser(name: String) { _users.add(name) }
    }

    val repo = DataRepository()
    repo.addUser("Member")
    println("Danh sách an toàn (chỉ đọc): ${repo.users}")
    // repo.users.add(...) // Lỗi biên dịch! Không thể can thiệp phá hỏng state
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
    exampleBackingPropertyPattern()

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
