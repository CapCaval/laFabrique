package org.capcaval.lafabrique.task;

import org.capcaval.lafabrique.common.CommandResult;

public interface Task <P>{
	CommandResult run(P value);
}
