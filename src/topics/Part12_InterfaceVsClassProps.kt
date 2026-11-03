package topics

/**
 * PHẦN 12: SO SÁNH THUỘC TÍNH TRONG INTERFACE VÀ CLASS
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Phân biệt bản chất thuộc tính giữa Interface và Class:
 *   + Interface: Thuộc tính hoàn toàn vô trạng thái (Stateless), không thể lưu dữ liệu trực tiếp trong bộ nhớ.
 *   + Class: Thuộc tính có trạng thái (Stateful), có Backing Field lưu trữ dữ liệu thực sự trong RAM.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Cho phép thiết kế hợp đồng (Contract) trừu tượng về cả hành vi lẫn cấu trúc thuộc tính mà class con phải tuân theo.
 * - Cho phép viết sẵn Default Implementation cho thuộc tính ngay trong Interface (bằng Custom Getter)
 *   mà không cần duplicate code ở từng class con.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * ┌─────────────────────────────┬─────────────────────────────────┬─────────────────────────────────┐
 * │ Tiêu chí                    │ Thuộc tính trong Interface      │ Thuộc tính trong Class          │
 * ├─────────────────────────────┼─────────────────────────────────┼─────────────────────────────────┤
 * │ Lưu trữ trạng thái (State)  │ KHÔNG thể lưu trữ state         │ CÓ thể lưu trữ state            │
 * │ Khởi tạo giá trị ( = value) │ KHÔNG thể gán: val x = 10 (LỖI) │ CÓ thể gán trực tiếp: val x = 10│
 * │ Backing Field               │ KHÔNG có Backing Field ('field')│ CÓ Backing Field (dùng 'field') │
 * │ Hình thức khai báo          │ Abstract hoặc có Custom Getter  │ Đầy đủ: abstract, open, custom  │
 * └─────────────────────────────┴─────────────────────────────────┴─────────────────────────────────┘
 * - Thuộc tính trong Interface tối ưu khi: Định nghĩa bản mẫu giao tiếp trừu tượng (Abstraction),
 *   cung cấp các logic phụ thuộc tính toán từ các thuộc tính con (`val fullName get() = "$firstName $lastName"`).
 * - Thuộc tính trong Class tối ưu khi: Cần nắm giữ dữ liệu cụ thể trong vòng đời của đối tượng.
 */

// ===============  ===========================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
interface NamedEntity {
    // 1. Thuộc tính trừu tượng: Class con bắt buộc phải override và cấp phát lưu trữ
    val firstName: String
    val lastName: String

    // 2. Thuộc tính có Custom Getter trong Interface:
    // KHÔNG dùng 'field', chỉ tính to  án dựa trên các thuộc tính khác
    val fullName: String
        get() = "$firstName $lastName"
}

class Customer(
    override val firstName: String,
    override val lastName: String
) : NamedEntity {
    // Thuộc tính trong Class: Có ô nhớ riêng (Backing Field)
    var email: String = "customer@example.com"
}

fun exampleInterfaceVsClassProps() {
    println("--- 12.1 Minh họa thuộc tính Interface vs Class ---")
    val customer = Customer("John", "Doe")
    println("Full name từ interface getter: ${customer.fullName}")
    println("Email lưu trữ trong class: ${customer.email}")
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 12.1:
 * Thiết kế interface [TaxableItem]:
 * - Khai báo abstract property `val price: Double` (giá gốc).
 * - Khai báo abstract property `val taxRate: Double` (tỷ lệ thuế, ví dụ 0.1 cho 10%).
 * - Khai báo computed property `val finalPrice: Double` ngay trong interface với custom getter:
 *   trả về `price * (1.0 + taxRate)`.
 *
 * Thiết kế class [ElectronicProduct] implement [TaxableItem]:
 * - Constructor nhận `override val price: Double` và `override val taxRate: Double = 0.1`.
 * - Thuộc tính trong class: `val warrantyMonths: Int = 12`.
 */
interface TaxableItem {
    val price: Double
    val taxRate: Double

    val finalPrice: Double
        get() = price * (1.0 + taxRate)
}

class ElectronicProduct(
    val name: String,
    override val price: Double,
    override val taxRate: Double = 0.1,
    val warrantyMonths: Int = 12
) : TaxableItem

fun runPart12() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 12: INTERFACE VS CLASS PROPS")
    println("==================================================")
    exampleInterfaceVsClassProps()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val laptop = ElectronicProduct("Laptop Pro", 1000.0, 0.1, 24)
    // Giá gốc: 1000.0, Thuế: 10% -> finalPrice = 1100.0
    check(laptop.finalPrice == 1100.0) { "Sai finalPrice: ${laptop.finalPrice}" }
    check(laptop.warrantyMonths == 24) { "Sai warrantyMonths" }
    println("✅ Bài tập 12.1 (TaxableItem & ElectronicProduct): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 12!\n")
}

fun main() {
    runPart12()
}
