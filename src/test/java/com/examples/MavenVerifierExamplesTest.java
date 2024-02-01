package com.examples;

import java.io.File;

import org.apache.maven.shared.verifier.VerificationException;
import org.apache.maven.shared.verifier.Verifier;
import org.junit.Test;

public class MavenVerifierExamplesTest {

	@Test
	public void testRunOnMavenQuickStartExample() throws VerificationException {
		String baseDir = new File("target/test-classes/maven-quickstart-example").getAbsolutePath();
		Verifier verifier = new Verifier(baseDir);
		verifier.addCliArgument("package");
		verifier.execute();
		verifier.verify(true); // throws an exception in case of errors in the build log
		verifier.verifyFilePresent(verifier.getBasedir() + "/target/maven-quickstart-example-1.0-SNAPSHOT.jar");
		verifier.verifyFileContentMatches(
				verifier.getBasedir() + "/target/surefire-reports/com.examples.AppTest.txt",
				"(?s).*Tests run: 1, Failures: 0, Errors: 0, Skipped: 0(?s).*");
	}
}
