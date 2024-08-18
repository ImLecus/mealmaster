package gui

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox
import scala.collection.mutable
import defs.db
object Main extends JFXApp3 {
  private val pages = mutable.Map[String, VBox]()

  def navigate(page: String): Unit = {
    if (!pages.contains(page)) {
      return
    }
    stage.scene = new Scene(
      pages(page)
    )
  }

  private def createScene(): Unit = {

    stage = new JFXApp3.PrimaryStage {
      title = "Mealmaster"

      scene = new Scene {
        root = new VBox {
          spacing = 10
          children = Seq(

          )
          stylesheets += getClass.getResource("style.css").toExternalForm
        }
      }
      minWidth = 800
      minHeight = 600

    }

    pages("home") = new HomePage()
  }

  override def start(): Unit = {
    db.init()
    createScene()

    navigate("home")
  }

}
