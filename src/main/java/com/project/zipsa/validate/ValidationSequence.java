package com.project.zipsa.validate;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({
        Default.class,
        ValidationGroup.RegExValidateGroup.class
})
public interface ValidationSequence {
}
