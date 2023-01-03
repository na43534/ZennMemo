public class TweetServiceTest {
    private TweetService tweetService;
    private TweetDao tweetDao;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter_clone_test", "username", "password");
        tweetDao = new TweetDao(connection);
        tweetService = new TweetService(tweetDao);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void testPostTweet() throws SQLException {
        // ツイートを投稿
        tweetService.postTweet(1L, "Hello, world!");
        // ツイートが正しく投稿されていることを確認
        assertEquals("Hello, world!", tweetDao.findById(1L).getContent());
    }

    @Test
    public void testLikeTweet() throws SQLException {
        // ツイートを投稿
        tweetDao.insertTweet(1L, "Hello, world!");
        // ツイートにいいねを付ける
        tweetService.likeTweet(2L, 1L);
        // いいねが付いていることを確認
        assertEquals(1, tweetDao.findById(1L).getLikeCount());
    }
}
