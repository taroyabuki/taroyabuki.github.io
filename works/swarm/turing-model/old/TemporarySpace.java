import swarm.space.*;
import swarm.*;
import swarm.defobj.*;
import swarm.collections.*;

public class TemporarySpace extends Discrete2dImpl{
	int xsize,ysize;
	public TemporarySpace(Zone aZone,int x,int y){
		super(aZone,x,y);
		xsize=x;
		ysize=y;
	}
	
	public double getAverageNearX$Y$inR(int x,int y,int r){
		int i;
		double a=0;
		
		for(i=-r;i<=r;++i)
			a+=getValueAtX$Y((x+i+xsize)%xsize,y);
		
		return(a/(2.0*r+1.0));
	}
	
	public void initializeLattice(){
		int x,y;
		
		for(y=0;y<ysize;++y)
			for(x=0;x<xsize;++x)
				putValue$atX$Y(0,x,y);
	}
}