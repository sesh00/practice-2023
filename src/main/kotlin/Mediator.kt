import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JFrame


class Mediator {
    lateinit var panel: Panel
    private lateinit var componentsList: List<List<Int>>
    private lateinit var algorithm: Algorithm
    private var graph = Graph()
    private val vertexMap: MutableMap<Int, Vertex> = mutableMapOf()
    private var currentState: State = State.NONE

    enum class State {
        NONE,
        DFS1,
        DFS2
    }


    fun run() {
        val frame = JFrame("Graph Manipulator")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane.preferredSize = Dimension(600, 450)
        frame.minimumSize = Dimension(600, 450)

        panel = Panel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        frame.contentPane.add(panel)

        val graphicalInterface = GraphicalInterface(panel, this)
        graphicalInterface.setupButtons()

        frame.pack()
        frame.isVisible = true
    }

    fun startShow(){
        panel.sccList = mutableListOf()
        panel.vertices.keys.forEach { key ->  key.bypassNumber = null }
        panel.explanation = Explanations.START

        panel.vertices.forEach{(k, v) -> vertexMap[k.id!!] = k}
        panel.vertices.forEach { (k, v) ->
            v.forEach { n ->
                graph.addEdge(k.id!!, n.id!!)
            }
            if (v.isEmpty()) graph.addVertex(k.id!!)
        }
        algorithm = Algorithm(graph)
        componentsList = algorithm.getComponents()
    }

    fun nextStep(){
        when (currentState) {
            State.NONE -> {
                currentState = State.DFS1
                nextStep()
            }
            State.DFS1 -> {
                panel.explanation = Explanations.DFS
                val visitedCount = panel.visited.size
                if (visitedCount < algorithm.traversalFirst.size) {
                    panel.visited = getVertexList(algorithm.traversalFirst.subList(0, visitedCount + 1))
                    panel.visited.last().bypassNumber = visitedCount + 1
                } else {
                    panel.vertices = panel.transposeGraph(panel.vertices)
                    currentState = State.DFS2
                    panel.visited = mutableListOf()
                    panel.explanation = Explanations.TRANSPOSEGRAPH
                }
                panel.repaint()
            }
            State.DFS2 -> {
                panel.explanation = Explanations.DFSONTRANSPOSE
                val visitedCount = panel.visited.size
                if (visitedCount < algorithm.traversalSecond.size) {
                    val visited = algorithm.traversalSecond.subList(0, visitedCount + 1)
                    panel.visited = getVertexList(visited)
                    val currentComponents = mutableListOf<MutableList<Vertex>>()
                    for (component in componentsList) {
                        if (visited.containsAll(component))
                            currentComponents.add(getVertexList(component))
                    }
                    panel.sccList = currentComponents

                } else {
                    panel.vertices = panel.transposeGraph(panel.vertices)
                    currentState = State.NONE
                    panel.visited = mutableListOf()

                    panel.disableButtons()
                    panel.disableMouseListener(true)
                    getResult()
                }
                panel.repaint()
            }
        }

    }

    fun getResult() {
        panel.explanation = Explanations.RESULT
        panel.vertices.keys.forEach { key ->  key.bypassNumber = null }

        if (currentState == State.DFS2) {
            panel.vertices = panel.transposeGraph(panel.vertices)
            currentState = State.NONE
        }
        val components = mutableListOf<MutableList<Vertex>>()
        for (component in componentsList) {
            components.add(getVertexList(component))
        }
        panel.sccList = components
        panel.repaint()
        panel.visited = mutableListOf()
        graph = Graph()
    }

    private fun getVertexList(idList: List<Int>): MutableList<Vertex> {
        val vertices = mutableListOf<Vertex>()
        for (id in idList)
            vertexMap[id]?.let { vertices.add(it) }
        return vertices
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Mediator().run()
        }
    }
}

