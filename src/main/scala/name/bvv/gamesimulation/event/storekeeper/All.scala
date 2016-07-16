package name.bvv.gamesimulation.event.storekeeper

import name.bvv.gamesimulation.event.Event
import name.bvv.gamesimulation.config.model.Product

/**
 * Created by user on 07.11.2014.
 */
case class All(id: Int, products: List[Product]) extends Event(id) {
}
