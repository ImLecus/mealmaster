package defs

import java.io._

@SerialVersionUID(1L)
class DataBase (
               val categories: Set[Category],
               val meals: Set[Meal],
               var plans: List[Plan],
               var ingredients: Set[Ingredient]
               ) extends Serializable {

}
