import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
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
