package akkaMultinodePingPong.pinger

import akka.actor.{ActorRef, ActorSystem}

object Main extends App {
  val system = ActorSystem("Pinger")

  val pinger: ActorRef = system.actorOf(Pinger.props, "pinger")

  sys.addShutdownHook {
    println("Terminating...")
    system.terminate()
  }
}
