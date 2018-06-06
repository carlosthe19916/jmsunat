import org.junit.Assert;

public class LogAssert {

    private final Log log;

    LogAssert(Log log) {
        this.log = log;
    }

    public LogAssert hasLineContaining(String match) {
        Assert.assertTrue(log.getLogName() + " should contain '" + match + "'", this.log.getLines().stream().anyMatch(e -> e.contains(match)));
        return this;
    }

}