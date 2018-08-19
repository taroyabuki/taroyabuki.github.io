import swarm.*;
import swarm.objectbase.*;
import swarm.defobj.*;
import swarm.space.*;

/**
 * step�ɕύX��������B
 * �����_���E�E�H�[�N�̍s����ɕʂ�Bug�����邩�ǂ����𒲂ׁA
 * ���Ȃ���Έړ�����悤�ɂ���B<BR>
 * <BR>
 * ����ɁA�a��H�ׂ��ꍇ�ɂ̓t���OhaveEaten�𗧂āAreportBug�����\�b�hreport���Ă΂ꂽ�Ƃ��ɁA
 * ���̂��Ƃ�񍐂ł���悤�ɂ���B
 */
public class Bug extends SwarmObjectImpl {
	int xPos, yPos;
	int worldXSize, worldYSize;
	FoodSpace foodSpace;
	Grid2d world;
	int haveEaten;
	
	public Bug(Zone aZone){
		super(aZone);
	}
	
	public Object setWorld$Food(Grid2d w,FoodSpace f){
		world=w;
		foodSpace=f;
		setWorldSizeX$Y(world.getSizeX(),world.getSizeY());
		return this;
	}
	
	public Object setX$Y(int x, int y){
		xPos = x;
		yPos = y;
		world.putObject$atX$Y(this,xPos,yPos);
		return this;
	}
	
	public Object setWorldSizeX$Y(int xSize, int ySize){
		worldXSize=xSize;
		worldYSize=ySize;
		return this;
	}
	
	public void step(){
		int newX,newY;
		haveEaten=0; // flag for reporting
		
		newX = xPos + Globals.env.uniformIntRand.getIntegerWithMin$withMax(-1,1);
		newY = yPos + Globals.env.uniformIntRand.getIntegerWithMin$withMax(-1,1);
		newX = (newX + worldXSize) % worldXSize;
		newY = (newY + worldYSize) % worldYSize;
		
		// Check that no other bug is at the new site.
		if (world.getObjectAtX$Y(newX,newY) == null){
			world.putObject$atX$Y(null,xPos,yPos);
			setX$Y(newX, newY);
		}
		
		if (foodSpace.getValueAtX$Y(xPos,yPos) == 1){
			foodSpace.putValue$atX$Y(0,xPos,yPos);
			haveEaten=1; // set flag for reporting
		}
	}
	
	/**
	 * If we are the reporterBug - we check that we have eaten and report it.
	 */
	public Object report(){
		if(haveEaten==1)
			System.out.println("I found food at X = " + xPos + " Y = " + yPos +"!");
		return this;
	}
}