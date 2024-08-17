package console
import defs._
import scala.io.StdIn._
import scala.util.control.Breaks._

object console {
  def createIngredient(): Unit = {

    print("Select the ingredient name: ")
    val name = readLine()

    val exists = db.getIngredient(name)
    if(exists != null){
      println("This ingredient already exists.")
      return
    }

    print("Select the ingredient unit price: ")
    var price = 0f
    while(price == 0f){
      try {
        price = readFloat()
      }
      catch {
        case e: NumberFormatException => println("The price must be a number. Try again.")
      }
    }

    print("Select the ingredient unit calories: ")
    var calories = 0
    while (calories == 0) {
      try {
        calories = readInt()
      }
      catch {
        case e: NumberFormatException => println("The calories must be a number. Try again.")
      }
    }

    val i = new Ingredient(name,price,calories, new CategoryMap(List(),List()))
    db.addIngredient(i)
    println(s"Ingredient '$name' has been added successfully.\n")
  }

  def editIngredient(): Unit = {

  }

  def createMeal(): Unit = {

  }

  def editMeal(): Unit = {

  }

  def createPlan(): Unit = {

  }
}
