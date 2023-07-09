import java.io.BufferedWriter
import java.io.FileWriter
import javax.swing.Box
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class GraphicalInterface(private val panel: Panel,
                         private val mediator: Mediator) {
    private val fileChooser = JFileChooser()

    fun setupButtons() {
        val buttonsData = listOf(
            ButtonData("Очистить") { panel.removeAllPoints() },
            ButtonData("Добавить ребро") { panel.addEdge() },
            ButtonData("Удалить вершину") { panel.removeVertex() },
            ButtonData("Удалить ребро") { panel.removeEdge() },
            ButtonData("Сохранить в файл") { saveGraphToFile() },
            ButtonData("Файл") { chooseFile() },
        )

        buttonsData.forEach { buttonData ->
            val button = JButton(buttonData.text).apply {
                isFocusPainted = false
                addActionListener { buttonData.action() }
            }
            panel.add(button)
        }


        val nextStepButton = createButton("Следующий шаг") { mediator.nextStep() }
        val prevStepButton = createButton("Предыдущий шаг") { mediator.prevStep() }

        val startAlgorithmButton =  createButton("Вычислить") {
            panel.repaint()
            panel.disableButtons()
            panel.disableMouseListener(false)
            mediator.startShow()
        }

        val resultButton = createButton("Результат") {
            mediator.getResult()
            panel.disableButtons()
            panel.disableMouseListener(true)
        }

        panel.startButton = startAlgorithmButton

        panel.add(startAlgorithmButton)
        panel.add(Box.createVerticalGlue())
        panel.add(nextStepButton)
        panel.add(prevStepButton)
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
        val fileFilter = FileNameExtensionFilter("Text Files", "txt")
        fileChooser.fileFilter = fileFilter

        val returnValue = fileChooser.showOpenDialog(panel)

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            val fileName = selectedFile.name
            val fileExtension = fileName.substringAfterLast(".", "")
            if (fileExtension == "txt") {
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
            } else {
                panel.explanation = Explanations.INVALIDFILEFORMAT
                panel.removeAllPoints()
                panel.repaint()
            }
        }
    }

    fun saveGraphToFile() {
        val fileChooser = JFileChooser()
        fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
        fileChooser.fileFilter = FileNameExtensionFilter("Text Files (*.txt)", "txt")

        val result = fileChooser.showSaveDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile

            try {
                val writer = BufferedWriter(FileWriter(selectedFile))
                for ((source, targets) in panel.vertices) {
                    for (target in targets)
                        writer.write("${source.id} ${target.id}\n")
                    if (targets.isEmpty()) writer.write("${source.id}\n")
                }
                writer.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class ButtonData(val text: String, val action: () -> Unit)

