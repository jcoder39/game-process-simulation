package name.bvv.gamesimulation.config.model

import scala.beans.BeanProperty

/**
 * Created by user on 05.11.2014.
 */
class Ability {
  @BeanProperty var name: String = ""
  @BeanProperty var duration: Int = 0
}
