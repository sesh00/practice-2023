import java.io.File

object FileHandler {
    fun readGraphFromFile(filePath: String): MutableMap<Vertex, MutableSet<Vertex>> {
        val vertices = mutableMapOf<Vertex, MutableSet<Vertex>>()
        val verticesMap = mutableMapOf<Int, Vertex>()
        val file = File(filePath)

        file.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val vertexId = line.split(" ").map { it.toInt() }


                verticesMap.getOrPut(vertexId[0]) { Vertex(id = vertexId[0]) }
                verticesMap.getOrPut(vertexId[1]) { Vertex(id = vertexId[1]) }
                val vertex1 = verticesMap[vertexId[0]]!!
                val vertex2 = verticesMap[vertexId[1]]!!

                vertices.getOrPut(vertex1) { mutableSetOf() }.add(vertex2)
                vertices.getOrPut(vertex2) { mutableSetOf() }
            }
        }

        return vertices
    }
}


