package defs

import java.io._
import scala.collection.mutable
@SerialVersionUID(1L)
class DataBase (
               val categories: mutable.Set[String],
               val meals: mutable.Set[Meal],
               var plans: mutable.ListBuffer[Plan],
               var ingredients: mutable.Set[Ingredient],
               var planner: MealPlanner
               ) extends Serializable {

}
