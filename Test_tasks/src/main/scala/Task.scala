import opennlp.tools.chunker.{ChunkerME, ChunkerModel}
import opennlp.tools.namefind.{NameFinderME, TokenNameFinderModel}
import opennlp.tools.postag.{POSModel, POSTaggerME}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import org.apache.commons.math3.optim.PointValuePair
import org.apache.commons.math3.optim.linear.{LinearConstraint, LinearConstraintSet, LinearObjectiveFunction, Relationship, SimplexSolver}
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

import java.awt.{Font, Graphics, Menu}
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.image.BufferedImage
import java.io.*
import java.util
import javax.imageio.ImageIO
import javax.swing.{JFrame, JMenu, JMenuBar, JMenuItem, JPanel, JScrollPane, JTextArea, SwingUtilities, WindowConstants}
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
        readWriteFiles()
      }
    })
    items.apply(2).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        readPixels()
      }
    })
    items.apply(3).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        compareImages()
      }
    })
    items.apply(4).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        textArea.setText("")
        val modelIn = new FileInputStream("en-token.bin")
        val model = new TokenizerModel(modelIn)
        val tokenizer = new TokenizerME(model)

        val tokens = tokenizer.tokenize("John Smith is a software engineer at Google.")

        tokens.foreach(l => textArea.append(l + "\n"))

        modelIn.close()
      }
    })
    items.apply(5).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        identifyEntities()
      }
    })
    items.apply(6).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        identifyGroups()
      }
    })
    items.apply(7).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        linearProg()
      }
    })
    items.apply(8).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        statistics()
      }
    })
    items.apply(9).addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        System.exit(0)
      }
    }
    )
  }

  private def identifyEntities(): Unit = {
    textArea.setText("")
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
      textArea.append(s"$entityType: $entity\n")
    }
  }

  private def identifyGroups(): Unit = {
    textArea.setText("")
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
      textArea.append(s"${chunks(i)}: ${tokens(i)}\n")
    }
  }

  private def readFile(): Unit = {
    textArea.setText("")
    val fileHandle = new File("text.txt")
    val fis = new FileInputStream(fileHandle)
    val bis = new BufferedInputStream(fis)
    val dis = new DataInputStream(bis)

    try {
      while (dis.available() != 0) {
        val line = dis.readLine()
        textArea.append(line + "\n")
      }
    } catch {
      case e: IOException =>
        textArea.append("An error occured while reading the file:" + e.getMessage + "\n")
    }
    finally {
      dis.close()
      textArea.append("Finished file reading\n")
    }
  }

  private def readWriteFiles(): Unit = {
    // WRITE

    // Method 1
    // Easiest way
    val fileWriter = new FileWriter(new File("test1.txt"))
    fileWriter.write("File writer test")
    fileWriter.close()

    // Method 2
    // For formatted text, like printf
    val printWriter = new PrintWriter(new File("test2.txt"))
    val s = "PrintWriter"
    val number = 2
    printWriter.printf("This is a %dnd method using %s", number, s)
    printWriter.close()

    // Method 3
    // Stores information in the form of bytes, unlike the 1st and 2nd methods (stores it as a string)
    val dataOutputStream = new DataOutputStream(new FileOutputStream(new File("test3.txt")))
    for (i <- 1 to 10) {
      dataOutputStream.writeInt(i)
    }
    dataOutputStream.close()

    // Method 3
    // Writes more efficiently. Using buffer for bulk write?
    val bufferedPrintWriter = new BufferedWriter(new PrintWriter(new File("data.txt")))
    for (i <- 1 to 25) {
      bufferedPrintWriter.write(i.toString)
    }
    bufferedPrintWriter.close()



    // READ
    textArea.setText("")
    //Method 1
    // Native method to read from a file
    val fileName1 = "test1.txt"
    scala.io.Source.fromFile(fileName1).getLines().foreach(l => textArea.append(l + "\n"))

    // Method 2
    // Efficiently reading from a file. Using buffer that provide bulk read from disk?
    val fileName2 = "test2.txt"
    val bufferedSource = scala.io.Source.fromFile(fileName2)
    val text = bufferedSource.getLines().mkString
    textArea.append(text + "\n")
    bufferedSource.close()

    // Method 3
    // Using Java API
    val fileName3 = "test2.txt"
    val fileReader = new BufferedReader(new FileReader(fileName3))

    def handleRead(line: String): Unit = {
      val newLine = fileReader.readLine()
      if (newLine != null) // if there are more lines to read
        handleRead(newLine)
        textArea.append(newLine + "\n")
    }

    handleRead(fileReader.readLine())
    fileReader.close()
  }

  private def linearProg(): Unit = {
    textArea.setText("")
    // create a constraint: 2x + 3y <= 4
    val coeffs: Array[Double] = Array(2.0, 3.0)
    val coeffs2: Array[Double] = Array(1.0, 3.0) // задаются для целевой ф-ии
    val coeffs3: Array[Double] = Array(1.0, 2.0)
    val relationship: Relationship = Relationship.LEQ // задаёт отношения
    val value: Double = 4.0
    val coeffs1: Array[Double] = Array(-1.0, 3.0)
    val relationship2: Relationship = Relationship.GEQ
    val relationship3: Relationship = Relationship.GEQ
    val value2: Double = 1.0
    val value3: Double = 4.0
    val objectiveFunction = new LinearObjectiveFunction(coeffs2, 0.0)
    val constraint: LinearConstraint = new LinearConstraint(coeffs, relationship, value)
    val constraint2: LinearConstraint = new LinearConstraint(coeffs1, relationship2, value2)
    val constraint3: LinearConstraint = new LinearConstraint(coeffs3, relationship3, value3)

    // Solve the optimization
    val solver = new SimplexSolver() // симплекс-метод

    val constraintsList2: java.util.List[LinearConstraint] = new util.ArrayList[LinearConstraint]()
    constraintsList2.add(constraint)
    constraintsList2.add(constraint2)
    constraintsList2.add(constraint3)
    val result: PointValuePair = solver.optimize(objectiveFunction, new LinearConstraintSet(constraintsList2), GoalType.MINIMIZE) // указывается целевая функция, 																		      //линейные ограничения, тип целевой 																	     // функции - направлена на минимум
    // Print the solution
    textArea.append(s"Minimum value: ${result.getValue}\n") // это результат целевой функции, в итоге выводит это в точке оптимума
    textArea.append(s"Solution: ${result.getPoint.mkString(", ")}\n") // значения переменных в точке оптимума
  }

  private def statistics(): Unit = {
    textArea.setText("")
    // Создать массив данных для анализа
    val data = Array(1.2, 3.4, 5.6, 7.8, 9.0)

    // Создать объект DescriptiveStatistics и добавить данные
    val stats = new DescriptiveStatistics()
    for (value <- data) {
      stats.addValue(value)
    }

    // Вычислить основные статистические показатели
    val mean = stats.getMean // Среднее значение
    val std = stats.getStandardDeviation // Стандартное отклонение
    val min = stats.getMin // Минимальное значение
    val max = stats.getMax // Максимальное значение
    val median = stats.getPercentile(50) // Медиана
    val quartile1 = stats.getPercentile(25) // Первый квартиль
    val quartile3 = stats.getPercentile(75) // Третий квартиль

    // Вывести результаты на экран
    textArea.append(s"Среднее значение: $mean\n")
    textArea.append(s"Стандартное отклонение: $std\n")
    textArea.append(s"Минимальное значение: $min\n")
    textArea.append(s"Максимальное значение: $max\n")
    textArea.append(s"Медиана: $median\n")
    textArea.append(s"Первый квартиль: $quartile1\n")
    textArea.append(s"Третий квартиль: $quartile3")
  }

  private def readPixels(): Unit = {
    textArea.setText("")
    val image: BufferedImage = ImageIO.read(new File("image.PNG"))
    for (y <- 0 until image.getHeight) {
      for (x <- 0 until image.getWidth) {
        val pixel = image.getRGB(x, y)
        val red = (pixel >> 16) & 0xff
        val green = (pixel >> 8) & 0xff
        val blue = pixel & 0xff
        textArea.append(s"Pixel at ($x, $y): (R:$red, G:$green, B:$blue)" + "\n")
      }
    }
  }

  private def calculateStatistics(filePath: String): Array[Double] = {
    // Load the image using ImageIO
    val localFrame = new JFrame()
    localFrame.setSize(1280, 720)
    localFrame.setLocationRelativeTo(null)

    var image: BufferedImage = ImageIO.read(new File(filePath))
    val pane = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        g.drawImage(image, 0, 0, null)
      }
    }
    localFrame.add(pane)
    localFrame.setVisible(true)

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
    textArea.append(s"Mean value: $mean" + "\n")
    textArea.append(s"Stdev: $stdDev" + "\n\n")
    Array(mean, stdDev)
  }

  private def compareImages(): Unit = {
    textArea.setText("")
    var picStatistics = calculateStatistics("one.jpg")
    val mean1 = picStatistics(0)
    val stdDev1 = picStatistics(1)
    picStatistics = calculateStatistics("two.jpg")
    val mean2 = picStatistics(0)
    val stdDev2 = picStatistics(1)
    val similarity = (mean1 * mean2 + stdDev1 * stdDev2) / math.sqrt(pow(mean1, 2) + pow(stdDev1, 2)) / math.sqrt(pow(mean2, 2) + pow(stdDev2, 2))
    textArea.append(similarity.toString + "\n")
    if (similarity > 0.99d)
      textArea.append("Pictures are equal")
    else
      textArea.append("Pictures are not equal")
  }

  val frame = new JFrame("My Application")
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  frame.setSize(800, 600)
  frame.setLocationRelativeTo(null)
  val textArea = new JTextArea()
  val font = new Font(Font.DIALOG, Font.BOLD, 12)
  textArea.setFont(font)
  textArea.setEditable(false)
  val scroller = new JScrollPane(textArea)

  def main(args: Array[String]): Unit = {
    val menuBar = new JMenuBar()
    val mainMenu = new JMenu("Tasks")
    val menu1 = new JMenuItem("Read file")
    val menu2 = new JMenuItem("Write and read files")
    val menu3 = new JMenuItem("Read pixels' array")
    val menu4 = new JMenuItem("Images' compare and characteristics")
    val menu5 = new JMenuItem("Divide text to tokens")
    val menu6 = new JMenuItem("Identify entities")
    val menu7 = new JMenuItem("Divide text to groups")
    val menu8 = new JMenuItem("Linear programming")
    val menu9 = new JMenuItem("Statistics")
    val exit = new JMenuItem("Exit")
    addListeners(Array(menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, menu9, exit))
    frame.add(scroller)
    menuBar.add(mainMenu)
    mainMenu.add(menu1)
    mainMenu.add(menu2)
    mainMenu.add(menu3)
    mainMenu.add(menu4)
    mainMenu.add(menu5)
    mainMenu.add(menu6)
    mainMenu.add(menu7)
    mainMenu.add(menu8)
    mainMenu.add(menu9)
    mainMenu.addSeparator()
    mainMenu.add(exit)
    frame.setJMenuBar(menuBar)
    frame.setVisible(true)
  }
}