import swarm.simtoolsgui.*;
import swarm.gui.*;
import swarm.simtoolsgui.*;
import swarm.space.*;
import swarm.activity.*;
import swarm.defobj.*;
import swarm.objectbase.*;
import swarm.*;

public class ObserverSwarm extends GUISwarmImpl{
	public int displayFrequency;
	ActionGroup displayActions;
	Schedule displaySchedule;
	ModelSwarm modelSwarm;
	Colormap colorMap;
	ZoomRaster worldRaster;
	Value2dDisplay activatorDisplay;
	
	public ObserverSwarm(Zone aZone){
		super(aZone);
		displayFrequency=1;
		EmptyProbeMap probeMap;
		probeMap=new EmptyProbeMapImpl(aZone,this.getClass());
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("displayFrequency",getClass()));
		
        Globals.env.probeLibrary.setProbeMap$For(probeMap,this.getClass());
	}
	
	public Object buildObjects(){
		super.buildObjects();
		modelSwarm = (ModelSwarm)
			Globals.env.lispAppArchiver.getWithZone$key(Globals.env.globalZone,"modelSwarm");
		Globals.env.createArchivedProbeDisplay (modelSwarm,"modelSwarm");
		Globals.env.createArchivedProbeDisplay (this, "observerSwarm");
		getControlPanel().setStateStopped();
		modelSwarm.buildObjects();
		colorMap=new ColormapImpl(this);
		colorMap.setColor$ToName((byte)0,"white");
		colorMap.setColor$ToName((byte)1,"black");
		
		worldRaster=new ZoomRasterImpl(this);
		worldRaster.setColormap(colorMap);
		worldRaster.setZoomFactor(1);
		worldRaster.setWidth$Height(
			modelSwarm.worldXSize,
			modelSwarm.worldYSize);
		worldRaster.setWindowTitle("Generated Pattern");
		worldRaster.pack();
		
		activatorDisplay=new Value2dDisplayImpl(
			getZone(),worldRaster,colorMap,modelSwarm.getWorld());
		
		return this;
	}
	
	public Object buildActions(){
		super.buildActions();
		modelSwarm.buildActions();
		displayActions=new ActionGroupImpl(this);
		
		try {
			displayActions.createActionTo$message(activatorDisplay,
				new Selector(activatorDisplay.getClass(),"display",false));
			displayActions.createActionTo$message(worldRaster,
				new Selector(worldRaster.getClass(),"drawSelf",false));
			displayActions.createActionTo$message(
				getActionCache(),
				new Selector(getActionCache().getClass(),"doTkEvents",true));
		} catch (Exception e) {
			System.out.println ("Exception: " + e.getMessage ());
			System.exit(1);
		}
		
		displaySchedule = new ScheduleImpl(this,1);
		displaySchedule.at$createAction(0,displayActions);
		
		return this;
    }
	
	public Activity activateIn(Swarm context){
    	super.activateIn(context);
		modelSwarm.activateIn(this);
		displaySchedule.activateIn(this);
		return getActivity();
	}
}
