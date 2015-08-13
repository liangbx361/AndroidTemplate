package com.common.tools.task;

import java.util.List;

public interface TaskDoneListener {
	
	public void taskDone(Integer resultCode, List<Object> list);
}
