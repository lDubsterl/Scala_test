import opennlp.tools.chunker.{ChunkerME, ChunkerModel}
import opennlp.tools.namefind.{NameFinderME, TokenNameFinderModel}
import opennlp.tools.postag.{POSModel, POSTaggerME}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

import java.awt.{Graphics, Menu}
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.image.BufferedImage
import java.io.*
import javax.imageio.ImageIO
import javax.swing.{JFrame, JMenu, JMenuBar, JMenuItem, JPanel, SwingUtilities}
import scala.util.Random
import math.pow

object Task {

  private def addListeners(items: Array[JMenuItem]): Unit = {
    items.apply(0).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        readFile()
      }
    })
    items.apply(1).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        readPixels()
      }
    })
    items.apply(2).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        compareImages()
      }
    })
    items.apply(3).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        val modelIn = new FileInputStream("en-token.bin")
        val model = new TokenizerModel(modelIn)
        val tokenizer = new TokenizerME(model)

        val tokens = tokenizer.tokenize("John Smith is a software engineer at Google.")

        tokens.foreach(println)

        modelIn.close()
      }
    })
    items.apply(4).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        identifyEntities()
      }
    })
    items.apply(5).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        identifyGroups()
      }
    })
    items.apply(6).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {

      }
    })
    items.apply(7).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        System.exit(0)
      }
    }
    )
  }

  private def identifyEntities(): Unit = {
    val tokenizerModelIn = new FileInputStream("en-token.bin")
    val tokenizerModel = new opennlp.tools.tokenize.TokenizerModel(tokenizerModelIn)
    val tokenizer = new opennlp.tools.tokenize.TokenizerME(tokenizerModel)
    // Load NER model
    val nerModelIn = new FileInputStream("en-ner-person.bin")
    val nerModel = new TokenNameFinderModel(nerModelIn)
    val ner = new NameFinderME(nerModel)

    // Define some sample text
    val text = "John Smith is a software engineer at Google. Joe Biden is a president of USA."

    // Tokenize the text
    val tokens = tokenizer.tokenize(text)

    // Find the named entities in the text
    val spans = ner.find(tokens)

    // Print the named entities and their types
    for (span <- spans) {
      val entityType = span.getType
      val entity = tokens.slice(span.getStart, span.getEnd).mkString(" ")
      println(s"$entityType: $entity")
    }
  }

  private def identifyGroups(): Unit = {
    // Load tokenizer model
    val tokenizerModelIn = new FileInputStream("en-token.bin")
    val tokenizerModel = new TokenizerModel(tokenizerModelIn)
    val tokenizer = new TokenizerME(tokenizerModel)
    tokenizerModelIn.close()

    // Load POS tagger model
    val posModelIn = new FileInputStream("en-pos-maxent.bin")
    val posModel = new POSModel(posModelIn)
    val posTagger = new POSTaggerME(posModel)
    posModelIn.close()

    // Load chunker model
    val chunkerModelIn = new FileInputStream("en-chunker.bin")
    val chunkerModel = new ChunkerModel(chunkerModelIn)
    val chunker = new ChunkerME(chunkerModel)
    chunkerModelIn.close()

    // Define some sample text
    val text = "John Smith is a software engineer at Google."

    // Tokenize the text
    val tokens = tokenizer.tokenize(text)

    // Tag parts of speech
    val posTags = posTagger.tag(tokens)

    // Chunk the tagged tokens
    val chunks = chunker.chunk(tokens, posTags)

    // Print the chunks
    for (i <- chunks.indices) {
      println(s"${chunks(i)}: ${tokens(i)}")
    }
  }

  private def readFile(): Unit = {
    val fileHandle = new File("text.txt")
    val fis = new FileInputStream(fileHandle)
    val bis = new BufferedInputStream(fis)
    val dis = new DataInputStream(bis)

    try {
      while (dis.available() != 0) {
        val line = dis.readLine()
        println(line)
      }
    } catch {
      case e: IOException =>
        println("An error occured while reading the file:" + e.getMessage)
    }
    finally {
      dis.close()
      println("Finished file reading")
    }
  }

  private def readPixels(): Unit = {
    val image: BufferedImage = ImageIO.read(new File("image.PNG"))
    for (y <- 0 until image.getHeight) {
      for (x <- 0 until image.getWidth) {
        val pixel = image.getRGB(x, y)
        val red = (pixel >> 16) & 0xff
        val green = (pixel >> 8) & 0xff
        val blue = pixel & 0xff
        println(s"Pixel at ($x, $y): (R:$red, G:$green, B:$blue)")
      }
    }
  }

  private def calculateStatistics(filePath: String): Array[Double] = {
    // Load the image using ImageIO

    var image: BufferedImage = ImageIO.read(new File(filePath))
    // Get the width and height of image
    val width = image.getWidth
    val height = image.getHeight
    // Get the array of pixels from the image
    var pixels = Array.ofDim[Int](width * height)
    image.getRGB(0, 0, width, height, pixels, 0, width)
    val count = 1000
    var numbers = List[Double]()
    val random = new Random()

    for (i <- 0 until count) {
      var j = random.nextInt(width * height)
      val num = math.abs(pixels(j))
      numbers = numbers.appended(num)
    }

    val stats = new DescriptiveStatistics()
    numbers.foreach(stats.addValue)
    val mean = stats.getMean
    val stdDev = stats.getStandardDeviation
    println(s"Mean value: $mean")
    println(s"Stdev: $stdDev")
    Array(mean, stdDev)
  }

  private def compareImages(): Unit = {
    var picStatistics = calculateStatistics("one.jpg")
    val mean1 = picStatistics(0)
    val stdDev1 = picStatistics(1)
    picStatistics = calculateStatistics("two.jpg")
    val mean2 = picStatistics(0)
    val stdDev2 = picStatistics(1)
    val similarity = (mean1 * mean2 + stdDev1 * stdDev2) / math.sqrt(pow(mean1, 2) + pow(stdDev1, 2)) / math.sqrt(pow(mean2, 2) + pow(stdDev2, 2))
    println(similarity)
    if (similarity > 0.99d)
      println("Pictures are equal")
    else
      println("Pictures are not equal")
  }

  def main(args: Array[String]): Unit = {
    val frame = new JFrame("My Application")
    frame.setSize(800, 600)
    frame.setLocationRelativeTo(null)
    val menuBar = new JMenuBar()
    val mainMenu = new JMenu("Tasks")
    val menu1 = new JMenuItem("Read file")
    val menu2 = new JMenuItem("Read pixels' array")
    val menu3 = new JMenuItem("Images' compare and characteristics")
    val menu4 = new JMenuItem("Divide text to tokens")
    val menu5 = new JMenuItem("Identify entities")
    val menu6 = new JMenuItem("Divide text to groups")
    val menu7 = new JMenuItem("Divide text to groups")
    val exit = new JMenuItem("Exit")
    addListeners(Array(menu1, menu2, menu3, menu4, menu5, menu6, menu7, exit))
    menuBar.add(mainMenu)
    mainMenu.add(menu1)
    mainMenu.add(menu2)
    mainMenu.add(menu3)
    mainMenu.add(menu4)
    mainMenu.add(menu5)
    mainMenu.add(menu6)
    mainMenu.add(menu7)
    mainMenu.addSeparator()
    mainMenu.add(exit)
    frame.setJMenuBar(menuBar)
    frame.setVisible(true)
  }
}