package topics

/**
 * PHẦN 11: BACKING FIELD LÀ GÌ, TÁC DỤNG GÌ VÀ TẠI SAO KOTLIN CẦN NÓ?
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - `field` (Backing Field) là định danh đại diện cho ô nhớ lưu trữ vật lý thực sự của thuộc tính trong RAM.
 * - Chỉ tồn tại và được phép sử dụng bên trong khối mã của Custom Getter hoặc Custom Setter.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Ngăn chặn lỗi lặp vô hạn `StackOverflowError` gây sập ứng dụng:
 *   Trong Kotlin, mọi thao tác truy cập `this.property = value` đều được dịch thành lời gọi hàm setter ngầm.
 *   Nếu bên trong chính setter mà bạn viết `this.property = value`, nó sẽ vô tình gọi lại chính setter đó
 *   lặp đi lặp lại mãi mãi cho đến khi tràn bộ nhớ Call Stack.
 * - Từ khóa `field` ra đời để lập trình viên có thể đọc/ghi trực tiếp vào ô nhớ mà KHÔNG kích hoạt lại setter/getter.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Khi nào có Backing Field (Tốn bộ nhớ RAM):
 *   + Khi thuộc tính sử dụng getter/setter mặc định.
 *   + Hoặc khi custom getter/setter có chứa từ khóa `field`.
 * - Khi nào KHÔNG có Backing Field (Tối ưu tiết kiệm RAM):
 *   + Khi thuộc tính là Computed Property (chỉ có custom `get()` tính toán từ thuộc tính khác).
 *   + Instance của class sẽ không tốn bất kỳ byte RAM nào để lưu thuộc tính đó.
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
