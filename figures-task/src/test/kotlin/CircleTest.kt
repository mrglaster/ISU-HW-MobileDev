import models.Circle
import org.junit.jupiter.api.*

class CircleTest {
    private lateinit var circle: Circle

    @Test
    @DisplayName("Circle area")
    fun testArea() {
        circle = Circle(0, 0, 5)
        val expectedArea = Math.PI * 5 * 5
        Assertions.assertEquals(expectedArea.toFloat(), circle.area(), 0.01f)
    }

    @Test
    @DisplayName("Circle rotate clockwise")
    fun testRotateClockwise() {
        val centerX = 0
        val centerY = 0
            circle = Circle(0, 10, 5)
        circle.rotate(RotateDirection.Clockwise, centerX, centerY)
        Assertions.assertEquals(10, circle.x)
        Assertions.assertEquals(0, circle.y)
    }

    @Test
    @DisplayName("Circle rotate counter clockwise")
    fun testRotateCounterClockwise() {
        val centerX = 0
        val centerY = 0
        circle = Circle(10, 0, 5)
        circle.rotate(RotateDirection.CounterClockwise, centerX, centerY)
        Assertions.assertEquals(0, circle.x)
        Assertions.assertEquals(10, circle.y)
    }

    @Test
    @DisplayName("Circle resize")
    fun testResize() {
        val zoom = 2
        circle = Circle(0, 0, 5)
        circle.resize(zoom)
        Assertions.assertEquals(10, circle.r)
    }
}