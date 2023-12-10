// More info here https://www.baeldung.com/scala/file-io

import java.io.FileWriter;
import java.io.File 
import java.io.PrintWriter
import java.io.DataOutputStream
import java.io.BufferedWriter
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader

object Main {
    def main(args: Array[String]): Unit = {

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
        printWriter.printf("This is a %dnd method using %s", new Integer(number), s)
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

        //Method 1
        // Native method to read from a file
        val fileName1 = "test1.txt"
        scala.io.Source.fromFile(fileName1).getLines().foreach(println)

        // Method 2
        // Efficiently reading from a file. Using buffer that provide bulk read from disk?
        val fileName2 = "test2.txt"
        val bufferedSource = scala.io.Source.fromFile(fileName2)
        val text = bufferedSource.getLines().mkString
        println(text)
        bufferedSource.close()

        // Method 3
        // Using Java API
        val fileName3 = "test2.txt"
        val fileReader = new BufferedReader(new FileReader(fileName3))
        def handleRead(line : String) : Unit = {
            val newLine = fileReader.readLine()
            if(newLine != null)  // if there are more lines to read
                handleRead(newLine)
                println(newLine)
        }
        handleRead(fileReader.readLine())
        fileReader.close()

    }
}