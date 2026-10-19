# KHO LUYỆN TẬP KOTLIN CORE (23 CHỦ ĐỀ)

Dự án này đã được cấu trúc thành **23 file riêng biệt** trong thư mục `src/topics/` tương ứng chính xác với 23 câu hỏi lý thuyết và thực hành cốt lõi của Kotlin.

Mỗi file bao gồm 3 phần chính:
1. **Lý thuyết tóm lược & Bản chất** (Đầy đủ theo 23 câu hỏi phỏng vấn/kiến thức chuẩn).
2. **Ví dụ minh họa chạy được** (`example...()`): Giải thích trực quan, in kết quả ra màn hình.
3. **Bài tập thực hành có test case tự động** (`check(...)`): Có bài tập tình huống thực tế, kèm hàm kiểm tra xem bạn đã giải đúng hay chưa.
4. **Hàm `main()` độc lập**: Bạn có thể mở từng file và nhấn nút **Run** (icon Play màu xanh ▶ trong IntelliJ IDEA / Android Studio) để chạy riêng từng bài.

---

## 📂 Danh Sách Các File Trong `src/topics/`

| Phần | File | Nội Dung Lý Thuyết & Thực Hành |
| :---: | :--- | :--- |
| **01** | [`Part01_KotlinVsJava.kt`](file:///d:/code/test/src/topics/Part01_KotlinVsJava.kt) | Mối quan hệ, tương thích 100% (Interoperability), Null Safety, Smart Cast, Conciseness. |
| **02** | [`Part02_PrimitiveTypes.kt`](file:///d:/code/test/src/topics/Part02_PrimitiveTypes.kt) | Kiểu dữ liệu nguyên thủy (Primitive), tối ưu Bytecode JVM, Boxing/Unboxing với `Int?`. |
| **03** | [`Part03_TypeInference.kt`](file:///d:/code/test/src/topics/Part03_TypeInference.kt) | Suy luận kiểu (Type Inference) và các trường hợp bắt buộc phải chỉ định kiểu rõ ràng. |
| **04** | [`Part04_NullSafety.kt`](file:///d:/code/test/src/topics/Part04_NullSafety.kt) | Thể hiện Null Safety: `T?`, Safe Call `?.`, Elvis `?:`, `!!`, `let`, Smart Cast null-check. |
| **05** | [`Part05_MutableVsImmutable.kt`](file:///d:/code/test/src/topics/Part05_MutableVsImmutable.kt) | Phân biệt `val` vs `var`, `List` vs `MutableList`, đóng gói an toàn (Encapsulation). |
| **06** | [`Part06_Constructors.kt`](file:///d:/code/test/src/topics/Part06_Constructors.kt) | Primary Constructor, khối `init`, Secondary Constructor và ủy quyền `: this(...)`. |
| **07** | [`Part07_AccessModifiers.kt`](file:///d:/code/test/src/topics/Part07_AccessModifiers.kt) | 4 phạm vi truy cập: `public` (mặc định), `private`, `protected`, `internal` (module). |
| **08** | [`Part08_ValVsConstVal.kt`](file:///d:/code/test/src/topics/Part08_ValVsConstVal.kt) | So sánh `val` (Runtime) vs `const val` (Compile-time Inlined Constant). |
| **09** | [`Part09_LateinitVsLazy.kt`](file:///d:/code/test/src/topics/Part09_LateinitVsLazy.kt) | So sánh `lateinit` (var, manual, isInitialized) vs `by lazy` (val, automatic, thread-safe). |
| **10** | [`Part10_CustomGetterSetter.kt`](file:///d:/code/test/src/topics/Part10_CustomGetterSetter.kt) | Custom Getter/Setter, Computed Properties, thu hẹp quyền `private set`. |
| **11** | [`Part11_BackingField.kt`](file:///d:/code/test/src/topics/Part11_BackingField.kt) | Từ khóa `field` (Backing Field), tại sao cần để tránh đệ quy vô hạn (StackOverflowError). |
| **12** | [`Part12_InterfaceVsClassProps.kt`](file:///d:/code/test/src/topics/Part12_InterfaceVsClassProps.kt) | So sánh thuộc tính trong Interface (không lưu state, không field) và Class. |
| **13** | [`Part13_EnumVsSealedClass.kt`](file:///d:/code/test/src/topics/Part13_EnumVsSealedClass.kt) | So sánh `enum class` (hằng số giá trị) và `sealed class` (quản lý phân cấp State/Event). |
| **14** | [`Part14_NestedVsInnerClass.kt`](file:///d:/code/test/src/topics/Part14_NestedVsInnerClass.kt) | Nested Class (Static ngầm, tránh memory leak) vs Inner Class (`inner`, `this@Outer`). |
| **15** | [`Part15_DataClass.kt`](file:///d:/code/test/src/topics/Part15_DataClass.kt) | Ưu điểm của Data Class: tự sinh `equals`, `hashCode`, `toString`, `copy`, `componentN`. |
| **16** | [`Part16_CopyAndEquality.kt`](file:///d:/code/test/src/topics/Part16_CopyAndEquality.kt) | Bản chất hàm `copy()`: Shallow Copy, Structural Equality (`==`) vs Referential (`===`). |
| **17** | [`Part17_HigherOrderFunctions.kt`](file:///d:/code/test/src/topics/Part17_HigherOrderFunctions.kt) | Higher-Order Functions (HOF): Hàm nhận hàm khác làm tham số hoặc trả về hàm. |
| **18** | [`Part18_LambdaVsHOF.kt`](file:///d:/code/test/src/topics/Part18_LambdaVsHOF.kt) | Lambda Expression (Giá trị) vs HOF (Hàm tiêu thụ), cú pháp Trailing Lambda và `it`. |
| **19** | [`Part19_ExtensionFunctions.kt`](file:///d:/code/test/src/topics/Part19_ExtensionFunctions.kt) | Extension Functions: Mở rộng tính năng không cần kế thừa, bản chất Static trong Bytecode. |
| **20** | [`Part20_ExtensionVsMemberConflict.kt`](file:///d:/code/test/src/topics/Part20_ExtensionVsMemberConflict.kt) | Xung đột cùng signature: Member Function luôn thắng (`Member always wins`). Overload. |
| **21** | [`Part21_ObjectKeyword.kt`](file:///d:/code/test/src/topics/Part21_ObjectKeyword.kt) | 3 vai trò của từ khóa `object`: Singleton Object, Companion Object, Anonymous Expression. |
| **22** | [`Part22_TailrecFunction.kt`](file:///d:/code/test/src/topics/Part22_TailrecFunction.kt) | Hàm đệ quy đuôi (`tailrec`): Tối ưu hóa biến đệ quy thành vòng lặp Bytecode, tránh tràn Stack. |
| **23** | [`Part23_ScopeFunctions.kt`](file:///d:/code/test/src/topics/Part23_ScopeFunctions.kt) | 5 Scope Functions (`let`, `run`, `with`, `apply`, `also`): Context Object (`it` vs `this`) và Return Value. |

---

## 🚀 Cách Chạy & Luyện Tập

### Cách 1: Chạy trực tiếp trong IDE (IntelliJ IDEA / Android Studio)
1. Mở bất kỳ file nào trong thư mục `src/topics/` (ví dụ: [`Part04_NullSafety.kt`](file:///d:/code/test/src/topics/Part04_NullSafety.kt)).
2. Nhấn vào biểu tượng **Play màu xanh (▶)** ở cạnh dòng `fun main()` ở cuối file.
3. Xem kết quả thực thi và làm bài tập.

### Cách 2: Chạy toàn bộ 23 phần qua file điều phối [`Main.kt`](file:///d:/code/test/src/Main.kt)
- Mở file [`Main.kt`](file:///d:/code/test/src/Main.kt) và chạy `fun main()`.
- Toàn bộ 23 chủ đề sẽ lần lượt chạy các hàm ví dụ minh họa và tự động kiểm tra test case bài tập.
