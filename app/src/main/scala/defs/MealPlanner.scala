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
    private var meals : mutable.Set[Meal] = null
    private var categories: mutable.Set[String] = null
    private var average_calories = 0
    private var average_price = 0.0f
    private var balanceSet = Map[String, Int]()
    private var breakAverageCheck = false

    def run : Plan = {
      val size = meals.size
      val random = new Random()
      val plan = new Plan(new ListBuffer[Meal](), PLANNER_MODE.describe(), 0)
      val mealNumber = PLANNER_MODE.getMealNumber
      val balanceTable = new BalanceTable(categories)

      var iteration = 0
      var real_iteration = 0
      while(iteration < mealNumber){
        // Get a random meal
        val meal = getRandomMeal
        
        real_iteration += 1
        if(real_iteration > 50 && !breakAverageCheck){
          // There are not enough results to complete the plan.
          // This case breaks the infinite bucle by breaking the
          // price and calories check.
          println("Warning! There are not enough meals to make a precise plan. \nPrice and calories check are now disabled.")
          breakAverageCheck = true
        }
        
        
        breakable {
          // Check if the meal exists and has not been added on the same plan
          if(plan.meals.contains(meal)){
            println(s"The meal ${meal.name} has been already added to the plan")
          }
          // Price and calories check, disabled if breakAverageCheck = true
          if(!isPriceAccurate(meal) || !isCaloriesAccurate(meal)){
            break()
          }
          // Balance test
          if(iteration > 0 && !testBalance(meal, balanceTable.getBalanceRecommendation)){
            println(s"Meal ${meal.name} did not pass the Balance test. Needed: ${balanceTable.getBalanceRecommendation}")
            break()
          }
          balanceTable.addMeal(meal)
          // Originality test
          
          plan.meals += meal
          plan.totalCalories += meal.getTotalCalories
          iteration += 1
        }
      }


      plan
    }

    private def testBalance(m: Meal, needs: String): Boolean = {
      val random = new Random()
      val prob = new Random().nextFloat()
      if(prob <= balance){
        var result = false
        breakable {
          for (i <- m.ingredients) {
            for (category <- i._1.categories) {
              if (category._1 == needs) {
                result = true
                break()
              }
            }
          }
        }
        return result
      }
      true
    }

    private def getRandomMeal: Meal = {
      val random = new Random()
      val prob = random.nextFloat * meals.size
      meals.toSeq(prob.toInt)
    }

    private def isPriceAccurate(m: Meal): Boolean = {
        if(breakAverageCheck){
          return true
        }
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
      if (breakAverageCheck) {
        return true
      }
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

    def initialize(m: mutable.Set[Meal], c: mutable.Set[String]) : Unit = {
        categories = c
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
