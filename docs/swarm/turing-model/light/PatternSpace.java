import swarm.space.*;
import swarm.defobj.*;
import swarm.*;

public class PatternSpace extends Discrete2dImpl{
	int width,history;
	double[] u,u1,u2,v,v1,v2;

	int ru,rv;
	double w1,w2,m0,m1,p,d,e,initProb;	

	public PatternSpace(Zone aZone,int W,int H){
		super(aZone,W,H);
		width=W;
		history=H;
		u=new double[width];
		u1=new double[width];
		u2=new double[width];
		v=new double[width];
		v1=new double[width];
		v2=new double[width];
	}
	
	int index(int i){
		return (i+width)%width;
	}

	public void setParams(int Ru,int Rv,
		double W1,double W2,double M0,double M1,double P,double D,double E,double InitProb){
		ru=Ru;
		rv=Rv;
		w1=W1;
		w2=W2;
		m0=M0;
		m1=M1;
		p=P;
		d=D;
		e=E;
		initProb=InitProb;
		
		for(int i=0;i<width;++i){
			if(Globals.env.uniformDblRand.getDoubleWithMin$withMax(0.0,1.0)<initProb)
				u[index(i)]=1;
			else
				u[index(i)]=0;
			u1[index(i)]=0;
			u2[index(i)]=0;
			v[index(i)]=0;
			v1[index(i)]=0;
			v2[index(i)]=0;
		}
	}
	
	public void update(){
		step1();
		step2();
		step3();
		step4();
		step5();
		step6();
		
		for(int h=0;h<history-1;++h)
			for(int i=0;i<width;++i)
				putValue$atX$Y(getValueAtX$Y(i,h+1),i,h);
		for(int i=0;i<width;++i)
			putValue$atX$Y((int)u[index(i)],i,history-1);
	}
	
	public void step1(){
		for(int i=0;i<width;++i){
			if(v[index(i)]>=1) v1[index(i)]=(int)(v[index(i)]*(1-d)-e);
			else v1[index(i)]=0;
		}
	}
		
	public void step2(){
		for(int i=0;i<width;++i){
			if(u[index(i)]==0){
				if(Globals.env.uniformDblRand.getDoubleWithMin$withMax(0.0,1.0)<p)
					u1[index(i)]=1;
				else u1[index(i)]=0;// ‚±‚Ì•”•ªA˜_•¶‚É‘‚¢‚Ä‚È‚¢‚¯‚Ç‘åŽ–
			}
			else
				u1[index(i)]=u[index(i)];
		}
	}
		
	public void step3(){
		for(int i=0;i<width;++i){
			if(u1[index(i)]==1) v2[index(i)]=v1[index(i)]+w1;
			else v2[index(i)]=v1[index(i)];
		}
	}
		
	public void step4(){
		for(int i=0;i<width;++i){
			int nu=0;
			for(int j=-ru;j<=ru;++j)
				if(u[index(i+j)]==1) nu++;
			if(u1[index(i)]==0 && nu>java.lang.Math.round(m0+m1*v2[index(i)])) u2[index(i)]=1;
			else u2[index(i)]=u1[index(i)];
		}
	}
	
	public void step5(){
		for(int i=0;i<width;++i){
			double meanV2=0;
			for(int j=-rv;j<=rv;++j){
				meanV2+=v2[index(i+j)];
			}
			meanV2/=(2.*rv+1.);
			v[index(i)]=(int)java.lang.Math.round(meanV2);
		}
	}
	
	public void step6(){
		for(int i=0;i<width;++i){
			if(v[index(i)]>=w2)
				u[index(i)]=0;
			else
				u[index(i)]=u2[index(i)];
		}
	}
}
