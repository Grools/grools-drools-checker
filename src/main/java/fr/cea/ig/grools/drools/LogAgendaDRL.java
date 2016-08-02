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

import org.drools.core.reteoo.RuleTerminalNodeLeftTuple;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * LogAgendaDRL
 */
public class LogAgendaDRL implements AgendaEventListener {
    protected static final transient Logger logger = ( Logger ) LoggerFactory.getLogger( LogAgendaDRL.class);

    public LogAgendaDRL(){

    }

    @Override
    public void matchCreated( final MatchCreatedEvent event ) {
        final RuleTerminalNodeLeftTuple match   = ( RuleTerminalNodeLeftTuple ) event.getMatch();
        final List<Object>              objects = match.getObjects();
        final StringBuilder             sb      = new StringBuilder();
        sb.append( "================ " + match.getRule().getName() +  " ================")
          .append( '\n' );
        final String obj = objects.stream()
                                  .map( Object::toString )
                                  .collect( Collectors.joining( "\n" ) );
        sb.append( obj );
        logger.debug( sb.toString() );
    }

    @Override
    public void matchCancelled( final MatchCancelledEvent event ) {
    }

    @Override
    public void beforeMatchFired( final BeforeMatchFiredEvent event ) {
    }

    @Override
    public void afterMatchFired( final AfterMatchFiredEvent event ) {
    }

    @Override
    public void agendaGroupPopped( final AgendaGroupPoppedEvent event ) {
        logger.debug( "Removing Agenda: " + event.getAgendaGroup().getName() );
    }

    @Override
    public void agendaGroupPushed( final AgendaGroupPushedEvent event ) {
        logger.debug( "Activating Agenda: " + event.getAgendaGroup().getName() );

    }

    @Override
    public void beforeRuleFlowGroupActivated( final RuleFlowGroupActivatedEvent event ) {
        System.out.println();
    }

    @Override
    public void afterRuleFlowGroupActivated( final RuleFlowGroupActivatedEvent event ) {
        System.out.println();

    }

    @Override
    public void beforeRuleFlowGroupDeactivated( final RuleFlowGroupDeactivatedEvent event ) {
        System.out.println();

    }

    @Override
    public void afterRuleFlowGroupDeactivated( final RuleFlowGroupDeactivatedEvent event ) {
        System.out.println();

    }
}
