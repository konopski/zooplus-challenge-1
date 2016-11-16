package com.alexcibotari.zooplus.utils.validation.constraint.definition;

import com.alexcibotari.zooplus.utils.validation.constraint.ConstraintDefinition;

import javax.validation.constraints.Past;

public class PastDefinition extends ConstraintDefinition<PastDefinition, Past> {

    public PastDefinition(Past constraint) {
        this();
        message(constraint.message());
    }

    public PastDefinition() {
        super(Past.class);
    }

}
