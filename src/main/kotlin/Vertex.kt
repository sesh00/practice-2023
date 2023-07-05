import java.awt.Point
import java.awt.Rectangle

class Vertex(
    var x: Int = 0,
    var y: Int = 0,
) {
    val radius: Int = 40

    var id: Int? = null
        private set

    companion object {
        var idCounter: Int = 1

        fun createWithId(x: Int, y: Int): Vertex {
            val vertex = Vertex(x, y)
            vertex.id = idCounter++
            return vertex
        }
    }

    private val bounds: Rectangle
        get() = Rectangle(x - radius / 2, y - radius / 2, radius, radius)

    fun containsPoint(point: Point): Boolean {
        return bounds.contains(point)
    }

}
