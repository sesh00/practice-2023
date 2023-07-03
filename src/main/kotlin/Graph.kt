class Graph {
    private val adjacencyList: MutableMap<Int, MutableList<Int>> = mutableMapOf()

    fun addVertex(vertex: Int) {
        adjacencyList[vertex] = mutableListOf()
    }

    fun addEdge(source: Int, destination: Int) {
        adjacencyList[source]?.add(destination)
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

    fun getEdges(): List<Pair<Int, Int>> {
        val edges = mutableListOf<Pair<Int, Int>>()
        adjacencyList.forEach { (source, destinations) ->
            destinations.forEach { destination ->
                edges.add(Pair(source, destination))
            }
        }
        return edges
    }

    fun getNeighbors(vertex: Int): List<Int> {
        return adjacencyList[vertex] ?: emptyList()
    }

     fun getTranspose(): Graph {
        val transposedGraph = Graph()

        for (vertex in 0 until adjacencyList.size) {
            for (neighbor in adjacencyList[vertex]!!) {
                transposedGraph.addEdge(neighbor, vertex)
            }
        }

        return transposedGraph
    }

    fun getSize(): Int {
        return adjacencyList.size
    }


    override fun toString(): String {
        val stringBuilder = StringBuilder()

        adjacencyList.forEach { (source, destinations) ->
            stringBuilder.append("$source -> ")
            stringBuilder.append(destinations.joinToString(", "))
            stringBuilder.append("\n")
        }

        return stringBuilder.toString()
    }
}
