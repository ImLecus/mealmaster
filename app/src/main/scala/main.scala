import defs._
import scala.util.control.Breaks._
import console._
@main
def main(): Unit = {
  db.init()

  breakable {
      while(true){
        println("Hello! Select an action:\n0. Exit\n1. Create an ingredient\n2. Create a meal\n3. Create a plan\n4. Edit an ingredient\n5. Edit a meal")
        val name = console.askInt
        val action: Unit = name match {
          case 0 => break()
          case 1 => console.createIngredient()
          case 2 => console.createMeal()
          case 3 => {}
          case 4 => console.editIngredient()
          case 5 => {}
          case _ => println("Option does not exist.")
        }
      }
  }

}

