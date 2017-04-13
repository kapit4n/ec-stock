package scalafx.ecstock.ui.layout

import scalafx.ecstock.commons.EcStockExample
import scalafx.geometry.Insets
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{AnchorPane, BorderPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

/** 
  *
  */
class EcStockBorderPane extends EcStockExample {


  def getContent = {
    // Top content using a rectangle
    val topRectangle = new Rectangle() {
      width = 400
      height = 20
      fill = Color.DarkSeaGreen
      stroke = Color.Black
    }

    // Left content using VBox
    val leftVBox = new VBox {
      spacing = 10
      children = List(Label("Left Hand"), Label("Choice One"), Label("Choice Two"), Label("Choice Three"))
    }

    // Center content using Anchor Pane
    val centerLabel = Label("We're in the center area.")
    val imageButton = new ImageView {
      image = new Image(this.getClass.getResourceAsStream("/scalafx/ecstock/images/icon-48x48.png"))
    }
    AnchorPane.setTopAnchor(centerLabel, 10.0)
    AnchorPane.setTopAnchor(imageButton, 40.0)
    AnchorPane.setLeftAnchor(centerLabel, 80.0)
    AnchorPane.setLeftAnchor(imageButton, 80.0)
    val centerAnchorPane = new AnchorPane {
      children = List(centerLabel, imageButton)
    }

    // Right content using VBox
    val rightVBox = new VBox {
      spacing = 10
      children = List(Label("Right Hand"), Label("Thing A"), Label("Thing B"), Label("Thing C"))
    }

    // Right content
    val bottomLabel = Label("I am a status message. I am at the bottom")

    new BorderPane {
      maxWidth = 400
      maxHeight = 300
      padding = Insets(20)
      top = topRectangle
      left = leftVBox
      center = centerAnchorPane
      right = rightVBox
      bottom = bottomLabel
    }
  }
}