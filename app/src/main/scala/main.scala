import defs._
@main
def main(): Unit = {
  db.init()

  db.addIngredient( new Ingredient(
    "Barra de pan",
    0.4f,
    12,
    new CategoryMap(
      List(db.getCategory("Carbohidratos")
      ),
      List(12)
    )
  ))

  println(db.getCategory("Carbohidratos").name);
  println("Hello world!")

}

