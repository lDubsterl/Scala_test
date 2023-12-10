file:///D:/Projects/FProg/Scala_test/image_processing/src/main/scala/Main.scala
### java.lang.ClassCastException: class scala.reflect.internal.Types$PolyType cannot be cast to class scala.reflect.internal.Types$OverloadedType (scala.reflect.internal.Types$PolyType and scala.reflect.internal.Types$OverloadedType are in unnamed module of loader 'app')

occurred in the presentation compiler.

action parameters:
offset: 1280
uri: file:///D:/Projects/FProg/Scala_test/image_processing/src/main/scala/Main.scala
text:
```scala
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
    val pixels = Array.ofDim[@@Int]
    image.getRGB(0, 0, width, height, pixels, 0, width)
    val count = 1000
    var numbers = ListDouble // Исправлено: переменная должна быть изменяемой
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

```



#### Error stacktrace:

```
scala.reflect.internal.Symbols$Symbol.alternatives(Symbols.scala:2017)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCall.alternatives(SignatureHelpProvider.scala:154)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.$anonfun$fromTree$2(SignatureHelpProvider.scala:282)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.$anonfun$fromTree$2$adapted(SignatureHelpProvider.scala:281)
	scala.Option$WithFilter.$anonfun$withFilter$1(Option.scala:362)
	scala.Option$WithFilter.$anonfun$withFilter$1$adapted(Option.scala:362)
	scala.Option$WithFilter.map(Option.scala:319)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.fromTree(SignatureHelpProvider.scala:281)
	scala.meta.internal.pc.SignatureHelpProvider.signatureHelp(SignatureHelpProvider.scala:27)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$signatureHelp$1(ScalaPresentationCompiler.scala:282)
```
#### Short summary: 

java.lang.ClassCastException: class scala.reflect.internal.Types$PolyType cannot be cast to class scala.reflect.internal.Types$OverloadedType (scala.reflect.internal.Types$PolyType and scala.reflect.internal.Types$OverloadedType are in unnamed module of loader 'app')