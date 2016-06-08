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


import fr.cea.ig.grools.Reasoner;
import fr.cea.ig.grools.fact.Observation;
import fr.cea.ig.grools.fact.ObservationImpl;
import fr.cea.ig.grools.fact.ObservationType;
import fr.cea.ig.grools.fact.PriorKnowledge;
import fr.cea.ig.grools.fact.PriorKnowledgeImpl;
import fr.cea.ig.grools.logic.TruthValue;
import fr.cea.ig.grools.fact.RelationImpl;
import fr.cea.ig.grools.fact.Relation;
import fr.cea.ig.grools.fact.RelationType;
import lombok.NonNull;

/**
 * Cases
 */
class Cases {

    static void case1(@NonNull final Reasoner reasoner) throws Exception {
        PriorKnowledge pk1 = PriorKnowledgeImpl.builder()
                                               .name( "pk1" )
                                               .build();
        Observation o1 = ObservationImpl.builder()
                                        .name( "o" )
                                        .type( ObservationType.COMPUTATION )
                                        .truthValue( TruthValue.t )
                                        .build();
        Relation o1ToPk1 = new RelationImpl( o1, pk1, o1.getType() );
        reasoner.insert( pk1, o1, o1ToPk1 );

        reasoner.reasoning();

    }

    static void case2(@NonNull final Reasoner reasoner) throws Exception {
        PriorKnowledge pk1 = PriorKnowledgeImpl.builder()
                                               .name( "pk1" )
                                               .build();
        Observation o1 = ObservationImpl.builder()
                                        .name( "o" )
                                        .type( ObservationType.ANNOTATION )
                                        .build();
        Relation o1ToPk1 = new RelationImpl( o1, pk1, o1.getType() );
        reasoner.insert( pk1, o1, o1ToPk1 );

        reasoner.reasoning();
    }

    static void case3(@NonNull final Reasoner reasoner) throws Exception {
        PriorKnowledge pk1 = PriorKnowledgeImpl.builder()
                                               .name( "pk1" )
                                               .build();
        Observation o1 = ObservationImpl.builder()
                                        .name( "o1" )
                                        .type( ObservationType.ANNOTATION )
                                        .truthValue( TruthValue.f )
                                        .build();
        Relation o1ToPk1 = new RelationImpl( o1, pk1, o1.getType() );
        reasoner.insert( pk1, o1, o1ToPk1 );

        reasoner.reasoning();

        Observation o2 = ObservationImpl.builder()
                                        .name( "o2" )
                                        .type( ObservationType.COMPUTATION )
                                        .build();
        Relation o2ToPk1 = new RelationImpl( o2, pk1, o2.getType() );
        reasoner.insert( o2, o2ToPk1 );

        reasoner.reasoning();

    }

    static void case4( @NonNull final Reasoner reasoner ) throws Exception {
        PriorKnowledge pk1 = PriorKnowledgeImpl.builder()
                                               .name( "pk1" )
                                               .build();
        Observation o1 = ObservationImpl.builder()
                                        .name( "o1" )
                                        .type( ObservationType.ANNOTATION )
                                        .build();
        Relation o1ToPk1 = new RelationImpl( o1, pk1, o1.getType() );

        Observation o2 = ObservationImpl.builder()
                                        .name( "o2" )
                                        .type( ObservationType.COMPUTATION )
                                        .build();
        Relation o2ToPk1 = new RelationImpl( o2, pk1, o2.getType() );
        reasoner.insert( pk1, o1, o1ToPk1, o2, o2ToPk1 );

        reasoner.reasoning();

        reasoner.delete( o1ToPk1 );
        reasoner.reasoning();
    }

    static void case5( @NonNull final Reasoner reasoner ) throws Exception {
        PriorKnowledge pk1 = PriorKnowledgeImpl.builder()
                                               .name( "pk1" )
                                               .build();
        PriorKnowledge pk11 = PriorKnowledgeImpl.builder()
                                                .name( "pk11" )
                                                .build();
        Relation    r11 = new RelationImpl( pk11, pk1, RelationType.PART) ;
        Observation o11 = ObservationImpl.builder()
                                         .name( "o11" )
                                         .type( ObservationType.COMPUTATION )
                                         .truthValue( TruthValue.t )
                                         .build();
        Relation o11ToPk11 = new RelationImpl( o11, pk11, o11.getType() );
        reasoner.insert( pk1, o11, o11ToPk11, pk11, r11 );

        reasoner.reasoning();
    }

