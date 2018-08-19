import swarm.simtoolsgui.*;
import swarm.gui.*;
import swarm.space.*;
import swarm.activity.*;
import swarm.defobj.*;
import swarm.objectbase.*;
import swarm.*;

public class ObserverSwarm extends GUISwarmImpl{
	ActionGroup displayActions;
	Schedule displaySchedule;
	ModelSwarm modelSwarm;
	Colormap colorMap;
	ZoomRaster patternRaster;
	Value2dDisplay patternDisplay;
	
	public ObserverSwarm(Zone aZone){
		super(aZone);
	}
	
	public Object buildObjects(){
		super.buildObjects();
		modelSwarm = (ModelSwarm)
			Globals.env.lispAppArchiver.getWithZone$key(Globals.env.globalZone,"modelSwarm");
		Globals.env.createArchivedProbeDisplay (modelSwarm,"modelSwarm");
		if(modelSwarm==null){
			System.out.println("Can't find the modelSwarm parameters.");
			System.exit(1);
		}
		getControlPanel().setStateStopped();

		modelSwarm.buildObjects();
		
		colorMap=new ColormapImpl(this);
		colorMap.setColor$ToName((byte)0,"white");
		colorMap.setColor$ToName((byte)1,"black");
		
		patternRaster=new ZoomRasterImpl(this);
		patternRaster.setColormap(colorMap);
		patternRaster.setZoomFactor(1);
		patternRaster.setWidth$Height(
			modelSwarm.getWidth(),
			modelSwarm.getHistory());
		patternRaster.setWindowTitle("Generated Pattern");
		patternRaster.pack();
		
		patternDisplay=new Value2dDisplayImpl(
			this,patternRaster,colorMap,modelSwarm.getPattern());
		
		return this;
	}
	
	public Object buildActions(){
		super.buildActions();
		modelSwarm.buildActions();
		displayActions=new ActionGroupImpl(this);
		
		try {
			displayActions.createActionTo$message(patternDisplay,
				new Selector(patternDisplay.getClass(),"display",false));
			displayActions.createActionTo$message(patternRaster,
				new Selector(patternRaster.getClass(),"drawSelf",false));
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
