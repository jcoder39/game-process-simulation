package name.bvv.gamesimulation.config.model

import java.util

import scala.beans.BeanProperty

/**
 * Created by user on 05.11.2014.
 */
class Recipe extends Base{
  @BeanProperty var product: Product = null
  @BeanProperty var components: util.List[Product] = null
}