    static void case6( @NonNull final Reasoner reasoner ) throws Exception {
        PriorKnowledge pk1 = PriorKnowledgeImpl.builder()
                                               .name( "pk1" )
                                               .build();
        PriorKnowledge pk11 = PriorKnowledgeImpl.builder()
                                                .name( "pk11" )
                                                .build();
        PriorKnowledge pk12 = PriorKnowledgeImpl.builder()
                                                .name( "pk12" )
                                                .build();
        Relation    r11 = new RelationImpl( pk11, pk1, RelationType.PART) ;
        Relation    r12 = new RelationImpl( pk12, pk1, RelationType.PART) ;
        Observation o11 = ObservationImpl.builder()
                                         .name( "o11" )
                                         .type( ObservationType.COMPUTATION )
                                         .truthValue( TruthValue.t )
                                         .build();
        Relation o11ToPk11 = new RelationImpl( o11, pk11, o11.getType() );
        reasoner.insert( pk1, o11, o11ToPk11, pk11, r11, pk12, r12 );

        reasoner.reasoning();
    }

    static void case7( @NonNull final Reasoner reasoner ) throws Exception {
        PriorKnowledge pk1 = PriorKnowledgeImpl.builder()
                                               .name( "pk1" )
                                               .build();
        Observation o1 = ObservationImpl.builder()
                                        .name( "o1" )
                                        .type( ObservationType.ANNOTATION )
                                        .build();
        Relation o1ToPk1 = new RelationImpl( o1, pk1, o1.getType() );

        Observation o2 = ObservationImpl.builder()
                                        .name( "o2" )
                                        .truthValue(TruthValue.f)
                                        .type( ObservationType.COMPUTATION )
                                        .build();
        Relation o2ToPk1 = new RelationImpl( o2, pk1, o2.getType() );
        reasoner.insert( pk1, o1, o1ToPk1, o2, o2ToPk1 );

        reasoner.reasoning();
    }

    static void case8(@NonNull final Reasoner reasoner){

        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name( "A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final Relation relationBA = new RelationImpl( nB, nA, RelationType.PART );
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.PART );
        reasoner.insert( nA, nB, nC, relationBA, relationCA);

        final Observation oB = ObservationImpl.builder()
                                        .type( ObservationType.COMPUTATION)
                                        .name("oB")
                                        .truthValue( TruthValue.t )
                                        .build();
        final Relation oToB = new RelationImpl( oB, nB, oB.getType() );

        final Observation oC = ObservationImpl.builder()
                                        .type(ObservationType.COMPUTATION)
                                        .name("oC")
                                        .truthValue(TruthValue.t )
                                        .build();
        final Relation oToC = new RelationImpl( oC, nC, oC.getType() );

        reasoner.insert( oB, oC, oToB, oToC);

        reasoner.reasoning();
    }

    static void case9( @NonNull final Reasoner reasoner){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final Relation relationBA = new RelationImpl( nB, nA, RelationType.PART );
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.PART );
        reasoner.insert( nA, nB, nC, relationBA, relationCA);

        final Observation oB = ObservationImpl.builder()
                                              .type(ObservationType.COMPUTATION)
                                              .name("oB")
                                              .truthValue(TruthValue.f)
                                              .build();
        final Relation oToB = new RelationImpl( oB, nB, oB.getType() );

        final Observation oC = ObservationImpl.builder()
                                              .type(ObservationType.COMPUTATION)
                                              .name("oC")
                                              .truthValue(TruthValue.f)
                                              .build();
        final Relation oToC = new RelationImpl( oC, nC, oC.getType() );
        
        reasoner.insert( oB, oC, oToB, oToC );

