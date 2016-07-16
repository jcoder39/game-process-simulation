package name.bvv.gamesimulation

import java.io.{File, FileInputStream, InputStream}

import akka.actor.Props
import name.bvv.gamesimulation.config.model.Config
import name.bvv.gamesimulation.log.Logger
import name.bvv.gamesimulation.role.{FactoryWorker, Master, StoreKeeper}
import org.yaml.snakeyaml.Yaml

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
/**
 * Created by user on 05.11.2014.
 */
object Main extends App {
  var config: Config = null
  override def main(args: Array[String]) {
    val yaml: Yaml = new Yaml()
    val file = new File("config.yaml")
    println("settings in " + file.getAbsolutePath)
    val in: InputStream = new FileInputStream(file)
    config = yaml.loadAs(in, classOf[Config])

    val system = akka.actor.ActorSystem("system")
    val master = system.actorOf(Props[Master], name = "master")
    val storeKeeper = system.actorOf(Props[StoreKeeper], name = "store_keeper")
    val factoryWorker = system.actorOf(Props[FactoryWorker], name = "factory_worker")

    system.scheduler.schedule(0.seconds, 1.seconds, factoryWorker, "tick")
    master ! "start"
    Logger.start()
  }

}
