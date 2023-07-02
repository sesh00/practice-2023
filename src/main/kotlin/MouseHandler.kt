import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class MouseHandler(private val panel: Panel) : MouseAdapter() {
    private var draggedVertex: Vertex? = null
    private var dragOffsetX = 0
    private var dragOffsetY = 0

    override fun mousePressed(e: MouseEvent) {
        val clickedPoint = e.point
        var check = false
        for (vertex in panel.vertices)
            if (vertex.containsPoint(clickedPoint)) {
                draggedVertex = vertex
                dragOffsetX = clickedPoint.x - vertex.x
                dragOffsetY = clickedPoint.y - vertex.y
                check = true
                break
            }
        if (!check) {
            panel.vertices.add(Vertex(e.x, e.y))
            panel.repaint()
        }
    }

    override fun mouseReleased(e: MouseEvent) {
        draggedVertex = null
    }

    override fun mouseDragged(e: MouseEvent) {
        if (draggedVertex != null) {
            draggedVertex?.x = e.x - dragOffsetX
            draggedVertex?.y = e.y - dragOffsetY
            panel.repaint()
        }
    }
}