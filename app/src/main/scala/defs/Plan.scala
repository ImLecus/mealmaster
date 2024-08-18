package defs

import scala.io.StdIn._
import scala.collection.mutable.ListBuffer
class Plan (
           val meals: ListBuffer[Meal],
           val planType: String,
           var totalCalories: Int
           ) extends Serializable {

  def print(): Unit = {
    println("PLAN SUMMARY\n-------------")
    println("Meals: ")
    var i = 1
    for(meal <- meals){
      println(s"$i. ${meal.name}")
      i += 1
    }
    println("Press any key to continue...")
    readLine()

  }
}
