package akkaMultinodePingPong.ponger

import akka.actor.{ActorRef, ActorSystem}

object Main extends App {
  val system = ActorSystem("Ponger")

  val pinger: ActorRef = system.actorOf(Ponger.props, "ponger")

  sys.addShutdownHook {
    println("Terminating...")
    system.terminate()
  }
}
