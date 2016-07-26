/*
 *
 * Copyright LABGeM 2015
 *
 * author: Jonathan MERCIER
 *
 * This software is a computer program whose purpose is to annotate a complete genome.
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 */

package fr.cea.ig.grools.drools;

import fr.cea.ig.grools.Mode;
import fr.cea.ig.grools.Reasoner;
import fr.cea.ig.grools.Verbosity;
import fr.cea.ig.grools.fact.Observation;
import fr.cea.ig.grools.fact.ObservationImpl;
import fr.cea.ig.grools.fact.ObservationType;
import fr.cea.ig.grools.fact.PriorKnowledge;
import fr.cea.ig.grools.fact.Relation;
import fr.cea.ig.grools.fact.RelationImpl;
import fr.cea.ig.grools.logic.TruthValue;
import fr.cea.ig.grools.logic.TruthValuePowerSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * NormalTest
 */
public class NormalTest {
    private Reasoner reasoner;

    @Before
    public void setUp(){
        reasoner = new ReasonerImpl( Mode.NORMAL, Verbosity.HIGHT);
    }

    @Test
    public void case1() throws Exception {
        Cases.case1( reasoner );
        final PriorKnowledge pk1 = reasoner.getPriorKnowledge( "pk1" );

       assertEquals( TruthValuePowerSet.T, pk1.getPrediction() );
        reasoner.close();
    }

    @Test
    public void case2() throws Exception {
        Cases.case2( reasoner );
        final PriorKnowledge pk1 = reasoner.getPriorKnowledge( "pk1" );

        assertEquals( TruthValuePowerSet.T, pk1.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pk1.getExpectation() );
        reasoner.close();
    }

    @Test
    public void case3() throws Exception {
        Cases.case3( reasoner );
        final PriorKnowledge pk1 = reasoner.getPriorKnowledge( "pk1" );


        assertEquals( TruthValuePowerSet.B, pk1.getPrediction() );
        assertEquals( TruthValuePowerSet.F, pk1.getExpectation() );
        reasoner.close();
    }

    @Test
    public void case4() throws Exception {
        Cases.case4( reasoner );
        final PriorKnowledge pk1 = reasoner.getPriorKnowledge( "pk1" );
        assertEquals( TruthValuePowerSet.T, pk1.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pk1.getExpectation() );
        reasoner.close();
    }

    @Test
    public void case5() throws Exception {
        Cases.case5( reasoner );
        final PriorKnowledge pk1 = reasoner.getPriorKnowledge( "pk1" );
        final PriorKnowledge pk11 = reasoner.getPriorKnowledge( "pk11" );

        assertEquals( TruthValuePowerSet.T, pk11.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pk1.getPrediction() );
        reasoner.close();
    }

    @Test
    public void case6() throws Exception {
        Cases.case6( reasoner );
        final PriorKnowledge pk1 = reasoner.getPriorKnowledge( "pk1" );
        final PriorKnowledge pk11 = reasoner.getPriorKnowledge( "pk11" );
        final PriorKnowledge pk12 = reasoner.getPriorKnowledge( "pk12" );

       assertEquals( TruthValuePowerSet.T, pk11.getPrediction() );
       assertEquals( TruthValuePowerSet.N, pk12.getPrediction() );
       assertEquals( TruthValuePowerSet.NT, pk1.getPrediction() );
        reasoner.close();
    }

    @Test
    public void case7() throws Exception {
        Cases.case7( reasoner );
        final PriorKnowledge pk1 = reasoner.getPriorKnowledge( "pk1" );

        assertEquals( TruthValuePowerSet.B, pk1.getPrediction() );
        assertEquals( TruthValuePowerSet.T, pk1.getExpectation() );
        reasoner.close();
    }

