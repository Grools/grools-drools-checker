package fr.cea.ig.grools.drools;

import fr.cea.ig.grools.Mode;
import fr.cea.ig.grools.Reasoner;
import fr.cea.ig.grools.Verbosity;
import fr.cea.ig.grools.fact.PriorKnowledge;
import fr.cea.ig.grools.logic.TruthValuePowerSet;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * DispensableTest
 */
public class SpecificTest {
    private Reasoner reasoner;

    @Before
    public void setUp(){
        reasoner = new ReasonerImpl( Mode.SPECIFIC, Verbosity.HIGHT);
    }

    @Test
    public void case1() throws Exception {
        Cases.case1( reasoner );
        final PriorKnowledge pk1 = reasoner.getPriorKnowledge( "pk1" );

       assertEquals( TruthValuePowerSet.T, pk1.getPrediction() );
       reasoner.close();
    }

    @Test
    public void case18() throws Exception {
        Cases.case18( reasoner );
        final PriorKnowledge pkA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge pkB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge pkC = reasoner.getPriorKnowledge( "C" );

        assertTrue( pkC.getIsSpecific() );
        assertTrue( pkB.getIsSpecific() );
        assertEquals( TruthValuePowerSet.T, pkC.getPrediction() );
        assertEquals( TruthValuePowerSet.F, pkB.getPrediction() );
        assertEquals( TruthValuePowerSet.TF, pkA.getPrediction() );
        reasoner.close();
    }
    @Test
    public void case20() throws Exception {
        Cases.case20( reasoner );
        final PriorKnowledge pkA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge pkB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge pkC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge pkD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge pkE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge pkF = reasoner.getPriorKnowledge( "F" );
        final PriorKnowledge pkG = reasoner.getPriorKnowledge( "G" );
        final PriorKnowledge pkH = reasoner.getPriorKnowledge( "H" );
        final PriorKnowledge pkI = reasoner.getPriorKnowledge( "I" );
        final PriorKnowledge pkJ = reasoner.getPriorKnowledge( "J" );
        final PriorKnowledge pkK = reasoner.getPriorKnowledge( "K" );
        final PriorKnowledge pkL = reasoner.getPriorKnowledge( "L" );

        assertTrue( pkJ.getIsSpecific() );
        assertEquals( TruthValuePowerSet.N, pkL.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pkK.getPrediction() );
        assertEquals( TruthValuePowerSet.N, pkJ.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pkI.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pkH.getPrediction() );
        assertEquals( TruthValuePowerSet.N, pkG.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pkF.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pkE.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pkD.getPrediction() );
        assertEquals( TruthValuePowerSet.NT, pkC.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pkB.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pkA.getPrediction() );
        reasoner.close();
    }
}
