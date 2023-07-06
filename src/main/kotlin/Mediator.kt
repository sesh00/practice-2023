import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JFrame


class Mediator {
    lateinit var panel: Panel
    private var graph = Graph()
    private val vertexMap: MutableMap<Int, Vertex> = mutableMapOf()
    private var currentState: State = State.NONE

    enum class State {
        NONE,
        TRANSPOSED,
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

    }

    fun nextStep(){
        when (currentState) {
            State.NONE -> {
                //val transposedGraph = graph.getTranspose()


            }
            State.TRANSPOSED -> {


            }
            State.DFS1 -> {



            }
            State.DFS2 -> {



            }
        }

    }

    fun getResult() {
        val algorithm = Algorithm(graph)
        val componentsList = algorithm.getComponents()
        val components = mutableListOf<MutableList<Vertex>>()
        for (component in componentsList) {
            components.add(mutableListOf())
            for (vertexId in component)
                vertexMap[vertexId]?.let { components.last().add(it) }
        }
        panel.sccList = components
        panel.repaint()
        graph = Graph()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Mediator().run()
        }
    }
}

