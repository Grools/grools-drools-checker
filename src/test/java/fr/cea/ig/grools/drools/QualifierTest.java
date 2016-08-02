package fr.cea.ig.grools.drools;

import fr.cea.ig.grools.Mode;
import fr.cea.ig.grools.Reasoner;
import fr.cea.ig.grools.Verbosity;
import fr.cea.ig.grools.fact.PriorKnowledge;
import fr.cea.ig.grools.fact.Relation;
import fr.cea.ig.grools.fact.RelationType;
import fr.cea.ig.grools.logic.Qualifier;
import fr.cea.ig.grools.logic.TruthValuePowerSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class QualifierTest {
    private Reasoner reasoner;

    @Before
    public void setUp(){
        reasoner = new ReasonerImpl( Mode.NORMAL, Verbosity.MEDIUM);
    }

    @Test
    public void case27() throws Exception {
        Cases.case27( reasoner );
        final PriorKnowledge pkA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge pkB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge pkC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge pkD = reasoner.getPriorKnowledge( "D" );
        final Relation dToA = reasoner.getRelation( pkD, pkA, RelationType.SUBTYPE );
        final Relation cToB = reasoner.getRelation( pkC, pkB, RelationType.SUBTYPE );
        final Relation dToB = reasoner.getRelation( pkD, pkB, RelationType.SUBTYPE );
        final Relation dToC = reasoner.getRelation( pkD, pkC, RelationType.SUBTYPE );

        assertTrue( dToA.getQualifiers().contains( Qualifier.GREATEST_TRUTH_DEGREE ) );
        assertTrue( dToB.getQualifiers().contains( Qualifier.GREATEST_TRUTH_DEGREE ) );
        assertTrue( dToC.getQualifiers().contains( Qualifier.GREATEST_TRUTH_DEGREE ) );

        assertEquals( TruthValuePowerSet.T, pkD.getPrediction( ) );
        assertEquals( TruthValuePowerSet.T, pkC.getExpectation( ) );
        assertEquals( TruthValuePowerSet.NT, pkD.getExpectation( ) );
        reasoner.close();
    }
    @Test
    public void case28() throws Exception {
        Cases.case28( reasoner );
        final PriorKnowledge pkA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge pkB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge pkC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge pkD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge pkE = reasoner.getPriorKnowledge( "E" );
        final Relation dToA = reasoner.getRelation( pkD, pkA, RelationType.SUBTYPE );
        final Relation cToB = reasoner.getRelation( pkC, pkB, RelationType.SUBTYPE );
        final Relation dToB = reasoner.getRelation( pkD, pkB, RelationType.SUBTYPE );
        final Relation dToC = reasoner.getRelation( pkD, pkC, RelationType.SUBTYPE );
        final Relation bToE = reasoner.getRelation( pkB, pkE, RelationType.SUBTYPE );

        assertTrue( dToB.getQualifiers().contains( Qualifier.GREATEST_TRUTH_DEGREE ) );
        assertTrue( dToC.getQualifiers().contains( Qualifier.GREATEST_TRUTH_DEGREE ) );

        assertEquals( TruthValuePowerSet.B, pkD.getPrediction( ) );
        assertEquals( TruthValuePowerSet.T, pkC.getExpectation( ) );
        assertEquals( TruthValuePowerSet.B, pkB.getPrediction( ) );
        assertEquals( TruthValuePowerSet.N, pkB.getExpectation( ) );
        assertEquals( TruthValuePowerSet.B, pkA.getPrediction( ) );
        assertEquals( TruthValuePowerSet.N, pkA.getExpectation( ) );
        assertEquals( TruthValuePowerSet.NT, pkD.getExpectation( ) );
        reasoner.close();
    }
}
