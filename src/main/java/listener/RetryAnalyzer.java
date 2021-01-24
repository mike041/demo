package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int maxRetryCount = 3;
    int currentRetryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (currentRetryCount < maxRetryCount) {
            currentRetryCount++;
            return true;

        } else {
            return false;

        }
    }
}
