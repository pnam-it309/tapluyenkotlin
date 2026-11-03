package topics

import java.math.BigInteger

/**
 * PHẦN 22: HÀM ĐỆ QUY ĐUÔI (TAILREC) VÀ BẢN CHẤT TỐI ƯU
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Kích hoạt cơ chế Tối ưu hóa đệ quy đuôi (Tail Call Optimization - TCO) của trình biên dịch Kotlin.
 * - Trình biên dịch tự động "tháo gỡ" (unroll) hàm đệ quy thành một **vòng lặp lặp lại (iterative loop)**
 *   ở cấp độ JVM Bytecode.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Đệ quy truyền thống sẽ liên tục đẩy thêm Stack Frame vào Call Stack của JVM ở mỗi bước gọi.
 *   Khi độ sâu đệ quy lớn (vài nghìn đến hàng triệu lần gọi), ứng dụng sẽ sập ngay lập tức với lỗi `StackOverflowError`.
 * - `tailrec` giải quyết triệt để lỗi này: Độ phức tạp bộ nhớ Stack giảm từ $O(N)$ xuống $O(1)$,
 *   cho phép chạy đệ quy sâu hàng triệu bước mà không hề tăng dung lượng Stack.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Điều kiện bắt buộc của `tailrec`:
 *   + Lời gọi đệ quy phải là **hành động cuối cùng** (Tail-position) trước khi return.
 *   + Không được chứa phép tính kèm sau lời gọi (ví dụ: `return n * fact(n - 1)` là SAI vì phải chờ nhân `n`).
 *   + Phải chuyển đổi sang kỹ thuật dùng biến tích lũy (Accumulator): `return fact(n - 1, acc * n)`.
 * - So với vòng lặp `while/for`:
 *   + Bytecode sinh ra tương đương về tốc độ và bộ nhớ.
 *   + `tailrec` tối ưu hơn về mặt tư duy biểu diễn thuật toán (phong cách hàm thuần túy, bất biến, dễ chứng minh tính đúng đắn).
 * - Giới hạn: Không áp dụng được cho đệ quy phân nhánh (Duyệt cây nhị phân, đồ thị DFS).
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================

// Hàm đệ quy đuôi tính tổng từ 1 đến n
tailrec fun sumUpTo(n: Long, accumulator: Long = 0L): Long {
    if (n <= 0L) return accumulator
    // Lệnh gọi đệ quy là biểu thức cuối cùng được thực thi
    return sumUpTo(n - 1L, accumulator + n)
}

// Hàm đệ quy đuôi tính số Fibonacci thứ n
tailrec fun fibonacciTail(n: Int, a: BigInteger = BigInteger.ZERO, b: BigInteger = BigInteger.ONE): BigInteger {
    return when (n) {
        0 -> a
        1 -> b
        else -> fibonacciTail(n - 1, b, a + b)
    }
}

fun exampleTailrecDemonstration() {
    println("--- 22.1 Đệ quy 100.000 lần không hề bị StackOverflow ---")
    // Với đệ quy thường, gọi 100.000 frame sẽ làm crash ứng dụng ngay lập tức!
    // Nhưng với tailrec, nó chạy như một vòng lặp siêu tốc:
    val sum = sumUpTo(100_000L)
    println("Tổng từ 1 đến 100.000: $sum")

    println("Fibonacci thứ 50: ${fibonacciTail(50)}")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 22.1:
 * Viết hàm đệ quy đuôi [factorialTail] tính giai thừa của số `n: Long`.
 * Sử dụng tham số `accumulator: BigInteger = BigInteger.ONE`.
 * Yêu cầu:
 * - Đánh dấu từ khóa `tailrec`.
 * - Nếu n <= 1: trả về `accumulator`.
 * - Ngược lại: gọi đệ quy đuôi `factorialTail(n - 1, accumulator * BigInteger.valueOf(n))`.
 */
tailrec fun factorialTail(n: Long, accumulator: BigInteger = BigInteger.ONE): BigInteger {
    if (n <= 1L) return accumulator
    return factorialTail(n - 1L, accumulator.multiply(BigInteger.valueOf(n)))
}

/**
 * BÀI TẬP 22.2:
 * Viết hàm đệ quy đuôi [findGcdTail] tìm Ước Chung Lớn Nhất (GCD) của 2 số nguyên a và b (thuật toán Euclid):
 * Yêu cầu:
 * - Đánh dấu `tailrec`.
 * - Nếu b == 0: trả về a.
 * - Ngược lại: gọi đệ quy đuôi với `a = b` và `b = a % b`.
 */
tailrec fun findGcdTail(a: Long, b: Long): Long {
    return if (b == 0L) a else findGcdTail(b, a % b)
}

fun runPart22() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 22: TAILREC FUNCTION")
    println("==================================================")
    exampleTailrecDemonstration()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Test factorialTail
    val fact5 = factorialTail(5L) // 5! = 120
    check(fact5 == BigInteger.valueOf(120)) { "Sai factorial(5): $fact5" }

    // Test factorial số lớn với tailrec
    val fact20 = factorialTail(20L)
    check(fact20 == BigInteger("2432902008176640000")) { "Sai factorial(20)" }
    println("✅ Bài tập 22.1 (factorialTail): Đạt chuẩn!")

    // Test findGcdTail
    check(findGcdTail(48, 18) == 6L) { "Sai GCD(48, 18)" }
    check(findGcdTail(100, 25) == 25L) { "Sai GCD(100, 25)" }
    check(findGcdTail(17, 13) == 1L) { "Sai GCD(17, 13)" }
    println("✅ Bài tập 22.2 (findGcdTail Euclid): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 22!\n")
}

fun main() {
    runPart22()
}
