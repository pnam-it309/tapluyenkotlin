# KHO LUYỆN TẬP KOTLIN CORE (23 CHỦ ĐỀ TOÀN DIỆN)

Dự án này đã được cấu trúc thành **23 file riêng biệt** trong thư mục [`src/topics/`](file:///d:/code/test/src/topics/) tương ứng chính xác với 23 chủ đề lý thuyết, so sánh tối ưu và thực hành cốt lõi của Kotlin.

Mỗi file đã được chuẩn hóa và nâng cấp với đầy đủ các nội dung:
1. 🎯 **Tác dụng (Bản chất & Mục đích)**: Định nghĩa chính xác tính năng làm gì ở cấp độ ngôn ngữ & Bytecode JVM.
2. 💡 **Vì sao lại dùng đến nó**: Nỗi đau giải quyết được (triệt tiêu `NullPointerException`, `StackOverflowError`, Memory Leak, boilerplate code, race condition...).
3. ⚖️ **So sánh & Trường hợp tối ưu hơn**: Bảng đối chiếu các giải pháp thay thế, chỉ rõ trường hợp nào nên dùng cái nào để tối ưu hiệu năng và bộ nhớ RAM.
4. 💻 **Code mẫu so sánh trực quan** (`example...()`): In trực tiếp kết quả đối chiếu ra màn hình khi chạy.
5. 🧪 **Bài tập thực hành có test case tự động** (`check(...)`): Có bài tập tình huống thực tế, tự động assert kiểm tra kết quả.
6. 🚀 **Hàm `main()` độc lập**: Mở từng file và nhấn nút **Run ▶** trong IntelliJ IDEA / Android Studio để chạy riêng từng phần.

---

## 📂 Danh Sách 23 Chủ Đề Trong `src/topics/`

| Phần | File | Bản Chất, Vì Sao Dùng & So Sánh Tối Ưu |
| :---: | :--- | :--- |
| **01** | [`Part01_KotlinVsJava.kt`](file:///d:/code/test/src/topics/Part01_KotlinVsJava.kt) | Mối quan hệ, tương thích 100% (Interoperability), Null Safety, Smart Cast, Conciseness. Tối ưu hơn Java cho app mới. |
| **02** | [`Part02_PrimitiveTypes.kt`](file:///d:/code/test/src/topics/Part02_PrimitiveTypes.kt) | Kiểu dữ liệu nguyên thủy (Primitive), tối ưu Bytecode JVM. So sánh `Int` (Stack - 0 overhead) vs `Int?` (Heap wrapper). |
| **03** | [`Part03_TypeInference.kt`](file:///d:/code/test/src/topics/Part03_TypeInference.kt) | Suy luận kiểu tự động. So sánh khi nào nên suy luận (biến cục bộ) vs bắt buộc tường minh (Public API, Polymorphism). |
| **04** | [`Part04_NullSafety.kt`](file:///d:/code/test/src/topics/Part04_NullSafety.kt) | Triệt tiêu NPE: Safe Call `?.`, Elvis `?:` (Guard clause), `?.let`, Smart Cast vs cảnh báo nguy hiểm `!!`. |
| **05** | [`Part05_MutableVsImmutable.kt`](file:///d:/code/test/src/topics/Part05_MutableVsImmutable.kt) | `val` vs `var`, `List` vs `MutableList`. Mô hình Backing Property an toàn bao đóng (Encapsulation). |
| **06** | [`Part06_Constructors.kt`](file:///d:/code/test/src/topics/Part06_Constructors.kt) | Primary Constructor + Default Arguments (tối ưu 95%) vs Secondary Constructors (kế thừa Android View). |
| **07** | [`Part07_AccessModifiers.kt`](file:///d:/code/test/src/topics/Part07_AccessModifiers.kt) | 4 phạm vi: `public`, `private`, `protected`, `internal` (module-level) & `private set` trong State Management. |
| **08** | [`Part08_ValVsConstVal.kt`](file:///d:/code/test/src/topics/Part08_ValVsConstVal.kt) | `val` (Runtime) vs `const val` (Compile-time Inlined Constant). Tối ưu hiệu năng và yêu cầu cho Annotation. |
| **09** | [`Part09_LateinitVsLazy.kt`](file:///d:/code/test/src/topics/Part09_LateinitVsLazy.kt) | `lateinit var` (Manual init, DI / Lifecycle) vs `by lazy` (Val, On-demand automatic, Thread-safe). |
| **10** | [`Part10_CustomGetterSetter.kt`](file:///d:/code/test/src/topics/Part10_CustomGetterSetter.kt) | Computed Properties (tiết kiệm RAM) vs Hàm thường. Validate dữ liệu đầu vào trong setter. |
| **11** | [`Part11_BackingField.kt`](file:///d:/code/test/src/topics/Part11_BackingField.kt) | Từ khóa `field` (Backing Field), tại sao cần để tránh đệ quy vô hạn `StackOverflowError`. |
| **12** | [`Part12_InterfaceVsClassProps.kt`](file:///d:/code/test/src/topics/Part12_InterfaceVsClassProps.kt) | Thuộc tính Interface (Stateless, không `field`, làm Abstraction) vs Thuộc tính Class (Stateful, có `field`). |
| **13** | [`Part13_EnumVsSealedClass.kt`](file:///d:/code/test/src/topics/Part13_EnumVsSealedClass.kt) | `enum class` (Hằng số giá trị cố định) vs `sealed class` (Phân cấp State/Event động cho MVI/MVVM). |
| **14** | [`Part14_NestedVsInnerClass.kt`](file:///d:/code/test/src/topics/Part14_NestedVsInnerClass.kt) | Nested Class (Static ngầm, tránh rò rỉ bộ nhớ Memory Leak) vs Inner Class (`inner`, `this@Outer`). |
| **15** | [`Part15_DataClass.kt`](file:///d:/code/test/src/topics/Part15_DataClass.kt) | Tự sinh `equals`, `hashCode`, `toString`, `copy`, `componentN`. So sánh Data Class (DTO/State) vs Regular Class. |
| **16** | [`Part16_CopyAndEquality.kt`](file:///d:/code/test/src/topics/Part16_CopyAndEquality.kt) | Bản chất `copy()` là Shallow Copy, Structural Equality (`==`) vs Referential Equality (`===`). |
| **17** | [`Part17_HigherOrderFunctions.kt`](file:///d:/code/test/src/topics/Part17_HigherOrderFunctions.kt) | Higher-Order Functions (HOF), Functional Types `(T) -> R`, tối ưu hóa 0 overhead bộ nhớ với từ khóa `inline`. |
| **18** | [`Part18_LambdaVsHOF.kt`](file:///d:/code/test/src/topics/Part18_LambdaVsHOF.kt) | Lambda (Giá trị biểu thức) vs HOF (Hàm tiêu thụ), Trailing Lambda, `it` ngầm định vs đặt tên tường minh. |
| **19** | [`Part19_ExtensionFunctions.kt`](file:///d:/code/test/src/topics/Part19_ExtensionFunctions.kt) | Mở rộng tính năng không cần kế thừa, bản chất Static Dispatch trong Bytecode, thay thế Utility class. |
| **20** | [`Part20_ExtensionVsMemberConflict.kt`](file:///d:/code/test/src/topics/Part20_ExtensionVsMemberConflict.kt) | Quy tắc phân giải xung đột: **Member Function luôn luôn chiến thắng (`Member always wins`)**. Overloading. |
| **21** | [`Part21_ObjectKeyword.kt`](file:///d:/code/test/src/topics/Part21_ObjectKeyword.kt) | 3 vai trò của `object`: Singleton Object (Thread-safe), Companion Object (Factory method), Anonymous Expression. |
| **22** | [`Part22_TailrecFunction.kt`](file:///d:/code/test/src/topics/Part22_TailrecFunction.kt) | Đệ quy đuôi (`tailrec`): Tự động biến đệ quy thành vòng lặp Bytecode, đưa độ phức tạp Call Stack về O(1). |
| **23** | [`Part23_ScopeFunctions.kt`](file:///d:/code/test/src/topics/Part23_ScopeFunctions.kt) | 5 Scope Functions (`apply`, `also`, `let`, `run`, `with`): Ngữ cảnh (`this` vs `it`) & Giá trị trả về. |

---

## 🚀 Cách Chạy & Luyện Tập

### Cách 1: Chạy trực tiếp trong IDE (IntelliJ IDEA / Android Studio)
1. Mở bất kỳ file nào trong thư mục `src/topics/` (ví dụ: [`Part04_NullSafety.kt`](file:///d:/code/test/src/topics/Part04_NullSafety.kt)).
2. Nhấn vào biểu tượng **Play màu xanh (▶)** ở cạnh dòng `fun main()` ở cuối file.
3. Xem kết quả so sánh lý thuyết & code chạy thực tế in ra console.

### Cách 2: Chạy toàn bộ 23 phần qua file điều phối [`Main.kt`](file:///d:/code/test/src/Main.kt)
- Mở file [`Main.kt`](file:///d:/code/test/src/Main.kt) và chạy `fun main()`.
- Toàn bộ 23 chủ đề sẽ lần lượt thực thi các ví dụ minh họa và tự động kiểm tra test case bài tập.
