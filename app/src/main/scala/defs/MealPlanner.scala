package defs
import defs.PRICE_LEVEL.{HIGH, LOW, MODERATE}

import scala.util.Random
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.*

class MealPlanner (
                  var PLANNER_MODE: PLANNER_MODE,
                  var PRICE_LEVEL: PRICE_LEVEL,
                  var CALORIES_LEVEL: CALORIES_LEVEL,
                  var originality: Float,
                  var balance: Float,
                  var previousResults: mutable.ListBuffer[Plan]
                  ) extends Serializable{
    var meals : mutable.Set[Meal] = null
    var average_calories = 0
    var average_price = 0.0f

    def run : Plan = {
      val size = meals.size
      val random = new Random()
      val plan = new Plan(new ListBuffer[Meal](), PLANNER_MODE.describe(), 0)
      val mealNumber = PLANNER_MODE.getMealNumber

      var iteration = 0
      while(iteration < mealNumber){
        // Get a random meal
        val meal = getRandomMeal
        // Check if the meal exists and has not been added
        breakable {
          if(plan.meals.contains(meal)){
            println(s"The meal ${meal.name} has been already added to the plan")
          }
          if(!isPriceAccurate(meal) || !isCaloriesAccurate(meal)){
            break()
          }

          plan.meals += meal
          plan.totalCalories += meal.getTotalCalories
          iteration += 1
        }
      }


      plan
    }

    private def getRandomMeal: Meal = {
      val random = new Random()
      val prob = random.nextFloat * meals.size
      meals.toSeq(prob.toInt)
    }

    private def isPriceAccurate(m: Meal): Boolean = {
        val price = PRICE_LEVEL match {
          case LOW =>
            m.getTotalPrice < average_price * 0.8
          case MODERATE =>
            m.getTotalPrice > average_price * 0.8 && m.getTotalPrice < average_price * 1.2
          case HIGH =>
            m.getTotalPrice > average_price * 1.2
        }
        price
    }

    private def isCaloriesAccurate(m: Meal): Boolean = {
      val price = CALORIES_LEVEL match {
        case defs.CALORIES_LEVEL.LOW =>
          m.getTotalCalories < average_calories * 0.8
        case defs.CALORIES_LEVEL.MODERATE =>
          m.getTotalCalories > average_calories * 0.8 && m.getTotalCalories < average_calories * 1.2
        case defs.CALORIES_LEVEL.HIGH =>
          m.getTotalCalories > average_calories * 1.2
      }
      price
    }

    def initialize(m: mutable.Set[Meal]) : Unit = {
        meals = m
        val size = m.size
        for(element <- m){
          average_calories += element.getTotalCalories
          average_price += element.getTotalPrice
        }
        try {
          average_calories /= size
          average_price /= size
        }
        catch {
          case e: ArithmeticException => println("There are no meals to make a plan")
        }
    }

}
