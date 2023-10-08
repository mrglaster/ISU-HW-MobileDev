package models

import Movable
import RotateDirection
import Transforming

class Rect(var x: Int, var y: Int, var width: Int, var height: Int) : Movable, Transforming, Figure(0) {
    var color: Int = -1
    lateinit var name: String
    constructor(rect: Rect) : this(rect.x, rect.y, rect.width, rect.height)

    override fun move(dx: Int, dy: Int) {
        x += dx; y += dy
    }

    override fun area(): Float {
        return (width*height).toFloat()
    }

    override fun resize(zoom: Int){
        width *= zoom
        height *= zoom
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        var h_plus = if (y > centerY){
            y -= height
            true
        } else false
        var w_plus = if (x < centerX){
            x += width
            true
        } else false

        val rzX = x - centerX
        val rzY = y - centerY

        x = centerX
        y = centerY

        if (direction == RotateDirection.Clockwise) {
            if (h_plus && !w_plus) h_plus = false
            else if (h_plus == w_plus) w_plus = !w_plus
            else if (w_plus && !h_plus) h_plus = true
            x += rzY
            y -= rzX
        } else if (direction == RotateDirection.CounterClockwise){
            if (h_plus && !w_plus) w_plus = true
            else if (h_plus == w_plus) h_plus = !h_plus
            else if (w_plus && !h_plus) w_plus = false
            x -= rzY
            y += rzX
        }

        val rem = width
        width = height
        height = rem

        if (w_plus) x -= width
        if (h_plus) y += height
    }

}