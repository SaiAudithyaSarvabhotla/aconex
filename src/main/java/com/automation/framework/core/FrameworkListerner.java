package com.automation.framework.core;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.framework.utils.Reporter;

public class FrameworkListerner implements ITestListener {

	@Override
	public void onStart(ITestContext context) {
		Reporter.startScript(context.getName());
		//Logger.info("###################### START ######################");
		System.out.println("Test Case Name " + context.getName() + " has started");
	}

	@Override
	public void onFinish(ITestContext context) {
		Reporter.endScript();
		//Logger.info("###################### END ######################");
		System.out.println("Test Case Name " + context.getName() + " was finished");
	}

	@Override
	public void onTestStart(ITestResult result) {
		Reporter.startTest(result.getName());
		System.out.println(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Reporter.endTest(result.getName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.endTest(result.getName());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.endTest(result.getName());

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

}
