import swarm.space.*;
import swarm.*;
import swarm.defobj.*;
import swarm.collections.*;

public class ActivatorSpace extends DblBuffer2dImpl{
	TemporarySpace inhibitor;
	TemporarySpace inhibitorTmp;
	TemporarySpace activatorTmp;
	
	double p,w1,w2,m0,m1,d,e,initialProb;
	int ru,rv;
	int xsize,ysize;
	
	public ActivatorSpace(Zone aZone,int x,int y){
		super(aZone,x,y);
		xsize=x;
		ysize=y;
	}
	
	public int howManyNeigborsAtX$Y$inR(int x,int y,int r){
		int i,n=0;
		for(i=-r;i<=r;++i)
		if(getValueAtX$Y((x+i+xsize)%xsize,y)==1) n++;
		return n;
	}
	
	public void setInhibitor(TemporarySpace o){
		inhibitor=o;
	}
	
	public void setInhibitorTmp(TemporarySpace o){
		inhibitorTmp=o;
	}
	
	public void setActivatorTmp(TemporarySpace o){
		activatorTmp=o;
	}
	
	public void setRule(double P,double W1,double W2,double M0,double M1,
	int Ru,int Rv,double D,double E,double InitialProb){
		p=P;
		w1=W1;
		w2=W2;
		m0=M0;
		m1=M1;
		ru=Ru;
		rv=Rv;
		d=D;
		e=E;
		initialProb=InitialProb;
		
		initializeLattice();
		inhibitor.initializeLattice();
	}
	
	public Object stepRule(){
		int x,y;
		int tmpInt=0;
		double tmpFloat=0.0;
		
		for(y=0;y<ysize-1;++y)
			for(x=0;x<xsize;++x)
				putValue$atX$Y(getValueAtX$Y(x,y),x,(y+1));
		
		// step 1
		for(x=0; x<xsize;++x){
			tmpInt=inhibitor.getValueAtX$Y(x,0);
			if(tmpInt>=1) tmpInt=(int)java.lang.Math.round(tmpInt*(1.0-d)-e);
			else tmpInt=0;
			inhibitorTmp.putValue$atX$Y(tmpInt,x,0);
		}
		
		// step 2
		for(x=0; x<xsize;++x){
			tmpInt=getValueAtX$Y(x,0);
			if(tmpInt==0){
				if(Globals.env.uniformDblRand.getDoubleWithMin$withMax(0.0,1.0)<p)
					tmpInt=1;
			}
			activatorTmp.putValue$atX$Y(tmpInt,x,0);
		}
		
		// step 3
		for(x=0; x<xsize;++x){
			tmpInt=inhibitorTmp.getValueAtX$Y(x,0);
			if(activatorTmp.getValueAtX$Y(x,0)==1)
				tmpInt+=w1;
			inhibitorTmp.putValue$atX$Y(tmpInt,x,0);
		}
		
		// step 4
		for(x=0; x<xsize;++x){
			tmpInt=activatorTmp.getValueAtX$Y(x,0);
			tmpFloat=m0+m1*inhibitorTmp.getValueAtX$Y(x,0);
			if(tmpInt==0 && howManyNeigborsAtX$Y$inR(x,0,ru)>java.lang.Math.round(tmpFloat))
				tmpInt=1;
			activatorTmp.putValue$atX$Y(tmpInt,x,0);
		}
		
		// step 5
		for(x=0; x<xsize;++x){
			tmpFloat=inhibitorTmp.getAverageNearX$Y$inR(x,0,rv);
			tmpInt=(int)java.lang.Math.round(tmpFloat);
			inhibitor.putValue$atX$Y(tmpInt,x,0);
		}
		
		// step 6
		for(x=0; x<xsize;++x){
			tmpInt=inhibitor.getValueAtX$Y(x,0);
			if(tmpInt>=w2) tmpInt=0;
			else tmpInt=activatorTmp.getValueAtX$Y(x,0);
			putValue$atX$Y(tmpInt,x,0);
		}
		
		updateLattice();
		return this;
	}
	
	public void initializeLattice(){
		int x;
		
		for(x=0; x<xsize; ++x){
			if(Globals.env.uniformDblRand.getDoubleWithMin$withMax(0.0,1.0)<initialProb)
				putValue$atX$Y(1,x,0);
			else
				putValue$atX$Y(0,x,0);
		}
		updateLattice();
	}
	
	public int ROUND(double A){
		return ( ( (A)-(int)(A)>0.5 ) ? (int)(A+1) : (int)(A) );
	}
}
