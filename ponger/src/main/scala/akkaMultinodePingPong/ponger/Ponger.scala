package akkaMultinodePingPong.ponger

import akka.actor.{Actor, Props}

object Ponger {
  def props = Props(new Ponger)
}

class Ponger extends Actor {
  var count: Long = 0
  var continue = true

  def receive: PartialFunction[Any, Unit] = {
    case "welcome" =>
      println("Hello! I'm Ponger. I respond on the messages \"pong\" and I send \"ping\" back")
    case "pong" =>
      if(continue) {
        count += 1
        sender() ! "ping"
      }
    case "stop" =>
      println(s"I'm Ponger! I was asked to stop. The total number of messages is $count")
      continue = false
      context.system.terminate()
  }
}
