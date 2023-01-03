public class TweetDao {
    private Connection connection;

    public TweetDao(Connection connection) {
        this.connection = connection;
    }

    public void insertTweet(long userId, String content) throws SQLException {
        String sql = "INSERT INTO tweets (user_id, content) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
        }
    }

    public Tweet findById(long id) throws SQLException {
        String sql = "SELECT * FROM tweets WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    long userId = rs.getLong("user_id");
                    String content = rs.getString("content");
                    LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                    return new Tweet(id, userId, content, createdAt);
                } else {
                    return null;
                }
            }
        }
    }
}
