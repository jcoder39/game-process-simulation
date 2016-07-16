package name.bvv.gamesimulation.config.model

import java.util

import scala.beans.BeanProperty

/**
 * Created by user on 05.11.2014.
 */
class Project extends Base{
  @BeanProperty var abilities: util.List[Ability] = null
}
