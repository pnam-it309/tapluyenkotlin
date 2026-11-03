package topics

/**
 * PHẦN 18: LAMBDA LÀ GÌ, SO SÁNH HIGHER-ORDER FUNCTION VÀ LAMBDA
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Lambda Expression: Là một hàm vô danh (Anonymous function) được viết dưới dạng biểu thức `{ a, b -> a + b }`.
 *   Đóng vai trò như một GIÁ TRỊ (Function Value) có thể gán vào biến hoặc truyền làm đối số.
 * - Higher-Order Function (HOF): Là HÀM TIÊU THỤ nhận Lambda làm đối số hoặc sản xuất trả về Lambda.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Xây dựng phong cách lập trình khai báo (Declarative): Người lập trình chỉ cần mô tả "Muốn làm gì"
 *   thay vì "Làm từng bước lặp như thế nào".
 * - Giảm đáng kể số dòng code, tăng tính trực quan với cú pháp Trailing Lambda và định danh ngầm định `it`.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Trailing Lambda Syntax: Nếu tham số cuối của hàm là lambda, luôn đưa ra ngoài dấu ngoặc tròn:
 *   `list.filter { it > 0 }` thay vì `list.filter({ it > 0 })`.
 * - Tên ngầm định `it` vs Tên tham số tường minh:
 *   + *Dùng `it`*: Khi lambda ngắn gọn và chỉ có 1 tham số duy nhất.
 *   + *Khai báo tên tường minh (`{ user -> ... }`)*: Khi lambda có nhiều tham số hoặc lồng nhau phức tạp,
 *     giúp tránh nhầm lẫn biến ngữ cảnh.
 * - Method Reference (`::println`): Tối ưu hơn lambda `{ println(it) }` khi chỉ chuyển tiếp nguyên vẹn tham số.
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
