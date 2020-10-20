import org.junit.platform.engine.DiscoveryFilter;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Set;

public class Distributor {

    public static void main(String[] args) {
        TestPlan testPlan = LauncherFactory.create().discover(new LauncherDiscoveryRequestBuilder()
                .selectors(DiscoverySelectors.selectClasspathRoots(Set.of(Paths.get(URI.create("file:///D:/SOURCE/OPEN_SOURCE/vibhaajan/junit5/build/classes/java/test/")))))
//                .selectors(DiscoverySelectors.selectDirectory(new File(URI.create("file:///D:/SOURCE/OPEN_SOURCE/vibhaajan/junit5/build/classes/java/test/"))))
                .filters(ClassNameFilter.includeClassNamePatterns(".*"))
                .build());
        System.out.println(testPlan.getRoots());
    }
}
