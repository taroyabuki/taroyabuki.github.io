import swarm.*;
import swarm.activity.*;
import swarm.defobj.*;
import swarm.objectbase.*;

public class ModelSwarm extends SwarmImpl{
	public int ru,rv;
	public double w1,w2,m0,m1,p,d,e,initProb;
	
	int width,history;
	PatternSpace patternSpace;
	
	ActionGroup modelActions;
	Schedule modelSchedule;
	
	public ModelSwarm(Zone aZone){
		super(aZone);
		
		ru=1;
		rv=17;
		w1=1.;
		w2=1.;
		m0=0.;
		m1=0.;
		p=0.002;
		d=0.;
		e=1.;
		initProb=0.;
		
		width=300;
		history=300;
		
		EmptyProbeMap probeMap;
		probeMap=new EmptyProbeMapImpl(aZone,this.getClass());
		
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("ru",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("rv",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("w1",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("w2",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("m0",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("m1",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("p",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("d",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("e",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("initProb",getClass()));
          
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
          ("initializeCell",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetA",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetB",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetC",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetD",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetE",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetF",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetG",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetH",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetI",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetJ",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForMessage$inClass
		  ("parameterSetK",getClass()));
				
        Globals.env.probeLibrary.setProbeMap$For(probeMap,this.getClass());

	}
	
	public Object buildObjects(){
		patternSpace=new PatternSpace(this,width,history);
		initializeCell();
		return this;
	}
	
	public Object buildActions(){
		modelActions=new ActionGroupImpl(this);
		try{
			modelActions.createActionTo$message(patternSpace,
				new Selector(Class.forName("PatternSpace"),"update",false));
		} catch (Exception e) {
			e.printStackTrace (System.err);
			System.exit(1);
		}
		
		modelSchedule=new ScheduleImpl(this,1);
		modelSchedule.at$createAction(0,modelActions);
		return this;
	}
	
	public Activity activateIn(Swarm context){
    	super.activateIn (context);
    	modelSchedule.activateIn(this);
		return getActivity();
	}
	
	public PatternSpace getPattern(){
		return patternSpace;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHistory(){
		return history;
	}
	
	public void initializeCell(){
		if(patternSpace!=null)
			patternSpace.setParams(ru,rv,w1,w2,m0,m1,p,d,e,initProb);
	}
	
	public void parameterSetA(){
		d=0; e=1; initProb=0;
		ru=1; rv=17; w1=1; w2=1; m0=m1=0; p=0.002;
		initializeCell();
	}

	public void parameterSetB(){
		d=0; e=1; initProb=0;
		ru=10; rv=0; w1=8; w2=21; m0=0; m1=1; p=0.002;
		initializeCell();
	}

	public void parameterSetC(){
		d=0; e=1; initProb=0;
		ru=2; rv=0; w1=10; w2=48; m0=m1=0; p=0.002;
		initializeCell();
	}

	public void parameterSetD(){
		d=0; e=1; initProb=0;
		ru=1; rv=16; w1=8; w2=6; m0=m1=0; p=0.002;
		initializeCell();
	}

	public void parameterSetE(){
		d=0; e=1; initProb=0;
		ru=1; rv=17; w1=16; w2=6; m0=m1=0; p=0.002;
		initializeCell();
	}

	public void parameterSetF(){
		d=0; e=1; initProb=0;
		ru=3; rv=8; w1=2; w2=11; m0=0; m1=0.3; p=0.001;
		initializeCell();
	}

	public void parameterSetG(){
		ru=1; rv=23; w1=4; w2=61; m0=m1=0; p=0; d=0.05; e=0; initProb=0.1;
		initializeCell();
	}

	public void parameterSetH(){
		d=0; e=1; initProb=0;
		ru=3; rv=8; w1=2; w2=11; m0=0; m1=0.3; p=0.001;
		initializeCell();
	}

	public void parameterSetI(){
		initProb=0;
		ru=3; rv=0; w1=5; w2=12; m0=0; m1=0.22; p=0.004; d=0.19; e=0.0;
		initializeCell();
	}

	public void parameterSetJ(){
		initProb=0;
		ru=2; rv=0; w1=6; w2=35; m0=0; m1=0.05; p=0.002; d=0.1; e=0.0;
		initializeCell();
	}
	
	public void parameterSetK(){
		d=0; e=1; initProb=0;
		ru=1; rv=2; w1=5; w2=10; m0=0; m1=0.3; p=0.002;
		initializeCell();
	}
	
}
