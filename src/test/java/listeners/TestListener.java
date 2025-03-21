package listeners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Step;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished: " + context.getName());
    }

    @Step("Log step: {0}")
    public static void logStep(String step) {
        System.out.println("STEP: " + step);
    }
}