package fr.cea.ig.grools.drools;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.cea.ig.grools.Mode;
import fr.cea.ig.grools.Reasoner;
import fr.cea.ig.grools.VariantMode;
import fr.cea.ig.grools.Verbosity;
import fr.cea.ig.grools.fact.Concept;
import fr.cea.ig.grools.fact.Observation;
import fr.cea.ig.grools.fact.ObservationType;
import fr.cea.ig.grools.fact.PriorKnowledge;
import fr.cea.ig.grools.fact.Relation;
import fr.cea.ig.grools.fact.RelationType;
import fr.cea.ig.grools.logic.TruthValue;
import fr.cea.ig.grools.logic.TruthValuePowerSet;
import fr.cea.ig.grools.logic.TruthValueSet;
import lombok.NonNull;
import org.drools.KnowledgeBase;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.conf.MBeansOption;
import org.kie.api.marshalling.Marshaller;
import org.kie.api.marshalling.ObjectMarshallingStrategy;
import org.kie.api.marshalling.ObjectMarshallingStrategyAcceptor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.conf.RuleEngineOption;
import org.kie.internal.marshalling.MarshallerFactory;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ReasonerImpl
 */
public final class ReasonerImpl implements Reasoner {

    private final static String KNAME   = "concept-reasoning";
    private final static transient Logger LOGGER  = (Logger) LoggerFactory.getLogger( ReasonerImpl.class );

    private final KieSession reasoner;
    private final Mode       mode;
    private final Verbosity  verbosity;

    private static String getKname( @NonNull final Mode mode){
        return mode.getMode() + '-' + KNAME;
    }


    private static <T> void storeQueryResults( @NonNull final QueryResults rows, @NonNull final String field, @NonNull final Set<T> results){
        for( final QueryResultsRow r : rows){
            final Object object = r.get( field );
            assert( object instanceof Set<?> );
            results.addAll((Set<T> ) object);
        }
    }

    private static <T> T getResult( @NonNull final QueryResults rows, @NonNull final String field, Class<T> type){
        final Iterator<QueryResultsRow> iterator= rows.iterator();
        T result = null;
        if( iterator.hasNext()) {
            final Object object =  iterator.next().get( field );
            assert(type.cast( object ) != null);
            result = type.cast( object );
        }
        return result;

    }

    private <T> T query( @NonNull final String queryName, @NonNull final String field, Class<T> type){
        final QueryResults rows = reasoner.getQueryResults( queryName );
        return getResult(rows, field, type);
    }


    private <T> T query( @NonNull final String queryName, @NonNull final String field, Class<T> type, @NonNull final Object... parameters){
        final QueryResults rows = reasoner.getQueryResults( queryName, parameters );
        return getResult(rows, field, type);
    }


    private <T> void query( @NonNull final String queryName, @NonNull final String field, @NonNull final Set<T> results){
        final QueryResults  rows    = reasoner.getQueryResults( queryName );
        storeQueryResults(rows, field, results);
    }


    private <T> void query( @NonNull final String queryName, @NonNull final Object parameter, @NonNull final String field, @NonNull final Set<T> results){
        final QueryResults rows    = reasoner.getQueryResults( queryName, parameter );
        storeQueryResults(rows, field, results);
    }


