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
	 * �R���X�g���N�^�̕⏕���\�b�h
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
	 * �܂��_�̒u����Ă��Ȃ��i�q�����邩
	 */
	public boolean remainingQ(){
		return !remainingPoints.isEmpty();
	}
	
	/**
	 * PatternSpace�ɐV�����_�������_���ɒǉ�����B<BR>
	 * �����ł̓N���X�^�����O�̌v�Z�͍s��Ȃ��B
	 * ���̌v�Z�͏d���Ɨ\�z����邽�߁AgetPercolationProb���Ă΂ꂽ�Ƃ��̂ݍs���B
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
	 * �N���X�^�ɕ����鏈�����s�����\�b�h<BR>
	 * �E�͂��߁A���ׂĂ̓_��unchecked<BR>
	 * �E�_�����Ԃɒ��ׂĂ���<BR>
	 * �Eunchecked�̓_�ɂȂ����Ă���_���ċA�I�ɒ��ׂ�(traceCluster)<BR>
	 * �E�ł���������clusterData�̒��ŁA�ő�̂��̂��ő�N���X�^<BR>
	 * <BR>
	 * ����͍ċA�I�ȏ��������A������񔽕��ł��ł���B<BR>
	 * �Q�l�F���c�_�F�u�p�[�R���[�V�����̉Ȋw�v��3��
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
	 * trace�̕⏕���\�b�h
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
	 * �Z���m�����v�Z����B<BR>
	 * �����ŁAPatternSpace�ɍő�N���X�^�T�C�Y��₢���킹�A
	 * ���̌��ʂƁA�S�i�q���̔䂪�Z���m���ł���B
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
