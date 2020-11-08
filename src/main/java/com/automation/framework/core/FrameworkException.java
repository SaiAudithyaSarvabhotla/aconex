package com.automation.framework.core;

import com.automation.framework.utils.Reporter;
import com.relevantcodes.extentreports.LogStatus;

public class FrameworkException extends Exception {

	private static final long serialVersionUID = 1L;

	public FrameworkException(Exception exception) {
		System.out.println(exception.toString());
		Reporter.reportEventWithScreenshot(LogStatus.FAIL, getClassMethodName(), exception.toString());
	}

	private synchronized static String getClassMethodName() {
		String className, classSimpleName, methodName, classMethodName;
		className = Thread.currentThread().getStackTrace()[3].getClassName();
		classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length - 1];
		methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		classMethodName = "[" + classSimpleName + ":" + methodName + "]";
		return classMethodName;
	}
}