    private <T> void query( @NonNull final String queryName, @NonNull final Object parameter1, @NonNull final Object parameter2, @NonNull final String field, @NonNull final Set<T> results){
        final QueryResults rows    = reasoner.getQueryResults( queryName, parameter1, parameter2 );
        storeQueryResults(rows, field, results);
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
                reasoner.addEventListener( new LogRuleDRL() );
                reasoner.addEventListener( new LogAgendaDRL() );
                root.setLevel( Level.DEBUG);
                break;
            case HIGHT:
                reasoner.addEventListener( new LogAgendaDRL() );
                root.setLevel( Level.TRACE);
                break;
        }
        reasoner.setGlobal( "logger", LOGGER );
        reasoner.insert( mode );
        final EnumSet<TruthValuePowerSet> tvpset = EnumSet.allOf(TruthValuePowerSet.class);
        tvpset.forEach( reasoner::insert );
        final EnumSet<TruthValueSet> tvset = EnumSet.allOf(TruthValueSet.class);
        tvset.forEach( reasoner::insert );
        final EnumSet<TruthValue> tv = EnumSet.allOf( TruthValue.class);
        tv.forEach( reasoner::insert );
        final EnumSet<ObservationType> observationTypes = EnumSet.allOf( ObservationType.class);
        observationTypes.forEach( reasoner::insert );
        final EnumSet<RelationType> relationTypes = EnumSet.allOf( RelationType.class);
        relationTypes.forEach( reasoner::insert );


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
        //final KieSessionConfiguration kieConf = ks.newKieSessionConfiguration();
        //kieConf.setProperty("drools.propertySpecific", "ALWAYS");
        //kbaseConf.setProperty("drools.propertySpecific", "ALWAYS");
        //kbaseConf.setOption( EventProcessingOption.CLOUD );
        // kbaseConf.setOption( MaxThreadsOption.get( 8 ) );
        kbaseConf.setOption( EventProcessingOption.STREAM );
        kbaseConf.setOption(RuleEngineOption.PHREAK );
        if( verbosity.compare( Verbosity.MEDIUM ) >= 0 )
            kbaseConf.setOption( MBeansOption.ENABLED );
        this.reasoner   = kContainer.newKieBase(getKname(m), kbaseConf).newKieSession( );
        this.mode       = m;
        this.verbosity  = verbosity;
        init();
    }

    public ReasonerImpl( @NonNull final KieSession kieSession, @NonNull final Mode m, Verbosity verbosity){
        this.reasoner   = kieSession;
        this.mode       = m;
        this.verbosity  = verbosity;
        init();
    }


    @Override
    public void addVariantMode(@NonNull final VariantMode... variants ) {
        final EntryPoint ep = reasoner.getEntryPoint( "add variant mode" );
        Arrays.stream(variants).forEach(ep::insert);
        reasoning();
    }


    @Override
    public void removeVariantMode(@NonNull final VariantMode... variants ) {
        final EntryPoint ep = reasoner.getEntryPoint( "remove variant mode" );
        Arrays.stream(variants).forEach(ep::insert);
        reasoning();
    }


    @Override
    public void insert(@NonNull final Object... data) {
        Arrays.stream(data)
              .forEach( (i) -> register( i, verbosity, reasoner ) );
    }


    @Override
    public void insert(@NonNull final Collection<?> data) {
        data.forEach( ( i ) -> register( i, verbosity, reasoner ) );
    }


    @Override
    public void delete(@NonNull final Object... data) {
        for( final Object obj : data){
            final FactHandle fact = reasoner.getFactHandle( obj );
            if( fact != null ){
                if( obj instanceof Concept) {
                    final Concept concept = (Concept) obj;
                    final Set<Relation> sources = getRelationsWithSource(concept);
                    final Set<Relation> targets = getRelationsWithTarget(concept);
                    delete(sources);
                    delete(targets);
                }
                reasoner.delete( fact );
            }
        }
    }


    @Override
    public void delete(@NonNull final Collection<?> data) {
        for( final Object obj : data){
            final FactHandle fact = reasoner.getFactHandle( obj );
            if( fact != null ){
                if( obj instanceof Concept) {
                    final Concept concept = (Concept) obj;
                    final Set<Relation> sources = getRelationsWithSource(concept);
                    final Set<Relation> targets = getRelationsWithTarget(concept);
                    delete(sources);
                    delete(targets);
                }
                reasoner.delete( fact );
            }
        }
    }

    @Override
    public Set<Concept> getConcepts(  ){
        final Set<Concept> concepts = new HashSet<>();
        query( "getConcepts", "$concepts", concepts );
        return concepts;
    }


    @Override
    public Concept getConcept(@NonNull final String $name){
        return query("getConcept", "$concept", Concept.class, $name);
    }


    @Override
    public PriorKnowledge getPriorKnowledge(@NonNull final String $name){
        return query("getPriorKnowledge", "$priorknowledge", PriorKnowledge.class, $name);
    }


    @Override
    public Set<PriorKnowledge> getPriorKnowledges(){
        final Set<PriorKnowledge> priorKnowledges = new HashSet<>();
        query( "getPriorKnowledges", "$priorKnowledges", priorKnowledges);
        return priorKnowledges;
    }

    @Override
    public Set<PriorKnowledge> getLeavesPriorKnowledges(){
        final Set<PriorKnowledge> priorKnowledges = new HashSet<>();
        query( "getLeavesPriorKnowledges", "$leaves", priorKnowledges);
        return priorKnowledges;
    }

    @Override
    public Set<PriorKnowledge> getTopsPriorKnowledges(){
        final Set<PriorKnowledge> priorKnowledges = new HashSet<>();
        query( "getTopsPriorKnowledges", "$tops", priorKnowledges);
        return priorKnowledges;
    }

    @Override
    public Relation getRelation( final Concept $source, final Concept $target, final Enum<?> $type ){
        return query("getRelation", "$relation", Relation.class, $source, $target, $type);
    }

    @Override
    public Set<Relation> getRelations( final Concept $source, final Concept $target ){
        final Set<Relation> relations = new HashSet<>();
        query("getRelationsUsingImpliedConcepts", $source, $target, "$relations", relations);
        return relations;
    }


    @Override
    public Set<Relation> getRelationsWithSource( final Concept $source ){
        final Set<Relation> relations = new HashSet<>();
        query("getRelationsWithSource", $source, "$relations", relations);
        return relations;
    }

    @Override
    public Set<Relation> getRelationsWithTarget( final Concept concept ){
        final Set<Relation> relations = new HashSet<>();
        query("getRelationsWithTarget", concept, "$relations", relations);
        return relations;
    }

    @Override
    public Set<Relation> getRelations(){
        final Set<Relation> relations = new HashSet<>();
        query( "getRelations", "$relations", relations);
        return relations;
    }


    @Override
    public Set<Observation> getObservations(){
        final Set<Observation> observations = new HashSet<>();
        query( "getObservations", "$observations", observations );
        return observations;
    }


    @Override
    public Set<Observation>  getObservationsUsingConceptRelation(@NonNull final String conceptId) {
        final Set<Observation> observations = new HashSet<>();
        query( "getObservationsUsingConceptRelation", "$observations", observations );
        return observations;
    }


    @Override
    public Observation getObservation(@NonNull final String $name) {
        return query( "getObservation", "$observation", Observation.class, $name );
    }

    @Override
    public Set<Relation> getSubGraph( final Concept concept ){
        final Set<Relation>         relations = getRelationsWithTarget( concept );
        final Set<Set<Relation>>    queue     = relations.stream()
                                                         .map(Relation::getSource)
                                                         .distinct()
                                                         .map(this::getSubGraph)
                                                         .collect( Collectors.toSet() );
        for( final Set<Relation> children : queue)
                relations.addAll( children );
        //relations.addAll( getSubGraph( relation.getSource() ) ); // throw ConcurrentModificationException
        return relations;
    }

    @Override
    public void reasoning() {
        reasoner.getAgenda( ).getAgendaGroup( "specific mark" ).setFocus( );
        // fix relations between concept
        reasoner.getAgenda( ).getAgendaGroup( "graph-fixer" ).setFocus( );
        reasoner.fireAllRules( );

        // remove the agenda
        reasoner.getAgenda( ).getAgendaGroup( "graph-fixer" ).clear( );
        reasoner.getAgenda( ).getAgendaGroup( "specific mark" ).clear( );

        // Infer prediction corresponding to leaf nodes, Infer expectation corresponding to top nodes
        // follow by the post prediction agenda, example of use: search specific relations
        reasoner.getAgenda( ).getAgendaGroup( "observation" ).setFocus( );
        reasoner.getAgenda( ).getAgendaGroup( "qualifier mark" ).setFocus( );
        reasoner.getAgenda( ).getAgendaGroup( "prediction inference" ).setFocus( );
        reasoner.getAgenda( ).getAgendaGroup( "expectation inference" ).setFocus( );
        reasoner.getAgenda( ).getAgendaGroup( "conclusion" ).setFocus( );
        reasoner.fireAllRules( );
    }



    public Reasoner copy(){
        KieSession ksessionCopy;
        try {
            final Marshaller marshaller= MarshallerFactory.newMarshaller(reasoner.getKieBase());
            final ByteArrayOutputStream o         = new ByteArrayOutputStream();
            marshaller.marshall( o, reasoner );
            ksessionCopy = marshaller.unmarshall(   new ByteArrayInputStream( o.toByteArray() ),
                                                    reasoner.getSessionConfiguration( ),
                                                    KnowledgeBaseFactory.newEnvironment()
                                                );
        }
        catch (  Exception e) {
            throw new RuntimeException(e);
        }
        return new ReasonerImpl( ksessionCopy, mode, verbosity);
    }


    @Override
    public Mode getMode() {
        return mode;
    }


    @Override
    public void close() throws Exception {
        reasoner.dispose( );
    }

    @Override
    public void save( @NonNull final File file) throws IOException {
        final ObjectMarshallingStrategyAcceptor acceptor    = MarshallerFactory.newClassFilterAcceptor(new String[] { "*.*" });
        final ObjectMarshallingStrategy         strategy    = MarshallerFactory.newSerializeMarshallingStrategy(acceptor);
        final Marshaller                        marshaller  = MarshallerFactory.newMarshaller(reasoner.getKieBase(), new ObjectMarshallingStrategy[] { strategy });
        final FileOutputStream                  fos         = new FileOutputStream(file);
        final ObjectOutputStream                oos         = new ObjectOutputStream(fos);
        oos.writeObject(reasoner.getKieBase());
        oos.writeObject(reasoner.getSessionConfiguration());
        oos.writeObject(mode);
        oos.writeObject(verbosity);
        marshaller.marshall( fos, reasoner );
        oos.close();
        fos.close();
    }
}
