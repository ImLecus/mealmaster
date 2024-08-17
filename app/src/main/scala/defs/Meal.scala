package defs

import scala.collection.mutable
class Meal (
           var name: String,
           var ingredients: mutable.Map[Ingredient, Float]
           ) extends Serializable {
}
