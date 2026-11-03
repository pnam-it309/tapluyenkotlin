package topics

/**
 * PHẦN 7: ACCESS MODIFIERS (TỪ KHÓA PHẠM VI TRUY CẬP) TRONG KOTLIN
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Kiểm soát phạm vi nhìn thấy (Visibility) của class, object, constructor, hàm và thuộc tính:
 *   1. `public` (Mặc định trong Kotlin): Truy cập từ mọi nơi.
 *   2. `internal`: Giới hạn truy cập trong cùng một Module biên dịch (Gradle/Maven module).
 *   3. `protected`: Chỉ class hiện tại và các class con kế thừa được phép truy cập.
 *   4. `private`: Chỉ class hiện tại (nếu trong class) hoặc chỉ file hiện tại (nếu ở top-level) được truy cập.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Bảo vệ tính toàn vẹn và tính bao đóng (Encapsulation) của phần mềm.
 * - Khắc phục điểm yếu *package-private* của Java (dễ bị class cùng package bên ngoài chọc vào).
 * - `internal` giải quyết hoàn hảo bài toán giấu implementation details trong kiến trúc đa module (Multi-module Architecture)
 *   hoặc khi đóng gói SDK/Thư viện chia sẻ cho người khác.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - `internal` vs `public`:
 *   + *Tối ưu nhất*: Luôn đặt `internal` cho các helper classes, database entities, network adapters bên trong module.
 *     Chỉ mở `public` cho các Interfaces, Domain Models và Entry-points mà tầng bên ngoài thực sự cần dùng.
 * - `private set`:
 *   + *Tối ưu tuyệt đối*: Khi cần cho bên ngoài đọc thuộc tính nhưng cấm bên ngoài ghi giá trị (`var counter: Int = 0; private set`).
 *     Tránh phải viết getter/setter thủ công như Java.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================

// Top-level private: chỉ file này nhìn thấy
private val FILE_SECRET = "TOP_SECRET_IN_FILE"

open class BaseVehicle {
    public val brand: String = "Toyota"       // Bất kỳ ai cũng thấy
    internal val engineModel: String = "V8"    // Cùng module thấy
    protected val secretVin: String = "VIN123" // Chỉ class này và class con thấy
    private val assemblyKey: String = "KEY001" // Chỉ BaseVehicle thấy

    fun inspect() {
        println("Trong BaseVehicle: brand=$brand, engine=$engineModel, vin=$secretVin, key=$assemblyKey")
    }
}

class Car : BaseVehicle() {
    fun showDetails() {
        println("Trong Car: brand=$brand, engine=$engineModel, vin=$secretVin")
        // println(assemblyKey) // LỖI BIÊN DỊCH: assemblyKey là private ở BaseVehicle
    }
}

fun exampleOptimalPrivateSet() {
    println("--- 7.1 So sánh tối ưu: private set trong State Management ---")
    class CounterViewModel {
        // Cho phép đọc tự do từ bên ngoài, nhưng chỉ nội bộ ViewModel mới được tăng/giảm
        var count: Int = 0
            private set

        fun increment() { count++ }
    }

    val vm = CounterViewModel()
    vm.increment()
    println("Giá trị đọc công khai: ${vm.count}")
    // vm.count = 10 // LỖI BIÊN DỊCH: Cannot assign to 'count': the setter is private in 'CounterViewModel'
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 7.1:
 * Thiết kế hệ thống bảo mật tài khoản cho nhân viên:
 * - Tạo abstract class [EmployeeAccount] gồm:
 *   + `val username: String` (public)
 *   + `protected var role: String` (chỉ class con sửa/đọc được)
 *   + `private val passwordHash: String` (chỉ class cha giữ, không class con nào sửa được)
 *   + Hàm `fun verifyPassword(rawInput: String): Boolean` (public): kiểm tra hash `rawInput.hashCode().toString() == passwordHash`
 * - Tạo class con [AdminAccount(username: String, initialPassword: String)]:
 *   + Trong constructor hoặc init, tự động gán `role = "ADMIN"`.
 *   + Cung cấp hàm `fun getRoleForAudit(): String` trả về `role`.
 */
abstract class EmployeeAccount(
    val username: String,
    rawPassword: String,
    initialRole: String
) {
    protected var role: String = initialRole
    private val passwordHash: String = rawPassword.hashCode().toString()

    fun verifyPassword(rawInput: String): Boolean {
        return rawInput.hashCode().toString() == passwordHash
    }
}

class AdminAccount(username: String, initialPassword: String) : EmployeeAccount(
    username = username,
    rawPassword = initialPassword,
    initialRole = "ADMIN"
) {
    fun getRoleForAudit(): String {
        // Có thể truy cập 'role' vì nó là protected
        return role
    }
}

fun runPart07() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 7: ACCESS MODIFIERS")
    println("==================================================")
    val car = Car()
    car.showDetails()
    println("Đọc FILE_SECRET bên trong file: $FILE_SECRET")
    exampleOptimalPrivateSet()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val admin = AdminAccount("admin_root", "pass123456")
    check(admin.username == "admin_root") { "Sai username" }
    check(admin.getRoleForAudit() == "ADMIN") { "Sai role" }
    check(admin.verifyPassword("pass123456")) { "Sai verifyPassword đúng" }
    check(!admin.verifyPassword("sai_mat_khau")) { "Sai verifyPassword sai" }

    println("✅ Bài tập 7.1 (EmployeeAccount vs AdminAccount Modifiers): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 7!\n")
}
fun main() {
    runPart07()
}
