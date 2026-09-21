import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

// TE-27603 test-matrix helper: no browser. Behaviour is chosen by system properties so one
// class serves the retry (C7/C8) and timeout (C12) cases.
public class Test5 {
    @Test
    public void test5_behaviour() throws IOException, InterruptedException {
        String mode = System.getProperty("tmMode", "pass");
        switch (mode) {
            case "flaky": {
                // fails the first time on a VM, passes once the marker exists (a retry on the same VM)
                File marker = new File(System.getProperty("java.io.tmpdir"), "te27603-retry-marker");
                if (marker.exists()) {
                    System.out.println("TE27603 marker present: passing on retry");
                    return;
                }
                Assert.assertTrue(marker.createNewFile(), "marker created");
                Assert.fail("TE-27603 deliberate first-attempt failure (flaky)");
            }
            case "sleep": {
                int secs = Integer.parseInt(System.getProperty("tmSleep", "180"));
                System.out.println("TE27603 sleeping " + secs + "s");
                Thread.sleep(secs * 1000L);
                return;
            }
            case "fail":
                Assert.fail("TE-27603 deliberate failure");
            default:
                return;
        }
    }
}
