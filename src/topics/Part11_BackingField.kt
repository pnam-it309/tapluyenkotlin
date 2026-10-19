package topics

/**
 * PHẦN 11: BACKING FIELD LÀ GÌ, TÁC DỤNG GÌ VÀ TẠI SAO KOTLIN CẦN NÓ?
 *
 * Lý thuyết:
 * 1. Khái niệm:
 *    - 'field' (Backing Field) là một trường ẩn (hidden field) do trình biên dịch sinh ra
 *      để lưu trữ giá trị vật lý thực tế của thuộc tính trong bộ nhớ RAM.
 *    - Từ khóa 'field' chỉ có thể sử dụng bên trong custom getter hoặc custom setter.
 *
 * 2. Tác dụng:
 *    - Lưu trữ trạng thái (state) thực sự của thuộc tính.
 *
 * 3. Tại sao Kotlin cần Backing Field?
 *    - Tránh Đệ Quy Vô Hạn (Infinite Recursion):
 *      Trong Java, ta có biến private `int count` và hàm `setCount(int value) { this.count = value; }`.
 *      Trong Kotlin, cú pháp truy cập thuộc tính luôn thông qua getter/setter ngầm.
 *      Nếu trong setter của 'count', ta viết:
 *          set(value) {
 *              this.count = value // -> VÔ TÌNH GỌI LẠI CHÍNH SETTER NÀY!
 *          }
 *      Việc này sẽ gây lặp vô hạn và lập tức crash chương trình với lỗi java.lang.StackOverflowError.
 *    - Do đó, từ khóa 'field' được cung cấp để gán/đọc trực tiếp giá trị vào ô nhớ
 *      mà KHÔNG kích hoạt lại setter/getter.
 *
 * 4. Khi nào một thuộc tính KHÔNG có Backing Field?
 *    - Khi thuộc tính là computed property (chỉ có get() tính toán từ cái khác mà không đọc/ghi 'field').
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
class UserAccount {
    // 1. Thuộc tính CÓ Backing Field:
    var username: String = "anonymous"
        set(value) {
            println("  -> [Setter] Kiểm tra và gán username mới")
            if (value.isNotBlank()) {
                // ĐÚNG: Gán trực tiếp vào ô nhớ ngầm thông qua 'field'
                field = value.trim()

                // SAI (CẤM VIẾT):
                // this.username = value // -> Sẽ gây StackOverflowError ngay lập tức!
            }
        }

    // 2. Thuộc tính KHÔNG CÓ Backing Field:
    // Biến này chỉ tính toán tại chỗ lúc get, compiler không cấp phát ô nhớ riêng cho nó.
    val isRegistered: Boolean
        get() = username != "anonymous"
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 11.1:
 * Thiết kế class [Counter]:
 * - Khai báo thuộc tính `var count: Int = 0`:
 *   + Custom getter: In ra "Đang đọc count: $field" và trả về `field`.
 *   + Custom setter:
 *     - Nếu `value >= 0`: gán vào `field`.
 *     - Nếu `value < 0`: in ra "Không chấp nhận số âm, giữ nguyên" và KHÔNG thay đổi `field`.
 * - Khai báo thuộc tính `val isPositive: Boolean`:
 *   + Computed property (không dùng backing field): trả về `count > 0`.
 */
class Counter {
    var count: Int = 0
        get() {
            // Đọc từ backing field
            return field
        }
        set(value) {
            // Ghi vào backing field an toàn
            if (value >= 0) {
                field = value
            }
        }

    // Không có backing field
    val isPositive: Boolean
        get() = count > 0
}

fun runPart11() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 11: BACKING FIELD")
    println("==================================================")
    val user = UserAccount()
    println("Ban đầu: username = ${user.username}, registered = ${user.isRegistered}")

    user.username = "   alex_kotlin   "
    println("Sau khi gán: username = '${user.username}', registered = ${user.isRegistered}")

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val c = Counter()
    check(c.count == 0) { "Sai giá trị ban đầu" }
    check(!c.isPositive) { "0 không phải là số dương" }

    c.count = 10
    check(c.count == 10 && c.isPositive) { "Sai sau khi gán 10" }

    c.count = -5 // Bị từ chối
    check(c.count == 10) { "Sai: count không được nhận số âm" }

    println("✅ Bài tập 11.1 (Counter Backing field & Computed property): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 11!\n")
}

fun main() {
    runPart11()
}