    @Test
    public void case8() throws Exception {
        Cases.case8( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        assertEquals( TruthValuePowerSet.T, cB.getPrediction() );
        assertEquals( TruthValuePowerSet.T, cC.getPrediction() );
        assertEquals( TruthValuePowerSet.T, cA.getPrediction() );
        reasoner.close();
    }

    @Test
    public void case9() throws Exception {
        Cases.case9( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        assertEquals(TruthValuePowerSet.F, cB.getPrediction() );
        assertEquals(TruthValuePowerSet.F, cC.getPrediction() );
        assertEquals(TruthValuePowerSet.F, cA.getPrediction() );
        reasoner.close();
    }

    @Test
    public void case10() throws Exception {
        Cases.case10( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        assertEquals(TruthValuePowerSet.T, cB.getPrediction() );
        assertEquals(TruthValuePowerSet.F, cC.getPrediction() );
        assertEquals(TruthValuePowerSet.TF, cA.getPrediction() );
        reasoner.close();
    }

    @Test
    public void case11() throws Exception {
        Cases.case11( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        assertEquals( TruthValuePowerSet.B,  cB.getPrediction() );
        assertEquals( TruthValuePowerSet.F,  cC.getPrediction() );
        assertEquals( TruthValuePowerSet.FB, cA.getPrediction() );
        reasoner.close();
    }

    @Test
    public void case12() throws Exception {
        Cases.case12( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge cD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge cE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge cF = reasoner.getPriorKnowledge( "F" );
        assertEquals( TruthValuePowerSet.T,  cF.getPrediction() );
        assertEquals( TruthValuePowerSet.F,  cE.getPrediction() );
        assertEquals( TruthValuePowerSet.F,  cD.getPrediction() );
        assertEquals( TruthValuePowerSet.TF, cC.getPrediction() );
        assertEquals( TruthValuePowerSet.TF, cB.getPrediction() );
        assertEquals( TruthValuePowerSet.TF,  cA.getPrediction() );
        reasoner.close();
    }


    @Test
    public void case13() throws Exception {
        Cases.case13( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge cD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge cE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge cF = reasoner.getPriorKnowledge( "F" );

        assertEquals( TruthValuePowerSet.T, cF.getPrediction() );
        assertEquals( TruthValuePowerSet.F, cE.getPrediction() );
        assertEquals( TruthValuePowerSet.F, cD.getPrediction() );
        assertEquals( TruthValuePowerSet.TF, cC.getPrediction() );
        assertEquals( TruthValuePowerSet.F, cB.getPrediction() );
        assertEquals( TruthValuePowerSet.TF, cA.getPrediction() );
        reasoner.close();
    }


    @Test
    public void case14() throws Exception {
        Cases.case14( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge cD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge cE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge cF = reasoner.getPriorKnowledge( "F" );

        assertEquals( TruthValuePowerSet.F,  cE.getPrediction() );
        assertEquals( TruthValuePowerSet.T,  cF.getPrediction() );
        assertEquals( TruthValuePowerSet.F,  cD.getPrediction() );
        assertEquals( TruthValuePowerSet.TF, cC.getPrediction() );
        assertEquals( TruthValuePowerSet.F,  cB.getPrediction() );
        assertEquals( TruthValuePowerSet.TF, cA.getPrediction() );

        assertEquals( TruthValuePowerSet.F, cE.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cF.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cD.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cC.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cB.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cA.getExpectation() );

        Observation o = ObservationImpl.builder()
                                       .name("oD2")
                                       .truthValue( TruthValue.t )
                                       .type( ObservationType.EXPERIMENTATION )
                                       .build();

        Relation oD2TocD = new RelationImpl( o, cD, o.getType() );

        reasoner.insert( o, oD2TocD );
        reasoner.reasoning();
        assertEquals( TruthValuePowerSet.TF, cD.getExpectation() );


        reasoner.close();
    }


    @Test
    public void case15() throws Exception {
        Cases.case15( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge cD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge cE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge cF = reasoner.getPriorKnowledge( "F" );

        assertEquals( TruthValuePowerSet.T  , cF.getPrediction() );
        assertEquals( TruthValuePowerSet.T  , cE.getPrediction() );
        assertEquals( TruthValuePowerSet.F  , cD.getPrediction() );
        assertEquals( TruthValuePowerSet.T  , cC.getPrediction() );
        assertEquals( TruthValuePowerSet.TF , cB.getPrediction() );
        assertEquals( TruthValuePowerSet.T  , cA.getPrediction() );

        assertEquals( TruthValuePowerSet.F, cA.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cB.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cC.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cD.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cE.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cF.getExpectation() );

        Observation o = ObservationImpl.builder()
                                       .name("oD2")
                                       .truthValue( TruthValue.t )
                                       .type( ObservationType.EXPERIMENTATION )
                                       .build();
        Relation oToD = new RelationImpl( o, cD, o.getType() );
        reasoner.insert( o, oToD );
        reasoner.reasoning();
        assertEquals( TruthValuePowerSet.TF, cD.getExpectation() );


        reasoner.close();
    }


    @Test
    public void case16() throws Exception {
        Cases.case16( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge cD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge cE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge cF = reasoner.getPriorKnowledge( "F" );

        assertEquals( TruthValuePowerSet.T  , cF.getPrediction() );
        assertEquals( TruthValuePowerSet.N  , cE.getPrediction() );
        assertEquals( TruthValuePowerSet.F  , cD.getPrediction() );
        assertEquals( TruthValuePowerSet.NT , cC.getPrediction() );
        assertEquals( TruthValuePowerSet.NF , cB.getPrediction() );
        assertEquals( TruthValuePowerSet.NT , cA.getPrediction() );

        assertEquals( TruthValuePowerSet.F, cA.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cB.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cC.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cD.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cE.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cF.getExpectation() );

        Observation o = ObservationImpl.builder()
                                       .name("oE2")
                                       .truthValue( TruthValue.t )
                                       .type( ObservationType.COMPUTATION )
                                       .build();
        Relation oToE = new RelationImpl( o, cE, o.getType() );
        reasoner.insert( o, oToE );
        reasoner.reasoning();
        assertEquals( TruthValuePowerSet.T, cE.getPrediction() );
        assertEquals( TruthValuePowerSet.T, cC.getPrediction() );
        assertEquals( TruthValuePowerSet.T, cA.getPrediction() );


        reasoner.close();
    }


    @Test
    public void case17() throws Exception {
        Cases.case17( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge cD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge cE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge cF = reasoner.getPriorKnowledge( "F" );

        assertEquals( TruthValuePowerSet.T  , cF.getPrediction() );
        assertEquals( TruthValuePowerSet.N  , cE.getPrediction() );
        assertEquals( TruthValuePowerSet.F  , cD.getPrediction() );
        assertEquals( TruthValuePowerSet.NT , cC.getPrediction() );
        assertEquals( TruthValuePowerSet.NF , cB.getPrediction() );
        assertEquals( TruthValuePowerSet.NT , cA.getPrediction() );

        assertEquals( TruthValuePowerSet.T, cA.getExpectation() );
        assertEquals( TruthValuePowerSet.N, cB.getExpectation() );
        assertEquals( TruthValuePowerSet.T, cC.getExpectation() );
        assertEquals( TruthValuePowerSet.N, cD.getExpectation() );
        assertEquals( TruthValuePowerSet.NT, cE.getExpectation() );
        assertEquals( TruthValuePowerSet.T, cF.getExpectation() );

        Observation o = ObservationImpl.builder()
                                       .name("oE2")
                                       .truthValue( TruthValue.t )
                                       .type( ObservationType.ANNOTATION )
                                       .build();
        Relation oToE = new RelationImpl( o, cE, o.getType() );
        reasoner.insert( o, oToE );
        reasoner.reasoning();
        assertEquals( TruthValuePowerSet.T, cE.getPrediction() );
        assertEquals( TruthValuePowerSet.T, cC.getPrediction() );
        assertEquals( TruthValuePowerSet.T, cA.getPrediction() );

        reasoner.close();
    }

    @Test
    public void case21() throws Exception{
        Cases.case21( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge cD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge cE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge cF = reasoner.getPriorKnowledge( "F" );

        assertEquals( TruthValuePowerSet.T  , cF.getPrediction() );
        assertEquals( TruthValuePowerSet.N  , cE.getPrediction() );
        assertEquals( TruthValuePowerSet.F  , cD.getPrediction() );
        assertEquals( TruthValuePowerSet.NT , cC.getPrediction() );
        assertEquals( TruthValuePowerSet.NF , cB.getPrediction() );
        assertEquals( TruthValuePowerSet.NT , cA.getPrediction() );

        assertEquals( TruthValuePowerSet.N, cA.getExpectation() );
        assertEquals( TruthValuePowerSet.N, cB.getExpectation() );
        assertEquals( TruthValuePowerSet.T, cC.getExpectation() );
        assertEquals( TruthValuePowerSet.N, cD.getExpectation() );
        assertEquals( TruthValuePowerSet.NT, cE.getExpectation() );
        assertEquals( TruthValuePowerSet.T, cF.getExpectation() );

        reasoner.close();
    }

    @Test
    public void case22() throws Exception{
        Cases.case22( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );
        final PriorKnowledge cD = reasoner.getPriorKnowledge( "D" );
        final PriorKnowledge cE = reasoner.getPriorKnowledge( "E" );
        final PriorKnowledge cF = reasoner.getPriorKnowledge( "F" );

        assertEquals( TruthValuePowerSet.T  , cF.getPrediction() );
        assertEquals( TruthValuePowerSet.N  , cE.getPrediction() );
        assertEquals( TruthValuePowerSet.F  , cD.getPrediction() );
        assertEquals( TruthValuePowerSet.NT , cC.getPrediction() );
        assertEquals( TruthValuePowerSet.NF , cB.getPrediction() );
        assertEquals( TruthValuePowerSet.NT , cA.getPrediction() );

        assertEquals( TruthValuePowerSet.N, cA.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cB.getExpectation() );
        assertEquals( TruthValuePowerSet.N, cC.getExpectation() );
        assertEquals( TruthValuePowerSet.F, cD.getExpectation() );
        assertEquals( TruthValuePowerSet.NF, cE.getExpectation() );
        assertEquals( TruthValuePowerSet.N, cF.getExpectation() );

        reasoner.close();
    }

    @Test
    public void case23() throws Exception{
        Cases.case23( reasoner );
        final PriorKnowledge cA = reasoner.getPriorKnowledge( "A" );
        final PriorKnowledge cB = reasoner.getPriorKnowledge( "B" );
        final PriorKnowledge cC = reasoner.getPriorKnowledge( "C" );

        assertEquals( TruthValuePowerSet.T  , cC.getPrediction() );
        assertEquals( TruthValuePowerSet.T  , cB.getPrediction() );
        assertEquals( TruthValuePowerSet.T  , cA.getPrediction() );

        assertEquals( TruthValuePowerSet.N, cA.getExpectation() );
        assertEquals( TruthValuePowerSet.T, cB.getExpectation() );
        assertEquals( TruthValuePowerSet.NT, cC.getExpectation() );
        reasoner.close();
    }
}
