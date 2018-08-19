import swarm.simtoolsgui.*;
import swarm.gui.*;
import swarm.space.*;
import swarm.activity.*;
import swarm.defobj.*;
import swarm.objectbase.*;
import swarm.*;
import swarm.analysis.*;
import java.util.*;


public class ObserverSwarm extends GUISwarmImpl{
	public int graphInterval;

	ActionGroup displayActions,statActions;
	Schedule displaySchedule,statSchedule;
	ModelSwarm modelSwarm;
	Colormap colorMap;
	ZoomRaster patternRaster;
	Value2dDisplay patternDisplay;
	EZGraph resultGraph;
	EZBin clusterSizeHist;
	LinkedList clusterSizeData;
	
	public ObserverSwarm(Zone aZone){
		super(aZone);
		graphInterval=10;
	}
	
	public Object buildObjects(){
		super.buildObjects();
		Globals.env.createArchivedProbeDisplay (this, "observerSwarm");
		
		resultGraph=new EZGraphImpl(
			this,"Percolation Probability","points","P(points)","resultGraph");
		try {
			resultGraph.enableDestroyNotification$notificationMethod
			  (this, new Selector(getClass(),"_resultGraphDeath_",false));
			resultGraph.createSequence$withFeedFrom$andSelector(
				"P",this,new Selector(this.getClass(),"getPercolationProb", false));
			resultGraph.createSequence$withFeedFrom$andSelector(
				"cluster num / 0.15 N^2",this,new Selector(this.getClass(),"getRelativeClusterNum", false));
		} catch (Exception e) {
			System.err.println ("Exception: " + e.getMessage());
		}
						
		modelSwarm = (ModelSwarm)
			Globals.env.lispAppArchiver.getWithZone$key(Globals.env.globalZone,"modelSwarm");
		Globals.env.createArchivedProbeDisplay (modelSwarm,"modelSwarm");
		if(modelSwarm==null){
			System.out.println("Can't find the modelSwarm parameters.");
			System.exit(1);
		}
		getControlPanel().setStateStopped();

		modelSwarm.buildObjects();

		clusterSizeData = new LinkedList ();
		clusterSizeData.add (new Double(0));
		EZBinC ezbinCreating = new EZBinCImpl (new EZBinImpl ());
		ezbinCreating.createBegin (getZone());
		ezbinCreating.setCollection (clusterSizeData);
		Class memberClass= clusterSizeData.get (0).getClass ();
		try{
			ezbinCreating.setProbedSelector(new Selector (memberClass, "doubleValue", false));
		} catch (Exception e) {
			e.printStackTrace (System.err);
		}
		ezbinCreating.setTitle ("Histogram of Cluster Size");
		ezbinCreating.setBinCount (10);
		ezbinCreating.setLowerBound (0);
		ezbinCreating.setUpperBound (modelSwarm.getWidth()*modelSwarm.getHeight()/200);
		clusterSizeHist = (EZBin) ezbinCreating.createEnd ();

		colorMap=new ColormapImpl(this);
		colorMap.setColor$ToName((byte)0,"white");
		colorMap.setColor$ToName((byte)1,"black");
		colorMap.setColor$ToName((byte)2,"red");
		
		patternRaster=new ZoomRasterImpl(this);
		patternRaster.setColormap(colorMap);
		patternRaster.setZoomFactor(1);
		patternRaster.setWidth$Height(
			modelSwarm.getWidth(),
			modelSwarm.getHeight());
		patternRaster.setWindowTitle("Generated Pattern");
		patternRaster.pack();
		
		patternDisplay=new Value2dDisplayImpl(
			this,patternRaster,colorMap,modelSwarm.getPattern());
	
		return this;
	}
	
	/**
	 * 浸透確率の問い合わせる。
	 */
	public double getPercolationProb(){
		return modelSwarm.getPattern().getPercolationProb();
	}
	
