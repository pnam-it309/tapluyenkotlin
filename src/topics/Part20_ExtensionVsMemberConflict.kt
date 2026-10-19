package topics

/**
 * PHẦN 20: XUNG ĐỘT GIỮA EXTENSION FUNCTION VÀ MEMBER FUNCTION
 *
 * Lý thuyết:
 * 1. Câu hỏi kinh điển:
 *    - Nếu một Extension Function giống hệt một Member Function về TÊN, DANH SÁCH THAM SỐ
 *      và KIỂU TRẢ VỀ (cùng signature), thì khi gọi hàm nào sẽ chạy?
 *
 * 2. Câu trả lời:
 *    - MEMBER FUNCTION LUÔN ĐƯỢC ƯU TIÊN THỰC THI (Member Always Wins)!
 *    - Trình biên dịch sẽ luôn liên kết và gọi phương thức thành viên có sẵn của Class.
 *    - Extension Function cùng signature sẽ bị "che khuất" (shadowed) hoàn toàn.
 *
 * 3. Trường hợp Overloading (Khác tham số):
 *    - Nếu Extension Function có tên giống nhưng nhận tham số KHÁC (Overloading),
 *      thì cả 2 hàm đều có thể được gọi độc lập dựa trên tham số truyền vào!
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
class Device {
    fun printStatus() {
        println("Member function: Device is RUNNING (Từ class gốc)")
    }

    fun reboot() {
        println("Member function: Device is REBOOTING")
    }
}

// 1. Trùng signature 100% với member function
// Cảnh báo: Extension is shadowed by a member: public final fun printStatus(): Unit
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun Device.printStatus() {
    println("Extension function: Device status từ Extension")
}

// 2. Cùng tên nhưng khác tham số (Overload) -> Cả 2 đều dùng được!
fun Device.reboot(delaySeconds: Int) {
    println("Extension function: Reboot sau $delaySeconds giây")
}

fun exampleMemberVsExtensionPrecedence(): String {
    val dev = Device()

    // Sẽ gọi Member function hay Extension function? -> Luôn là MEMBER!
    dev.printStatus()

    // Gọi hàm extension overload:
    dev.reboot(5)

    return "MEMBER_CALLED"
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

class SampleCalculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }
}

// Trùng signature với member function
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun SampleCalculator.add(a: Int, b: Int): Int {
    return (a + b) * 100 // Giả sử extension cố tình làm sai để kiểm tra hàm nào chạy
}

// Extension function overload với 3 tham số
fun SampleCalculator.add(a: Int, b: Int, c: Int): Int {
    return a + b + c
}

/**
 * BÀI TẬP 20.1:
 * Viết hàm [verifyPrecedence] kiểm tra:
 * - Khi gọi `calc.add(2, 3)`, kết quả phải là 5 (do member function chạy)
 *   chứ không phải 500 (từ extension function).
 * - Khi gọi `calc.add(2, 3, 4)`, kết quả phải là 9 (gọi extension overload).
 */
fun verifyPrecedence(): Boolean {
    val calc = SampleCalculator()
    val memberResult = calc.add(2, 3)
    val extensionOverloadResult = calc.add(2, 3, 4)

    return memberResult == 5 && extensionOverloadResult == 9
}

fun runPart20() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 20: EXTENSION VS MEMBER")
    println("==================================================")
    exampleMemberVsExtensionPrecedence()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val isCorrect = verifyPrecedence()
    check(isCorrect) { "Member function không được ưu tiên đúng như kỳ vọng!" }
    println("✅ Bài tập 20.1 (Member Function Precedence): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 20!\n")
}

fun main() {
    runPart20()
}
