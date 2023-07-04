import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class MouseHandler(private val panel: Panel) : MouseAdapter() {
    private var draggedVertex: Vertex? = null
    private var dragOffsetX = 0
    private var dragOffsetY = 0

    var index = 1

    var startVertex: Vertex? = null
    var isDrawingEdge: Boolean = false
    var isRemovingVertex: Boolean = false
    var isRemovingEdge: Boolean = false

    override fun mousePressed(e: MouseEvent) {
        val clickedPoint = e.point
        var vertexClicked = false

        for (vertex in panel.vertices.keys) {
            if (vertex.containsPoint(clickedPoint)) {
                draggedVertex = vertex
                dragOffsetX = clickedPoint.x - vertex.x
                dragOffsetY = clickedPoint.y - vertex.y
                vertexClicked = true
                break
            }
        }

        if (!vertexClicked) {
            panel.vertices[Vertex(e.x, e.y, index)] = mutableSetOf()
            resetFlags()
            panel.repaint()
            index++
        }

        if ((isDrawingEdge || isRemovingEdge) && startVertex == null && vertexClicked) {
            startVertex = draggedVertex
        } else if (isDrawingEdge && startVertex != null && vertexClicked && startVertex != draggedVertex) {
            panel.vertices[startVertex]?.add(draggedVertex!!)
            resetFlags()
        } else if (isRemovingEdge && startVertex != null && vertexClicked && startVertex != draggedVertex) {
            panel.vertices[startVertex]?.remove(draggedVertex!!)
            resetFlags()
        } else if (isRemovingVertex && vertexClicked) {
            panel.vertices.remove(draggedVertex)
            panel.vertices.forEach { (vert, adjacencyList) ->
                if (adjacencyList.contains(draggedVertex)) {
                    panel.vertices[vert]?.remove(draggedVertex)
                }
            }
            resetFlags()
        }

        panel.repaint()
    }


    override fun mouseReleased(e: MouseEvent) {
        draggedVertex = null
    }

    override fun mouseDragged(e: MouseEvent) {
        draggedVertex?.let {
            it.x = e.x - dragOffsetX
            it.y = e.y - dragOffsetY
            panel.repaint()
        }
    }

     fun resetFlags() {
        startVertex = null
        isDrawingEdge = false
        isRemovingVertex = false
        isRemovingEdge = false
    }
}
