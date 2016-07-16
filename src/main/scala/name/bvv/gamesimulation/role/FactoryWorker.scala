package name.bvv.gamesimulation.role

import akka.actor.Actor
import name.bvv.gamesimulation.buildings.Factory
import name.bvv.gamesimulation.event.{False, True, Event}
import name.bvv.gamesimulation.event.factory.Start
import name.bvv.gamesimulation.config.model.Project
import name.bvv.gamesimulation.event.storekeeper.{All, Finish, Put, Get}
import name.bvv.gamesimulation.log.Logger
import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * Created by user on 07.11.2014.
 */
class FactoryWorker extends Actor {
  var workFlag = false
  var started: mutable.Map[String, Factory] = new mutable.HashMap[String, Factory]()
  var queue: mutable.Map[Int, Event] = new mutable.HashMap[Int, Event]()
  val sKeeper = context.actorSelection("/user/store_keeper")
  var reject = 0
  override def receive = {
    case "tick" => {
      work()
    }
    case Start(id, project: Project) => {
      started += project.getName -> new Factory(id, project.getName, project.getAbilities.toList).changeRecipe()
      workFlag = true
    }
    case True(id) => {
      val event: Event = queue.get(id) match {
        case Some(value) => value
        case None => null
      }
      event.status = Event.Ok
      queue.update(id, event)
    }
    case False(id) => {
      val event: Event = queue.get(id) match {
        case Some(value) => value
        case None => null
      }
      event.status = Event.No
      queue.update(id, event)
    }
    case Finish(id) => {
      if(workFlag) {
        workFlag = false
        sKeeper ! All(0, List())
      }
    }
    case All(id, p) => {
      Logger.setProducts(p)
      Logger.dump()
    }
  }

  def work(): Unit = {
    reject = 0
    if(!workFlag) { println("idle"); return }
    started.foreach { entry =>
      val key: String = entry._1
      val factory: Factory = entry._2
      if(factory.rejected){
        reject += 1
      }
      if(reject == started.size){
        self ! Finish(0)
      }
      factory.status match {
        case Factory.Start => {
          if (!queue.contains(factory.id)) {
            val event = Get(factory.id, factory.recipe.components.toList)
            queue += factory.id -> event
            sKeeper ! event
            factory.status = Factory.Wait
          }
        }
        case Factory.Busy => {
          factory.working()
        }
        case Factory.Done => {
          if (!queue.contains(factory.id)) {
            val event = Put(factory.id, List(factory.recipe.getProduct))
            queue += factory.id -> event
            sKeeper ! event
            factory.status = Factory.Wait
          }
        }
        case Factory.Stop => {
        }
        case Factory.Wait => {
          val event: Event = queue.get(factory.id) match {
            case Some(value) => value
            case None => null
          }
          event match {
            case Get(id, products) => {
              if (event.status == Event.Ok) {
                factory.setTimer()
                factory.rejected = false
              } else {
                factory.changeRecipe()
                factory.rejected = true
              }
              queue.remove(factory.id)
            }
            case Put(id, products) => {
              queue.remove(factory.id)
              factory.changeRecipe()
            }
            case _ =>
          }
        }
      }
    }
  }
}