	/**
	 * クラスタ数の問い合わせ
	 */
	public double getRelativeClusterNum(){
		return modelSwarm.getPattern().getClusterNum()
			/(0.15*modelSwarm.getHeight()*modelSwarm.getWidth());
	}
	
	/** 
	 * 2つのスケジュールを用いる。ひとつは画面の更新、
	 * 統計データ（クラスタの判定も含む）の更新<BR>
	 * クラスタリングの計算は重いことが予想されるから、
	 * graphIntervalの間隔で行う（初期値は10だが、Probeから変更できる）。 
	 */
	public Object buildActions(){
		super.buildActions();
		modelSwarm.buildActions();
		
		displayActions=new ActionGroupImpl(this);
		try {
			displayActions.createActionTo$message(patternDisplay,
				new Selector(patternDisplay.getClass(),"display",false));
			displayActions.createActionTo$message(patternRaster,
				new Selector(patternRaster.getClass(),"drawSelf",false));
			displayActions.createActionTo$message(getActionCache(),
				new Selector(getActionCache().getClass(),"doTkEvents",true));
			displayActions.createActionTo$message(this,
				new Selector(getClass(),"checkToStop",false));
		} catch (Exception e) {
			System.out.println ("Exception: " + e.getMessage ());
			System.exit(1);
		}
		displaySchedule = new ScheduleImpl(this,1);
		displaySchedule.at$createAction(0,displayActions);
		
		statActions=new ActionGroupImpl(this);
		try {
			statActions.createActionTo$message(this,new Selector(getClass(),"showStats",false));
			statActions.createActionTo$message(this,new Selector(getClass(),"showHist",false));
			statActions.createActionTo$message(getActionCache(),
				new Selector(getActionCache().getClass(),"doTkEvents",true));
		} catch (Exception e) {
			System.out.println ("Exception: " + e.getMessage ());
			System.exit(1);
		}
		statSchedule = new ScheduleImpl(this,graphInterval);
		statSchedule.at$createAction(0,statActions);
		
		return this;
    }
	
	public Activity activateIn(Swarm context){
    	super.activateIn(context);
		modelSwarm.activateIn(this);
		displaySchedule.activateIn(this);
		statSchedule.activateIn(this);
		return getActivity();
	}
	
	public Object _resultGraphDeath_(Object caller){
		resultGraph.drop();
		resultGraph = null;
		return this;
	}
	
	/**
	 * 統計データ（クラスタリングも含む）の更新<BR>
	 */
	public Object showStats(){
		if(resultGraph!=null)
			resultGraph.step();
		return this;
	}
	
	/**
	 * ヒストグラムの更新
	 */
	public Object showHist(){
		//clusterSizeData=modelSwarm.getPattern().getClusterSizeData();
		//これではうまくいかない。仕方なく、
		updateClusterSizeData();
		if(clusterSizeHist!=null){
			clusterSizeHist.reset();
			clusterSizeHist.update();
			clusterSizeHist.output();
		}
		return this;
	}
	
	/**
	 * showHistの補助メソッド
	 */
	void updateClusterSizeData(){
		clusterSizeData.clear();
		Iterator it=modelSwarm.getPattern().getClusterSizeData().iterator();
		while(it.hasNext()){
			clusterSizeData.add(new
				Double(((Integer)it.next()).intValue() //このままだとすごく遅い
				                                                         //同じ値のデータがたくさんあるとだめらしい
				                                                         //Quick Sortしているわけでもないだろうに
				                                                         //仕方ないから、少しノイズを加えておく
				+Globals.env.uniformDblRand.getDoubleWithMin$withMax(0.0,0.01)));
		}
	}
	
	/**
	 * 終了の判定<BR>
	 * ModelSwarmが停止していたら、最終処理をして停止。
	 */
	public void checkToStop(){
		if(modelSwarm.stopQ()){
			showStats();
			getActionCache().doTkEvents();
			System.out.println("Simulation is over.");
			getControlPanel().setStateStopped();
		}
	}
}
