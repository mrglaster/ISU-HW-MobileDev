package models
import RotateDirection
import Transforming


class Circle (var x:Int, var y:Int, var r:Int):Figure(0), Transforming{

    override fun area(): Float {
        return (java.lang.Math.PI * r * r).toFloat()
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val rzX = x - centerX
        val rzY = y - centerY

        x = centerX
        y = centerY

        if (direction == RotateDirection.Clockwise) {
            x += rzY
            y -= rzX
        } else if (direction == RotateDirection.CounterClockwise){
            x -= rzY
            y += rzX
        }
    }

    override fun resize(zoom: Int) {
        r *= zoom
    }
}