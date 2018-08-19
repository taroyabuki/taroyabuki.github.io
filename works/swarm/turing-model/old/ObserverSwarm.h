#import "ModelSwarm.h"
#import <simtoolsgui/GUISwarm.h>

@interface ObserverSwarm: GUISwarm
{
	int displayFrequency;
	
	id displayActions;
	id displaySchedule;
	
	ModelSwarm *modelSwarm;
	
	id <Colormap> colorMap;
	id <ZoomRaster> worldRaster;
	id <Value2dDisplay> activatorDisplay;
}

+ createBegin: aZone;
- createEnd;
- buildObjects;
- buildActions;
- activateIn: swarmContext;
@end
