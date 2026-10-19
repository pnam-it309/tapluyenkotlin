import topics.*
import java.util.Scanner

/**
 * CHƯƠNG TRÌNH TỔNG HỢP LUYỆN TẬP KOTLIN CORE (23 PHẦN)
 *
 * Mỗi phần tương ứng với 1 file trong thư mục `src/topics/`:
 *  - Phần 01: [Part01_KotlinVsJava.kt]
 *  - Phần 02: [Part02_PrimitiveTypes.kt]
 *  - Phần 03: [Part03_TypeInference.kt]
 *  - Phần 04: [Part04_NullSafety.kt]
 *  - Phần 05: [Part05_MutableVsImmutable.kt]
 *  - Phần 06: [Part06_Constructors.kt]
 *  - Phần 07: [Part07_AccessModifiers.kt]
 *  - Phần 08: [Part08_ValVsConstVal.kt]
 *  - Phần 09: [Part09_LateinitVsLazy.kt]
 *  - Phần 10: [Part10_CustomGetterSetter.kt]
 *  - Phần 11: [Part11_BackingField.kt]
 *  - Phần 12: [Part12_InterfaceVsClassProps.kt]
 *  - Phần 13: [Part13_EnumVsSealedClass.kt]
 *  - Phần 14: [Part14_NestedVsInnerClass.kt]
 *  - Phần 15: [Part15_DataClass.kt]
 *  - Phần 16: [Part16_CopyAndEquality.kt]
 *  - Phần 17: [Part17_HigherOrderFunctions.kt]
 *  - Phần 18: [Part18_LambdaVsHOF.kt]
 *  - Phần 19: [Part19_ExtensionFunctions.kt]
 *  - Phần 20: [Part20_ExtensionVsMemberConflict.kt]
 *  - Phần 21: [Part21_ObjectKeyword.kt]
 *  - Phần 22: [Part22_TailrecFunction.kt]
 *  - Phần 23: [Part23_ScopeFunctions.kt]
 */
fun main(args: Array<String>) {
    val runners = mapOf<Int, Pair<String, () -> Unit>>(
        1 to Pair("Kotlin vs Java & Ưu điểm vượt trội", ::runPart01),
        2 to Pair("Kiểu dữ liệu nguyên thủy (Primitive Types)", ::runPart02),
        3 to Pair("Type Inference (Suy luận kiểu)", ::runPart03),
        4 to Pair("Thể hiện của Null Safety", ::runPart04),
        5 to Pair("Mutable vs Immutable", ::runPart05),
        6 to Pair("Primary & Secondary Constructors", ::runPart06),
        7 to Pair("Access Modifiers", ::runPart07),
        8 to Pair("So sánh val vs const val", ::runPart08),
        9 to Pair("lateinit vs by lazy", ::runPart09),
        10 to Pair("Custom Getter & Setter", ::runPart10),
        11 to Pair("Backing Field & Infinite Recursion", ::runPart11),
        12 to Pair("Thuộc tính Interface vs Class", ::runPart12),
        13 to Pair("Enum Class vs Sealed Class", ::runPart13),
        14 to Pair("Nested Class vs Inner Class", ::runPart14),
        15 to Pair("Ưu điểm của Data Class", ::runPart15),
        16 to Pair("copy() & Equality (== vs ===)", ::runPart16),
        17 to Pair("Higher-Order Function", ::runPart17),
        18 to Pair("Lambda vs Higher-Order Function", ::runPart18),
        19 to Pair("Extension Function", ::runPart19),
        20 to Pair("Extension vs Member Precedence", ::runPart20),
        21 to Pair("Từ khóa object (Singleton, Companion, Anonymous)", ::runPart21),
        22 to Pair("Hàm đệ quy đuôi (tailrec)", ::runPart22),
        23 to Pair("5 Scope Functions (let, run, with, apply, also)", ::runPart23)
    )

    // Nếu truyền đối số từ dòng lệnh (ví dụ: java -jar app.jar all hoặc 1)
    if (args.isNotEmpty()) {
        val arg = args[0].trim().lowercase()
        if (arg == "all") {
            println("=== CHẠY TOÀN BỘ 23 PHẦN LÝ THUYẾT & BÀI TẬP KOTLIN CORE ===\n")
            runners.toSortedMap().forEach { (part, pair) ->
                pair.second.invoke()
            }
            println("🎉🎉 TẤT CẢ 23 PHẦN ĐÃ CHẠY VÀ KIỂM TRA THÀNH CÔNG! 🎉🎉")
            return
        }
        val partNum = arg.toIntOrNull()
        if (partNum != null && runners.containsKey(partNum)) {
            runners[partNum]?.second?.invoke()
            return
        }
    }

    // Chạy mặc định toàn bộ để kiểm tra tự động
    println("======================================================================")
    println("          CHƯƠNG TRÌNH LUYỆN TẬP KOTLIN CORE - 23 CHỦ ĐỀ")
    println("======================================================================")
    runners.toSortedMap().forEach { (part, pair) ->
        pair.second.invoke()
    }
    println("======================================================================")
    println("   TẤT CẢ 23 PHẦN ĐÃ ĐƯỢC THỰC THI VÀ VƯỢT QUA TEST CASE THỰC HÀNH!")
    println("======================================================================")
    println("Mẹo: Bạn có thể mở từng file trong 'src/topics/PartXX_*.kt' để sửa code,")
    println("làm bài tập hoặc nhấn nút Run (phím Play màu xanh) trên từng file riêng biệt!")
}