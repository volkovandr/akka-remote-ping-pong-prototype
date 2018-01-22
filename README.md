# akka-remote-ping-pong-prototype

An example implementation of the ping-pong interaction between remote Akka actors over network

To run it build the Docker images first with

```
sbt docker:publishLocal
```

And then run the docker composition with

```
docker-compose up
```
