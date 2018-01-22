package akkaMultinodePingPong.pinger

import akka.actor.{Actor, ActorRef, Props}
import akkaMultinodePingPong.pinger.Pinger.WelcomePonger

object Pinger {
  def props = Props(new Pinger)
  case class WelcomePonger(ponger: ActorRef)
}

class Pinger extends Actor {
  var count: Long = 0
  var ponger: ActorRef = _
  var continue = false

  def receive: PartialFunction[Any, Unit] = {
    case "welcome" =>
      println("Hello! I'm Pinger. I respond on the messages \"ping\" and I send \"pong\" back")
    case WelcomePonger(pongerRef) =>
      ponger = pongerRef
      println(s"This is my ponger: ${ponger.path}")
    case "ping" =>
      if(continue) {
        count += 1
        sender() ! "pong"
      }
    case "start" =>
      println("I'm pinger! And we are starting the game!")
      ponger ! "pong"
      continue = true
    case "stop" =>
      println(s"I'm Pinger! I was asked to stop. The total number of messages is $count")
      context.system.terminate()
  }
}
