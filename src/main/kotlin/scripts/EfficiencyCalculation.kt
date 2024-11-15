package scripts

import file.CustomFile
import operations.MathOperations.Companion.multiply
import operations.ParallelOperations

fun efficiencyCalculation() {
    val parallelOp: ParallelOperations = ParallelOperations()

    val nValues = listOf(10, 100, 1000, 10_000, 100_000, 1_000_000 , 10_000_000, 100_000_000)
    val mValues = listOf(1, 2, 3, 4, 5, 10, 20, 30, 100)
    val distribution = listOf(1, 6, 4, 10)

    println("N\tM\tTime (ms)")
    for (n in nValues) {
        val filePath = "C:/Users/m2002/Desktop/$n.txt"
        CustomFile.generateFile(filePath, n)

        for (m in mValues) {

            val timeTaken = measureTime {

                val lines = CustomFile.readFile(filePath)

                parallelOp.parallelOperation(lines, m, n, ::multiply)

            }

            val time = timeTaken / 1_000_000.0
            println("$n\t$m\t${time}")
        }
    }
}
