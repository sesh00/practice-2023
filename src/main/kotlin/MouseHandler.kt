import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class MouseHandler(private val panel: Panel) : MouseAdapter() {
    private var draggedVertex: Vertex? = null
    private var dragOffsetX = 0
    private var dragOffsetY = 0
    private var index = 1

    var startVertex: Vertex? = null
    var isDrawingEdge: Boolean = false
    var isRemovingVertex: Boolean = false

    override fun mousePressed(e: MouseEvent) {
        val clickedPoint = e.point
        var check = false
        for (vertex in panel.vertices.keys)
            if (vertex.containsPoint(clickedPoint)) {
                draggedVertex = vertex
                dragOffsetX = clickedPoint.x - vertex.x
                dragOffsetY = clickedPoint.y - vertex.y
                check = true
                break
            }

        if (!check) {
            panel.vertices[Vertex(e.x, e.y, index)] = mutableSetOf()
            isDrawingEdge = false
            isRemovingVertex = false
            startVertex = null
            panel.repaint()
            index++
        } else {
            if (isDrawingEdge) {
                if (startVertex == null) startVertex = draggedVertex
                else {
                    if (startVertex != draggedVertex)
                        panel.vertices[startVertex]!!.add(draggedVertex!!)
                    isDrawingEdge = false
                    startVertex = null
                }
                panel.repaint()
            } else if (isRemovingVertex) {
                panel.vertices.remove(draggedVertex)
                panel.vertices.forEach { vert, adjacencyList ->
                    if (adjacencyList.contains(draggedVertex))
                        panel.vertices[vert]?.remove(draggedVertex)
                }
                isRemovingVertex = false
                panel.repaint()
            }
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