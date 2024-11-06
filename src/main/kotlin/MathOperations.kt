import java.io.File

class MathOperations {

    companion object {

        /*fun multiply(filePath: String, multiplier: Int): MutableList<Int> {
            val results = mutableListOf<Int>()

            File(filePath).forEachLine { line ->
                val number = line.toInt()
                val result = number * multiplier
                results.add(result.toString().toInt())
                println(result)
            }

            return results

            File(filePath).bufferedWriter().use { out ->
                for (i in results) {
                    out.write("$i\n")
                }
            }

        }*/

        fun <T : Number> multiply(lines: List<String>, multiplier: T): MutableList<T> {
            val results = mutableListOf<T>()

            for (line in lines) {
                val number = line.toInt()
                val result = calculateMultiply(number, multiplier)
                results.add(result)
                //println(result)
            }

            return results
        }

        private fun <T : Number> calculateMultiply(number: Int, multiplier: T): T {
            return when (multiplier) {
                is Int -> (number * multiplier.toInt()) as T
                is Double -> (number * multiplier.toDouble()) as T
                is Float -> (number * multiplier.toFloat()) as T
                is Long -> (number * multiplier.toLong()) as T
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }

        fun pow(lines: List<String>): MutableList<Long> {
            val results = mutableListOf<Long>()

            for (line in lines) {
                val number = line.toLong()
                val result = number * number
                results.add(result)
                //println(result)
            }

            return results
        }

        private fun calculateFactorial(n: Int): Int {
            /*return if (n <= 1) 1 else n * calculateFactorial(n - 1)*/
            if (n < 0) throw IllegalArgumentException("Факториал не определен для отрицательных чисел.")
            var result = 1
            for (i in 2..n) {
                result *= i
            }
            return result
        }

        fun factorial(lines: List<String>): MutableList<Int> {
            val results = mutableListOf<Int>()

            for (line in lines) {
                val number = line.toInt()
                val result = calculateFactorial(number)
                results.add(result)
                //println(result)
            }

            return results
        }

        private fun calculateFibonacci(n: Int): Long {
            if (n <= 1) return n.toLong()
            var a = 0L
            var b = 1L
            for (i in 2..n) {
                val temp = a + b
                a = b
                b = temp
            }
            return b
        }

        fun fibonacci(lines: List<String>): MutableList<Long> {
            val results = mutableListOf<Long>()

            for (line in lines) {
                val number = line.toInt()
                val result = calculateFibonacci(number)
                results.add(result)
                //println(result)
            }
            return results
        }
    }

}