        reasoner.reasoning();
    }

    static void case10( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final Relation relationBA = new RelationImpl( nB, nA, RelationType.PART );
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.PART );
        reasoner.insert( nA, nB, nC, relationBA, relationCA);

        Observation oB = ObservationImpl.builder().type(ObservationType.COMPUTATION)
                                        .name("oB")
                                        .truthValue(TruthValue.t )
                                        .build();

        Observation oC = ObservationImpl.builder().type(ObservationType.COMPUTATION)
                                        .name("oC")
                                        .truthValue(TruthValue.f)
                                        .build();
        final Relation oToB = new RelationImpl( oB, nB, oB.getType() );
        final Relation oToC = new RelationImpl( oC, nC, oC.getType() );

        reasoner.insert( oB, oC, oToB, oToC );

        reasoner.reasoning();
    }

    static void case11( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final Relation relationBA = new RelationImpl( nB, nA, RelationType.PART );
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.PART );
        reasoner.insert( nA, nB, nC, relationBA, relationCA);

        final Observation oB1 = ObservationImpl.builder().type(ObservationType.COMPUTATION)
                                         .name("oB1")
                                         .truthValue(TruthValue.t )
                                         .build();

        final Observation oB2 = ObservationImpl.builder().type(ObservationType.COMPUTATION)
                                         .name("oB2")
                                         .truthValue(TruthValue.f)
                                         .build();

        final Observation oC = ObservationImpl.builder().type(ObservationType.COMPUTATION)
                                        .name("oC")
                                        .truthValue(TruthValue.f)
                                        .build();
        final Relation oB1ToB = new RelationImpl( oB1, nB, oB1.getType() );
        final Relation oB2ToB = new RelationImpl( oB2, nB, oB2.getType() );
        final Relation oCToC  = new RelationImpl( oC,  nC, oC.getType()  );
        reasoner.insert( oB1, oB2, oC, oB1ToB, oB2ToB, oCToC);

        final Relation obsB1RelConB    = new RelationImpl( oB1 , nB, RelationType.PREDICTION );
        final Relation obsB2RelConB    = new RelationImpl( oB2 , nB, RelationType.PREDICTION );
        final Relation obsCRelConC     = new RelationImpl( oC  , nC, RelationType.PREDICTION );

        reasoner.insert( oB1, oB2, oC, obsB1RelConB, obsB2RelConB, obsCRelConC );

        reasoner.reasoning();

    }

    static void case12( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final PriorKnowledge nD = PriorKnowledgeImpl.builder().name("D").build();
        final PriorKnowledge nE = PriorKnowledgeImpl.builder().name("E").build();
        final PriorKnowledge nF = PriorKnowledgeImpl.builder().name("F").build();

        final Relation relationBA = new RelationImpl( nB, nA, RelationType.SUBTYPE);
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.SUBTYPE);
        final Relation relationDB = new RelationImpl( nD, nB, RelationType.PART );
        final Relation relationEB = new RelationImpl( nE, nB, RelationType.PART );
        final Relation relationEC = new RelationImpl( nE, nC, RelationType.PART );
        final Relation relationFC = new RelationImpl( nF, nC, RelationType.PART );


        reasoner.insert( nA, nB, nC, nD, nE, nF, relationBA, relationCA, relationDB, relationEB, relationEC, relationFC);

        final Observation oB1 = ObservationImpl.builder()
                                         .type(ObservationType.COMPUTATION)
                                         .name("oB1")
                                         .truthValue(TruthValue.t )
                                         .build();

        final Observation oD1 = ObservationImpl.builder()
                                         .type(ObservationType.COMPUTATION)
                                         .name("oD1")
                                         .truthValue(TruthValue.f)
                                         .build();

        final Observation oE1 = ObservationImpl.builder()
                                         .type(ObservationType.COMPUTATION)
                                         .name("oE")
                                         .truthValue(TruthValue.f)
                                         .build();

        final Observation oF1 = ObservationImpl.builder()
                                         .type(ObservationType.COMPUTATION)
                                         .name("oF")
                                         .truthValue(TruthValue.t )
                                         .build();
        final Relation oB1ToB = new RelationImpl( oB1, nB, oB1.getType() );
        final Relation oD1ToD = new RelationImpl( oD1, nD, oD1.getType() );
        final Relation oE1ToE = new RelationImpl( oE1, nE, oE1.getType() );
        final Relation oF1ToF = new RelationImpl( oF1, nF, oF1.getType() );
        reasoner.insert( oB1, oD1, oE1, oF1, oB1ToB, oD1ToD, oE1ToE, oF1ToF );

        reasoner.reasoning();

    }

    static void case13( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final PriorKnowledge nD = PriorKnowledgeImpl.builder().name("D").build();
        final PriorKnowledge nE = PriorKnowledgeImpl.builder().name("E").build();
        final PriorKnowledge nF = PriorKnowledgeImpl.builder().name("F").build();

        final Relation relationBA = new RelationImpl( nB, nA, RelationType.SUBTYPE);
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.SUBTYPE);
        final Relation relationDB = new RelationImpl( nD, nB, RelationType.PART );
        final Relation relationEB = new RelationImpl( nE, nB, RelationType.PART );
        final Relation relationEC = new RelationImpl( nE, nC, RelationType.PART );
        final Relation relationFC = new RelationImpl( nF, nC, RelationType.PART );


        reasoner.insert( nA, nB, nC, nD, nE, nF, relationBA, relationCA, relationDB, relationEB, relationEC, relationFC);

        final Observation oD1 = ObservationImpl.builder()
                                         .type(ObservationType.COMPUTATION)
                                         .name("oD1")
                                         .truthValue(TruthValue.f)
                                         .build();

        final Observation oE1 = ObservationImpl.builder()
                                         .type(ObservationType.COMPUTATION)
                                         .name("oE")
                                         .truthValue(TruthValue.f)
                                         .build();

        final Observation oF1 = ObservationImpl.builder()
                                         .type(ObservationType.COMPUTATION)
                                         .name("oF")
                                         .truthValue(TruthValue.t )
                                         .build();
        final Relation oD1ToD = new RelationImpl( oD1, nD );
        final Relation oE1ToE = new RelationImpl( oE1, nE );
        final Relation oF1ToF = new RelationImpl( oF1, nF );
        reasoner.insert( oD1, oE1, oF1, oD1ToD, oE1ToE, oF1ToF );

        reasoner.insert( oD1, oE1, oF1);

        reasoner.reasoning();

    }

    static void case14( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final PriorKnowledge nD = PriorKnowledgeImpl.builder().name("D").build();
        final PriorKnowledge nE = PriorKnowledgeImpl.builder().name("E").build();
        final PriorKnowledge nF = PriorKnowledgeImpl.builder().name("F").build();

        final Relation relationBA = new RelationImpl( nB, nA, RelationType.SUBTYPE);
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.SUBTYPE);
        final Relation relationDB = new RelationImpl( nD, nB, RelationType.PART );
        final Relation relationEB = new RelationImpl( nE, nB, RelationType.PART );
        final Relation relationEC = new RelationImpl( nE, nC, RelationType.PART );
        final Relation relationFC = new RelationImpl( nF, nC, RelationType.PART );


        reasoner.insert( nA, nB, nC, nD, nE, nF, relationBA, relationCA, relationDB, relationEB, relationEC, relationFC);

        Observation opD1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opD1")
                                          .truthValue(TruthValue.f)
                                          .build();

        Observation opE1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opE")
                                          .truthValue(TruthValue.f)
                                          .build();

        Observation opF1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opF")
                                          .truthValue(TruthValue.t )
                                          .build();


        Observation oeA1 = ObservationImpl.builder()
                                          .type( ObservationType.EXPERIMENTATION)
                                          .name("oeA1")
                                          .truthValue(TruthValue.f)
                                          .build();
        final Relation oD1ToD = new RelationImpl( opD1, nD );
        final Relation oE1ToE = new RelationImpl( opE1, nE );
        final Relation oF1ToF = new RelationImpl( opF1, nF );
        final Relation oeA1ToA= new RelationImpl( oeA1, nA );
        reasoner.insert( opD1, opE1, opF1, oeA1, oD1ToD, oE1ToE, oF1ToF, oeA1ToA );


        reasoner.reasoning();

    }

    static void case15( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final PriorKnowledge nD = PriorKnowledgeImpl.builder().name("D").build();
        final PriorKnowledge nE = PriorKnowledgeImpl.builder().name("E").build();
        final PriorKnowledge nF = PriorKnowledgeImpl.builder().name("F").build();

        final Relation relationBA = new RelationImpl( nB, nA, RelationType.SUBTYPE);
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.SUBTYPE);
        final Relation relationDB = new RelationImpl( nD, nB, RelationType.PART );
        final Relation relationEB = new RelationImpl( nE, nB, RelationType.PART );
        final Relation relationEC = new RelationImpl( nE, nC, RelationType.PART );
        final Relation relationFC = new RelationImpl( nF, nC, RelationType.PART );


        reasoner.insert( nA, nB, nC, nD, nE, nF, relationBA, relationCA, relationDB, relationEB, relationEC, relationFC);

        Observation opD1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opD1")
                                          .truthValue(TruthValue.f)
                                          .build();

        Observation opE1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opE")
                                          .truthValue(TruthValue.t )
                                          .build();

        Observation opF1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opF")
                                          .truthValue(TruthValue.t )
                                          .build();


        Observation oeA1 = ObservationImpl.builder()
                                          .type( ObservationType.EXPERIMENTATION)
                                          .name("oeA1")
                                          .truthValue(TruthValue.f)
                                          .build();
        final Relation oD1ToD = new RelationImpl( opD1, nD );
        final Relation oE1ToE = new RelationImpl( opE1, nE );
        final Relation oF1ToF = new RelationImpl( opF1, nF );
        final Relation oeA1ToA= new RelationImpl( oeA1, nA );
        reasoner.insert( opD1, opE1, opF1, oeA1, oD1ToD, oE1ToE, oF1ToF, oeA1ToA );

        reasoner.reasoning();

    }

    static void case16( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final PriorKnowledge nD = PriorKnowledgeImpl.builder().name("D").build();
        final PriorKnowledge nE = PriorKnowledgeImpl.builder().name("E").build();
        final PriorKnowledge nF = PriorKnowledgeImpl.builder().name("F").build();

        final Relation relationBA = new RelationImpl( nB, nA, RelationType.SUBTYPE);
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.SUBTYPE);
        final Relation relationDB = new RelationImpl( nD, nB, RelationType.PART );
        final Relation relationEB = new RelationImpl( nE, nB, RelationType.PART );
        final Relation relationEC = new RelationImpl( nE, nC, RelationType.PART );
        final Relation relationFC = new RelationImpl( nF, nC, RelationType.PART );


        reasoner.insert( nA, nB, nC, nD, nE, nF, relationBA, relationCA, relationDB, relationEB, relationEC, relationFC);

        Observation opD1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opD1")
                                          .truthValue(TruthValue.f)
                                          .build();

        Observation opF1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opF")
                                          .truthValue(TruthValue.t )
                                          .build();


        Observation oeA1 = ObservationImpl.builder()
                                          .type( ObservationType.EXPERIMENTATION)
                                          .name("oeA1")
                                          .truthValue(TruthValue.f)
                                          .build();
        final Relation oD1ToD = new RelationImpl( opD1, nD );
        final Relation oF1ToF = new RelationImpl( opF1, nF );
        final Relation oeA1ToA= new RelationImpl( oeA1, nA );
        reasoner.insert( opD1, opF1, oeA1, oD1ToD, oF1ToF, oeA1ToA );


        reasoner.reasoning();

    }

    static void case17( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final PriorKnowledge nD = PriorKnowledgeImpl.builder().name("D").build();
        final PriorKnowledge nE = PriorKnowledgeImpl.builder().name("E").build();
        final PriorKnowledge nF = PriorKnowledgeImpl.builder().name("F").build();

        final Relation relationBA = new RelationImpl( nB, nA, RelationType.SUBTYPE);
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.SUBTYPE);
        final Relation relationDB = new RelationImpl( nD, nB, RelationType.PART );
        final Relation relationEB = new RelationImpl( nE, nB, RelationType.PART );
        final Relation relationEC = new RelationImpl( nE, nC, RelationType.PART );
        final Relation relationFC = new RelationImpl( nF, nC, RelationType.PART );


        reasoner.insert( nA, nB, nC, nD, nE, nF, relationBA, relationCA, relationDB, relationEB, relationEC, relationFC);

        Observation opD1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opD1")
                                          .truthValue(TruthValue.f)
                                          .build();

        Observation opF1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opF")
                                          .truthValue(TruthValue.t )
                                          .build();


        Observation oeA1 = ObservationImpl.builder()
                                          .type( ObservationType.EXPERIMENTATION)
                                          .name("oeA1")
                                          .truthValue(TruthValue.t )
                                          .build();
        final Relation oD1ToD = new RelationImpl( opD1, nD );
        final Relation oF1ToF = new RelationImpl( opF1, nF );
        final Relation oeA1ToA= new RelationImpl( oeA1, nA );
        reasoner.insert( opD1, opF1, oeA1, oD1ToD, oF1ToF, oeA1ToA );


        reasoner.reasoning();

    }

    static void case18( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").isDispensable( true ).build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final Relation relationBA = new RelationImpl( nB, nA, RelationType.PART);
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.PART);
        final Observation opB1 = ObservationImpl.builder()
                                                .type(ObservationType.COMPUTATION)
                                                .name("opB1")
                                                .truthValue(TruthValue.f)
                                                .build();
        final Observation opC1 = ObservationImpl.builder()
                                                .type(ObservationType.COMPUTATION)
                                                .name("opC1")
                                                .truthValue(TruthValue.t)
                                                .build();
        final Relation opB1ToB = new RelationImpl( opB1, nB );
        final Relation opC1ToC = new RelationImpl( opC1, nC );
        reasoner.insert( nA, nB, nC, relationBA, relationCA, opB1, opC1, opB1ToB, opC1ToC);

        reasoner.reasoning();

    }

    static void case19( @NonNull final Reasoner reasoner ){
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name("A").build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name("B").build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name("C").build();
        final PriorKnowledge nD = PriorKnowledgeImpl.builder().name("D").build();
        final PriorKnowledge nE = PriorKnowledgeImpl.builder().name("E").isDispensable( true ).build();
        final PriorKnowledge nF = PriorKnowledgeImpl.builder().name("F").build();

        final Relation relationBA = new RelationImpl( nB, nA, RelationType.SUBTYPE);
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.SUBTYPE);
        final Relation relationDB = new RelationImpl( nD, nB, RelationType.PART );
        final Relation relationEB = new RelationImpl( nE, nB, RelationType.PART );
        final Relation relationEC = new RelationImpl( nE, nC, RelationType.PART );
        final Relation relationFC = new RelationImpl( nF, nC, RelationType.PART );


        reasoner.insert( nA, nB, nC, nD, nE, nF, relationBA, relationCA, relationDB, relationEB, relationEC, relationFC);

        final Observation opD1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opD1")
                                          .truthValue(TruthValue.f)
                                          .build();

        final Observation opF1 = ObservationImpl.builder()
                                          .type(ObservationType.COMPUTATION)
                                          .name("opF")
                                          .truthValue(TruthValue.t )
                                          .build();


        final Observation oeA1 = ObservationImpl.builder()
                                          .type( ObservationType.EXPERIMENTATION)
                                          .name("oeA1")
                                          .truthValue(TruthValue.t )
                                          .build();
        final Relation oD1ToD = new RelationImpl( opD1, nD );
        final Relation oF1ToF = new RelationImpl( opF1, nF );
        final Relation oeA1ToA= new RelationImpl( oeA1, nA );
        reasoner.insert( opD1, opF1, oeA1, oD1ToD, oF1ToF, oeA1ToA );


        reasoner.reasoning();

    }


    static void case20( @NonNull final Reasoner reasoner ) {
        final PriorKnowledge nA = PriorKnowledgeImpl.builder().name( "A" ).build();
        final PriorKnowledge nB = PriorKnowledgeImpl.builder().name( "B" ).build();
        final PriorKnowledge nC = PriorKnowledgeImpl.builder().name( "C" ).build();
        final PriorKnowledge nD = PriorKnowledgeImpl.builder().name( "D" ).build();
        final PriorKnowledge nE = PriorKnowledgeImpl.builder().name( "E" ).build();
        final PriorKnowledge nF = PriorKnowledgeImpl.builder().name( "F" ).build();
        final PriorKnowledge nG = PriorKnowledgeImpl.builder().name( "G" ).build();
        final PriorKnowledge nH = PriorKnowledgeImpl.builder().name( "H" ).build();
        final PriorKnowledge nI = PriorKnowledgeImpl.builder().name( "I" ).build();
        final PriorKnowledge nJ = PriorKnowledgeImpl.builder().name( "J" ).build();
        final PriorKnowledge nK = PriorKnowledgeImpl.builder().name( "K" ).build();
        final PriorKnowledge nL = PriorKnowledgeImpl.builder().name( "L" ).build();

        reasoner.insert( nA, nB, nC, nD, nE, nF, nG, nH, nI, nJ, nK, nL );

        final Relation relationBA = new RelationImpl( nB, nA, RelationType.SUBTYPE );
        final Relation relationCA = new RelationImpl( nC, nA, RelationType.SUBTYPE );

        reasoner.insert( relationBA, relationCA );

        final Relation relationDB = new RelationImpl( nD, nB, RelationType.PART );
        final Relation relationEB = new RelationImpl( nE, nB, RelationType.PART );
        final Relation relationFB = new RelationImpl( nF, nB, RelationType.PART );

        reasoner.insert( relationDB, relationEB, relationFB );

        final Relation relationDC = new RelationImpl( nD, nC, RelationType.PART );
        final Relation relationFC = new RelationImpl( nF, nC, RelationType.PART );
        final Relation relationGC = new RelationImpl( nG, nC, RelationType.PART );

        reasoner.insert( relationDC, relationFC, relationGC );


        final Relation relationHD = new RelationImpl( nF, nD, RelationType.PART );
        final Relation relationIE = new RelationImpl( nI, nE, RelationType.PART );
        final Relation relationJE = new RelationImpl( nJ, nE, RelationType.PART );
        final Relation relationKF = new RelationImpl( nK, nF, RelationType.PART );
        final Relation relationLG = new RelationImpl( nL, nG, RelationType.PART );


        reasoner.insert( relationHD, relationIE, relationJE, relationKF, relationLG);



        final Observation opH1 = ObservationImpl.builder()
                                                .type(ObservationType.COMPUTATION)
                                                .name("opH1")
                                                .truthValue(TruthValue.t)
                                                .build();
        final Observation opI1 = ObservationImpl.builder()
                                                .type(ObservationType.COMPUTATION)
                                                .name("opI1")
                                                .truthValue(TruthValue.t)
                                                .build();
        final Observation opK1 = ObservationImpl.builder()
                                                .type(ObservationType.COMPUTATION)
                                                .name("opK1")
                                                .truthValue(TruthValue.t)
                                                .build();
        final Observation oeA1 = ObservationImpl.builder()
                                                .type(ObservationType.EXPERIMENTATION)
                                                .name("oeA1")
                                                .truthValue(TruthValue.t)
                                                .build();

        reasoner.insert( opH1, opI1, opK1, oeA1 );

        final Relation opH1ToH = new RelationImpl( opH1, nH );
        final Relation opI1ToI = new RelationImpl( opI1, nI );
        final Relation opK1ToK = new RelationImpl( opK1, nK );
        final Relation oeA1ToA = new RelationImpl( oeA1, nA );

        reasoner.insert( opH1ToH, opI1ToI, opK1ToK, oeA1ToA );


        reasoner.reasoning();
    }
}
