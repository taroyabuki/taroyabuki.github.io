#import "ObserverSwarm.h"
#import "ModelSwarm.h"
#import <activity.h>
#import <simtoolsgui.h>

@implementation ObserverSwarm

+ createBegin: aZone
{
	ObserverSwarm *obj;
	id <ProbeMap> probeMap;
	
	obj = [super createBegin: aZone];
	obj->displayFrequency = 1;
	
	probeMap = [EmptyProbeMap createBegin: aZone];
	[probeMap setProbedClass: [self class]];
	probeMap = [probeMap createEnd];
	
	[probeMap addProbe: [probeLibrary getProbeForVariable: "displayFrequency"
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
	[super buildObjects];
	
	  if ((modelSwarm = 
		[lispAppArchiver getWithZone: self key: "modelSwarm"]) == nil)
			raiseEvent(InvalidOperation,
			"Can't find the modelSwarm parameters");
	
	CREATE_PROBE_DISPLAY (modelSwarm);
	CREATE_PROBE_DISPLAY (self);
	
	[controlPanel setStateStopped];
	
	[modelSwarm buildObjects];
	
	colorMap = [Colormap create: self];
	[colorMap setColor: 0 ToName: "white"];
	[colorMap setColor: 1 ToName: "black"];
	
	worldRaster = [ZoomRaster create: self];
	[worldRaster setColormap: colorMap];
	[worldRaster setZoomFactor: 1];
	[worldRaster setWidth: [[modelSwarm getWorld] getSizeX]
	             Height: [[modelSwarm getWorld] getSizeY]];
	[worldRaster setWindowTitle: "Activator"];
	[worldRaster pack];
	
	activatorDisplay = [Value2dDisplay createBegin: self];
	[activatorDisplay setDisplayWidget: worldRaster colormap: colorMap];
	[activatorDisplay setDiscrete2dToDisplay: [modelSwarm getWorld]];
	activatorDisplay = [activatorDisplay createEnd];
	
	return self;
}


- buildActions
{
	[super buildActions];
	
	[modelSwarm buildActions];
	
	displayActions = [ActionGroup create: self];
	[displayActions createActionTo: activatorDisplay         message: M(display)];
	[displayActions createActionTo: worldRaster         message: M(drawSelf)];
	[displayActions createActionTo: actionCache         message: M(doTkEvents)];
	[displayActions createActionTo: probeDisplayManager message: M(update)];
	
	displaySchedule = [Schedule createBegin: self];
	[displaySchedule setRepeatInterval: displayFrequency];
	displaySchedule = [displaySchedule createEnd];
	[displaySchedule at: 0 createAction: displayActions];
	
	return self;
}


- activateIn: swarmContext
{
	[super activateIn: swarmContext];
	[modelSwarm activateIn: self];
	[displaySchedule activateIn: self];
	
	return [self getSwarmActivity];
}
@end
