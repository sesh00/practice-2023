import java.awt.*
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel


class Panel : JPanel() {
    var vertices: MutableList<Vertex> = mutableListOf()
    private val mouseHandler = MouseHandler(this)

    init {
        addMouseListener(mouseHandler)
        addMouseMotionListener(mouseHandler)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        vertices.forEach { vert ->
            if (vert.x >= 0 && vert.y >= 0) {
                val g2d = g as Graphics2D
                g2d.stroke = BasicStroke(2f)

                val halfRad = vert.radius/2
                g2d.color = Color.BLACK
                g2d.drawOval(vert.x - halfRad, vert.y - halfRad, vert.radius, vert.radius)

                val fontSize = 20
                g2d.font = Font("Arial ", Font.BOLD, fontSize)

                val letter = "22"
                val fm = g2d.fontMetrics
                val letterWidth = fm.stringWidth(letter)
                val letterHeight = fm.height

                val letterX = vert.x - halfRad + (vert.radius - letterWidth) / 2
                val letterY = vert.y - halfRad + (vert.radius + letterHeight) / 2

                g2d.color = Color.BLACK
                g2d.drawString(letter, letterX, letterY)
            }
        }
    }

    fun removeAllPoints() {
        vertices = mutableListOf()
        repaint()
    }
}

