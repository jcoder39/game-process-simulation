package name.bvv.gamesimulation.event

/**
 * Created by user on 07.11.2014.
 */
class Event(id: Int) {
  var status: Event.Event = Event.None
}

object Event extends Enumeration {
  type Event = Value
  val Ok, No, None = Value
}
