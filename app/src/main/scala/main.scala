import defs._
import scala.util.control.Breaks._
import scala.io.StdIn._
import console._
@main
def main(): Unit = {
  db.init()

  breakable {
      while(true){
        println("Hello! Select an action:\n1. Create an ingredient\n2. Create a meal\n3. Create a plan\n4. Edit an ingredient\n5. Edit a meal")
        try {
          var name = readInt()
          val action: Unit = name match {
            case 0 => break()
            case 1 => console.createIngredient()
            case 2 => {}
            case 3 => {}
            case 4 => {}
            case 5 => {}
            case _ => println("Option does not exist.")
          }
        }
        catch {
          case e : NumberFormatException =>
            println("Option does not exist.")
        }

      }
  }

}

