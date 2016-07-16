package name.bvv.gamesimulation.config.model

import scala.beans.BeanProperty

/**
 * Created by user on 05.11.2014.
 */
class Product(
               @BeanProperty var id: String = "",
               @BeanProperty var num: Int = 0) {

  def this() = this("", 0)
}
