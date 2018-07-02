#import "ModelSwarm.h"
#import <random.h>
#import <activity.h>
#import <collections.h>

@implementation ModelSwarm

- getWorld
{
	return activator;
}


+ createBegin: aZone 
{
	ModelSwarm *obj;
	id <ProbeMap> probeMap;
	
	obj = [super createBegin: aZone];
	obj->worldXSize = 80;
	obj->worldYSize = 80;
	
	probeMap = [EmptyProbeMap createBegin: aZone];
	[probeMap setProbedClass: [self class]];
	probeMap = [probeMap createEnd];
	
	[probeMap addProbe: [probeLibrary getProbeForVariable: "worldXSize"
	                                    inClass: [self class]]];
	[probeMap addProbe: [probeLibrary getProbeForVariable: "worldYSize"
	                                    inClass: [self class]]];
	
	[probeLibrary setProbeMap: probeMap For: [self class]];
	
	return obj;
}


- createEnd
{
	return [super createEnd];
}


- buildObjects
{
	inhibitor=[TemporarySpace createBegin: self];
	[inhibitor setSizeX: worldXSize Y: 2];
	inhibitor=[inhibitor createEnd];
	[inhibitor initializeLattice];
	
	inhibitorTmp=[TemporarySpace createBegin: self];
	[inhibitorTmp setSizeX: worldXSize Y: 2];
	inhibitorTmp=[inhibitorTmp createEnd];
	[inhibitorTmp initializeLattice];
	
	activatorTmp=[TemporarySpace createBegin: self];
	[activatorTmp setSizeX: worldXSize Y: 2];
	activatorTmp=[activatorTmp createEnd];
	[activatorTmp initializeLattice];
	
	activator = [ActivatorSpace createBegin: self];
	[activator setSizeX: worldXSize Y: worldYSize];
	[activator setNumStates: 2];
	[activator setInhibitor: inhibitor];
	[activator setInhibitorTmp: inhibitorTmp];
	[activator setActivatorTmp: activatorTmp];
	activator = [activator createEnd];
	
	[activator setRule_p: 0.002 w1: 8.0 w2: 21.0 m0: 0.0 m1: 1.0 ru: 1 rv: 0 d: 0 e: 1.0 initialProb: 0.01];
	
	return self;
}


- buildActions
{
	modelActions = [ActionGroup create: self];
	[modelActions createActionTo: activator    message: M(stepRule)];
	
	modelSchedule = [Schedule createBegin: self];
	[modelSchedule setRepeatInterval: 1];
	modelSchedule = [modelSchedule createEnd];
	[modelSchedule at: 0 createAction: modelActions]; 
	
	return self;
}


- activateIn: swarmContext
{
	[super activateIn: swarmContext];
	[modelSchedule activateIn: self];
	
	return [self getSwarmActivity];
}
@end
