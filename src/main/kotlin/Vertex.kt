import java.awt.Point
import java.awt.Rectangle

class Vertex(
    var x: Int = 0,
    var y: Int = 0,
    var id: Int? = null,
) {
    val radius: Int = 40

    private val bounds: Rectangle
        get() = Rectangle(x - radius / 2, y - radius / 2, radius, radius)

    fun containsPoint(point: Point): Boolean {
        return bounds.contains(point)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vertex) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
