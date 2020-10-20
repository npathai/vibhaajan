import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RemoteClientTestFactory {

    @TestFactory
    public Stream<DynamicTest> dynamicTests() {
        return IntStream.of(1, 2, 3).peek((it) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).mapToObj(it -> DynamicTest.dynamicTest("Test " + it, () -> {
                    SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
                    LauncherFactory.create().execute(new LauncherDiscoveryRequestBuilder().selectors(
                            DiscoverySelectors.selectClass("TempTest")).build(), summaryGeneratingListener);
            List<TestExecutionSummary.Failure> failures = summaryGeneratingListener.getSummary().getFailures();
            if (!failures.isEmpty()) {
                throw new AssertionError(failures.stream().map(failure -> failure.getException()).collect(Collectors.toList()));
            }
        }
        ));
    }
}
