package name.bvv.gamesimulation.event

import name.bvv.gamesimulation.config.model.Building

/**
 * Created by user on 07.11.2014.
 */
case class ProjectEvent(id: Int, project: Building) extends Event(id){
}
