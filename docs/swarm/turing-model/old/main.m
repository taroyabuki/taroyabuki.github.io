#import <simtools.h>
#import "ObserverSwarm.h"

int
main (int argc, const char **argv)
{
	ObserverSwarm *observerSwarm;
	
	initSwarm (argc, argv);
	
	observerSwarm =  [ObserverSwarm create: globalZone];
	
	[observerSwarm buildObjects];
	[observerSwarm buildActions];
	[observerSwarm activateIn: nil];
	
	[observerSwarm go];
	
	return 0;
}
