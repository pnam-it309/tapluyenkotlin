package topics

/**
 * PHẦN 18: LAMBDA LÀ GÌ, SO SÁNH HIGHER-ORDER FUNCTION VÀ LAMBDA
 *
 * Lý thuyết:
 * 1. Lambda Expression:
 *    - Là một hàm vô danh (Anonymous Function) được viết dưới dạng biểu thức ngắn gọn:
 *      { x: Int, y: Int -> x + y }
 *    - Trong Kotlin, hàm là "First-Class Citizen", tức là một biểu thức Lambda có thể:
 *      + Được gán vào một biến: val sum = { a: Int, b: Int -> a + b }
 *      + Được truyền làm đối số cho hàm khác.
 *      + Được trả về từ một hàm.
 *
 * 2. So sánh Lambda và Higher-Order Function:
 *    - LAMBDA: Là "Dữ liệu/Giá trị" (Function Value/Expression).
 *    - HIGHER-ORDER FUNCTION: Là "Hàm chứa/tiêu thụ" (Consumer/Producer) nhận hoặc trả về Lambda.
 *
 * 3. Các cú pháp đặc sắc của Lambda trong Kotlin:
 *    - 'it': Tên ngầm định cho tham số duy nhất của lambda ({ it * 2 }).
 *    - Trailing Lambda: Nếu tham số cuối của hàm là lambda, được đưa ra ngoài ngoặc tròn 'myFunc(arg) { ... }'.
 *    - Bỏ ngoặc tròn: Nếu lambda là tham số duy nhất, bỏ luôn '()': 'list.filter { it > 0 }'.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
fun exampleLambdaSyntax() {
    println("--- 18.1 Biểu thức Lambda gán vào biến ---")
    // Khai báo biến giữ một lambda
    val square: (Int) -> Int = { x -> x * x }
    println("Bình phương của 6 = ${square(6)}")

    // Dùng implicit name 'it'
    val isEven: (Int) -> Boolean = { it % 2 == 0 }
    println("8 có chẵn không? ${isEven(8)}")

    println("\n--- 18.2 Trailing Lambda Syntax ---")
    val fruits = listOf("Chuối", "Táo", "Xoài", "Mận")

    // Thay vì viết: fruits.filter({ fruit -> fruit.length > 3 })
    // Ta viết chuẩn Kotlin Trailing Lambda với 'it':
    val longNames = fruits.filter { it.length > 3 }
    println("Trái cây tên dài: $longNames")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 18.1:
 * Viết hàm [transformList] nhận vào:
 * - `items: List<T>`
 * - `transformer: (T) -> R` (một lambda biến đổi phần tử kiểu T thành R).
 * Trả về `List<R>`.
 *
 * Áp dụng hàm trên để:
 * 1. Chuyển đổi danh sách các số nguyên [1, 2, 3] thành danh sách các chuỗi ["Số 1", "Số 2", "Số 3"].
 * 2. Tính bình phương của danh sách số nguyên.
 */
fun <T, R> transformList(items: List<T>, transformer: (T) -> R): List<R> {
    val result = mutableListOf<R>()
    for (item in items) {
        result.add(transformer(item))
    }
    return result
}

fun runPart18() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 18: LAMBDA VS HOF")
    println("==================================================")
    exampleLambdaSyntax()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val nums = listOf(1, 2, 3)

    // Dùng trailing lambda biến đổi sang chuỗi
    val strings = transformList(nums) { "Số $it" }
    check(strings == listOf("Số 1", "Số 2", "Số 3")) { "Sai transform sang String" }

    // Dùng trailing lambda tính bình phương
    val squares = transformList(nums) { it * it }
    check(squares == listOf(1, 4, 9)) { "Sai transform bình phương" }

    println("✅ Bài tập 18.1 (transformList với Trailing Lambda & 'it'): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 18!\n")
}

fun main() {
    runPart18()
}
