package name.bvv.gamesimulation.event.storekeeper

import name.bvv.gamesimulation.config.model.Product
import name.bvv.gamesimulation.event.Event

/**
 * Created by user on 07.11.2014.
 */
case class Get(id: Int, products: List[Product]) extends Event(id) {
}
