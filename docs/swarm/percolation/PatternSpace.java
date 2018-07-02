import swarm.space.*;
import swarm.defobj.*;
import swarm.*;
import java.util.*;

public class PatternSpace extends Discrete2dImpl{
	int width,height;
	
	LinkedList remainingPoints;
	LinkedList largestCluster;
	LinkedList clusterData;
	LinkedList clusterSizeData;
	boolean checked[][];

	public PatternSpace(Zone aZone,int W,int H){
		super(aZone,W,H);
		width=W;
		height=H;
		initializeSpace();
	}
	
	/**
	 * コンストラクタの補助メソッド
	 */
	void initializeSpace(){
		remainingPoints=new LinkedList();
		checked=new boolean[height][width];
		clusterSizeData=new LinkedList();
		for(int i=0;i<height;++i){
			for(int j=0;j<width;++j){
				int[] pair={i,j};
				remainingPoints.add(pair);
				this.putValue$atX$Y(0,i,j);
			}
		}
	}
	
	/**
	 * まだ点の置かれていない格子があるか
	 */
	public boolean remainingQ(){
		return !remainingPoints.isEmpty();
	}
	
	/**
	 * PatternSpaceに新しい点をランダムに追加する。<BR>
	 * ここではクラスタリングの計算は行わない。
	 * この計算は重いと予想されるため、getPercolationProbが呼ばれたときのみ行う。
	 */
	public void update(){
		int pIndex=Globals.env.uniformIntRand.getIntegerWithMin$withMax(0,remainingPoints.size()-1);
		int[] p=(int[])remainingPoints.get(pIndex);
		remainingPoints.remove(pIndex);
		int i=p[0];
		int j=p[1];
		this.putValue$atX$Y(1,i,j);	
	}
	
	/**
	 * クラスタに分ける処理を行うメソッド<BR>
	 * ・はじめ、すべての点はunchecked<BR>
	 * ・点を順番に調べていく<BR>
	 * ・uncheckedの点につながっている点を再帰的に調べる(traceCluster)<BR>
	 * ・できあがったclusterDataの中で、最大のものが最大クラスタ<BR>
	 * <BR>
	 * これは再帰的な処理だが、もちろん反復でもできる。<BR>
	 * 参考：小田垣孝「パーコレーションの科学」第3章
	 */
	public void trace(){
		int clusterIndex=-1;
		clusterData=new LinkedList();
		
		for(int i=0;i<height;++i){
			for(int j=0;j<width;++j){
				checked[i][j]=false;
			}
		}
		
		for(int i=0;i<height;++i){
			for(int j=0;j<width;++j){
				if(!checked[i][j]){
					if(getValueAtX$Y(i,j)!=0){
						clusterIndex++;
						clusterData.add(new LinkedList());
						traceCluster(i,j,clusterIndex);
					}
				}
			}
		}
		
		int s,maxSize=0;
		clusterSizeData.clear();
		Iterator it=clusterData.iterator();
		while(it.hasNext()){
			LinkedList cluster=(LinkedList)it.next();
			s=cluster.size();
			clusterSizeData.add(new Integer(s));
			if(maxSize<s){
				maxSize=s;
				largestCluster=cluster;
			}
		}
		
		for(int i=0;i<height;++i){
			for(int j=0;j<width;++j){
				if(this.getValueAtX$Y(i,j)==2){
					this.putValue$atX$Y(1,i,j);
				}
			}
		}
		
		it=largestCluster.iterator();
		int[] tmp;
		while(it.hasNext()){
			tmp=(int[])it.next();
			int i=tmp[0];
			int j=tmp[1];
			putValue$atX$Y(2,i,j);
		}
	}
	
	/**
	 * traceの補助メソッド
	 */
	void traceCluster(int i,int j,int ci){
		if(!checked[i][j]){
			checked[i][j]=true;
			if(getValueAtX$Y(i,j)!=0){
				int[] pair={i,j};
				((LinkedList)clusterData.get(ci)).add(pair);
				if(i!=0) traceCluster(i-1,j,ci);
				if(i!=height-1) traceCluster(i+1,j,ci);
				if(j!=0) traceCluster(i,j-1,ci);
				if(j!=width-1) traceCluster(i,j+1,ci);
			}
		}
	}
	
	/**
	 * 浸透確率を計算する。<BR>
	 * 内部で、PatternSpaceに最大クラスタサイズを問い合わせ、
	 * その結果と、全格子数の比が浸透確率である。
	 */
	public double getPercolationProb(){
		trace();
		return (double)largestCluster.size()/(width*height);
	}
		
	public LinkedList getClusterSizeData(){
		return clusterSizeData;
	}
	
	public int getClusterNum(){
		return clusterData.size();
	}
}
