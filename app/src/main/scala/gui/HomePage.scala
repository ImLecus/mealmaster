package gui
import scalafx.scene.layout.VBox
import scalafx.scene.control.{Button, Label}
import defs.Ingredient
class HomePage extends VBox {
  spacing = 10
  children = Seq(
    new Label("Welcome to the Home Page!"),
    new Button("Go to Settings") {

    },

    new IngredientWidget(new Ingredient("Pan",1,1,null))
  )
  stylesheets += getClass.getResource("style.css").toExternalForm
}
