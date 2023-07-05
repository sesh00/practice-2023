class Algorithm(private val numberOfVertices: Int) {
private val adjacencyList: MutableList<MutableList<Int>> = mutableListOf()

init {
	for (i in 0 until numberOfVertices) {
		adjacencyList.add(mutableListOf())
	}
}

fun addEdge(v: Int, w: Int) { //добавляем ребра
	adjacencyList[v].add(w)
}

fun getTranspose(): Algorithm { //трансонирование графа
	val transposedGraph = Algorithm(numberOfVertices)

	for (v in 0 until numberOfVertices) {
		for (w in adjacencyList[v]) {
			transposedGraph.addEdge(w, v)
		}
	}

	return transposedGraph
}


private fun DFSUtil(v: Int, visited: BooleanArray) { //обход в глубину
	visited[v] = true
	print("$v ")

	for (i in adjacencyList[v]) {
		if (!visited[i]) {
			DFSUtil(i, visited)
		}
	}
}

fun printSCCs() { //вывод компонент связности
	val stack = mutableListOf<Int>() //заводим стек
	val visited = BooleanArray(numberOfVertices) { false } //список посещенных вершин, по умолчанию false

	for (v in 0 until numberOfVertices) {
		if (!visited[v]) {
			fillOrder(v, visited, stack)
		}
	}

	val transposedGraph = getTranspose()

	for (i in 0 until numberOfVertices) {
		visited[i] = false
	}

	while (stack.isNotEmpty()) {	
		val v = stack.removeAt(stack.size - 1)

		if (!visited[v]) {
			transposedGraph.DFSUtil(v, visited)
			println()
		}
	}
}

private fun fillOrder(v: Int, visited: BooleanArray, stack: MutableList<Int>) { //обнавляем значение
	visited[v] = true

	for (i in adjacencyList[v]) {
		if (!visited[i]) {
			fillOrder(i, visited, stack)
		}
	}

	stack.add(v)
}
}
fun main() {
val graph = Algorithm(8)
graph.addEdge(0, 1)
graph.addEdge(1, 2)
graph.addEdge(2, 0)
graph.addEdge(3, 1)
graph.addEdge(3, 2)
graph.addEdge(4, 3)
graph.addEdge(5, 2)
graph.addEdge(7, 4)
graph.addEdge(7, 7)
graph.addEdge(7, 6)
graph.addEdge(5, 6)
graph.addEdge(4, 5)
graph.addEdge(3, 4)
graph.addEdge(6, 5)

println("Компоненты сильной связности:")
graph.printSCCs()
}