package topics

/**
 * PHẦN 7: ACCESS MODIFIERS (TỪ KHÓA PHẠM VI TRUY CẬP) TRONG KOTLIN
 *
 * Lý thuyết:
 * 1. public (Mặc định trong Kotlin, khác với package-private mặc định của Java):
 *    - Có thể truy cập từ bất kỳ đâu trong toàn bộ project.
 * 2. private:
 *    - Cấp độ Class: Chỉ có thể truy cập bên trong thân của chính Class đó.
 *    - Cấp độ Top-level (ngoài class): Chỉ có thể truy cập bên trong cùng File .kt đó.
 * 3. protected:
 *    - Giống 'private', nhưng cho phép các Class con kế thừa (subclasses) truy cập được.
 *    - Lưu ý: Không thể đặt 'protected' ở cấp độ Top-level (vì file không có tính kế thừa).
 * 4. internal:
 *    - Khái niệm riêng của Kotlin: Chỉ có thể truy cập bên trong CÙNG MỘT MODULE biên dịch
 *      (ví dụ: cùng một module Gradle, Maven, hoặc IntelliJ Module).
 *    - Rất hữu dụng khi làm thư viện: Giấu implementation details không để bên ngoài module gọi.
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
interface gioitinh{
    fun them()
    fun sua()
}
class nam : gioitinh{
    override fun them() {
        val name : String? = null
        val age : Int? = null


    }
    override fun sua() {}
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

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val admin = AdminAccount("admin_root", "pass123456")
    check(admin.username == "admin_root") { "Sai username" }
    check(admin.getRoleForAudit() == "ADMIN") { "Sai role" }
    check(admin.verifyPassword("pass123456")) { "Sai verifyPassword đúng" }
    check(!admin.verifyPassword("sai_mat_khau")) { "Sai verifyPassword sai" }

    println("✅ Bài tập 7.1 (EmployeeAccount vs AdminAccount Modifiers): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 7!\n")
}
class mua(secretVin: Any) : car {
    val a = secretVin
}
fun main() {
    runPart07()
}
