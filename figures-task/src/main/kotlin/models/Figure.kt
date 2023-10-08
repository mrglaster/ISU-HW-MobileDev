package models

abstract class Figure(val id: Int):Comparable<Figure>{
     abstract fun area(): Float

     override fun compareTo(other: Figure) = other.area().compareTo(this.area())
}