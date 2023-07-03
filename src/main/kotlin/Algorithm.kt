class Algorithm(private val graph: Graph) {

    private fun dfsUtil(vertex: Int, visited: BooleanArray, stack: MutableList<Int>) {
        visited[vertex] = true

        for (neighbor in graph.getNeighbors(vertex)) {
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited, stack)
            }
        }

        stack.add(vertex)
    }


    private fun dfsPrint(vertex: Int, visited: BooleanArray, graph_: Graph) {
        visited[vertex] = true
        print("$vertex ")

        for (neighbor in graph_.getNeighbors(vertex)) {
            if (!visited[neighbor]) {
                dfsPrint(neighbor, visited, graph_)
            }
        }
    }

    fun printStronglyConnectedComponents() {
        val vertices = graph.getSize()
        val visited = BooleanArray(vertices) { false }
        val stack = mutableListOf<Int>()

        for (vertex in 0 until vertices) {
            if (!visited[vertex]) {
                dfsUtil(vertex, visited, stack)
            }
        }

        val transposedGraph = graph.getTranspose()

        visited.fill(false)

        while (stack.isNotEmpty()) {
            val vertex = stack.removeAt(stack.size - 1)

            if (!visited[vertex]) {
                dfsPrint(vertex, visited, transposedGraph)
                println()
            }
        }
    }
}
