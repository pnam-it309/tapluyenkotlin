package topics

/**
 * PHẦN 14: SO SÁNH NESTED CLASS VÀ INNER CLASS TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Định nghĩa một class nằm bên trong thân của một class khác để gom nhóm logic có tính kết dính cao.
 * - Mặc định là Nested Class (tương đương `static class` của Java).
 * - Sử dụng từ khóa `inner` để tạo Inner Class (non-static inner class, gắn với 1 instance của outer).
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Giải quyết vấn đề rò rỉ bộ nhớ (Memory Leak) kinh điển của Java:
 *   Trong Java, inner class mặc định luôn giữ tham chiếu ẩn đến outer class. Nếu đối tượng con sống lâu hơn
 *   (ví dụ tác vụ ngầm, Listener, Thread), GC không thể thu hồi class cha (gây leak toàn bộ Activity/Context).
 * - Kotlin đảo ngược triết lý: Mặc định là Nested Class (Static ngầm, KHÔNG giữ tham chiếu cha).
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * ┌─────────────────────────────┬─────────────────────────────────┬─────────────────────────────────┐
 * │ Tiêu chí                    │ Nested Class (Mặc định)         │ Inner Class (Có từ khóa 'inner')│
 * ├─────────────────────────────┼─────────────────────────────────┼─────────────────────────────────┤
 * │ Bản chất trong Java         │ static nested class             │ non-static inner class          │
 * │ Giữ tham chiếu Outer Class  │ KHÔNG giữ tham chiếu đến outer  │ CÓ giữ tham chiếu đến outer     │
 * │ Nguy cơ Memory Leak         │ Tuyệt đối an toàn               │ Nguy cơ leak nếu con sống lâu   │
 * │ Truy cập biến Outer Class   │ Không thể truy cập              │ CÓ THỂ qua 'this@Outer'         │
 * │ Cách khởi tạo               │ Outer.Nested() (không cần cha)  │ outerInstance.Inner() (cần cha) │
 * └─────────────────────────────┴─────────────────────────────────┴─────────────────────────────────┘
 * - Nested Class tối ưu nhất cho: Adapter ViewHolder, DTO con, Builders, Helper logic độc lập.
 * - Inner Class tối ưu khi: Class con gắn bó mật thiết với trạng thái của một instance cha cụ thể.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
class Computer(val model: String) {
    private val serialNumber: String = "SN-998877"

    // 1. Nested class (Static mặc định):
    // Độc lập hoàn toàn, không thể đọc model hay serialNumber của instance Computer
    class Specs(val ramGb: Int, val cpuCores: Int) {
        fun printSpecs() {
            println("Specs: $ramGb GB RAM, $cpuCores Cores")
            // println(serialNumber) // LỖI BIÊN DỊCH: Không truy cập được outer instance!
        }
    }

    // 2. Inner class (Dùng từ khóa 'inner'):
    // Gắn liền với một instance Computer cụ thể, giữ tham chiếu đến nó
    inner class Motherboard(val chipset: String) {
        fun printInfo() {
            // Truy cập trực tiếp được serialNumber và model của outer class
            println("Motherboard chipset: $chipset cho máy $model (Serial: $serialNumber)")
        }

        fun getOuterModel(): String = this@Computer.model
    }
}

fun exampleNestedVsInner() {
    println("--- 14.1 Khởi tạo Nested Class (Không cần instance Outer) ---")
    val specs = Computer.Specs(ramGb = 32, cpuCores = 8)
    specs.printSpecs()

    println("\n--- 14.2 Khởi tạo Inner Class (Bắt buộc phải từ instance Outer) ---")
    val myPc = Computer("Dell XPS 15")
    val mb = myPc.Motherboard("Intel Z790")
    mb.printInfo()
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 14.1:
 * Thiết kế class [Department(val deptName: String)]:
 * - Tạo nested class [Policy(val code: String, val ruleDescription: String)]:
 *   + Cung cấp hàm `fun summary(): String` trả về "[Policy $code] $ruleDescription".
 * - Tạo inner class [Employee(val empId: String, val fullName: String)]:
 *   + Cung cấp hàm `fun introduce(): String` trả về:
 *     "Nhân viên $fullName (Mã: $empId) thuộc phòng ban ${this@Department.deptName}."
 */
class Department(val deptName: String) {
    class Policy(val code: String, val ruleDescription: String) {
        fun summary(): String = "[Policy $code] $ruleDescription"
    }

    inner class Employee(val empId: String, val fullName: String) {
        fun introduce(): String {
            return "Nhân viên $fullName (Mã: $empId) thuộc phòng ban ${this@Department.deptName}."
        }
    }
}

fun runPart14() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 14: NESTED VS INNER CLASS")
    println("==================================================")
//    exampleNestedVsInner()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    // Khởi tạo Nested Policy mà không cần instance Department
    val policy = Department.Policy("SEC-01", "Bảo mật thông tin nội bộ")
    check(policy.summary() == "[Policy SEC-01] Bảo mật thông tin nội bộ") { "Sai policy summary" }

    // Khởi tạo Inner Employee từ một department cụ thể
    val itDept = Department("Kỹ thuật phần mềm")
    val dev = itDept.Employee("EMP-101", "Trần Minh Nam")
    check(dev.introduce() == "Nhân viên Trần Minh Nam (Mã: EMP-101) thuộc phòng ban Kỹ thuật phần mềm.") {
        "Sai introduce: ${dev.introduce()}"
    }

    println("✅ Bài tập 14.1 (Department Nested Policy & Inner Employee): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 14!\n")
}

fun main() {
    runPart14()
}
