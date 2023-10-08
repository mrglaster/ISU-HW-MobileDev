import models.Square
import org.junit.jupiter.api.*

class SquareTest {
    private lateinit var square: Square

    @Test
    @DisplayName("Square move")
    fun testMove() {
        square = Square(10, 10, 5)
        square.move(2, 3)
        Assertions.assertEquals(12, square.x)
        Assertions.assertEquals(13, square.y)
    }

    @Test
    @DisplayName("Square area")
    fun testArea() {
        square = Square(10, 10, 5)
        Assertions.assertEquals(25.0f, square.area())
    }

    @Test
    @DisplayName("Square resize")
    fun testResize() {
        square = Square(10, 10, 5)
        square.resize(2)
        Assertions.assertEquals(10, square.x)
        Assertions.assertEquals(10, square.y)
        Assertions.assertEquals(10, square.side)
    }

    @Test
    @DisplayName("Square rotate clockwise")
    fun testRotateClockwise() {
        square = Square(10, 10, 5)
        square.rotate(RotateDirection.Clockwise, 10, 10)
        Assertions.assertEquals(5, square.x)
        Assertions.assertEquals(10, square.y)
    }

    @Test
    @DisplayName("Display rotate counter clockwise")
    fun testRotateCounterClockwise() {
        square = Square(10, 10, 5)
        square.rotate(RotateDirection.CounterClockwise, 10, 10)
        Assertions.assertEquals(10, square.x)
        Assertions.assertEquals(15, square.y)
    }
}