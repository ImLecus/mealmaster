package gui
import defs.Ingredient
import scalafx.scene.layout.VBox
import scalafx.scene.control.{Button, Label}
class IngredientWidget(
                      val ingredient: Ingredient
                      ) extends VBox {
  styleClass += "widget"
  children = Seq(
    new Label(ingredient.name)
  )
}
