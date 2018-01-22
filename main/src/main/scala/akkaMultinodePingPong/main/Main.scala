package akkaMultinodePingPong.main

import akka.actor.{ActorRef, ActorSelection, ActorSystem}
import akkaMultinodePingPong.pinger.Pinger
import akkaMultinodePingPong.pinger.Pinger.WelcomePonger
import akkaMultinodePingPong.ponger.Ponger

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  val system = ActorSystem("Main")
  implicit val timeoute: akka.util.Timeout = 60 seconds


  val pingerSelection: ActorSelection = system.actorSelection("akka://Pinger@pinger:8000/user/pinger")
  val pongerSelection: ActorSelection = system.actorSelection("akka://Ponger@ponger:8000/user/ponger")

  val pingerActor: ActorRef = Await.result(pingerSelection.resolveOne(), Duration.Inf)
  val pongerActor: ActorRef = Await.result(pongerSelection.resolveOne(), Duration.Inf)


  //val pingerActor = system.actorOf(Pinger.props, "Pinger")
  //val pongerActor = system.actorOf(Ponger.props, "Ponger")

  println("Main: Okay, we are ready to start")

  pingerActor ! WelcomePonger(pongerActor)

  pingerActor ! "welcome"
  pongerActor ! "welcome"

  pingerActor ! "start"

  Thread.sleep(60000)

  pingerActor ! "stop"
  pongerActor ! "stop"

  println("Main: We are done here.")

  system.terminate()
}
