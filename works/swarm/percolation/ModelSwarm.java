import swarm.*;
import swarm.activity.*;
import swarm.defobj.*;
import swarm.objectbase.*;

public class ModelSwarm extends SwarmImpl{
	public int width,height;
	PatternSpace patternSpace;
	
	ActionGroup modelActions;
	Schedule modelSchedule;
	
	boolean stopFlag;
	
	public ModelSwarm(Zone aZone){
		super(aZone);
		
		width=50;
		height=50;
		
		stopFlag=false;
		
		EmptyProbeMap probeMap;
		probeMap=new EmptyProbeMapImpl(aZone,this.getClass());
		
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("width",getClass()));
		probeMap.addProbe(Globals.env.probeLibrary.getProbeForVariable$inClass
          ("height",getClass()));
				
        Globals.env.probeLibrary.setProbeMap$For(probeMap,this.getClass());

	}
	
	public Object buildObjects(){
		patternSpace=new PatternSpace(this,width,height);
		return this;
	}
	
	/**
	 * PatternSpaceのupdateをスケジュールに組み込む
	 */
	public Object buildActions(){
		modelActions=new ActionGroupImpl(this);
		try{
			modelActions.createActionTo$message(patternSpace,
				new Selector(Class.forName("PatternSpace"),"update",false));
			modelActions.createActionTo$message(this,
				new Selector(getClass(),"checkToStop",false));
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
	
	/**
	 * 終了判定<BR>
	 * PatternSpaceに点の置かれていない格子がなくなったら、
	 * 最後のクラスタリング(trace)をして終了
	 */
	public void checkToStop(){
		if(!patternSpace.remainingQ()){
			patternSpace.trace();
			this.getActivity().terminate();
			stopFlag=true;
		}
	}
	
	/**
	 * シミュレーションが終了したか
	 */
	public boolean stopQ(){
		return stopFlag;
	}

	public PatternSpace getPattern(){
		return patternSpace;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void initializeSpace(){
		if(patternSpace!=null)
			patternSpace.initializeSpace();
	}
}
