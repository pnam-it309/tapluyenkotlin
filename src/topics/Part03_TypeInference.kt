package topics

/**
 * PHẦN 3: TYPE INFERENCE (SUY LUẬN KIỂU) TRONG KOTLIN
 *
 * Lý thuyết:
 * - Khái niệm: Là khả năng của trình biên dịch Kotlin tự động suy luận kiểu dữ liệu
 *   của biến hoặc giá trị trả về của hàm dựa trên giá trị khởi tạo.
 * - Ví dụ:
 *     val name = "Kotlin" // Trình biên dịch tự hiểu là String
 *     val age = 25        // Trình biên dịch tự hiểu là Int
 * - Các trường hợp BẮT BUỘC hoặc NÊN khai báo kiểu rõ ràng (Explicit Type):
 *   1. Khai báo biến nhưng chưa gán giá trị ngay: val x: Int; x = 10
 *   2. Tạo collection rỗng không có phần tử mẫu: val list = emptyList<String>()
 *   3. Ép kiểu về interface/lớp cha (Polymorphism): val animal: Animal = Dog()
 *   4. Public API / Public Function trong thư viện lớn: Nên khai báo return type rõ ràng.
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
