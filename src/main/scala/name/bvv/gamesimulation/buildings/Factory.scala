package name.bvv.gamesimulation.buildings

import name.bvv.gamesimulation.Main
import name.bvv.gamesimulation.config.model.{Ability, Recipe}
import name.bvv.gamesimulation.log.{Log, Logger}

import scala.util.control.Breaks._
import scala.collection.JavaConversions._

/**
 * Created by user on 07.11.2014.
 */
class Factory(var id: Int, var name: String, var abilities: List[Ability]) {
  var recipe: Recipe = null
  var timer, logTime: Long = 0l
  var status: Factory.Value = Factory.Stop
  var rejected: Boolean = false
  def working(): Unit = {
    if(System.currentTimeMillis() >= timer && timer > 0) {
      status = Factory.Done
    }
  }

  def setTimer(): this.type = {
    for(ability: Ability <- abilities){
      if (recipe.getName.equals(ability.getName)) {
        if(logTime == 0){
          logTime = System.currentTimeMillis()
        }
        Logger.add(new Log(Logger.minutes(System.currentTimeMillis() - logTime), this.name, recipe.getName))
        timer = System.currentTimeMillis() + ability.getDuration * 60 * 1000
        status = Factory.Busy
      }
    }
    this
  }

  def changeRecipe(): this.type = {
    var next = false
    var loop = true
    while(loop) {
      breakable {
        for (ability: Ability <- abilities) {
          if (recipe == null) {
            recipe = Main.config.getByName(ability.getName, Main.config.getRecipes.toList)
            loop = false
            break()
          }

          if(next){
            recipe = Main.config.getByName(ability.getName, Main.config.getRecipes.toList)
            loop = false
            break()
          }
          if (recipe.getName.equals(ability.getName)) {
            next = true
          }
        }
      }
    }
    status = Factory.Start
    this
  }
}

object Factory extends Enumeration {
  type Factory = Value
  val Wait, Busy, Done, Stop, Start = Value
}
