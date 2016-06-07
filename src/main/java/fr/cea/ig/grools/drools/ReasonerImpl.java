package fr.cea.ig.grools.drools;


import fr.cea.ig.grools.fact.ObservationType;
import fr.cea.ig.grools.fact.PriorKnowledge;
import fr.cea.ig.grools.logic.TruthValue;
import fr.cea.ig.grools.logic.TruthValuePowerSet;
import fr.cea.ig.grools.logic.TruthValueSet;
import fr.cea.ig.grools.fact.RelationType;
import org.kie.api.conf.EventProcessingOption;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;

import fr.cea.ig.grools.fact.Concept;
import fr.cea.ig.grools.Mode;
import fr.cea.ig.grools.fact.Observation;
import fr.cea.ig.grools.Reasoner;
import fr.cea.ig.grools.fact.Relation;
import fr.cea.ig.grools.VariantMode;
import fr.cea.ig.grools.Verbosity;

import lombok.NonNull;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.MBeansOption;
import org.kie.api.marshalling.Marshaller;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.marshalling.MarshallerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * ReasonerImpl
 */
public final class ReasonerImpl implements Reasoner {

    private final static long serialVersionUID = 5808212875659211714L;
    private final static String KNAME   = "concept-reasoning";
    private final static Logger LOGGER  = (Logger) LoggerFactory.getLogger( ReasonerImpl.class );

    private final KieSession    kieSession;
    private final KieBase       kbase;
    private final Mode          mode;
    private final Verbosity     verbosity;

    private static String getKname( @NonNull final Mode mode){
        return mode.getMode() + '-' + KNAME;
    }


    private <T> T query( @NonNull final String queryName, @NonNull final String field, Class<T> type){
        final QueryResults              rows    = kieSession.getQueryResults( queryName );
        final Iterator<QueryResultsRow> iterator= rows.iterator();
        T result = null;
        if( iterator.hasNext()) {
            final Object object =  iterator.next().get( field );
            assert(type.cast( object ) != null);
            result = type.cast( object );
        }
        return result;
    }


    private <T> T query( @NonNull final String queryName, @NonNull final Object parameter, @NonNull final String field, Class<T> type){
        final QueryResults              rows    = kieSession.getQueryResults( queryName, parameter );
        final Iterator<QueryResultsRow> iterator= rows.iterator();
        T result = null;
        if( iterator.hasNext()) {
            final Object object =  iterator.next().get( field );
            assert(type.cast( object ) != null);
            result = type.cast( object );
        }
        return result;
    }


    private <T> T query( @NonNull final String queryName, @NonNull final Object parameter1, @NonNull final Object parameter2, @NonNull final String field, Class<T> type){
        final QueryResults              rows    = kieSession.getQueryResults( queryName, parameter1, parameter2 );
        final Iterator<QueryResultsRow> iterator= rows.iterator();
        T result = null;
        if( iterator.hasNext()) {
            final Object object =  iterator.next().get( field );
            assert(type.cast( object ) != null);
            result = type.cast( object );
        }
        return result;
    }


    private <T> void query( @NonNull final String queryName, @NonNull final String field, @NonNull final List<T> results){
        final QueryResults  rows    = kieSession.getQueryResults( queryName );

        for( final QueryResultsRow r : rows){
            final Object object = r.get( field );
            assert( object instanceof List<?> );
            results.addAll((List<T>) object);
        }
    }


    private <T> void query( @NonNull final String queryName, @NonNull final Object parameter, @NonNull final String field, @NonNull final List<T> results){
        final QueryResults rows    = kieSession.getQueryResults( queryName, parameter );

        for( final QueryResultsRow r : rows){
            final Object object = r.get( field );
            assert( object instanceof List<?> );
            results.addAll((List<T>) object);
        }
    }


    private void init(){
        final Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        switch ( verbosity ){
            case QUIET:
                root.setLevel( Level.OFF );
            case LOW:
                root.setLevel( Level.INFO);
                break;
            case MEDIUM:
                kieSession.addEventListener( new LogRuleDRL() );
                root.setLevel( Level.DEBUG);
                break;
            case HIGHT:
                root.setLevel( Level.TRACE);
                break;
        }
        kieSession.insert( mode );
        final EnumSet<TruthValuePowerSet> tvpset = EnumSet.allOf(TruthValuePowerSet.class);
        tvpset.forEach(kieSession::insert);
        final EnumSet<TruthValueSet> tvset = EnumSet.allOf(TruthValueSet.class);
        tvset.forEach(kieSession::insert);
        final EnumSet<TruthValue> tv = EnumSet.allOf( TruthValue.class);
        tv.forEach(kieSession::insert);
        final EnumSet<ObservationType> observationTypes = EnumSet.allOf( ObservationType.class);
        observationTypes.forEach(kieSession::insert);
        final EnumSet<RelationType> relationTypes = EnumSet.allOf( RelationType.class);
        relationTypes.forEach(kieSession::insert);
    }


    private static void register(@NonNull final Object object, @NonNull final Verbosity verbosity, @NonNull final KieSession kieSession){
        if( verbosity.compare( Verbosity.MEDIUM ) >= 0 ){
            /*try {
                final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
                ObjectName name = null;
                if ( object instanceof Observation )
                    name = new ObjectName("fr.cea.ig.grools:name=Observation");
                else if(object instanceof ObservationSet )
                    name = new ObjectName("fr.cea.ig.grools:name=ObservationSet");
                else if(object instanceof Concept )
                    name = new ObjectName("fr.cea.ig.grools:name=Concept");
                mbs.registerMBean(object, name);
            } catch ( MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e ) {
                e.printStackTrace();
            }*/
        }
        kieSession.insert(object);
    }


