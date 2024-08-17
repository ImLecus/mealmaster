import defs._
import scala.util.control.Breaks._
import console._
@main
def main(): Unit = {
  db.init()

  breakable {
      while(true){
        println("Hello! Select an action:\n0. Exit\n1. Create an ingredient\n2. Create a meal\n3. Create a plan\n4. Edit an ingredient\n5. Edit a meal\n6. See ingredient list\n7. See meal list")
        val name = console.askInt
        val action: Unit = name match {
          case 0 => break()
          case 1 => console.createIngredient()
          case 2 => console.createMeal()
          case 3 => console.createPlan()
          case 4 => console.editIngredient()
          case 5 => console.editMeal()
          case 6 => console.seeIngredientList()
          case 7 => console.seeMealList()
          case _ => println("Option does not exist.")
        }
      }
  }

}

