package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.execution.Location;
import com.intellij.execution.junit2.info.MethodLocation;
import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestStatusListener;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.rt.execution.junit.states.PoolOfTestStates;
import org.json.simple.JSONObject;


/**
 * Created by mihai on 4/8/14.
 */
public class TestListener extends TestStatusListener {

    private static final Logger LOG = Logger.getInstance(TestStatusListener.class);

    //copy paste from org.eclipse.jdt.junit.model.ITestElement.Result
    public static final class Result {
        /**
         * state that describes that the test result is undefined
         */
        public static final Result UNDEFINED = new Result("Undefined"); //$NON-NLS-1$
        /**
         * state that describes that the test result is 'OK'
         */
        public static final Result OK = new Result("OK"); //$NON-NLS-1$
        /**
         * state that describes that the test result is 'Error'
         */
        public static final Result ERROR = new Result("Error"); //$NON-NLS-1$
        /**
         * state that describes that the test result is 'Failure'
         */
        public static final Result FAILURE = new Result("Failure"); //$NON-NLS-1$
        /**
         * state that describes that the test result is 'Ignored'
         */
        public static final Result IGNORED = new Result("Ignored"); //$NON-NLS-1$

        private String fName;

        private Result(String name) {
            fName = name;
        }

        public String toString() {
            return fName;
        }
    }

    @Override
    public void testSuiteFinished(final AbstractTestProxy root) {
        for (AbstractTestProxy test : root.getAllTests()) {
            if (!test.isLeaf()) {
                continue;
            }
            System.out.println("TEST RUN");

            Project project = getProject(test);

            if (project == null) {
                return;
            }
            String qualifiedTestName = constructQualifiedName(test, project);
            Result testResult = computeTestResult(test);
            Double testTime = getTestTimeInSeconds(test);

            try {
                JSONObject testJSON = buildTestEventJSON(qualifiedTestName, testResult.toString(), testTime);
                //Cannot pull all the method into another thread, as AbstractTestProxy is not thread-safe.
                CopeRestAsyncService.getInstance().submitJSON(testJSON);
            } catch (Exception e) {
                LOG.error(e);
                return;
            }
        }
    }

    private Double getTestTimeInSeconds(AbstractTestProxy test) {
        return test.getDuration() / 1000.0;
    }

    private String constructQualifiedName(AbstractTestProxy test, Project project) {
        Location location = getLocation(test, project);

        if (location instanceof MethodLocation) {
            MethodLocation methodLocation = (MethodLocation) location;
            PsiClass testClass = methodLocation.getContainingClass();

            return testClass.getQualifiedName() + "." + methodLocation.getPsiElement().getName();
        }
        return null;
    }

    private Project getProject(AbstractTestProxy test) {
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();

        for (Project openedProject : openProjects) {
            Location location = getLocation(test, openedProject);

            if (location != null) {
                return openedProject;
            }
        }

        return null;
    }

    private Location getLocation(AbstractTestProxy test, Project project) {
        return test.getLocation(project, GlobalSearchScope.allScope(project));
    }

    private Result computeTestResult(AbstractTestProxy test) {
        if (test.isPassed()) {
            return Result.OK;
        }

        int testMagnitude = test.getMagnitude();

        if (testMagnitude == PoolOfTestStates.ERROR_INDEX) {
            return Result.ERROR;
        }

        if (testMagnitude == PoolOfTestStates.FAILED_INDEX || testMagnitude == PoolOfTestStates.COMPARISON_FAILURE) {
            return Result.FAILURE;
        }
        return Result.UNDEFINED;
    }

    protected static JSONObject buildCommonJSONObj(Enum eventType) {
        JSONObject obj;
        obj = new JSONObject();
        obj.put(JSONConstants.JSON_IDE, "INTEILLIJ");
        obj.put(JSONConstants.JSON_EVENT_TYPE, eventType.toString());
        obj.put(JSONConstants.JSON_TIMESTAMP, System.currentTimeMillis() + "");

        return obj;
    }

    protected JSONObject buildTestEventJSON(String fullyQualifiedTestMethod, String testResult, double elapsedTime)
            throws Exception {
        if (fullyQualifiedTestMethod == null || testResult == null)
            throw new Exception("Arguments cannot be null");
        if (fullyQualifiedTestMethod.isEmpty() || testResult.isEmpty())
            throw new Exception("Arguments cannot be empty");

        JSONObject obj = buildCommonJSONObj(Events.testRun);
        obj.put(JSONConstants.JSON_ENTITY_ADDRESS, fullyQualifiedTestMethod);
        obj.put(JSONConstants.JSON_TEST_RESULT, testResult);
        obj.put(JSONConstants.JSON_TEST_ELAPSED_TIME, elapsedTime);

        return obj;
    }
}
