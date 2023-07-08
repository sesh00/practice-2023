class Algorithm(private val graph: Graph) {
    var traversalFirst: MutableList<Int> = mutableListOf()
    val traversalSecond: MutableList<Int> = mutableListOf()
    private lateinit var stack: MutableList<Int>

    private fun dfsUtil(vertex: Int) {
        traversalFirst.add(vertex)
        for (neighbor in graph.getNeighbors(vertex)) {
            if (neighbor !in traversalFirst) {
                dfsUtil(neighbor)
            }
        }
        stack.add(vertex)
    }

    private fun dfs(vertex: Int, result: MutableList<Int>, graph_: Graph) {
        traversalSecond.add(vertex)
        result.add(vertex)

        for (neighbor in graph_.getNeighbors(vertex)) {
            if (neighbor !in traversalSecond) {
                dfs(neighbor, result, graph_)
            }
        }
    }

    fun getComponents(): List<List<Int>> {
        stack = mutableListOf()
        val result: MutableList<List<Int>> = mutableListOf()

        for (vertex in graph.getVertices()) {
            if (vertex !in traversalFirst) {
                dfsUtil(vertex)
            }
        }

        val transposedGraph = graph.getTranspose()
        traversalFirst = stack.toMutableList()

        while (stack.isNotEmpty()) {
            val vertex = stack.removeAt(stack.size - 1)

            if (vertex !in traversalSecond) {
                val component = mutableListOf<Int>()
                dfs(vertex, component, transposedGraph)
                result.add(component)
            }
        }
        return result
    }
}
