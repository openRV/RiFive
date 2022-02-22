

val spinalVersion = "1.6.0"

object riscv extends SbtModule {
  def scalaVersion = "2.12.14"

  override def millSourcePath = os.pwd

  def ivyDeps = Agg(
    ivy"com.github.spinalhdl::spinalhdl-core:$spinalVersion",
    ivy"com.github.spinalhdl::spinalhdl-lib:$spinalVersion"
  )

  def scalacPluginIvyDeps = Agg(ivy"com.github.spinalhdl::spinalhdl-idsl-plugin:$spinalVersion")
}