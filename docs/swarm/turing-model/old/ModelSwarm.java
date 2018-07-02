import swarm.*;
import swarm.activity.*;
import swarm.defobj.*;
import swarm.objectbase.*;
import swarm.space.*;
import swarm.collections.*;

public class ModelSwarm extends SwarmImpl{
	public int worldXSize, worldYSize;
	
	ActivatorSpace activator;
	TemporarySpace inhibitor;
	TemporarySpace inhibitorTmp;
	TemporarySpace activatorTmp;
	
	ActionGroup modelActions;
	Schedule modelSchedule;
	
	public ModelSwarm(Zone aZone){
		super(aZone);
		EmptyProbeMap probeMap;
		worldXSize=80;
		worldYSize=80;
		
		probeMap=new EmptyProbeMapImpl(aZone,this.getClass());
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("worldXSize",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("worldYSize",getClass()));
		
        Globals.env.probeLibrary.setProbeMap$For(probeMap,this.getClass());
	}
	
	public Object buildObjects(){
		inhibitor=new TemporarySpace(this,worldXSize,2);
		inhibitor.initializeLattice();
		
		inhibitorTmp=new TemporarySpace(this,worldXSize,2);
		inhibitorTmp.initializeLattice();
		
		activatorTmp=new TemporarySpace(this,worldXSize,2);
		activatorTmp.initializeLattice();
		
		activator=new ActivatorSpace(getZone(),worldXSize,worldYSize);
		//activator.setNumStates(2);
		activator.setInhibitor(inhibitor);
		activator.setInhibitorTmp(inhibitorTmp);
		activator.setActivatorTmp(activatorTmp);
		activator.setRule(0.001,2.,11.,0.,0.3,3,8,0.,1.,0.);
		
		return this;
	}
	
	public Object buildActions(){
		modelActions=new ActionGroupImpl(this);
		try{
			modelActions.createActionTo$message(activator,
				new Selector(activator.getClass(),"stepRule",false));
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
	
	public ActivatorSpace getWorld(){
		return activator;
	}
}
