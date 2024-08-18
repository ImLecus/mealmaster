package defs

import scala.util.control.Breaks._
import java.io._
import scala.collection.mutable

object db {
    private var instance: DataBase = _
    def init() : Unit = {
        val loadedDB = read("./src/data/db.ser")
        if(loadedDB == null){
            // Create a new DataBase and save it
            instance = new DataBase(
                mutable.Set(
                    "Carbohidratos",
                    "Verduras",
                    "Proteínas",
                    "Legumbres",
                    "Carne",
                    "Pescado",
                    "Otros"
                ),
                mutable.Set(),
                mutable.ListBuffer(),
                mutable.Set(),
                new MealPlanner(PLANNER_MODE.WEEKLY,PRICE_LEVEL.MODERATE, CALORIES_LEVEL.MODERATE,0.8,0.8, null)
            )
            write("./src/data/db.ser")
            return
        }
        instance = loadedDB
    }

    private def read(from: String) : DataBase = {
      // read a serialized file
      try {
          val in = new ObjectInputStream(new FileInputStream(from))
          val database = in.readObject().asInstanceOf[DataBase]
          in.close()
          println("Database recovered successfully")
          database
      }
      catch {

          case e: IOException =>
              println("There is no available database, initializing a new one...")
              null
      }
    }

    private def write(where: String) : Unit = {
        val out = new ObjectOutputStream(new FileOutputStream(where))
        out.writeObject(instance)
        out.close()
    }

    def addIngredient(ingredient: Ingredient): Unit = {
        instance.ingredients += ingredient
        write("./src/data/db.ser")
    }

    def getIngredient(name: String): Ingredient = {
        var ingredient: Ingredient = null
        breakable {
            for (i <- instance.ingredients) {
                if (i.name == name) {
                    ingredient = i
                    break()
                }
            }
        }
        ingredient
    }

    def editIngredient(name: String, newIngredient: Ingredient): Unit = {
        breakable {
            for (i <- instance.ingredients) {
                if (i.name == name) {
                    instance.ingredients = instance.ingredients - i + newIngredient
                    break()
                }
            }
        }
        write("./src/data/db.ser")
    }


    def getCategory(name: String): String = {
        var category: String = ""
        breakable {
            for (c <- instance.categories) {
                if (c == name) {
                    category = c
                    break()
                }
            }
        }
        category
    }

    def getMeal(name: String): Meal = {
        var meal: Meal = null
        breakable {
            for (m <- instance.meals) {
                if(m.name == name){
                    meal = m
                    break();
                }
            }
        }
        meal
    }

    def addMeal(meal: Meal): Unit = {
        instance.meals += meal
        write("./src/data/db.ser")
    }

    def editMeal(name: String, newMeal: Meal): Unit = {
        
        write("./src/data/db.ser")
    }


    def getPlanner : MealPlanner = {
        instance.planner
    }

    def getMeals: mutable.Set[Meal] = {
        instance.meals
    }

    def getIngredients: mutable.Set[Ingredient] = {
        instance.ingredients
    }

    def getCategories: mutable.Set[String] = {
        instance.categories
    }
}
