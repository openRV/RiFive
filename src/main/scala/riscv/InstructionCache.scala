
package riscv

import riscv.config._
import spinal.core._

import scala.io.BufferedSource
import scala.language.postfixOps

/**
 * first version of the simplest dul port ICache
 *
 * with 2 sync read port, no misaligned access allowed
 * can only be init, no read port
 * will be changed in future version
 */
case class InstructionCache() extends Component {

  val io = new Bundle {
    val Addr1: UInt = in UInt (DATA_WIDTH bits)
    val Addr2: UInt = in UInt (DATA_WIDTH bits)
    val Inst1: Bits = out Bits (INST_WIDTH bits)
    val Inst2: Bits = out Bits (INST_WIDTH bits)
  }
  val IData_File_Length: Int = iniData.length
  // read init inst and convert to bits array
  private val file: BufferedSource = scala.io.Source.fromFile(IDATA_INIT_FILE_PATH)
  file.close()
  private val iniData: Array[Bits] = file.getLines().toArray.map(B(_))
  private val ICache: Mem[Bits] = Mem(Bits(INST_WIDTH bits), IData_File_Length) init iniData
  io.Inst1 := ICache.readSync((io.Addr1 >> 2).resized)
  io.Inst2 := ICache.readSync((io.Addr2 >> 2).resized)
}
