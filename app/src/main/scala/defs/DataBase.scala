package defs

class DataBase (
               val categories: Set[Category],
               val meals: Set[Meal],
               var plans: List[Plan],
               var ingredients: Set[Ingredient]
               ){

}
