import java.awt.*
import javax.swing.JPanel
import kotlin.math.*

class Panel : JPanel() {
    private lateinit var g2d: Graphics2D
    private val mouseHandler = MouseHandler(this)
    private val fontStyle = Font("Arial ", Font.BOLD, 20)

    var explanation: Explanations = Explanations.NOTEXT
    var vertices: MutableMap<Vertex, MutableSet<Vertex>> = mutableMapOf()

    init {
        addMouseListener(mouseHandler)
        addMouseMotionListener(mouseHandler)
        background = Color.WHITE
        layout = FlowLayout()
    }

    companion object {
        private const val ARROW_SIZE = 10
        private const val LINE_WIDTH = 2f
        private val VERTEX_COLOR = Color.BLACK
        private val START_VERTEX_COLOR = Color.PINK
        private val TEXT_COLOR = Color.BLACK
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g2d = g as? Graphics2D ?: return
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)


        mouseHandler.startVertex?.let { startVertex ->
            explanation = Explanations.CHOOSEVERTEX2
            val halfRad = startVertex.radius / 2
            g2d.color = START_VERTEX_COLOR
            g2d.fillOval(startVertex.x - halfRad, startVertex.y - halfRad, startVertex.radius, startVertex.radius)
        }

        vertices.forEach { (vertex, adjacencyList) ->
            if (vertex.x >= 0 && vertex.y >= 0) {
                with(g2d) {
                    stroke = BasicStroke(LINE_WIDTH)
                    color = VERTEX_COLOR
                    font = fontStyle
                    val halfRad = vertex.radius / 2

                    drawOval(vertex.x - halfRad, vertex.y - halfRad, vertex.radius, vertex.radius)

                    val letter = vertex.id.toString()
                    val fm = fontMetrics
                    val letterWidth = fm.stringWidth(letter)
                    val letterHeight = fm.height
                    val letterX = vertex.x - halfRad + (vertex.radius - letterWidth) / 2
                    val letterY = vertex.y - halfRad / 4 * 5 + (vertex.radius + letterHeight) / 2

                    color = TEXT_COLOR
                    drawString(letter, letterX, letterY)

                    adjacencyList.forEach { adjacencyVertex ->
                        drawEdge(vertex, adjacencyVertex)
                    }
                }
            }
        }
        drawExplanation()
        explanation = Explanations.NOTEXT
    }

    private fun drawExplanation() {
        with(g2d) {
            var x = 150
            var y = 20
            color = TEXT_COLOR
            font = Font("Arial", Font.BOLD, 14)

            val words: List<String> = explanation.text.split(" ")
            var line = StringBuilder()
            var maxWidth = getWidth() - 150

            for (word in words) {
                if (fontMetrics.stringWidth("$line $word") <= maxWidth) {
                    line.append(word).append(" ")

                } else {
                    drawString(line.toString(), x, y)
                    y += fontMetrics.height
                    line = StringBuilder("$word ")
                }
            }

            drawString(line.toString(), x, y)
        }
    }

    private fun drawEdge(vertex1: Vertex, vertex2: Vertex) {
        val dx = vertex2.x - vertex1.x
        val dy = vertex2.y - vertex1.y
        val angle = atan2(dy.toDouble(), dx.toDouble())

        val startEndpoint = calculateEndpoint(vertex1, angle, vertex1.radius / 2)
        val endEndpoint = calculateEndpoint(vertex2, angle, -vertex2.radius / 2)

        with(g2d) {
            drawLine(startEndpoint.x, startEndpoint.y, endEndpoint.x, endEndpoint.y)
            drawArrow(endEndpoint.x, endEndpoint.y, angle - Math.PI / 6)
            drawArrow(endEndpoint.x, endEndpoint.y, angle + Math.PI / 6)
        }
    }

    private fun calculateEndpoint(vertex: Vertex, angle: Double, radiusOffset: Int): Point {
        val x = vertex.x + radiusOffset * cos(angle)
        val y = vertex.y + radiusOffset * sin(angle)
        return Point(x.toInt(), y.toInt())
    }

    private fun Graphics2D.drawArrow(x: Int, y: Int, angle: Double) {
        val arrowSize = ARROW_SIZE
        val x1 = (x - arrowSize * cos(angle)).toInt()
        val y1 = (y - arrowSize * sin(angle)).toInt()
        drawLine(x, y, x1, y1)
    }

    fun removeAllPoints() {
        vertices = mutableMapOf()
        Vertex.idCounter = 1
        repaint()
    }

    private fun updateMouseHandler(isDrawingEdge: Boolean, isRemovingVertex: Boolean, isRemovingEdge: Boolean) {
        with(mouseHandler) {
            startVertex = null
            this.isDrawingEdge = isDrawingEdge
            this.isRemovingVertex = isRemovingVertex
            this.isRemovingEdge = isRemovingEdge
        }
    }

    fun addEdge() {
        if (vertices.size >= 2) {
            updateMouseHandler(
                isDrawingEdge = true,
                isRemovingVertex = false, isRemovingEdge = false
            )
            explanation = Explanations.CHOOSEVERTEX1
            repaint()
        }
    }

    fun removeVertex() {
        if (vertices.size >= 1) {
            updateMouseHandler(
                isDrawingEdge = false,
                isRemovingVertex = true, isRemovingEdge = false
            )
            explanation = Explanations.DELVERTEX
            repaint()
        }
    }

    fun removeEdge() {
        if (vertices.any { (_, value) -> value.size >= 1 }) {
            updateMouseHandler(
                isDrawingEdge = false,
                isRemovingEdge = true, isRemovingVertex = true
            )
            explanation = Explanations.CHOOSEVERTEX1
            repaint()
        }
    }

    fun arrangeVerticesInCircle() {
        explanation = Explanations.NOTEXT
        val radius = min(width, height) * 0.4
        val centerX = width / 2 + 50
        val centerY = height / 2

        val angleStep = 2 * PI / vertices.size
        var angle = 0.0

        for (vertex in vertices.keys) {
            val x = centerX + (radius * cos(angle)).toInt()
            val y = centerY + (radius * sin(angle)).toInt()
            vertex.x = x
            vertex.y = y

            angle += angleStep
        }

        repaint()
        explanation = Explanations.CHOOSEVERTEX1
        repaint()
        updateMouseHandler(isDrawingEdge = false,
            isRemovingEdge = true, isRemovingVertex = true)
    }
}
