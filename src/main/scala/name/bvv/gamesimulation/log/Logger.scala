package name.bvv.gamesimulation.log

import java.io.{File, FileWriter}
import java.util

import name.bvv.gamesimulation.config.model.Product
import org.yaml.snakeyaml.Yaml

import scala.beans.BeanProperty
import scala.collection.JavaConversions._

/**
 * Created by user on 10.11.2014.
 */
object Logger {
  @BeanProperty var productionStartLog: util.List[Log] = new util.ArrayList[Log]()
  @BeanProperty var totalTime: Long = 0l
  @BeanProperty var products: util.List[Product] = new util.ArrayList[Product]()

  def start(): Unit = {
    totalTime = System.currentTimeMillis()
    println("log start")
  }

  def add(log: Log):Unit = {
    productionStartLog += log
  }

  def dump(): Unit = {
    println("finished")
    val file = new File("log.yaml")
    println("log saved in " + file.getAbsolutePath)
    totalTime = minutes(System.currentTimeMillis() - totalTime)
    val yaml: Yaml = new Yaml()
    val writer = new FileWriter(file)
    yaml.dump(this, writer)
    writer.close()
  }

  def minutes(timestamp: Long): Int = {
    var time = timestamp
    time /= 60 * 1000
    time.toInt
  }
}
