import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Graphics
import javax.swing.JButton
import javax.swing.JFrame

class Main {

}

fun main() {
    val frame = JFrame("Panel Repaint on Mouse Click")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.contentPane.preferredSize = Dimension(300, 200)

    val panel = Panel()
    panel.background = Color.WHITE
    panel.layout = FlowLayout()
    frame.contentPane.add(panel)

    val clearButton = JButton("Очистить")
    clearButton.setSize(50, 20)
    clearButton.addActionListener {
        panel.removeAllPoints()
    }
    panel.add(clearButton)

    val addEdgeButton = JButton("Добавить ребро")
    addEdgeButton.setSize(50, 20)
    addEdgeButton.addActionListener {
        panel.addEdge()
    }
    panel.add(addEdgeButton)

    val removeVertexButton = JButton("Удалить вершину")
    removeVertexButton.setSize(50, 20)
    removeVertexButton.addActionListener {
        panel.removeVertex()
    }
    panel.add(removeVertexButton)

    frame.pack()
    frame.isVisible = true
}
