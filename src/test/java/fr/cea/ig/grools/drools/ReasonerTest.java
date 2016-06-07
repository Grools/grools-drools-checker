package fr.cea.ig.grools.drools;

import fr.cea.ig.grools.Mode;
import fr.cea.ig.grools.Reasoner;
import fr.cea.ig.grools.VariantMode;
import fr.cea.ig.grools.Verbosity;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;

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
}
