import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class CustomFile {

    companion object {

        private const val DEFAULT_DIRECTORY = "C:/Users/m2002/Desktop"

        fun generateFile(filePath: String, countNumbers: Int): File? {
            try {
                File(filePath).bufferedWriter().use { out ->
                    for (i in 1..countNumbers) {
                        out.write("$i\n")
                    }
                }
                println("Файл '$filePath' успешно создан.")
                return File(filePath)
            } catch (e: FileNotFoundException) {
                println("Ошибка: Файл '$filePath' не найден.")
            } catch (e: IOException) {
                println("Ошибка: Не удалось записать в файл '$filePath'. ${e.message}")
            } catch (e: Exception) {
                println("Неизвестная ошибка: ${e.message}")
            }
            return null
        }

        fun generateFile(countNumbers: Int): File {
            val filePath = DEFAULT_DIRECTORY + "/" + Math.random().times(100).toInt()
            File(filePath).bufferedWriter().use { out ->
                for (i in 1..countNumbers) {
                    out.write("$i\n")
                }
            }
            println("Файл '$filePath' успешно создан.")
            return File(filePath)
        }

        fun readFile(filePath: String): List<String> {
            return File(filePath).readLines()
        }

    }

}
