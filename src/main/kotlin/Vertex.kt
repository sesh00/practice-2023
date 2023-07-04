import java.awt.Point
import java.awt.Rectangle

class Vertex(
    var x: Int,
    var y: Int,
    var id: Int? = null,
    var radius: Int = 40
) {
    private val bounds: Rectangle
        get() = Rectangle(x - radius / 2, y - radius / 2, radius, radius)

    fun containsPoint(point: Point): Boolean {
        return bounds.contains(point)
    }
}
