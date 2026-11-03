package topics

/**
 * PHẦN 3: TYPE INFERENCE (SUY LUẬN KIỂU) TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Cho phép trình biên dịch tự động suy luận kiểu dữ liệu tĩnh (Static Type) của biến
 *   hoặc giá trị trả về của hàm dựa trên biểu thức khởi tạo ở vế phải.
 * - Đảm bảo tính Type-safe tuyệt đối (không phải kiểu động dynamic như JS hay Python),
 *   kiểu dữ liệu đã được chốt cứng ngay tại Compile-time.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Xóa bỏ mã thừa thãi (Boilerplate / Code noise): Không cần lặp lại kiểu ở cả 2 vế
 *   như `HashMap<String, List<Int>> map = new HashMap<String, List<Int>>()`.
 * - Tăng tốc độ đọc hiểu mã nguồn: Người đọc dễ dàng tập trung vào tên biến và luồng nghiệp vụ.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Suy luận kiểu (Type Inference) tối ưu nhất khi:
 *   + Khai báo biến cục bộ (Local variables) trong hàm.
 *   + Biểu thức khởi tạo có kiểu hiển nhiên (`val name = "Kotlin"`, `val age = 25`, `val user = User()`).
 *   + Single-expression functions đơn giản trong nội bộ private (`private fun sum(a: Int, b: Int) = a + b`).
 * - Khai báo kiểu tường minh (Explicit Type) tối ưu và bắt buộc khi:
 *   1. **Public API & Public Member Functions**: Bắt buộc chỉ định kiểu trả về rõ ràng để tránh vô tình
 *      thay đổi ngầm làm vỡ hợp đồng giao tiếp (Contract) với bên ngoài.
 *   2. **Biến chưa gán giá trị ngay**: Khai báo trước rồi gán trong các nhánh `if/else` hoặc `when`.
 *   3. **Trừu tượng hóa & Đa hình (Polymorphism)**: Ép kiểu về Interface (`val list: List<String> = arrayListOf(...)`)
 *      thay vì để compiler infer thành Concrete class (`ArrayList<String>`).
 *   4. **Tránh nhầm lẫn kiểu số học**: `val id: Long = 100` (nếu không khai báo rõ, compiler sẽ infer là `Int`).
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
fun exampleTypeInference() {
    println("--- 3.1 Các trường hợp Compiler tự suy luận kiểu ---")
    val language = "Kotlin"           // Inferred as String
    val version = 1.9                // Inferred as Double
    val count = 100                  // Inferred as Int
    val isAwesome = true             // Inferred as Boolean
    val fruits = listOf("Táo", "Cam")// Inferred as List<String>

    println("language: $language (${language::class.simpleName})")
    println("version: $version (${version::class.simpleName})")
    println("count: $count (${count::class.simpleName})")
    println("fruits: $fruits (${fruits::class.simpleName})")

    println("\n--- 3.2 Trường hợp bắt buộc phải chỉ định kiểu ---")
    // Trường hợp 1: Biến chưa gán giá trị ngay lúc khai báo
    val deferredValue: String
    if (count > 50) {
        deferredValue = "High"
    } else {
        deferredValue = "Low"
    }
    println("deferredValue: $deferredValue")

    // Trường hợp 2: Khởi tạo danh sách rỗng, phải cung cấp type argument
    val emptyStrings = emptyList<String>()
    println("emptyStrings size: ${emptyStrings.size}")
}

fun exampleOptimalExplicitVsInference() {
    println("--- 3.3 So sánh: Interface Abstraction vs Inferred Concrete Class ---")
    // Kém tối ưu về đóng gói: Compiler suy luận thành java.util.ArrayList
    val concreteList = arrayListOf("A", "B")

    // Tối ưu về thiết kế hướng đối tượng: Chỉ định kiểu Interface List (Read-only)
    val abstractedList: List<String> = arrayListOf("A", "B")
    println("abstractedList type: ${abstractedList::class.simpleName}, contract: List<String>")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

interface Shape {
    fun area(): Double
}

class Circle(val radius: Double) : Shape {
    override fun area(): Double = Math.PI * radius * radius
}

class Square(val side: Double) : Shape {
    override fun area(): Double = side * side
}

/**
 * BÀI TẬP 3.1:
 * Viết hàm [createShapeMap] trả về một `Map<String, Shape>`.
 * Yêu cầu:
 * - Tạo map chứa "circle" trỏ tới `Circle(radius = 3.0)` và "square" trỏ tới `Square(side = 4.0)`.
 * - Lưu ý: Cả Circle và Square đều implement Shape. Hãy áp dụng hiểu biết về Polymorphism
 *   và Type Inference để Map có kiểu `Map<String, Shape>`.
 */
fun createShapeMap(): Map<String, Shape> {
    return mapOf(
        "circle" to Circle(3.0),
        "square" to Square(4.0)
    )
}

/**
 * BÀI TẬP 3.2:
 * Viết hàm [classifyNumber] nhận vào một số nguyên `n: Int`.
 * Dùng biểu thức `when` để trả về chuỗi:
 * - n < 0: "Negative"
 * - n == 0: "Zero"
 * - n > 0: "Positive"
 * Hãy viết theo dạng single-expression function (`fun classifyNumber(...) = ...`)
 * để Kotlin tự suy luận kiểu trả về của hàm.
 */
fun classifyNumber(n: Int) = when {
    n < 0 -> "Negative"
    n == 0 -> "Zero"
    else -> "Positive"
}

fun runPart03() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 3: TYPE INFERENCE")
    println("==================================================")
    exampleTypeInference()
    exampleOptimalExplicitVsInference()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Test Bài tập 3.1
    val shapeMap = createShapeMap()
    check(shapeMap.containsKey("circle") && shapeMap.containsKey("square")) { "Sai bài tập 3.1" }
    val circle = shapeMap["circle"]
    check(circle is Shape) { "circle không phải là Shape" }
    println("✅ Bài tập 3.1 (Map of Polymorphic Shapes): Đạt chuẩn!")

    // Test Bài tập 3.2
    check(classifyNumber(-5) == "Negative") { "Sai classifyNumber(-5)" }
    check(classifyNumber(0) == "Zero") { "Sai classifyNumber(0)" }
    check(classifyNumber(10) == "Positive") { "Sai classifyNumber(10)" }
    println("✅ Bài tập 3.2 (Single-expression function inference): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 3!\n")
}

fun main() {
    runPart03()
}
