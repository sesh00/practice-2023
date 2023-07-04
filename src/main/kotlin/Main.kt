import java.awt.Dimension
import javax.swing.JFrame

class Main {
    fun run() {
        val frame = JFrame("Panel Repaint on Mouse Click")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane.preferredSize = Dimension(300, 200)

        val panel = Panel()
        frame.contentPane.add(panel)

        val graphicalInterface = GraphicalInterface(panel)
        graphicalInterface.setupButtons()

        frame.pack()
        frame.isVisible = true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Main().run()
        }
    }
}

