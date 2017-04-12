package scalafx.ecstock

import java.io.IOException

import scala.collection.immutable.TreeMap
import scalafx.Includes._
import scalafx.ecstock.commons.{ExampleInfo, PageDisplayer, SortUtils}
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Region, TilePane}

/**
 * Object to load examples as Map which in turn is used
 * to create TreeItem in the UI
 */
object EcStockTree {

  private val exampleListPath = ExampleInfo.ecstockDir + "ui.tree"
  private val examplListURL = getClass.getResource(exampleListPath)

  def create(): EcStockTree = new EcStockTree(createTree(), createThumbnails())

  /**
   * build a map by iterating through the examples folder.
   * This is used in UI
   */
  private def createTree(): Map[String, List[TreeItem[String]]] = {
    val pairs = for ((dirName, examples) <- loadExampleNames()) yield {
      val leaves = for (leafName <- examples) yield {
        new TreeItem(ExampleInfo.formatAddSpaces(leafName))
      }
      dirName -> leaves.toList.sortWith(SortUtils.treeItemSort)
    }
    TreeMap(pairs: _*)
  }

  private def loadExampleNames(): Array[(String, Array[String])] = {

    require(examplListURL != null, "Failed to locate resource in classpath: " + exampleListPath)

    val lines = scala.io.Source.fromURL(examplListURL).getLines()

    for (line <- lines.toArray) yield {
      val v = line.split("->")
      assert(v.length == 2)
      val dirName = v.head.trim
      val examples = v(1).split(",").map(_.trim())
      dirName -> examples
    }
  }

  private def createThumbnails() = {
    val pairs = for ((dirName, examples) <- loadExampleNames()) yield {
      print("This is the main example")
      val groupName = dirName
      val thumbs = for (leafName <- examples) yield {
        val sampleName = ExampleInfo.formatAddSpaces(leafName)
        val img = new ImageView {
          val filePath = ExampleInfo.thumbnailPath(leafName, groupName)
          print(filePath)
          val inputStream = this.getClass.getResourceAsStream(filePath)
          if (inputStream == null) {
            throw new IOException("Unable to locate resource: " + filePath)
          }
          image = new Image(inputStream)
        }
        val button = new Button(sampleName, img) {
          print(sampleName)
          print(sampleName)
          print(img)
          print(img)
          print(groupName)
          print(groupName)
          prefWidth = 140
          prefHeight = 145
          contentDisplay = ContentDisplay.Top
          styleClass.clear()
          styleClass += "sample-tile"
          onAction = (ae: ActionEvent) => {

            EcStock.splitPane.items.remove(1)
            EcStock.splitPane.items.add(1,
              PageDisplayer.choosePage(groupName + " > " + sampleName))
          }
        }
        EcStockThumbNail(button)
      }
      dirName.capitalize -> thumbs.toList.sortWith(SortUtils.thumbNailsSort)
    }
    TreeMap(pairs: _*)
  }
}

case class EcStockThumbNail(button: Button)

/**
 * The class provide accessibility methods to access the
 * underlying map
 */
class EcStockTree(tree: Map[String, List[TreeItem[String]]],
                   thumbnails: Map[String, List[EcStockThumbNail]]) {

  def getLeaves(keyName: String) = tree(keyName)

  /**
   * returns the entire tree
   */
  def getTree: List[TreeItem[String]] = tree.map {
    case (name, items) => new TreeItem[String](name) {
      expanded = true
      children = items
    }
  }.toList

  def getThumbs(keyName: String) = thumbnails(keyName)


  def getDashThumbsCtrl =
    thumbnails.flatMap {
      case (heading, ts) => Seq(createCategoryLabel(heading), createTiles(ts))
    }

  def getDashThumb(ctrlGrpName: String) =
    Seq(
      createCategoryLabel(ctrlGrpName),
      createTiles(thumbnails(ctrlGrpName))
    )

  private def createCategoryLabel(value: String) =
    new Label {
      text = value
      maxWidth = Double.MaxValue
      minHeight = Region.USE_PREF_SIZE
      styleClass += "category-header"
    }

  private def createTiles(value: List[EcStockThumbNail]) = new TilePane {
    prefColumns = 1
    hgap = 4
    vgap = 4
    padding = Insets(10, 10, 10, 10)
    orientation = Orientation.Horizontal
    styleClass += "category-page-flow"
    children = value.map(_.button)
  }
}