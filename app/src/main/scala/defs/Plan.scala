package defs

import scala.io.StdIn._
import scala.collection.mutable
class Plan (
           val meals: mutable.ListBuffer[Meal],
           val planType: String,
           var totalCalories: Int
           ) extends Serializable {

  def print(): Unit = {
    println("PLAN SUMMARY\n------------------------------")
    println(s"Plan duration: $planType")
    println("------------------------------")
    println("Meals: ")

    var i = 1
    for(meal <- meals){
      println(s"$i. ${meal.name}")
      i += 1
    }
    println("------------------------------")
    println("Ingredients: ")
    val ingredients = mutable.Map[String, Float]()
    for (meal <- meals) {
      for(pair <- meal.ingredients){
        if(ingredients.contains(pair._1.name)){
            ingredients(pair._1.name) += pair._2
        }
        else{
            ingredients(pair._1.name) = pair._2
        }
      }
    }
    i = 1
    for(ing <- ingredients){
      println(s"$i. ${ing._1} (${ing._2.ceil.toInt})")
      i = i + 1
    }

    println("------------------------------")
    println(s"Total calories: $totalCalories")
    println("------------------------------")
    println("Press any key to continue...")
    readLine()

  }
}
