package topics

/**
 * PHẦN 6: CONSTRUCTORS TRONG KOTLIN (PRIMARY & SECONDARY)
 *
 * Lý thuyết:
 * 1. Primary Constructor:
 *    - Nằm ngay trên header của Class declaration: class User(val name: String, val age: Int = 18)
 *    - Chỉ có TỐI ĐA 1 Primary Constructor.
 *    - Có thể khai báo trực tiếp 'val'/'var' ở tham số để tạo luôn thuộc tính (property).
 *    - Không chứa code thực thi trực tiếp; mọi logic khởi tạo phải đặt trong khối 'init { ... }'.
 * 2. Secondary Constructor:
 *    - Khai báo bên trong body của class bằng từ khóa 'constructor(...)'.
 *    - Có thể có NHIỀU Secondary Constructor với các danh sách tham số khác nhau.
 *    - Chứa code thực thi trực tiếp bên trong cặp ngoặc nhọn.
 *    - BẮT BUỘC phải ủy quyền (delegate) về Primary Constructor bằng từ khóa ': this(...)'
 *      nếu class đã khai báo Primary Constructor.
 *
 * TIP HIỆN ĐẠI: Trong Kotlin, nhờ có "Default Argument Values" (tham số mặc định),
 * chúng ta hiếm khi cần Secondary Constructor so với Java!
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
class Person(val name: String) { // Primary Constructor
    var age: Int = 0
    var email: String = ""

    init {
        // Khối init chạy ngay khi Primary Constructor được gọi
        require(name.isNotBlank()) { "Tên không được để trống!" }
    }

    // Secondary Constructor 1: Nhận name và age
    constructor(name: String, age: Int) : this(name) {
        this.age = age
    }

    // Secondary Constructor 2: Nhận name, age và email
    constructor(name: String, age: Int, email: String) : this(name, age) {
        this.email = email
    }
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 6.1:
 * Thiết kế class [BankAccount] mô tả tài khoản ngân hàng:
 * - Primary Constructor: nhận `val accountNumber: String`, `val ownerName: String`,
 *   và `val initialBalance: Double = 0.0`.
 * - Thuộc tính `balance: Double`: khởi tạo bằng `initialBalance`.
 * - Khối `init`:
 *   + Kiểm tra `accountNumber` và `ownerName` không được rỗng/blank (nếu không ném IllegalArgumentException).
 *   + Kiểm tra `initialBalance` không được âm (< 0.0).
 * - Secondary Constructor:
 *   + Nhận `accountNumber: String`, `ownerName: String`, và `initialBalanceInVnd: Long` (tiền Việt).
 *   + Quy đổi sang USD theo tỷ giá giả định: 1 USD = 25000 VND (tức là balance = VND / 25000.0)
 *   + Bắt buộc delegate về Primary Constructor thông qua `: this(...)`.
 */
//class BankAccount(
//    val accountNumber: String,
//    val ownerName: String,
//    initialBalance: Double = 0.0
//) {
//    var balance: Double = initialBalance
//        private set
//
//    init {
//        require(accountNumber.isNotBlank()) { "Số tài khoản không được rỗng" }
//        require(ownerName.isNotBlank()) { "Tên chủ tài khoản không được rỗng" }
//        require(initialBalance >= 0.0) { "Số dư ban đầu không được âm" }
//    }
//
//    // Secondary constructor quy đổi từ VND
//    constructor(accountNumber: String, ownerName: String, initialBalanceInVnd: Long) : this(
//        accountNumber = accountNumber,
//        ownerName = ownerName,
//        initialBalance = initialBalanceInVnd.toDouble() / 25000.0
//    )
//
//    fun deposit(amount: Double) {
//        require(amount > 0) { "Số tiền nạp phải lớn hơn 0" }
//        balance += amount
//    }
//}

class acc(
    val number: String,
    val email: String,
    tien : Double = 0.0
){
    var balance : Double = tien
    private set
    init {
        require(number.isNotBlank()) { "ko duocwj rooxng " }
        require(email.isNotBlank()) { "ko duocwj rooxng " }

    }
}

fun runPart06() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 6: CONSTRUCTORS TRONG KOTLIN")
    println("==================================================")
    val p1 = Person("Alice")
    val p2 = Person("Bob", 25)
    val p3 = Person("Charlie", 30, "charlie@example.com")
    println("p1: ${p1.name}, age: ${p1.age}")
    println("p2: ${p2.name}, age: ${p2.age}")
    println("p3: ${p3.name}, age: ${p3.age}, email: ${p3.email}")

    println("\n--- KIỂM TRA BÀI TẬP ---")
//    // Test Primary Constructor với giá trị mặc định
//    val acc1 = BankAccount("ACC-001", "Nguyễn Văn A")
//    check(acc1.balance == 0.0) { "Sai số dư mặc định của acc1" }
//
//    // Test Primary Constructor có nạp số dư ban đầu
//    val acc2 = BankAccount("ACC-002", "Trần Thị B", 100.0)
//    check(acc2.balance == 100.0) { "Sai số dư acc2" }
//
//    // Test Secondary Constructor (VND sang USD)
//    val acc3 = BankAccount("ACC-003", "Lê Văn C", 2500000L) // 2.500.000 VND = 100.0 USD
//    check(kotlin.math.abs(acc3.balance - 100.0) < 0.001) { "Sai quy đổi từ VND: ${acc3.balance}" }
//
//    // Test validation
//    var validationPassed = false
//    try {
//        BankAccount("", "Invalid")
//    } catch (e: IllegalArgumentException) {
//        validationPassed = true
//    }
//    check(validationPassed) { "Chưa chặn được accountNumber rỗng" }

    println("✅ Bài tập 6.1 (BankAccount Primary & Secondary Constructor): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 6!\n")
}

fun main() {
    runPart06()
}
