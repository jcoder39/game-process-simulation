package name.bvv.gamesimulation.config.model

import java.util

import scala.beans.BeanProperty
import scala.collection.JavaConversions._
/**
 * Created by user on 05.11.2014.
 */
class Config {
  @BeanProperty var recipes: util.List[Recipe] = null
  @BeanProperty var products: util.List[Product] = null
  @BeanProperty var buildings: util.List[Building] = null
  @BeanProperty var projects: util.List[Project] = null

  def getByName[T <: Base](name: String, list: List[T]): T = {
    for(element <- list){
      if(element.name.equals(name)){
        return element
      }
    }
    null.asInstanceOf[T]
  }
}
