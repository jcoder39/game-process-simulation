package name.bvv.gamesimulation.role

import akka.actor.Actor
import name.bvv.gamesimulation.Main
import name.bvv.gamesimulation.event.factory.Start
import name.bvv.gamesimulation.event.{ProjectEvent, True, False}
import name.bvv.gamesimulation.config.model.Project
import scala.collection.JavaConversions._

/**
 * Created by user on 06.11.2014.
 */
class Master() extends Actor {
  val sKeeper = context.actorSelection("/user/store_keeper")
  val fWorker = context.actorSelection("/user/factory_worker")
  override def receive = {
    case True(id) =>
    case False(id) =>
    case "start" => {
      var count = 1
      for(p: Project <- Main.config.projects){
        fWorker ! Start(count, p)
        count +=1
      }
    }
    case ProjectEvent(id, building) => {
      for(project: Project <- Main.config.projects){
      }
    }
  }
}
