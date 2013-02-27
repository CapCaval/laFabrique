package org.capcaval.c3.component.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.capcaval.c3.component.ComponentEvent;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventValue {
	Class<? extends ComponentEvent> eventType();
}
