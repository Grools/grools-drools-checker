package fr.cea.ig.grools.drools;

import fr.cea.ig.grools.Mode;
import fr.cea.ig.grools.Reasoner;
import fr.cea.ig.grools.VariantMode;
import fr.cea.ig.grools.Verbosity;
import fr.cea.ig.grools.fact.Concept;
import fr.cea.ig.grools.fact.PriorKnowledgeImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * ReasonerTest
 */
public class ReasonerTest {
    private Reasoner reasoner;

    @Before
    public void setUp(){
        reasoner = new ReasonerImpl( Mode.NORMAL, Verbosity.HIGHT);
    }

    @Test
    public void copyReasoner(){
        final ReasonerImpl reasoner_copied = (ReasonerImpl) reasoner.copy();
        assertNotNull(reasoner_copied);
        assertNotEquals(reasoner_copied, reasoner);
    }

    @Test
    public void reasonerHasNormalMode(){
        final Mode mode = reasoner.getMode();
        EnumSet<VariantMode> vm = EnumSet.of( VariantMode.NORMAL );
        assertNotNull(mode);
        assertEquals(mode, Mode.NORMAL);
        assertTrue( mode.getVariants().containsAll( vm ) );
    }

    @Test
    public void reasonerHasEssentialMode(){
        reasoner.addVariantMode(VariantMode.DISPENSABLE);
        reasoner.reasoning();
        final Mode mode = reasoner.getMode();
        EnumSet<VariantMode> vm = EnumSet.of( VariantMode.DISPENSABLE );
        assertNotNull(mode);
        assertEquals(mode, Mode.NORMAL);
        assertTrue( mode.getVariants().containsAll( vm ) );
    }

    @Test
    public void getConcepts(){
        fr.cea.ig.grools.fact.PriorKnowledge pk1 = PriorKnowledgeImpl.builder().name( "test1" ).build();
        fr.cea.ig.grools.fact.PriorKnowledge pk2 = PriorKnowledgeImpl.builder().name( "test2" ).build();
        fr.cea.ig.grools.fact.PriorKnowledge pk3 = PriorKnowledgeImpl.builder().name( "test3" ).build();
        reasoner.insert( pk1,pk2,pk3 );
        final fr.cea.ig.grools.fact.PriorKnowledge concept1 = reasoner.getPriorKnowledge( "test1" );
        final Set<Concept> concepts = reasoner.getConcepts();
        assertNotNull( concept1 );
        assertEquals( pk1, concept1 );
        assertNotNull( concepts );
    }
}
