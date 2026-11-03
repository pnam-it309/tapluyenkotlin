package topics

/**
 * PHẦN 20: XUNG ĐỘT GIỮA EXTENSION FUNCTION VÀ MEMBER FUNCTION
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Quy định độ ưu tiên của trình biên dịch khi có sự trùng lặp giữa Extension Function và Member Function:
 *   **Member Function luôn luôn chiến thắng (Member Always Wins)!**
 * - Nếu cùng Function Signature (tên hàm, số lượng và kiểu tham số), Extension Function sẽ bị che khuất (Shadowed).
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Đảm bảo tính bảo mật và toàn vẹn của Class: Ngăn chặn việc bên ngoài viết extension nhằm thay đổi ngầm,
 *   chiếm quyền hoặc làm sai lệch hành vi vốn có của một class.
 * - Giữ sự ổn định cho các hệ thống phần mềm lớn khi có nhiều lập trình viên cùng làm việc.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Trùng Signature 100%: Compiler sẽ bỏ qua Extension và luôn gọi Member Function.
 * - Khác Signature (Overloading): Nếu Extension Function nhận các tham số khác với Member Function,
 *   cả hai đều hoạt động bình thường như các hàm nạp chồng.
 * - Lời khuyên thiết kế tối ưu: Không bao giờ cố tình viết Extension Function trùng tên và tham số với Member Function.
 *   Cần cẩn thận khi thư viện gốc nâng cấp phiên bản có thể bổ sung thêm method trùng tên với Extension của bạn.
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
