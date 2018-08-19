import swarm.*;

/**
 * �p�[�R���[�V�����̎��o��<BR>
 * <BR>
 * 2���������i�q�Ƀ����_���ɓ_��u���Ă����B
 * �ƂȂ肠���_�͂Ȃ����Ă���Ƃ݂Ȃ��B
 * �_�������Ă����ƁA�Ȃ������_�ɂ��傫�ȃN���X�^���ł���B<BR>
 * <BR>
 * ��Ԃ������ɑ傫���ꍇ�A�_�����閧�x�𒴂���ƁA
 * �N���X�^�̃T�C�Y�������ɑ傫���Ȃ邱�Ƃ��m���Ă���B<BR>
 * <BR>
 * ���̗ՊE�_�ɂ����āA�C�ӂɑI�񂾊i�q�_�������ɑ傫�ȃN���X�^�̈ꕔ�ɂȂ��Ă���m��
 * ��Z���m���Ƃ����B����́A�ő�N���X�^�Ɋ܂܂��_�̐��ƁA�S�i�q�_�̐��̔�ł���B<BR>
 * <BR>
 * �����ł͋�Ԃ��L���̏ꍇ�̃V�~�����[�V�����ŁA�Z���m���𒲂ׂ�B<BR>
 * <BR>
 * �Q�l�F���c�_�F�u�Ȃ���̉Ȋw�v�E�u�p�[�R���[�V�����̉Ȋw�v<BR>
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
