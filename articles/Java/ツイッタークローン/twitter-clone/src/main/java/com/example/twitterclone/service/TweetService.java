public class TweetService {
    private TweetDao tweetDao;

    public TweetService(TweetDao tweetDao) {
        this.tweetDao = tweetDao;
    }

    public void postTweet(long userId, String content) throws SQLException {
        tweetDao.insertTweet(userId, content);
    }

    public void likeTweet(long userId, long tweetId) throws SQLException {
        tweetDao.incrementLikeCount(tweetId);
        tweetDao.insertLike(userId, tweetId);
    }
}
