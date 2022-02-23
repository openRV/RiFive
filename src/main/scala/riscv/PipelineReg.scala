package riscv

import spinal.core._

import scala.collection.mutable

object Directions extends Enumeration {
  type Direction
  val Backward, Forward = Value
}

case class PipelineReg() extends Component {

  val io = new Bundle {
  }

  var ios: mutable.Set[BaseType] = Component.current.getAllIo
  var signals: Vector[(String, Directions.Value, Data)] = Vector() //("m",Directions.Forward,Bool()))
  var signalNum: Int = 0

  /**
   * used to add in / out signal to ios bundle
   */
  def addSignal[T <: BaseType](iSignalName: String, iSignalDirection: Directions.Value, iSignalType: T): Int = {
    val vec = Vector((iSignalName, iSignalDirection, iSignalType))
    signals = signals ++ vec
    /////////////////////////////////////////
    ios = ios + in(iSignalType)
    ios.last.setName("i_f_" + iSignalName)
    val from = ios.last
    ios = ios + out(iSignalType)
    ios.last.setName("o_f_" + iSignalName)
    val to = ios.last

    to.assignFromBits(from.asBits)
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
