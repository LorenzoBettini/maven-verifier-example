package com.examples;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.shared.verifier.VerificationException;
import org.apache.maven.shared.verifier.Verifier;
import org.junit.Test;

public class MavenVerifierExamplesTest {

	@Test
	public void testPackageOnMavenQuickStartExample() throws VerificationException {
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

	@Test
	public void testInstallOnMavenQuickStartExample() throws VerificationException, IOException {
		File localRepo = new File("target/test-classes/local-repo");
		FileUtils.deleteDirectory(localRepo);
		String baseDir = new File("target/test-classes/maven-quickstart-example").getAbsolutePath();
		Verifier verifier = new Verifier(baseDir);
		verifier.setLocalRepo(localRepo.getAbsolutePath());
		verifier.addCliArgument("install");
		verifier.execute();
		verifier.verify(true);
		verifier.verifyArtifactPresent("com.examples", "maven-quickstart-example", "1.0-SNAPSHOT", "jar");
	}

	@Test
	public void testInstallOnMavenExampleWithMavenWrapper() throws VerificationException, IOException {
		File localRepo = new File("target/test-classes/local-repo");
		FileUtils.deleteDirectory(localRepo);
		String baseDir = new File("target/test-classes/maven-example-with-maven-wrapper").getAbsolutePath();
		Verifier verifier = new Verifier(baseDir);
		verifier.setLocalRepo(localRepo.getAbsolutePath());
		verifier.setEnvironmentVariable("MVNW_VERBOSE", "true");
		verifier.addCliArgument("install");
		verifier.execute();
		verifier.verify(true);
		verifier.verifyArtifactPresent("com.examples", "maven-example-with-maven-wrapper", "1.0-SNAPSHOT", "jar");
		verifier.verifyTextInLog("Apache Maven Wrapper 3.2.0");
	}

	@Test
	public void testWithEmbeddedLauncher() throws VerificationException, IOException {
		File localRepo = new File("target/test-classes/local-repo");
		FileUtils.deleteDirectory(localRepo);
		String baseDir = new File("target/test-classes/maven-quickstart-example").getAbsolutePath();
		Verifier verifier = new Verifier(baseDir);
		verifier.setLocalRepo(localRepo.getAbsolutePath());
		verifier.setForkJvm(false); // use the Embedded3xLauncher Maven launcher
		verifier.addCliArgument("install");
		verifier.execute();
		verifier.verify(true);
		verifier.verifyArtifactPresent("com.examples", "maven-quickstart-example", "1.0-SNAPSHOT", "jar");
	}
}
