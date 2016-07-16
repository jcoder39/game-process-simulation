package name.bvv.gamesimulation.log

import scala.beans.BeanProperty

/**
 * Created by user on 10.11.2014.
 */
class Log(
           @BeanProperty var time: Long = 0,
           @BeanProperty var factory: String = "",
           @BeanProperty var recipet: String = ""
           ) {
}
