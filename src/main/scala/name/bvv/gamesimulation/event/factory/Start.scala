package name.bvv.gamesimulation.event.factory

import name.bvv.gamesimulation.config.model.Project
import name.bvv.gamesimulation.event.Event

/**
 * Created by user on 07.11.2014.
 */
case class Start(id: Int, project: Project) extends Event(id){

}
