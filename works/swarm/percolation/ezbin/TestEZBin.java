import java.util.List;
import java.util.LinkedList;
import swarm.defobj.Zone;
import swarm.analysis.EZBin;
import swarm.analysis.EZBinC;
import swarm.analysis.EZBinCImpl;
import swarm.analysis.EZBinImpl;
import swarm.simtoolsgui.GUISwarmImpl;
import swarm.Globals;
import swarm.Selector;

public class TestEZBin extends GUISwarmImpl {
    List l;
    EZBin ezbin;

    class Agent {
        double value;

        Agent (double value) {
            super ();
            
            this.value = value;
        }
        public double getAgentValue () {
            return value;
        }
    }

    TestEZBin (Zone aZone) {
        super (aZone);
        l = new LinkedList ();
        
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 1 << i; j++)
                l.add (new Agent ((double) i));

        EZBinC ezbinCreating = new EZBinCImpl (new EZBinImpl ());
        ezbinCreating.createBegin (aZone);
        ezbinCreating.setCollection (l);

        Class agentClass = l.get (0).getClass ();
        try {
            ezbinCreating.setProbedSelector
                (new Selector (agentClass, "getAgentValue", false));
        } catch (Exception e) {
            e.printStackTrace (System.err);
        }
        ezbinCreating.setTitle ("Value");
        ezbinCreating.setBinCount (5);
        ezbinCreating.setLowerBound (0);
        ezbinCreating.setUpperBound (5);
        ezbin = (EZBin) ezbinCreating.createEnd ();
    }

    void update () {
        ezbin.reset ();
        ezbin.update ();
        ezbin.output ();
    }
    
    public static void main (String[] args) {
        Globals.env.initSwarm ("TestEZBin", "0.0", "mgd@swarm.org", args);
        TestEZBin testEZBin = new TestEZBin (Globals.env.globalZone);

        testEZBin.buildObjects ();
        testEZBin.buildActions ();
        testEZBin.activateIn (null);
        testEZBin.update ();
        testEZBin.go ();
    }
}
