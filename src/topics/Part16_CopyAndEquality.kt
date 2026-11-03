package topics

/**
 * PHẦN 16: HÀM COPY() VÀ QUAN HỆ BÌNH ĐẲNG (== VS ===)
 *
 * 🎯 1. TÁC DỤNG (BẢN CHẤT & MỤC ĐÍCH):
 * - Hàm `copy()` cho phép tạo bản sao mới của một Data Class với khả năng tùy biến các trường mong muốn.
 * - Phân định rõ ràng giữa hai toán tử so sánh:
 *   1. `==` (Structural Equality): So sánh cấu trúc nội dung, tương đương gọi `equals()`.
 *   2. `===` (Referential Equality): So sánh tham chiếu địa chỉ ô nhớ RAM trên Heap.
 *
 * 💡 2. VÌ SAO LẠI DÙNG ĐẾN NÓ (GIẢI QUYẾT VẤN ĐỀ GÌ):
 * - Đóng vai trò hạt nhân trong kiến trúc hướng Unidirectional Data Flow (MVI, Redux, Jetpack Compose):
 *   Thay vì thay đổi trực tiếp (mutate) state cũ, ta tạo ra một object state mới thông qua `copy()`
 *   để hệ sinh thái nhận biết và trigger render lại UI mà không có race-condition.
 *
 * ⚖️ 3. SO SÁNH & TRƯỜNG HỢP SỬ DỤNG TỐI ƯU HƠN:
 * - Bản chất Shallow Copy của `copy()`:
 *   + `copy()` chỉ sao chép nông các giá trị nguyên thủy. Với các tham chiếu lồng nhau (như `MutableList`, object con),
 *     bản mới và bản cũ vẫn trỏ chung vào cùng một đối tượng trong bộ nhớ!
 *   + *Tối ưu*: Luôn dùng các thuộc tính Immutable (`List` thay vì `MutableList`) bên trong Data Class
 *     để tránh bị đột biến dữ liệu ngoài ý muốn khi dùng `copy()`.
 * - `==` vs `===`:
 *   + *`==`*: Tối ưu và chuẩn mực cho so sánh logic nghiệp vụ dữ liệu.
 *   + *`===`*: Tối ưu cho kiểm tra hiệu năng (Fast-path optimization) trong UI Diffing: Nếu 2 con trỏ bằng nhau (`===`),
 *     bỏ qua hoàn toàn việc duyệt so sánh các trường con.
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
data class UserSession(val userId: String, val authToken: String, val permissions: MutableList<String>)

fun exampleCopyAndEquality() {
    println("--- 16.1 Quan hệ giữa bản gốc và bản copy() ---")
    val original = UserSession("USER_01", "TOKEN_ABC", mutableListOf("READ"))

    // Tạo bản sao mới nhưng giữ nguyên thuộc tính
    val cloned = original.copy()

    // Tạo bản sao mới và thay đổi authToken
    val modified = original.copy(authToken = "TOKEN_XYZ")

    println("original == cloned  : ${original == cloned} (TRUE - cùng giá trị thuộc tính)")
    println("original === cloned : ${original === cloned} (FALSE - 2 ô nhớ khác nhau)")

    println("original == modified: ${original == modified} (FALSE - khác token)")

    println("\n--- 16.2 Bản chất Shallow Copy với đối tượng tham chiếu ---")
    // Cả 2 cùng trỏ tới 1 danh sách permissions trong bộ nhớ:
    cloned.permissions.add("WRITE")
    println("original permissions sau khi cloned sửa: ${original.permissions}")
    // Kết quả: original cũng bị thêm "WRITE"! Đó chính là Shallow Copy.
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

data class ProductItem(val id: String, val name: String, val price: Double, val inStock: Boolean)

/**
 * BÀI TẬP 16.1:
 * Viết hàm [applyDiscountAndVerify] nhận vào `product: ProductItem` và `discountPercentage: Double`.
 * Yêu cầu:
 * - Dùng hàm `copy()` để tạo bản sao mới với giá mới đã giảm:
 *   newPrice = price * (1.0 - discountPercentage / 100.0)
 * - Kiểm tra (assertion):
 *   + Bản sao mới `newProduct !== product` (địa chỉ ô nhớ phải khác nhau).
 *   + Nếu discountPercentage == 0.0 thì `newProduct == product` (structural equal).
 *   + Nếu discountPercentage > 0.0 thì `newProduct != product`.
 * - Trả về `newProduct`.
 */
fun applyDiscountAndVerify(product: ProductItem, discountPercentage: Double): ProductItem {
    val newPrice = product.price * (1.0 - discountPercentage / 100.0)
    val discountedProduct = product.copy(price = newPrice)

    // Kiểm tra tính chất ô nhớ và cấu trúc
    check(discountedProduct !== product) { "Lỗi: copy() phải tạo ra instance mới ở ô nhớ khác" }
    if (discountPercentage == 0.0) {
        check(discountedProduct == product) { "Lỗi: Không giảm giá thì 2 object phải equal ==" }
    } else {
        check(discountedProduct != product) { "Lỗi: Khác giá thì 2 object không thể equal ==" }
    }

    return discountedProduct
}

fun runPart16() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 16: COPY() VÀ EQUALITY")
    println("==================================================")
    exampleCopyAndEquality()

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val p = ProductItem("P01", "Màn hình 4K", 500.0, true)

    val discounted = applyDiscountAndVerify(p, 10.0) // Giảm 10% -> 450.0
    check(discounted.price == 450.0) { "Sai giá sau giảm" }

    val unchanged = applyDiscountAndVerify(p, 0.0)
    check(unchanged.price == 500.0 && unchanged == p && unchanged !== p) { "Sai kiểm tra unchanged" }

    println("✅ Bài tập 16.1 (copy() immutability & equality): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 16!\n")
}

fun main() {
    runPart16()
}
