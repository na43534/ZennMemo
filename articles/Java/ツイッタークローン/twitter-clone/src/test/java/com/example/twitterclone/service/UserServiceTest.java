public class UserServiceTest {
    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setup() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/twitter_clone_test?user=root&password=password");
        userDao = new UserDao(connection);
        userDao.drop();
        userDao.create();
        userService = new UserService(userDao);
    }

    @Test
    void signUp() throws SQLException {
        userService.signUp("testuser", "password");
        User user = userDao.findByUsername("testuser");
        assertEquals(1, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void signIn() throws SQLException {
        userDao.insert(1, "testuser", "password");
        User user = userService.signIn("testuser", "password");
        assertEquals(1, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }
}
