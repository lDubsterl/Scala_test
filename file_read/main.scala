import java.io._
    

object Main {
    def readFile(): Unit = {
        val fileHandle = new File("file.txt")
        val fis = new FileInputStream(fileHandle)
        val bis = new BufferedInputStream(fis)
        val dis = new DataInputStream(bis)
   
        try {
            while(dis.available() != 0) {
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

    def main(args: Array[String]): Unit = {
        readFile();
    }
}
