import java.io.File

object FileHandler {
    fun readGraphFromFile(filePath: String): MutableMap<Vertex, MutableSet<Vertex>>? {
        val vertices = mutableMapOf<Vertex, MutableSet<Vertex>>()
        val verticesMap = mutableMapOf<Int, Vertex>()
        val file = File(filePath)

        Vertex.idCounter = 1

        file.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val vertexId = line.split(" ").map {
                    try {
                        it.toInt()
                    } catch (e: NumberFormatException) {
                        return null
                    }
                }

                if (vertexId.size == 2 && vertexId[0] != vertexId[1]) {
                    verticesMap.getOrPut(vertexId[0]) { Vertex.createWithId(0, 0) }
                    verticesMap.getOrPut(vertexId[1]) { Vertex.createWithId(0, 0) }
                    val vertex1 = verticesMap[vertexId[0]]!!
                    val vertex2 = verticesMap[vertexId[1]]!!

                    vertices.getOrPut(vertex1) { mutableSetOf() }.add(vertex2)
                    vertices.getOrPut(vertex2) { mutableSetOf() }

                } else if (vertexId.size == 1) {
                    verticesMap.getOrPut(vertexId[0]) { Vertex.createWithId(0, 0) }
                    val vertex1 = verticesMap[vertexId[0]]!!
                    vertices.getOrPut(vertex1) { mutableSetOf() }
                } else
                    return null
            }
        }

        return vertices
    }
}


