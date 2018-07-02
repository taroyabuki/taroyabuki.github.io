ifeq ($(SWARMHOME),)
SWARMHOME=../../swarm-2.0
endif
APPLICATION=turing
APPVERSION=2.0
BUGADDRESS=bug-swarm@santafe.edu
OBJECTS=main.o ObserverSwarm.o ModelSwarm.o LifeSpace.o
include $(SWARMHOME)/etc/swarm/Makefile.appl

main.o: main.m ObserverSwarm.h LifeSpace.h 
ModelSwarm.o: ModelSwarm.h ModelSwarm.m
LifeSpace.o: LifeSpace.h LifeSpace.m
ObserverSwarm.o: ObserverSwarm.h ObserverSwarm.m ModelSwarm.h
