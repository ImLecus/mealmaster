package defs

import scala.collection.mutable
class BalanceTable (
                   val categories: mutable.Set[String]
                   ) {
    private val table = mutable.Map[String,Float]()
    for(c <- categories){
      table(c) = 0f
    }

    def getBalanceRecommendation: String = {
      var recommendation = ""
      var min = 10000000f
      for(pair <- table){
        if(pair._2 < min){
          min = pair._2
          recommendation = pair._1
        }
      }
      recommendation
    }

    def addMeal(m: Meal): Unit = {
      for(i <- m.ingredients){
        for(categories <- i._1.categories){
          if(!table.contains(categories._1)){
            table(categories._1) = 0
          }
          table(categories._1) += categories._2/100
        }
      }
    }
}
