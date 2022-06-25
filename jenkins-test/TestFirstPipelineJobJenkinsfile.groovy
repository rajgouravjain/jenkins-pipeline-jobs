package jenkins

import com.lesfurets.jenkins.unit.BasePipelineTest
import com.lesfurets.jenkins.unit.global.lib.GitSource
import org.junit.Before
import org.junit.Test

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library


class TestFirstPipelineJobJenkinsfile extends BasePipelineTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        //helper.registerAllowedMethod("sh", [Map.class], {c -> "bcc19744"})
        binding.setVariable('scm', {})
        // Assigns false to a job parameter ENABLE_TEST_STAGE
        binding.setVariable('WORKSPACE', "./")
        binding.setVariable('env', [
                WORKSPACE : System.getProperty("user.home") + "/codehub/jenkins-pipeline-jobs"
        ])
        helper.registerAllowedMethod("timeout", [Map.class, Closure.class], null)
        helper.registerAllowedMethod("timestamps", [Closure.class], { it -> it() })
        helper.registerAllowedMethod('checkout', [Closure.class], null)
        helper.registerAllowedMethod('tool', [Map.class], { t -> "${t.name}_HOME" })
        helper.registerAllowedMethod('tool', [String.class], { t -> "${t}_HOME" })
        //helper.registerAllowedMethod("library", [String.class], {String expression ->
        //    helper.getLibLoader().loadLibrary(expression)
        //    println helper.getLibLoader().libRecords
        //    return new LibClassLoader(helper,null)
        //})
        //helper.registerAllowedMethod(method("readFile", String.class), { file ->
        //    return Files.contentOf(new File(file), Charset.forName("UTF-8"))
        //})
        //helper.registerAllowedMethod("customMethodWithArguments", [String, int, Collection], { String stringArg, int intArg, Collection collectionArg ->
        //    return println "executing mock closure with arguments (arguments: '${stringArg}', '${intArg}', '${collectionArg}')"
        //})
    }

    @Test
    void should_execute_without_errors() throws Exception {
        String clonePath = 'sharedlib/'

        def library = library()
                .name('jenkins-pipeline-lib')
                .retriever(GitSource.gitSource('https://github.com/rajgouravjain/jenkins-pipeline-lib.git'))
                .targetPath(clonePath)
                .defaultVersion("master")
                .allowOverride(true)
                .implicit(false)
                .build()
        helper.registerSharedLibrary(library)

        //def script = loadScript("pipeline_jobs/SecondPipelineJob/Jenkinsfile")
        //script.execute()
        runScript("jenkins/FirstPipelineJob/Jenkinsfile")
        printCallStack()
    }
}
