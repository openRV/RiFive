package riscv

import riscv.config._
import spinal.core._

import scala.language.postfixOps

object RISCV {

  def InstWidth: Int = INST_WIDTH

  def getInstType(Inst: Bits): Bits = {
    var a: Bits = InstType.Undefined
    switch(BitRange(Inst).opcode) {
      is(RISCV.InstType.I) {
        a = InstType.I
      }
      is(RISCV.InstType.R) {
        a = InstType.R
      }
      is(RISCV.InstType.U) {
        a = InstType.U
      }
      is(RISCV.InstType.S) {
        a = InstType.S
      }
      is(RISCV.InstType.J) {
        a = InstType.J
      }
      is(RISCV.InstType.B) {
        a = InstType.B
      }
      is(RISCV.InstType.R4) {
        a = InstType.R4
      }
      default {
        InstType.Undefined
      }
    }
    a
  }

  case class BitRange(Inst: Bits) extends Area {
    def opcode: Bits = Inst(6 downto 0)

    def func7: Bits = Inst(31 downto 25)

    def rd: Bits = Inst(11 downto 7)

    def func3: Bits = Inst(14 downto 12)

    def rs1: Bits = Inst(19 downto 15)

    def rs2: Bits = Inst(24 downto 20)

    def rs3: Bits = Inst(31 downto 27)

    def csr: Bits = Inst(31 downto 20)
  }

  case class Imm(Inst: Bits, SignExtended: Bool) extends Area {
    private val imm = UInt(32 bits)

    private def Type = getInstType(Inst)

    switch(Type) {
      is(InstType.I) {
        imm := (Inst(31 downto 20)).resized
      }
      is(InstType.S) {
        imm := (Inst(31 downto 25) ## Inst(11 downto 7)).resized
      }
      is(InstType.B) {
        imm := (Inst(31) ## Inst(7) ## Inst(30 downto 25) ## Inst(11 downto 8)).resized
      }
      is(InstType.U) {
        imm := (Inst(31 downto 12) ## B("x000")).resized
      }
      is(InstType.J) {
        imm := (Inst(31) ## Inst(19 downto 12) ## Inst(20) ## Inst(30 downto 21)).resized
      }
      default {
        imm := 0
      }
    }
  }

  object InstType extends SpinalEnum {
    val R: Bits = spinal.core.B("0110011")
    val I: Bits = spinal.core.B("0000011")
    val S: Bits = spinal.core.B("0100011")
    val B: Bits = spinal.core.B("1100011")
    val J: Bits = spinal.core.B("1101111")
    val U: Bits = spinal.core.B("0110111")
    val R4: Bits = spinal.core.B("")
    val Undefined: Bits = spinal.core.B("0000000")
  }

}
