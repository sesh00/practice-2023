class Algorithm(private val numberOfVertices: Int) {
private val adjacency_List: MutableList<MutableList<Int>> = mutableListOf()

init {
	for (i in 0 until numberOfVertices) {
		adjacency_List.add(mutableListOf())
	}
}


fun add_Edge(v: Int, w: Int) { //добавление ребра в список смежности
	adjacency_List[v].add(w)
}


fun get_Transpose(): Algorithm { //транспонирование графа
	val transposed_Graph = Algorithm(numberOfVertices)

	for (v in 0 until numberOfVertices) {
		for (w in adjacency_List[v]) {
			transposed_Graph.add_Edge(w, v)
		}
	}
	return transposed_Graph
}


private fun fill_Order(v: Int, visited: BooleanArray, stack: MutableList<Int>) { //обход в глубину с заполнением стека
	visited[v] = true
	for (i in adjacency_List[v]) {
		if (!visited[i]) {
			fill_Order(i, visited, stack)
		}
	}

	stack.add(v)
}


private fun DFS(v: Int, visited: BooleanArray) { //обход в глубину
	visited[v] = true
	print("$v ")
	for (i in adjacency_List[v]) {
		if (!visited[i]) {
			DFS(i, visited)
		}
	}
}


fun Kosaraju() { //вывод компонент связности
	val stack = mutableListOf<Int>() //заводим стек
	val visited = BooleanArray(numberOfVertices) { false } //список посещенных вершин, по умолчанию false

	for (v in 0 until numberOfVertices) {
		if (!visited[v]) {
			fill_Order(v, visited, stack)
		}
	}

	val transposed_Graph = get_Transpose()

	for (i in 0 until numberOfVertices) {
		visited[i] = false
	}

	while (stack.isNotEmpty()) {	
		val v = stack.removeAt(stack.size - 1)
		if (!visited[v]) {
			transposed_Graph.DFS(v, visited)
			println()
		}
	}
}
}


/*
fun main() {
val graph = Algorithm(8)
graph.add_Edge(0, 1)
graph.add_Edge(1, 2)
graph.add_Edge(2, 0)
graph.add_Edge(3, 1)
graph.add_Edge(3, 2)
graph.add_Edge(4, 3)
graph.add_Edge(5, 2)
graph.add_Edge(7, 4)
graph.add_Edge(7, 7)
graph.add_Edge(7, 6)
graph.add_Edge(5, 6)
graph.add_Edge(4, 5)
graph.add_Edge(3, 4)
graph.add_Edge(6, 5)

println("Компоненты сильной связности:")
graph.Kosaraju()
}
*/
