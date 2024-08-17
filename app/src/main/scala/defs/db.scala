package defs

import scala.util.control.Breaks._
import java.io._

object db {
    private var instance: DataBase = _

    def init() : Unit = {
        val loadedDB = read("./src/data/db.ser")
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

    def getCategory(name: String): Category = {
        var category: Category = null
        breakable {
            for (c <- instance.categories) {
                if (c.name == name) {
                    category = c
                    break()
                }
            }
        }
        category
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

}
