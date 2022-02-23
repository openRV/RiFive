package riscv

import spinal.core._

import scala.collection.mutable
import scala.language.postfixOps

object Directions extends Enumeration {
  type Direction
  val Backward, Forward = Value
}

case class PipelineReg() extends Component {

  val io = new Bundle {
    val a = in UInt (32 bits)
  }

  private val comp = Component.current
  private val ios: mutable.Set[BaseType] = comp.getAllIo
  private var signals: Vector[(String, Directions.Value, BaseType)] = Vector() //("m",Directions.Forward,Bool()))
  private var signalNum: Int = 0

  /**
   * used to add in / out signal to ios bundle
   */
  def addSignal[T <: BaseType](iSignalName: String, iSignalDirection: Directions.Value, iSignalType: T): Int = {
    val vec = Vector((iSignalName, iSignalDirection, iSignalType))
    signals = signals ++ vec
    /////////////////////////////////////////
    if (iSignalDirection == Directions.Forward) {
      ios += in(iSignalType)
      ios.last.setName("in_i_f_" + iSignalName)
      val from = ios.last
      ios += out(iSignalType)
      ios.last.setName("io_o_f_" + iSignalName)
      val to = ios.last
      to.assignFromBits(from.asBits)
    } else {
      ios += in(iSignalType)
      ios.last.setName("io_i_b_" + iSignalName)
      val from = ios.last
      ios += out(iSignalType)
      ios.last.setName("io_o_b_" + iSignalName)
      val to = ios.last
      to.assignFromBits(from.asBits.resized)
    }
    ////////////////////////////////////////
    signalNum += 2
    signalNum
  }

  def printSignal(): Unit = {
    signals.foreach {
      line => {
        println("\033[0;36m" + line._1 + " " + line._2 + " " + line._3 + "\033[0m")
      }
    }
  }

  def printSignalNum(): Int = {
    println("\033[0;36m io signal Num:" + signalNum + "\033[0m")
    signalNum
  }

  def printSignalIO(): Unit = {
    println("\033[0;36m" + ios + "\033[0m")
  }

}
