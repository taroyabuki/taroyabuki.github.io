import swarm.*;

/**
 * パーコレーションの視覚化<BR>
 * <BR>
 * 2次元正方格子にランダムに点を置いていく。
 * となりあう点はつながっているとみなす。
 * 点が増えていくと、つながった点による大きなクラスタができる。<BR>
 * <BR>
 * 空間が無限に大きい場合、点がある密度を超えると、
 * クラスタのサイズも無限に大きくなることが知られている。<BR>
 * <BR>
 * この臨界点において、任意に選んだ格子点が無限に大きなクラスタの一部になっている確率
 * を浸透確率という。これは、最大クラスタに含まれる点の数と、全格子点の数の比である。<BR>
 * <BR>
 * ここでは空間が有限の場合のシミュレーションで、浸透確率を調べる。<BR>
 * <BR>
 * 参考：小田垣孝「つながりの科学」・「パーコレーションの科学」<BR>
 * <BR>
 * <IMG src="../percolation.png" border="0"><BR>
 * <BR>
 * @author YABUKI Taro
 */
public class Percolation{
	public static void main(String[] args) {
		ObserverSwarm observerSwarm;
		
		Globals.env.initSwarm("percolation", "0.0", "YABUKI Taro", args);
		observerSwarm=new ObserverSwarm(Globals.env.globalZone);
		observerSwarm.buildObjects();
		observerSwarm.buildActions();
		observerSwarm.activateIn(null);
		observerSwarm.go();
	}
}
