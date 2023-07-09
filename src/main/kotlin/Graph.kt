import java.io.File
import kotlin.random.Random


class Graph {
    private val adjacencyList: MutableMap<Int, MutableList<Int>> = mutableMapOf()

    fun addVertex(vertex: Int) {
        adjacencyList.getOrPut(vertex) { mutableListOf() }
    }

    fun addEdge(source: Int, destination: Int) {
        adjacencyList.getOrPut(source) { mutableListOf() }.add(destination)
        adjacencyList.getOrPut(destination) { mutableListOf() }
    }

    fun removeVertex(vertex: Int) {
        adjacencyList.remove(vertex)
        adjacencyList.values.forEach { it.remove(vertex) }
    }

    fun removeEdge(source: Int, destination: Int) {
        adjacencyList[source]?.remove(destination)
    }

    fun getVertices(): Set<Int> {
        return adjacencyList.keys
    }

    fun getNeighbors(vertex: Int): List<Int> {
        return adjacencyList[vertex] ?: emptyList()
    }

     fun getTranspose(): Graph {
        val transposedGraph = Graph()

        for (vertex in adjacencyList.keys) {
            for (neighbor in adjacencyList[vertex]!!) {
                transposedGraph.addEdge(neighbor, vertex)
            }
        }

        return transposedGraph
    }

    fun getSize(): Int {
        return adjacencyList.size
    }


}


object GraphGenerator {
    private const val leftBorder = 20
    private const val rightBorder = 31

    private fun generateRandomList(): List<Pair<Int, Int>> {
        val n = Random.nextInt(leftBorder, rightBorder)
        val g = Random.nextInt(0, 3*n)
        val list = mutableListOf<Pair<Int, Int>>()

        for (i in 1..g) {
            val m = Random.nextInt(0, n)
            var k = Random.nextInt(0, n)

            while (k == m) {
                k = Random.nextInt(0, n)
            }

            val pair = Pair(m, k)
            list.add(pair)
        }

        return list
    }

    fun generateGraph(): MutableMap<Vertex, MutableSet<Vertex>> {
        val edgesList = generateRandomList()
        val verticesMap = mutableMapOf<Int, Vertex>()
        val vertices = mutableMapOf<Vertex, MutableSet<Vertex>>()

        edgesList.forEach { (source, destination)->
            verticesMap.getOrPut(source) { Vertex.createWithId(0, 0) }
            verticesMap.getOrPut(destination) { Vertex.createWithId(0, 0) }
            val vertex1 = verticesMap[source]!!
            val vertex2 = verticesMap[destination]!!

            vertices.getOrPut(vertex1) { mutableSetOf() }.add(vertex2)
            vertices.getOrPut(vertex2) { mutableSetOf() }
        }

        return vertices
    }
}
