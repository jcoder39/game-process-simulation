package name.bvv.gamesimulation.role

import java.util

import akka.actor.Actor
import name.bvv.gamesimulation.Main
import name.bvv.gamesimulation.event.{True, False}
import name.bvv.gamesimulation.event.storekeeper.{All, Put, Get}
import name.bvv.gamesimulation.config.model.Product

import scala.collection.mutable
import scala.collection.JavaConversions._

/**
 * puts and gets products to storage
 */
class StoreKeeper extends Actor {
  var no = false
  var products: mutable.Map[String, Int] = mutable.HashMap[String, Int]()
  for(product <- Main.config.products){
    products += product.id -> product.num
  }
  var cache: mutable.Map[String, Int] = mutable.HashMap[String, Int]()
  val fWorker = context.actorSelection("/user/factory_worker")
  override def receive = {
    case Get(id, p) => {
      getProducts(p)
      if(no){
        sender ! False(id)
      } else {
        sender ! True(id)
      }
    }
    case Put(id, p) => {
      putProducts(p)
      if(no){
        sender ! False(id)
      } else {
        sender ! True(id)
      }
    }
    case All(id, p) => {
      val pList = new util.ArrayList[Product]()
      this.products.foreach { entry =>
        val key = entry._1
        val value = entry._2
        pList.add(new Product(key, value))
      }
      fWorker ! All(id, pList.toList)
    }
  }

  def putProducts(products: List[Product]): Unit = {
    cache.clear()
    no = false
    for(product <- products){
      val count = this.products.getOrElse(product.id, 0)
      cache += product.id -> (count + product.num)
    }
    if(cache.nonEmpty){
      cache.foreach { entry =>
        val product = entry._1
        val num = entry._2
        this.products.update(product, num)
        no = true
      }
    }
  }

  def getProducts(products: List[Product]): Unit = {
    cache.clear()
    no = false
    for (product <- products) {
      if (!this.products.contains(product.id)) {
        no = true
      }
    }
    if(!no){
      for(product <- products){
        val count = this.products.getOrElse(product.id, 0)
        if(count != 0 && !(count - product.num < 0)) {
          cache += product.id -> (count - product.num)
        }
      }
      if(cache.nonEmpty && products.size == cache.size){
        cache.foreach { entry =>
          val product = entry._1
          val num = entry._2
          if(num > 0) {
            this.products.update(product, num)
          } else {
            this.products.remove(product)
          }
        }
      } else {
        no = true
      }
    }
  }
}
