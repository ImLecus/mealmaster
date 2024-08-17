package defs

import scala.collection.mutable
class Meal (
           var name: String,
           var ingredients: mutable.Map[Ingredient, Float]
           ) extends Serializable {

  def printIngredients(): Unit = {
    var index = 1
    for(i <- ingredients){
      println(s"$index. ${i._1.name} (${i._2}) ")
    }
  }

  def getTotalCalories: Int = {
     var total = 0
     for(i <- ingredients.keys){
       total += i.calories
     }
     total
  }

  def getTotalPrice: Float = {
    var total = 0f
    for (i <- ingredients.keys) {
      total += i.price
    }
    total
  }
}
