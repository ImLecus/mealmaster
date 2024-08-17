package defs

import scala.collection.mutable
class Ingredient(
                var name: String,
                var price: Float,
                var calories: Int,
                var categories: mutable.Map[String, Int]
                ) extends Serializable {
}
