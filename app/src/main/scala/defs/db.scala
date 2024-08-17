package defs

import scala.util.control.Breaks._
object db {
    private var instance: DataBase = _

    def init() : Unit = {
        val loadedDB = read()
        if(loadedDB == null){

            // Create a new DataBase and save it
            instance = new DataBase(
                Set(
                    new Category("Carbohidratos"),
                    new Category("Verduras"),
                    new Category("Proteínas"),
                    new Category("Legumbres"),
                    new Category("Carne"),
                    new Category("Pescado"),
                    new Category("Otros")
                ),
                Set(),
                List(),
                Set()
            )
            return
        }
        instance = loadedDB
    }

    private def read() : DataBase = {
      // read a serialized file
      return null
    }

    def write(where: String) : Unit = {

    }

    def addIngredient(ingredient: Ingredient): Unit = {
        instance.ingredients += ingredient
    }

    def getCategory(name: String): Category = {
        var category: Category = null;
        breakable {
            for (c <- instance.categories) {
                if (c.name == name) {
                    category = c
                    break()
                }
            }
        }
        category;
    }

}
