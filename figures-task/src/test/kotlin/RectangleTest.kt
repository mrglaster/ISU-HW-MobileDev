import models.Rect
import org.junit.jupiter.api.*

class RectangleTest {
    private lateinit var rect: Rect


    @Test
    @DisplayName("Rectangle Move")
    fun testMove() {
        rect = Rect(10, 20, 30, 40)
        rect.move(5, 10)
        Assertions.assertEquals(15, rect.x)
        Assertions.assertEquals(30, rect.y)
    }

    @Test
    @DisplayName("Rectangle Area")
    fun testArea() {
        rect = Rect(10, 20, 30, 40)
        val area = rect.area()
        Assertions.assertEquals(1200.0, area.toDouble(), 0.01)
    }

    @Test
    @DisplayName("Rectangle resize")
    fun testResize() {
        rect = Rect(10, 20, 30, 40)
        rect.resize(2)
        Assertions.assertEquals(60, rect.width)
        Assertions.assertEquals(80, rect.height)
    }

    @Test
    @DisplayName("Rectangle rotate clockwise")
    fun testRotateClockwise() {
        val centerX = 25
        val centerY = 40
        rect = Rect(10, 20, 30, 40)
        rect.rotate(RotateDirection.Clockwise, centerX, centerY)
        Assertions.assertEquals(-35, rect.x)
        Assertions.assertEquals(55, rect.y)
    }

    @Test
    @DisplayName("Rectangle rotate counter clockwise")
    fun testRotateCounterClockwise() {
        val centerX = 25
        val centerY = 40
        rect = Rect(10, 20, 30, 40)
        rect.rotate(RotateDirection.CounterClockwise, centerX, centerY)
        Assertions.assertEquals(45, rect.x)
        Assertions.assertEquals(55, rect.y)
    }
}