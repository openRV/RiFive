package riscv

import riscv.config._
import spinal.core._

import scala.io.BufferedSource

object main {
  def main(args: Array[String]): Unit = {
    val file: BufferedSource = scala.io.Source.fromFile(IDATA_INIT_FILE_PATH)
    val iniData: Array[Bits] = file.getLines().toArray.map(B(_))
    val IData_File_Length: Int = iniData.length
    // read init inst and convert to bits array
    file.close()

    iniData.foreach({
      line => {
        println(line)
      }
    })
  }
}