    public ReasonerImpl(@NonNull final Mode m ){
        this( m, Verbosity.QUIET );
    }


    public ReasonerImpl(){
        this( Mode.NORMAL );
    }


    public ReasonerImpl( @NonNull final Mode m, Verbosity verbosity){
        final KieServices           ks          = KieServices.Factory.get();
        final KieContainer          kContainer  = ks.getKieClasspathContainer();
        final KieBaseConfiguration  kbaseConf   = ks.newKieBaseConfiguration();
        kbaseConf.setProperty("drools.propertySpecific", "ALWAYS");
        kbaseConf.setOption( EventProcessingOption.STREAM );
        if( verbosity.compare( Verbosity.MEDIUM ) >= 0 )
            kbaseConf.setOption( MBeansOption.ENABLED );
        final KieBase kbase       = kContainer.newKieBase(getKname(m), kbaseConf);
        final KieSession kieSession = kbase.newKieSession();

        this.kbase      = kbase;
        this.kieSession = kieSession;
        this.mode       = m;
        this.verbosity  = verbosity;
        init();
    }


    private ReasonerImpl(@NonNull final KieBase kbase, @NonNull final KieSession kieSession, @NonNull final Mode mode ){
        this.kbase      = kbase;
        this.kieSession = kieSession;
        this.mode       = mode;
        this.verbosity  = Verbosity.QUIET;
        init();
    }


    @Override
    public void addVariantMode(@NonNull final VariantMode... variants ) {
        final EntryPoint ep = kieSession.getEntryPoint("add variant mode");
        Arrays.stream(variants).forEach(ep::insert);
        reasoning();
    }


    @Override
    public void removeVariantMode(@NonNull final VariantMode... variants ) {
        final EntryPoint ep = kieSession.getEntryPoint("remove variant mode");
        Arrays.stream(variants).forEach(ep::insert);
        reasoning();
    }


    @Override
    public void insert(@NonNull final Object... data) {
        Arrays.stream(data)
                .forEach( (i) -> register(i, verbosity, kieSession) );
    }


    @Override
    public void delete(@NonNull final Object... data) {
        Arrays.stream(data)
                .forEach( (i) -> kieSession.delete( kieSession.getFactHandle( i ) ) );
    }

    @Override
    public List<Concept> getConcepts(  ){
        final List<Concept> concepts = new ArrayList<>();
        query( "getConcepts", "$concepts", concepts );
        return concepts;
    }

    @Override
    public Relation getRelation( final Concept $source, final Concept $target ){
         return query("getRelation", $source, $target, "$relation", Relation.class);
    }

    @Override
    public Concept getConcept(@NonNull final String $name){
        return query("getConcept", $name, "$concept", Concept.class);
    }


    @Override
    public PriorKnowledge getPriorKnowledge(@NonNull final String $name){
        return query("getPriorKnowledge", $name, "$priorknowledge", PriorKnowledge.class);
    }


    @Override
    public List<Relation> getRelations(){
        final List<Relation> relations = new ArrayList<>();
        query( "getRelations", "$relations", relations);
        return relations;
    }


    @Override
    public List<Observation> getObservations(){
        final List<Observation> observations = new ArrayList<>();
        query( "getObservations", "$observations", observations );
        return observations;
    }


    @Override
    public List<Observation>  getObservationsUsingConceptRelation(@NonNull final String conceptId) {
        final List<Observation> observations = new ArrayList<>();
        query( "getObservationsUsingConceptRelation", "$observations", observations );
        return observations;
    }


    @Override
    public Observation getObservation(@NonNull final String $name) {
        return query( "getObservation", $name, "$observation", Observation.class );
    }


    @Override
    public void reasoning(){
        /*LOGGER.info("=================== initialisation ===================");
        kieSession.getAgenda().getAgendaGroup( "initialisation" ).setFocus();
        kieSession.fireAllRules();
        LOGGER.info("=================== prediction ===================");
        kieSession.getAgenda().getAgendaGroup( "prediction" ).setFocus();
        kieSession.fireAllRules();
        LOGGER.info("=================== expectation ===================");
        kieSession.getAgenda().getAgendaGroup( "expectation" ).setFocus();*/
        kieSession.fireAllRules();
    }


    public Reasoner copy(){
        KieSession ksessionCopy;
        try {
            final Marshaller marshaller= MarshallerFactory.newMarshaller(kbase);
            final ByteArrayOutputStream o         = new ByteArrayOutputStream();
            marshaller.marshall(o,kieSession);
            ksessionCopy = marshaller.unmarshall(
                                                    new ByteArrayInputStream( o.toByteArray() ),
                                                    kieSession.getSessionConfiguration(),
                                                    KnowledgeBaseFactory.newEnvironment()
                                                );
        }
        catch (  Exception e) {
            throw new RuntimeException(e);
        }
        return new ReasonerImpl(kbase, ksessionCopy, mode);
    }


    @Override
    public Mode getMode() {
        return mode;
    }


    @Override
    public void close() throws Exception {
        kieSession.dispose();
    }
}
