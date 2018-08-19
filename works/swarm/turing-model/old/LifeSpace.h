#import <space/Ca2d.h>
#import <space/Discrete2d.h>

@interface ActivatorSpace: Ca2d 
{
	id inhibitor;
	id inhibitorTmp;
	id activatorTmp;
	
	float p,w1,w2,m0,m1,d,e,initialProb;
	int ru,rv;
}

- (int)howManyNeighborsAtX: (int)x Y: (int)y inR: (int)r;
- setInhibitor: o;
- setInhibitorTmp: o;
- setActivatorTmp: o;
- setRule_p: (float)a w1: (float)b w2: (float)c m0: (float)d m1: (float)ee ru: (int)f rv: (int)g d: (float)h e: (float)i initialProb: (float)j;
@end


@interface TemporarySpace: Discrete2d
{
}

- (float)getAverageNearX: (int)x Y: (int)y inR: (int)r;
- initializeLattice;
@end
