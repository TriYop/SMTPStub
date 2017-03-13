package shared;

/**
 * Defines constant parameters for integration and unit testing
 *
 * @author TriYop
 * @since 1.0
 */
public final class TestParams {
    // host name the smtp will listen at
    public static final String TEST_HOST = "localhost";

    // TCP ports the smtp will listen at for unit tests
    public static final int UT_PORT = 25;
    public static final int UT_TLS_PORT = 587;

    // TCP ports the smtp will listen at for integration tests
    public static final int IT_PORT = 25;
    public static final int IT_TLS_PORT = 587;
}
