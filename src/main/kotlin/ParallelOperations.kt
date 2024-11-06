import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

class ParallelOperations {

    fun <T> parallelOperation(
        lines: List<String>,
        numTreads: Int,
        operationOperand: T,
        operation: suspend (lines: List<String>, operationOperand: T) -> (MutableList<T>),
    ) = runBlocking {

        val chunkSize = (lines.size + numTreads - 1) / numTreads

        val jobs = List(numTreads) { index ->
            launch {
                val start = index * chunkSize
                val end = minOf(start + chunkSize, lines.size)
                if (start < lines.size) {
                    operation(lines.subList(start, end), operationOperand)
                }
            }
        }

        jobs.forEach { it.join() }

    }

    fun <T> parallelOperation(
        lines: List<String>,
        numTreads: Int,
        operation: suspend (lines: List<String>) -> (MutableList<T>),
    ) = runBlocking {

        val chunkSize = (lines.size + numTreads - 1) / numTreads

        val jobs = List(numTreads) { index ->
            launch {
                val start = index * chunkSize
                val end = minOf(start + chunkSize, lines.size)
                if (start < lines.size) {
                    operation(lines.subList(start, end))
                }
            }
        }

        jobs.forEach { it.join() }

    }

    fun <T> parallelOperationNonUniform(
        lines: List<String>,
        numTreads: Int,
        operationOperand: T,
        distribution: List<Int>,
        operation: suspend (lines: List<String>, operationOperand: T) -> (MutableList<T>),
    ) = runBlocking {


        /*distribution.forEachIndexed { index, size ->
            val start = distribution.take(index).sum
            val end = start + size
        }*/

        val jobs = mutableListOf<Job>()
        var startIndex = 0

        for (size in distribution) {
            launch {
                val endIndex = startIndex + size
                if (startIndex < lines.size) {

                    val subList = lines.subList(startIndex, minOf(endIndex, lines.size))
                    operation(subList, operationOperand)
                }
                startIndex += size
            }.let { jobs.add(it) }
        }

        jobs.forEach { it.join() }

    }

}