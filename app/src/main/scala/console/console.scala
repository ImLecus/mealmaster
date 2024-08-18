package console
import defs._
import scala.io.StdIn._
import scala.util.control.Breaks._
import scala.collection.mutable

object console {

  def getPercentage(f: Float): String = {
    val float = f * 100
    val p = float.toInt
    p.toString + "%"
  }

  def askFloat: Float = {
    try {
      val f = readFloat()
      if(f < 0){
        return 0
      }
      f
    }
    catch {
      case e: NumberFormatException => 0
    }
  }

  def askInt: Int = {
    try {
      val i = readInt()
      if(i < 0){
        return -1
      }
      i
    }
    catch {
      case e: NumberFormatException => -1
    }
  }

  def seeIngredientList(): Unit = {
    println("Ingredient list\n---------------------")
    var index = 1
    for(i <- db.getIngredients){
      println(s"$index. ${i.name}")
      index += 1
    }
    println("Press any key to continue...")
    readLine
  }

  def seeMealList(): Unit = {
    println("Meal list\n---------------------")
    var index = 1
    for (i <- db.getMeals) {
      println(s"$index. ${i.name}")
      index += 1
    }
    println("Press any key to continue...")
    readLine
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
      println("The input must be a positive number. Try again.")
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

    val i = new Ingredient(name,price,calories, mutable.Map() )

    breakable {
      while(true){
        print("Add a category name (type 'done' to finish): ")
        val category = readLine()
        if(category == "done"){
          break()
        }
        print(s"How many percentage of '$category' has this meal? ")
        var percentage = askInt
        while(percentage == -1){
          println("The percentage must be a positive number below 100. Try again")
          percentage = askInt
          if(percentage > 100){
            percentage = -1
          }
        }
        i.categories(category) = percentage
      }
    }




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
            println("The input must be a positive number. Try again.")
            print("Type the new ingredient price: ")
            price = askFloat
          }
          ingredient.price = price

        case 3 =>
          print("Type the new ingredient calories: ")
          var calories = askInt
          while (calories == 0f) {
            println("The input must be a positive number. Try again.")
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
    print("Select the meal name: ")
    val name = readLine()

    val exists = db.getMeal(name)
    if (exists != null) {
      println("This meal already exists.")
      return
    }

    val meal = new Meal(name, mutable.Map())

    breakable {
      while(true) {
        print("Type the ingredient name (type 'done' to finish): ")
        var ingredientName = readLine()
        if(ingredientName == "done"){
          break()
        }
        var ingredient = db.getIngredient(ingredientName)
        if(ingredient == null){
          println(s"The ingredient '$ingredientName' does not exist. Try again.")
        }
        else {
          print("Type the ingredient amount: ")
          var ingredientAmount = askFloat
          if(ingredientAmount == 0){
            println("The amount must be a positive number. Try again.")
          }
          else{
            meal.ingredients(ingredient) = ingredientAmount
            println(s"$ingredientName ($ingredientAmount) added to the ingredient list\n")
          }
        }
      }
    }
    db.addMeal(meal)
    println(s"Meal '$name' has been added successfully.\n")
  }

  def editMeal(): Unit = {
    print("Type the name of the meal: ");
    var name = readLine()
    var meal = db.getMeal(name)
    if (meal == null) {
      println(s"The meal '$name' does not exist. \n")
      return
    }
    println("Select the meal property to be edited:\n1. Name\n2. Ingredients")
    val option = askInt
    val action: Unit = option match {
      case 0 => return
      case 1 =>
        print("Type the new ingredient name: ")
        meal.name = readLine()

      case 2 =>
        println(s"Here is the list of the '${meal.name}' ingredients: ")
        meal.printIngredients()
        print("Select the ingredient number to edit (0 to cancel): ")
        var number = askInt
        if(number == 0){
          return
        }


      case _ => println("Option does not exist.")
    }

    //db.editIngredient(name, ingredient)
    println(s"Meal '${meal.name}' has been edited successfully.\n")
  }

  def createPlan(): Unit = {
    var planType = PLANNER_MODE.NONE
    while(planType == PLANNER_MODE.NONE){
      print("Type the duration of the plan (now, daily, weekly, full-week): ")
      planType = readLine() match {
        case "now" => PLANNER_MODE.NOW
        case "daily" => PLANNER_MODE.DAILY
        case "weekly" => PLANNER_MODE.WEEKLY
        case "full-week" => PLANNER_MODE.FULL_WEEKLY
        case _ => PLANNER_MODE.NONE
      }
    }
    println("This is the actual configuration. Select a number to change the settings or 0 to proceed")
    println(s"1. Price level: ${db.getPlanner.PRICE_LEVEL.describe()}")
    println(s"2. Calories level: ${db.getPlanner.CALORIES_LEVEL.describe()}")
    println(s"3. Originality: ${getPercentage(db.getPlanner.originality)}")
    println(s"4. Balance: ${getPercentage(db.getPlanner.balance)}")
    var response = askInt
    if(response == 0){
      println("Creating meal plan...")
      db.getPlanner.PLANNER_MODE = planType
      db.getPlanner.initialize(db.getMeals, db.getCategories)
      val plan = db.getPlanner.run

      println("Meal plan created!")
      plan.print()
    }


  }
}
