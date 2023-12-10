file:///D:/Projects/FProg/Scala_test/22/src/main/scala/Main.scala
### scala.reflect.internal.FatalError: no context found for source-file:///D:/Projects/FProg/Scala_test/22/src/main/scala/Main.scala,line-4,offset=240

occurred in the presentation compiler.

action parameters:
offset: 240
uri: file:///D:/Projects/FProg/Scala_test/22/src/main/scala/Main.scala
text:
```scala
/* ------------------------------------------- РАСПОЗНОВАНИЕ ИМЕНОВАННЫХ СУЩНОСТЕЙ -------------------------------------------*/
// import java.io.FileInputStream
// import opennlp.tools.namefind.{NameFinderME, TokenNameFinderModel}
// i@@mport opennlp.tools.tokenize._
// import opennlp.tools.util.Span
  
//   object Main {
//       def main(args:Array[String]): Unit = {
//         val tokenizerModelIn = new FileInputStream("en-token.bin")
//         val tokenizerModel = new opennlp.tools.tokenize.TokenizerModel(tokenizerModelIn)
//         val tokenizer = new opennlp.tools.tokenize.TokenizerME(tokenizerModel)
//         // Load NER model
//         val nerModelIn = new FileInputStream("en-ner-person.bin")
//         val nerModel = new TokenNameFinderModel(nerModelIn)
//         val ner = new NameFinderME(nerModel)
  
//     	// Define some sample text
//         val text = "John Smith is a software engineer at Google. Joe Biden is a president of USA."
  
//         // Tokenize the text
//         val tokens = tokenizer.tokenize(text)
  
//         // Find the named entities in the text
//         val spans = ner.find(tokens)
  
//         // Print the named entities and their types
//         for(span <- spans) {
//               val entityType = span.getType
//               val entity = tokens.slice(span.getStart, span.getEnd).mkString(" ")
//               println(s"$entityType: $entity")
//         }
//     }
// }

import java.io.FileInputStream
import opennlp.tools.chunker.{ChunkerME, ChunkerModel}
import opennlp.tools.namefind.{NameFinderME, TokenNameFinderModel}
import opennlp.tools.postag.{POSModel, POSTaggerME}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import opennlp.tools.util.Span

object Main25 {
  def main(args: Array[String]): Unit = {
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

scala.reflect.internal.FatalError: no context found for source-file:///D:/Projects/FProg/Scala_test/22/src/main/scala/Main.scala,line-4,offset=240