import javax.swing.JButton
import javax.swing.JFileChooser

class GraphicalInterface(private val panel: Panel) {
    private val fileChooser = JFileChooser()

    fun setupButtons() {
        val clearButton = JButton("Очистить").apply {
            isFocusPainted = false
            addActionListener {
                panel.removeAllPoints()
            }
        }
        panel.add(clearButton)

        val addEdgeButton = JButton("Добавить ребро").apply {
            isFocusPainted = false
            addActionListener {
                panel.addEdge()
            }
        }
        panel.add(addEdgeButton)

        val removeVertexButton = JButton("Удалить вершину").apply {
            isFocusPainted = false
            addActionListener {
                panel.removeVertex()
            }
        }
        panel.add(removeVertexButton)

        val startAlgorithmButton = JButton("Вычислить").apply {
            isFocusPainted = false

        }
        panel.add(startAlgorithmButton)

        val getFileButton = JButton("Файл").apply {
            isFocusPainted = false
            addActionListener {
                chooseFile()
            }
        }
        panel.add(getFileButton)

        fileChooser.currentDirectory = java.io.File(".")
        fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
    }

    private fun chooseFile() {
        val returnValue = fileChooser.showOpenDialog(panel)
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile

        }
    }
}
