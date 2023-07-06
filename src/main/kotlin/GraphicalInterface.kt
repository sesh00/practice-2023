import javax.swing.Box
import javax.swing.JButton
import javax.swing.JFileChooser

class GraphicalInterface(private val panel: Panel,
                         private val mediator: Mediator) {
    private val fileChooser = JFileChooser()

    fun setupButtons() {
        val buttonsData = listOf(
            ButtonData("Очистить") { panel.removeAllPoints() },
            ButtonData("Добавить ребро") { panel.addEdge() },
            ButtonData("Удалить вершину") { panel.removeVertex() },
            ButtonData("Удалить ребро") { panel.removeEdge() },
            ButtonData("Файл") { chooseFile() },
            ButtonData("Вычислить") {
                panel.disableButtons()
                panel.disableMouseListener(false)
                mediator.startShow()
            }
        )

        buttonsData.forEach { buttonData ->
            val button = JButton(buttonData.text).apply {
                isFocusPainted = false
                addActionListener { buttonData.action() }
            }
            panel.add(button)
        }

        val nextStepButton = createButton("Следующий шаг") { mediator.nextStep() }
        val resultButton = createButton("Результат") {
            mediator.getResult()
            panel.disableButtons()
            panel.disableMouseListener(true)
        }

        panel.add(Box.createVerticalGlue())
        panel.add(nextStepButton)
        panel.add(resultButton)

        fileChooser.currentDirectory = java.io.File(".")
        fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
    }

    private fun createButton(text: String, action: () -> Unit): JButton {
        return JButton(text).apply {
            isFocusPainted = false
            isEnabled = false
            addActionListener { action() }
        }
    }

    private fun chooseFile() {
        val returnValue = fileChooser.showOpenDialog(panel)
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            val pathFile = selectedFile.absolutePath
            val result = FileHandler.readGraphFromFile(pathFile)
            if (result != null) {
                panel.vertices = result
                panel.arrangeVerticesInCircle()
            } else {
                panel.explanation = Explanations.FILEERROR
                panel.removeAllPoints()
                panel.repaint()
            }
        }
    }
}

data class ButtonData(val text: String, val action: () -> Unit)

