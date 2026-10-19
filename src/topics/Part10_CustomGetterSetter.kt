package topics

/**
 * PHẦN 10: CUSTOM GET VÀ SET TRONG KOTLIN
 *
 * Lý thuyết:
 * - Trong Kotlin, mỗi property (thuộc tính) khi khai báo đều tự động sinh ra
 *   getter (và setter nếu là 'var').
 * - Kotlin cho phép tùy chỉnh logic khi ĐỌC (getter) hoặc GHI (setter):
 *   + 'val' là read-only nên chỉ có thể có custom getter, không thể có setter.
 *   + 'var' có thể có cả custom getter và custom setter.
 *   + Setter có thể thay đổi phạm vi truy cập (ví dụ 'private set' để chỉ nội bộ class được gán).
 *   + Từ khóa 'field' đại diện cho Backing Field (nơi lưu trữ giá trị thực tế trong RAM).
 */

// ==========================================
// 1. VÍ DỤ MINH HỌA (EXAMPLES)
// ==========================================
class Rectangle(val width: Int, val height: Int) {
    // Thuộc tính tính toán (Computed Property): Không tốn RAM lưu trữ, tính mỗi khi get
    val area: Int
        get() = width * height

    val isSquare: Boolean
        get() = width == height

    // Thuộc tính có Backing Field và Custom Setter để validate dữ liệu
    var score: Int = 0
        set(value) {
            println("  -> Đang cập nhật score thành $value")
            if (value >= 0) {
                field = value // 'field' trỏ vào Backing Field thực tế
            } else {
                println("  [Cảnh báo] Điểm không thể âm, giữ nguyên giá trị cũ: $field")
            }
        }
}

// ==========================================
// 2. BÀI TẬP THỰC HÀNH (EXERCISES)
// ==========================================

/**
 * BÀI TẬP 10.1:
 * Thiết kế class [Temperature]:
 * - Khai báo thuộc tính `var celsius: Double` khởi tạo bằng 0.0:
 *   + Custom setter: Nhiệt độ không thể thấp hơn độ không tuyệt đối (-273.15°C).
 *     Nếu value < -273.15, ném IllegalArgumentException("Nhiệt độ không thể nhỏ hơn -273.15 độ C").
 *     Nếu hợp lệ, gán `field = value`.
 * - Khai báo thuộc tính `var fahrenheit: Double`:
 *   + Custom getter: Chuyển đổi từ celsius sang fahrenheit theo công thức:
 *     F = (celsius * 9.0 / 5.0) + 32.0
 *   + Custom setter: Chuyển đổi từ fahrenheit sang celsius rồi cập nhật lại celsius:
 *     C = (value - 32.0) * 5.0 / 9.0
 */
class Temperature(initialCelsius: Double = 0.0) {
    var celsius: Double = 0.0
        set(value) {
            require(value >= -273.15) { "Nhiệt độ không thể nhỏ hơn -273.15 độ C" }
            field = value
        }

    init {
        celsius = initialCelsius
    }

    var fahrenheit: Double
        get() = (celsius * 9.0 / 5.0) + 32.0
        set(value) {
            celsius = (value - 32.0) * 5.0 / 9.0
        }
}

fun runPart10() {
    println("==================================================")
    println("   CHẠY THỰC HÀNH PHẦN 10: CUSTOM GET VÀ SET")
    println("==================================================")
    val rect = Rectangle(10, 20)
    println("Diện tích: ${rect.area}")
    println("Hình vuông? ${rect.isSquare}")

    rect.score = 50
    rect.score = -10 // Sẽ bị chặn
    println("Score hiện tại: ${rect.score}")

    println("\n--- KIỂM TRA BÀI TẬP ---")
    val temp = Temperature(0.0) // 0 độ C = 32 độ F
    check(temp.fahrenheit == 32.0) { "Sai fahrenheit ở 0C: ${temp.fahrenheit}" }

    temp.fahrenheit = 212.0 // 212 F = 100 C
    check(kotlin.math.abs(temp.celsius - 100.0) < 0.001) { "Sai celsius khi set 212F: ${temp.celsius}" }

    var exceptionTriggered = false
    try {
        temp.celsius = -300.0 // Quá độ không tuyệt đối
    } catch (e: IllegalArgumentException) {
        exceptionTriggered = true
    }
    check(exceptionTriggered) { "Chưa chặn được nhiệt độ < -273.15" }
    println("✅ Bài tập 10.1 (Temperature C/F getter & setter): Đạt chuẩn!")
    println("🎉 Hoàn thành xuất sắc Phần 10!\n")
}

fun main() {
    runPart10()
}
