#import "LifeSpace.h"
#import <objectbase/Swarm.h>
#import <space.h>

@interface ModelSwarm: Swarm
{
	int worldXSize, worldYSize;
	
	ActivatorSpace *activator;
	TemporarySpace *inhibitor;
	TemporarySpace *inhibitorTmp;
	TemporarySpace *activatorTmp;
	
	id modelActions;
	id modelSchedule;
}

+ createBegin: aZone;
- createEnd;
- buildObjects;
- buildActions;
- activateIn: swarmContext;
- getWorld;
@end
