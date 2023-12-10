file:///D:/Projects/FProg/Scala_test/image_processing/src/main/scala/Main.scala
### file%3A%2F%2F%2FD%3A%2FProjects%2FFProg%2FScala_test%2Fimage_processing%2Fsrc%2Fmain%2Fscala%2FMain.scala:1: error: unclosed comment
/*---------------------ВЫВОД ПИКСЕЛЕЙ /*---------------------*/
^

occurred in the presentation compiler.

action parameters:
uri: file:///D:/Projects/FProg/Scala_test/image_processing/src/main/scala/Main.scala
text:
```scala
/*---------------------ВЫВОД ПИКСЕЛЕЙ /*---------------------*/
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

```



#### Error stacktrace:

```
scala.meta.internal.tokenizers.Reporter.incompleteInputError(Reporter.scala:27)
	scala.meta.internal.tokenizers.Reporter.incompleteInputError$(Reporter.scala:26)
	scala.meta.internal.tokenizers.Reporter$$anon$1.incompleteInputError(Reporter.scala:33)
	scala.meta.internal.tokenizers.Reporter.incompleteInputError(Reporter.scala:29)
	scala.meta.internal.tokenizers.Reporter.incompleteInputError$(Reporter.scala:29)
	scala.meta.internal.tokenizers.Reporter$$anon$1.incompleteInputError(Reporter.scala:33)
	scala.meta.internal.tokenizers.LegacyScanner.skipNestedComments(LegacyScanner.scala:52)
	scala.meta.internal.tokenizers.LegacyScanner.skipToCommentEnd(LegacyScanner.scala:71)
	scala.meta.internal.tokenizers.LegacyScanner.fetchToken(LegacyScanner.scala:313)
	scala.meta.internal.tokenizers.LegacyScanner.nextToken(LegacyScanner.scala:211)
	scala.meta.internal.tokenizers.LegacyScanner.foreach(LegacyScanner.scala:1011)
	scala.meta.internal.tokenizers.ScalametaTokenizer.uncachedTokenize(ScalametaTokenizer.scala:24)
	scala.meta.internal.tokenizers.ScalametaTokenizer.$anonfun$tokenize$1(ScalametaTokenizer.scala:17)
	scala.collection.concurrent.TrieMap.getOrElseUpdate(TrieMap.scala:962)
	scala.meta.internal.tokenizers.ScalametaTokenizer.tokenize(ScalametaTokenizer.scala:17)
	scala.meta.internal.tokenizers.ScalametaTokenizer$$anon$2.apply(ScalametaTokenizer.scala:332)
	scala.meta.tokenizers.Api$XtensionTokenizeDialectInput.tokenize(Api.scala:25)
	scala.meta.tokenizers.Api$XtensionTokenizeInputLike.tokenize(Api.scala:14)
	scala.meta.internal.parsers.ScannerTokens$.apply(ScannerTokens.scala:912)
	scala.meta.internal.parsers.ScalametaParser.<init>(ScalametaParser.scala:33)
	scala.meta.parsers.Parse$$anon$1.apply(Parse.scala:35)
	scala.meta.parsers.Api$XtensionParseDialectInput.parse(Api.scala:25)
	scala.meta.internal.semanticdb.scalac.ParseOps$XtensionCompilationUnitSource.toSource(ParseOps.scala:17)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument.toTextDocument(TextDocumentOps.scala:206)
	scala.meta.internal.pc.SemanticdbTextDocumentProvider.textDocument(SemanticdbTextDocumentProvider.scala:54)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticdbTextDocument$1(ScalaPresentationCompiler.scala:356)
```
#### Short summary: 

file%3A%2F%2F%2FD%3A%2FProjects%2FFProg%2FScala_test%2Fimage_processing%2Fsrc%2Fmain%2Fscala%2FMain.scala:1: error: unclosed comment
/*---------------------ВЫВОД ПИКСЕЛЕЙ /*---------------------*/
^