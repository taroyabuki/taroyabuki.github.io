#import "LifeSpace.h"
#import <random.h>
#define ROUND(A) ( ( (A)-(int)(A)>0.5 ) ? (int)(A+1) : (int)(A) )

@implementation ActivatorSpace

- setRule_p: (float)a w1: (float)b w2: (float)c m0: (float)dd m1: (float)ee ru: (int)f rv: (int)g d: (float)h e: (float)i initialProb: (float)j;
{
	p=a;
	w1=b;
	w2=c;
	m0=dd;
	m1=ee;
	ru=f;
	rv=g;
	d=h;
	e=i;
	initialProb=j;
	
	[self initializeLattice];
	[inhibitor initializeLattice];
	
	return self;
}


- setInhibitor: o
{
	inhibitor=o;
	return self;
}


- setInhibitorTmp: o
{
	inhibitorTmp=o;
	return self;
}


- setActivatorTmp: o
{
	activatorTmp=o;
	return self;
}


-stepRule
{
	int x,y;
	int tmpInt=0;
	float tmpFloat=0.0;
	
	for(y=0;y<ysize-1;++y)
		for(x=0;x<xsize;++x)
			[self putValue: [self getValueAtX: x Y: y] atX: x Y: (y+1)];
	
	
	// step 1
	
	for(x=0; x<xsize;++x){
		tmpInt=[inhibitor getValueAtX: x Y: 0];
		if(tmpInt>=1) tmpInt=ROUND(tmpInt*(1.0-d)-e);
		else tmpInt=0;
		[inhibitorTmp putValue: tmpInt atX: x Y: 0];
	}
	
	
	// step 2
	
	for(x=0; x<xsize;++x){
		tmpInt=[self getValueAtX: x Y: 0];
		if(tmpInt==0){
			if([uniformDblRand getDoubleWithMin: 0.0 withMax: 1.0]<p)
				tmpInt=1;
		}
		[activatorTmp putValue: tmpInt atX: x Y: 0];
	}
	
	
	// step 3
	
	for(x=0; x<xsize;++x){
		tmpInt=[inhibitorTmp getValueAtX: x Y: 0];
		if([activatorTmp getValueAtX: x Y: 0]==1)
			tmpInt+=w1;
		[inhibitorTmp putValue: tmpInt atX: x Y: 0];
	}
	
	
	// step 4
	
	for(x=0; x<xsize;++x){
		tmpInt=[activatorTmp getValueAtX: x Y: 0];
		tmpFloat=m0+m1*[inhibitorTmp getValueAtX: x Y: 0];
		if(tmpInt==0 && [self howManyNeighborsAtX: x Y: 0 inR: ru]>ROUND(tmpFloat))
			tmpInt=1;
		[activatorTmp putValue: tmpInt atX: x Y: 0];
	}
	
	
	// step 5
	
	for(x=0; x<xsize;++x){
		tmpFloat=[inhibitorTmp getAverageNearX: x Y: 0 inR: rv];
		tmpInt=ROUND(tmpFloat);
		[inhibitor putValue: tmpInt atX: x Y: 0];
	}
	
	
	// step 6
	
	for(x=0; x<xsize;++x){
		tmpInt=[inhibitor getValueAtX: x Y: 0];
		if(tmpInt>=w2) tmpInt=0;
		else tmpInt=[activatorTmp getValueAtX: x Y: 0];
		[self putValue: tmpInt atX: x Y: 0];
	}
	
	[self updateLattice];
	
	return self;
}


- (int)howManyNeighborsAtX: (int)x Y: (int)y inR: (int)r
{
	int i,n=0;
	
	for(i=-r;i<=r;++i)
		if([self getValueAtX: (x+i)%xsize Y: y]) n++;
	
	return(n);
}


- initializeLattice
{
	int x;
	
	for(x=0; x<xsize; ++x)
		if([uniformDblRand getDoubleWithMin: 0.0 withMax: 1.0]<initialProb)
			[self putValue: 1 atX: x Y: 0];
		else
			[self putValue: 0 atX: x Y: 0];
	
	[self updateLattice];
	
	return self;
}
@end


@implementation TemporarySpace

- (float)getAverageNearX: (int)x Y: (int)y inR: (int)r
{
	int i;
	float a=0;
	
	for(i=-r;i<=r;++i)
		a+=[self getValueAtX: (x+i)%xsize Y: y];
	
	return(a/(2.0*r+1.0));
}


- initializeLattice
{
	int x,y;
	
	for(y=0;y<ysize;++y)
		for(x=0;x<xsize;++x)
			[self putValue: 0 atX: x Y: y];
	
	return self;
}
@end
