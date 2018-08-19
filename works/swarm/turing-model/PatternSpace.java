import swarm.space.*;
import swarm.defobj.*;
import swarm.collections.*;

public class PatternSpace extends Discrete2dImpl{
	int width,history;
	Array cellVector;

	public PatternSpace(Zone aZone,int x,int y){
		super(aZone,x,y);
		width=x;
		history=y;
	}

	public void setCellVector(Array aVector){
		cellVector=aVector;
	}

	public void update(){
		int x,y;
		for(y=0;y<history-1;++y)
			for(x=0;x<width;++x)
				putValue$atX$Y(getValueAtX$Y(x,y+1),x,y);
		for(x=0;x<width;++x)
			putValue$atX$Y((int)java.lang.Math.round((
				(Cell)cellVector.atOffset(x)).getU()),x,history-1);
	}
	
	public double getUAt(int i){
		return ((Cell)cellVector.atOffset((i+width)%width)).getU();
	}
	
	public double getV2At(int i){
		return ((Cell)cellVector.atOffset((i+width)%width)).getV2();
	}
}
