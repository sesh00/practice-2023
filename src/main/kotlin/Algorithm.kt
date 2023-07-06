class Algorithm(private val graph: Graph) {
    private var visited: MutableMap<Int, Boolean> = mutableMapOf()
    private lateinit var stack: MutableList<Int>
    private lateinit var transposedGraph: Graph

    private fun dfsUtil(vertex: Int) {
        visited[vertex] = true
        for (neighbor in graph.getNeighbors(vertex)) {
            if (!visited[neighbor]!!) {
                dfsUtil(neighbor)
            }
        }

        stack.add(vertex)
    }

    private fun dfs(vertex: Int, result: MutableList<Int>, graph_: Graph) {
        visited[vertex] = true
        result.add(vertex)

        for (neighbor in graph_.getNeighbors(vertex)) {
            if (!visited[neighbor]!!) {
                dfs(neighbor, result, graph_)
            }
        }
    }

    fun getComponents(): List<List<Int>> {
        val vertices = graph.getVertices()
        visited = vertices.associateWith { false }.toMutableMap()
        stack = mutableListOf()
        val result: MutableList<List<Int>> = mutableListOf()

        for (vertex in graph.getVertices()) {
            if (!visited[vertex]!!) {
                dfsUtil(vertex)
            }
        }

        val transposedGraph = graph.getTranspose()

        visited = vertices.associateWith { false }.toMutableMap()

        while (stack.isNotEmpty()) {
            val vertex = stack.removeAt(stack.size - 1)

            if (!visited[vertex]!!) {
                val component = mutableListOf<Int>()
                dfs(vertex, component, transposedGraph)
                result.add(component)
            }
        }

        return result
    }
}
