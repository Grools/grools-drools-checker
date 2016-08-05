package fr.cea.ig.grools.drools;

import lombok.Getter;

/**
 * Step
 */
public enum Step {
    NONE        ("none"),
    OBSERVATION ("observation"),
    QUALIFIER   ("qualifier"),
    PREDICTION  ("prediction"),
    EXPECTATION ("expectation"),
    CONCLUSION  ("conclusion");

    @Getter
    private final String step;

    Step( final String step ){
        this.step = step;
    }

    @Override
    public String toString(){
        return step;
    }
}
