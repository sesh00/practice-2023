import java.awt.Point
import java.awt.Rectangle

class Vertex(var x: Int, var y: Int, var id: Int? = null, var radius: Int = 40) {
    fun containsPoint(point: Point): Boolean {
        val vertexBounds = Rectangle(x - radius/2, y - radius/2, radius, radius)
        return vertexBounds.contains(point)
    }
}