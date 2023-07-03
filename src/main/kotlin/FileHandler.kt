import java.io.File

class FileHandler {
    fun readGraphFromFile(filename: String): Graph {
        val graph = Graph()

        val lines = File(filename).readLines()
        for (line in lines) {
            val vertices = line.split(" ").map { it.toInt() }
            val source = vertices[0]
            val destination = vertices[1]
            graph.addEdge(source, destination)
        }

        return graph
    }
}
