package fr.cea.ig.grools.drools;

import fr.cea.ig.grools.fact.Concept;
import lombok.Getter;
import lombok.NonNull;

/**
 * Message
 */
public final class Message {
    @Getter
    private final Concept concept;

    public Message( @NonNull final Concept concept){
        this.concept = concept;
    }
}
