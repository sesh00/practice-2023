import java.awt.*
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel


class Vertex(var x: Int, var y: Int) {
    fun containsPoint(point: Point): Boolean {
        val vertexBounds = Rectangle(x - 5, y - 5, 10, 10)
        return vertexBounds.contains(point)
    }
}

class MyPanel : JPanel() {
    private var vertices: MutableList<Vertex> = mutableListOf()
    private var draggedVertex: Vertex? = null
    private var dragOffsetX = 0
    private var dragOffsetY = 0

    init {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                val clickedPoint = e.point
                var check = false
                for (vertex in vertices)
                    if (vertex.containsPoint(clickedPoint)) {
                        draggedVertex = vertex
                        dragOffsetX = clickedPoint.x - vertex.x
                        dragOffsetY = clickedPoint.y - vertex.y
                        check = true
                        break
                    }
                if (!check) {
                    vertices.add(Vertex(e.x, e.y))
                    repaint()
                }
            }
            override fun mouseReleased(e: MouseEvent) {
                draggedVertex = null
            }
        })
        addMouseMotionListener(object : MouseAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                if (draggedVertex != null) {
                    draggedVertex?.x = e.x - dragOffsetX
                    draggedVertex?.y = e.y - dragOffsetY
                    repaint()
                }
            }
        })
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        vertices.forEach { vert ->
            if (vert.x >= 0 && vert.y >= 0) {
                g.color = Color.BLACK
                g.fillOval(vert.x - 5, vert.y - 5, 10, 10)
            }
        }
    }

    fun removeAllPoints() {
        vertices = mutableListOf()
        repaint()
    }
}

fun main() {
    val frame = JFrame("Panel Repaint on Mouse Click")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.contentPane.preferredSize = Dimension(300, 200)

    val panel = MyPanel()
    panel.background = Color.WHITE
    frame.contentPane.add(panel)

    val button = JButton("Очистить")
    button.setSize(50, 20)
    button.addActionListener {
        panel.removeAllPoints()
        panel.repaint()
    }

    panel.layout = FlowLayout()
    panel.add(button)

    frame.pack()
    frame.isVisible = true
}
