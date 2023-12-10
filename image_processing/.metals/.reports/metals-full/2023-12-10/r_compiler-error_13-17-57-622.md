file:///D:/Projects/FProg/Scala_test/image_processing/src/main/scala/Main.scala
### scala.reflect.internal.FatalError: no context found for source-file:///D:/Projects/FProg/Scala_test/image_processing/src/main/scala/Main.scala,line-6,offset=194

occurred in the presentation compiler.

action parameters:
offset: 194
uri: file:///D:/Projects/FProg/Scala_test/image_processing/src/main/scala/Main.scala
text:
```scala
/*--------------------- ВЫВОД ПИКСЕЛЕЙ --------------------- */
// import java.awt.image.BufferedImage
// import javax.imageio.ImageIO
// import java.io.File

// object ImageRecognitionApp @@extends App {
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
  import java.imageio.ImageIO
  import scala.util.Random
  import org.apache.commons.math3.stat.descriptive.{DescriptiveStatistics, SummaryStatistics}
  
  object Main25 {
  def main(args:Array[String]): Unit = {
   	val imagePath = "image.PNG"
    // Load image using ImageIO
    val image: BufferedImage = ImageIO.read(new File(imagePath))
  
    // Get the width and height of the image  // Читаем картинку в массив пикселей, после этого случайными образом отбираем 1000 пикселей. 
  	val width = image.getWidth 		 
    val heigth = image.getHeight
  
    // Get the array of pixels from the image
    val pixels = Array.ofDim[Int](width * height)
  	image.getRGB(0, 0, width, height, pixels, 0, width)
    val count = 1000
    val numbers = List[Double]() // Тут случайно выбранные пиксели хранятся в списке
    val random = new Random()
  	for(i <- 0 until count) {
    	var j = random.nextInt(width * height)
      numbers = math.abs(pixels(j)) :: numbers // Номера пикселей, пиксель j мы будем добавлять в список numbers
  	}
    		
   	val stats1 = new DescriptiveStatistics() // Находим здесь среднее значение и стандартное отклонение  и дальше выводим
    numbers.foreach(stats.addValue) 	// здесь среднее значение и стандартное отклонение 
    val mean = stats1.getMean()		//
    val stdDev = math.sqrt(stats1.getStandartDeviation()) //
  
    println(s"Mean value: ${mean}") //  и дальше 
    println(s"Stdev: ${stdDev}")   // выводим
  }
}
```



#### Error stacktrace:

```
scala.tools.nsc.interactive.CompilerControl.$anonfun$doLocateContext$1(CompilerControl.scala:100)
	scala.tools.nsc.interactive.CompilerControl.doLocateContext(CompilerControl.scala:100)
	scala.tools.nsc.interactive.CompilerControl.doLocateContext$(CompilerControl.scala:99)
	scala.tools.nsc.interactive.Global.doLocateContext(Global.scala:114)
	scala.meta.internal.pc.PcDefinitionProvider.definitionTypedTreeAt(PcDefinitionProvider.scala:151)
	scala.meta.internal.pc.PcDefinitionProvider.definition(PcDefinitionProvider.scala:68)
	scala.meta.internal.pc.PcDefinitionProvider.definition(PcDefinitionProvider.scala:16)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$definition$1(ScalaPresentationCompiler.scala:321)
```
#### Short summary: 

scala.reflect.internal.FatalError: no context found for source-file:///D:/Projects/FProg/Scala_test/image_processing/src/main/scala/Main.scala,line-6,offset=194