package fr.cea.ig.grools.drools;

import fr.cea.ig.grools.fact.Concept;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Message
 */
public final class Message {
    @Getter
    private final Concept concept;

    @Getter @Setter
    private Step step;

    public Message( @NonNull final Concept concept){
        this.concept    = concept;
        this.step       = Step.NONE;
    }
}
