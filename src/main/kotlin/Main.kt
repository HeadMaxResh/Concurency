import MathOperations.Companion.factorial
import MathOperations.Companion.fibonacci
import MathOperations.Companion.multiply
import MathOperations.Companion.pow
import kotlinx.coroutines.runBlocking
import java.io.File


val PARALLEL_OPERATIONS = ParallelOperations()

fun main() = runBlocking {

    //File.generateFile(8)
    //File.parallelOperation(DEFAULT_DIRECTORY + 29, 4, 10_000_000, ::multiply)

    /*val parallelOp: ParallelOperations = ParallelOperations()

    val nValues = listOf(10, 100, 1000, 10_000, 100_000, 1_000_000 , 10_000_000, 100_000_000)
    val mValues = listOf(1, 2, 3, 4, 5, 10, 20, 30, 100)
    val distribution = listOf(1, 6, 4, 10)

    println("N\tM\tTime (ms)")
    for (n in nValues) {
        val filePath = "C:/Users/m2002/Desktop/$n.txt"
        File.generateFile(filePath, n)

        for (m in mValues) {

            val timeTaken = measureTime {

                val lines = File.readFile(filePath)

                parallelOp.parallelOperation(lines, m, n, ::multiply)

            }

            val time = timeTaken / 1_000_000.0
            println("$n\t$m\t${time}")
        }
    }*/

    while (true) {
        val filePath = selectFileAction() ?: continue
        val lines = CustomFile.readFile(filePath)

        println("Введите количество потоков для обработки (по умолчанию: 1):")
        val numThreadsInput = readLine()?.toIntOrNull() ?: 1

        if (!performMathOperation(lines, numThreadsInput)) {
            continue
        }
    }

}

fun selectFileAction(): String? {
    println("Выберите действие:\n1. Сгенерировать новый файл\n2. Использовать существующий файл")

    return when (readLine()?.toIntOrNull()) {
        1 -> {
            println("Введите количество значений для обработки:")
            val count = readLine()?.toIntOrNull() ?: run {
                println("Неверный ввод. Попробуйте снова.")
                return null
            }

            println("Введите путь для сохранения файла (по умолчанию: C:/Users/m2002/Desktop):")
            val filePathInput = readLine()
            val filePath = filePathInput?.takeIf { it.isNotBlank() } ?: "C:/Users/m2002/Desktop/$count.txt"
            CustomFile.generateFile(filePath, count)
            filePath
        }
        2 -> {
            println("Введите путь к существующему файлу:")
            val filePath = readLine()?.takeIf { it.isNotBlank() } ?: run {
                println("Путь не может быть пустым. Попробуйте снова.")
                return null
            }
            val file = File(filePath)
            if (!file.exists() || !file.isFile) {
                println("Файл по указанному пути не найден или это не файл. Попробуйте снова.")
                return null
            }
            if (!isFileContentValid(file)) {
                println(
                    "Содержимое файла некорректно. " +
                    "Убедитесь, что файл содержит только числа по одному на строку. " +
                    "Попробуйте снова."
                )
                return null
            }
            filePath
        }
        else -> {
            println("Неверный выбор действия. Попробуйте снова.")
            null
        }
    }
}

fun performMathOperation(lines: List<String>, numThreadsInput: Int): Boolean {
    while (true) {
        println("Выберите математическую операцию:\n1. Умножение\n2. Возведение в квадрат\n3. Факториал\n4. Фибоначчи")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Введите множитель:")
                val multiplier = readLine()?.toIntOrNull() ?: run {
                    println("Неверный ввод. Попробуйте снова.")
                    return false
                }
                val timeTaken = measureTime {
                    PARALLEL_OPERATIONS.parallelOperation(lines, numThreadsInput, multiplier, ::multiply)
                }
                println("Время выполнения: ${timeTaken / 1_000_000.0} мс")
                return true
            }
            2 -> {
                val timeTaken = measureTime {
                    PARALLEL_OPERATIONS.parallelOperation(lines, numThreadsInput, ::pow)
                }
                println("Время выполнения: ${timeTaken / 1_000_000.0} мс")
                return true
            }
            3 -> {
                val timeTaken = measureTime {
                    PARALLEL_OPERATIONS.parallelOperation(lines, numThreadsInput, ::factorial)
                }
                println("Время выполнения: ${timeTaken / 1_000_000.0} мс")
                return true
            }
            4 -> {
                val timeTaken = measureTime {
                    PARALLEL_OPERATIONS.parallelOperation(lines, numThreadsInput, ::fibonacci)
                }
                println("Время выполнения: ${timeTaken / 1_000_000.0} мс")
                return true
            }
            else -> {
                println("Неверный выбор операции. Попробуйте снова.")
            }
        }
    }
}

fun measureTime(block: () -> Unit): Long {
    val startTime = System.nanoTime()
    block()
    return System.nanoTime() - startTime
}

fun isFileContentValid(file: File): Boolean {
    if (file.length() == 0L) {
        return false
    }
    return file.readLines().all { line ->
        line.trim().toIntOrNull() != null
    }
}