package console
import defs._
import scala.io.StdIn._
import scala.util.control.Breaks._

object console {

  def askFloat: Float = {
    try {
      readFloat()
    }
    catch {
      case e: NumberFormatException => 0
    }
  }

  def askInt: Int = {
    try {
      readInt()
    }
    catch {
      case e: NumberFormatException => -1
    }
  }

  def createIngredient(): Unit = {

    print("Select the ingredient name: ")
    val name = readLine()

    val exists = db.getIngredient(name)
    if(exists != null){
      println("This ingredient already exists.")
      return
    }

    print("Select the ingredient unit price: ")

    var price = askFloat
    while(price == 0f){
      println("The input must be a number. Try again.")
      print("Select the ingredient unit price: ")
      price = askFloat
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
      print("Type the name of the ingredient: ");
      var name = readLine()
      var ingredient = db.getIngredient(name)
      if(ingredient == null){
        println(s"The ingredient '$name' does not exist. \n")
        return
      }
      println("Select the ingredient property to be edited:\n1. Name\n2. Price\n3. Calories\n4. Categories")
      val option = askInt
      val action: Unit = option match {
        case 0 => return
        case 1 =>
          print("Type the new ingredient name: ")
          ingredient.name = readLine()

        case 2 =>
          print("Type the new ingredient price: ")
          var price = askFloat
          while (price == 0f) {
            println("The input must be a number. Try again.")
            print("Type the new ingredient price: ")
            price = askFloat
          }
          ingredient.price = price

        case 3 =>
          print("Type the new ingredient calories: ")
          var calories = askInt
          while (calories == 0f) {
            println("The input must be a number. Try again.")
            print("Type the new ingredient calories: ")
            calories = askInt
          }
          ingredient.calories = calories

        case 4 =>
          println("Categories are not available yet.")

        case _ => println("Option does not exist.")
      }

      db.editIngredient(name, ingredient)
      println(s"Ingredient '${ingredient.name}' has been edited successfully.\n")
  }

  def createMeal(): Unit = {

  }

  def editMeal(): Unit = {

  }

  def createPlan(): Unit = {

  }
}
