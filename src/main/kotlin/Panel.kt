import java.awt.*
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel


class Panel : JPanel() {
    var vertices: MutableMap<Vertex, MutableSet<Vertex>> = mutableMapOf()
    private val mouseHandler = MouseHandler(this)

    init {
        addMouseListener(mouseHandler)
        addMouseMotionListener(mouseHandler)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2d = g as Graphics2D
        if (mouseHandler.startVertex != null) {
            val vert = mouseHandler.startVertex!!
            val halfRad = vert.radius / 2
            g2d.color = Color.PINK
            g2d.fillOval(vert.x - halfRad, vert.y - halfRad, vert.radius, vert.radius)
        }

        vertices.forEach { vert, adjacencyList ->
            if (vert.x >= 0 && vert.y >= 0) {
                g2d.stroke = BasicStroke(2f)

                val halfRad = vert.radius/2
                g2d.color = Color.BLACK
                g2d.drawOval(vert.x - halfRad, vert.y - halfRad, vert.radius, vert.radius)

                val fontSize = 20
                g2d.font = Font("Arial ", Font.BOLD, fontSize)

                val letter = vert.id.toString()
                val fm = g2d.fontMetrics
                val letterWidth = fm.stringWidth(letter)
                val letterHeight = fm.height

                val letterX = vert.x - halfRad + (vert.radius - letterWidth) / 2
                val letterY = vert.y - halfRad / 4 * 5 + (vert.radius + letterHeight) / 2

                g2d.color = Color.BLACK
                g2d.drawString(letter, letterX, letterY)
                for (adjacencyVert in adjacencyList) {
                    drawEdge(g2d, vert, adjacencyVert)
                }
            }
        }
    }

    fun drawEdge(g: Graphics, vertex1: Vertex, vertex2: Vertex) {
        val arrowSize = 10
        val dx = vertex2.x - vertex1.x
        val dy = vertex2.y - vertex1.y
        val angle = Math.atan2(dy.toDouble(), dx.toDouble())

        val delta1X = vertex1.radius / 2 * Math.cos(angle)
        val delta1Y = vertex1.radius / 2 * Math.sin(angle)
        val startX = vertex1.x + delta1X
        val startY = vertex1.y + delta1Y

        val delta2X = vertex2.radius / 2 * Math.cos(angle)
        val delta2Y = vertex2.radius / 2 * Math.sin(angle)
        val endX = vertex2.x - delta2X
        val endY = vertex2.y - delta2Y

        g.drawLine(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt())

        val x1 = (endX - arrowSize * Math.cos(angle - Math.PI / 6)).toInt()
        val y1 = (endY - arrowSize * Math.sin(angle - Math.PI / 6)).toInt()
        val x2 = (endX - arrowSize * Math.cos(angle + Math.PI / 6)).toInt()
        val y2 = (endY - arrowSize * Math.sin(angle + Math.PI / 6)).toInt()
        g.drawLine(endX.toInt(), endY.toInt(), x1, y1)
        g.drawLine(endX.toInt(), endY.toInt(), x2, y2)
    }

    fun removeAllPoints() {
        vertices = mutableMapOf()
        repaint()
    }

    fun addEdge() {
        mouseHandler.startVertex = null
        mouseHandler.isDrawingEdge = true
        mouseHandler.isRemovingVertex = false
    }

    fun removeVertex() {
        mouseHandler.isRemovingVertex = true
        mouseHandler.isDrawingEdge = false
    }
}

