import swarm.*;
import swarm.objectbase.*;
import swarm.defobj.*;

public class Cell extends SwarmObjectImpl {
	double u,v,u1,u2,v1,v2;
	
	int ru,rv;
	double w1,w2,m0,m1,p,d,e;
	
	int offset;
	PatternSpace patternSpace;
	
	public Cell(Zone aZone,int Offset,PatternSpace ps){
		super(aZone);
		offset=Offset;
		patternSpace=ps;
	}
	
	public double getU(){
		return u;
	}
	
	public double getV2(){
		return v2;
	}

	public void activate(){
		u=1;
		initialize();
	}
	
	public void inhibit(){
		u=0;
		initialize();
	}
	
	void initialize(){
		v=0;
		u1=0;
		u2=0;
		v1=0;
		v2=0;
	}
	
	public void setParams(int Ru,int Rv,
		double W1,double W2,double M0,double M1,double P,double D,double E){
		ru=Ru;
		rv=Rv;
		w1=W1;
		w2=W2;
		m0=M0;
		m1=M1;
		p=P;
		d=D;
		e=E;
	}
	
	public void step1(){
		if(v>=1) v1=(int)(v*(1-d)-e);
		else v1=0;
	}
		
	public void step2(){
		if(u==0){
			if(Globals.env.uniformDblRand.getDoubleWithMin$withMax(0.0,1.0)<p)
				u1=1;
			else u1=0;// ‚±‚Ì•”•ªA˜_•¶‚É‘‚¢‚Ä‚È‚¢‚¯‚Ç‘åŽ–
		}
		else
			u1=u;
	}
		
	public void step3(){
		if(u1==1) v2=v1+w1;
		else v2=v1;
	}
		
	public void step4(){
		int nu=0;
		for(int i=-ru;i<=ru;++i)
			if(patternSpace.getUAt(offset+i)==1) nu++;
		if(u1==0 && nu>java.lang.Math.round(m0+m1*v2)) u2=1;
		else u2=u1;
	}
	
	public void step5(){
		double meanV2=0;
		for(int i=-rv;i<=rv;++i){
			meanV2+=patternSpace.getV2At(offset+i);
		}
		meanV2/=(2.*rv+1.);
		v=(int)java.lang.Math.round(meanV2);
	}
	
	public void step6(){
		if(v>=w2)
			u=0;
		else
			u=u2;
	}
}
