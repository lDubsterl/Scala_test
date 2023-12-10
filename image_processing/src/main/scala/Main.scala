/*--------------------- ВЫВОД ПИКСЕЛЕЙ --------------------- */
// import java.awt.image.BufferedImage
// import javax.imageio.ImageIO
// import java.io.File

// object ImageRecognitionApp extends App {
//   val image: BufferedImage = ImageIO.read(new File("image.PNG"))

//   for (y <- 0 until image.getHeight) {
//     for (x <- 0 until image.getWidth) {
//       val pixel = image.getRGB(x, y)
//       val red = (pixel >> 16) & 0xff
//       val green = (pixel >> 8) & 0xff
//       val blue = pixel & 0xff
//       println(s"Pixel at ($x, $y): (R:$red, G:$green, B:$blue)")
//     }
//   }
// }

/*--------------------- ОПРЕДЕЛЕНИЕ СТАТИСТИЧЕСКИХ ПАРАМЕТРОВ --------------------- */
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import scala.util.Random
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

object Main25 {
  def main(args: Array[String]): Unit = {
    val imagePath = "image.PNG"
    // Load image using ImageIO
    val image: BufferedImage = ImageIO.read(new File(imagePath))

    // Get the width and height of the image
    val width = image.getWidth
    val height = image.getHeight

    // Get the array of pixels from the image
    val pixels = Array.ofDim[Int](1000)
    image.getRGB(0, 0, width, height, pixels, 0, width)
    val count = 1000
    var numbers = Nil: List[Double]
    val random = new Random()

    // Randomly select 1000 pixels and add their absolute values to the list
    for (_ <- 0 until count) {
      val j = random.nextInt(width * height)
      numbers = math.abs(pixels(j)) :: numbers
    }

    // Calculate and print the mean and standard deviation of the selected pixels
    val stats = new DescriptiveStatistics()
    numbers.foreach(stats.addValue)
    val mean = stats.getMean
    val stdDev = math.sqrt(stats.getVariance) // Исправлено: использование getVariance для расчета стандартного отклонения

    println(s"Mean value: $mean")
    println(s"Stdev: $stdDev")
  }
}
