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
                val visitedCount = panel.visited.size
                if (visitedCount < algorithm.traversalFirst.size) {
                    val visited = getVertexList(algorithm.traversalFirst.subList(0, visitedCount + 1))
                    panel.visited = visited
                } else {
                    panel.vertices = panel.transposeGraph(panel.vertices)
                    currentState = State.DFS2
                    panel.visited = mutableListOf()
                }
                panel.repaint()
            }
            State.DFS2 -> {
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

