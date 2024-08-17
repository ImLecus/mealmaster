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

    println("Press any key to continue...")
    readLine()

  }
}
