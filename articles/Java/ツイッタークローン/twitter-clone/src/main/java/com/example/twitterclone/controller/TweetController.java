public class TweetController {
    private TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    public void postTweet(HttpServletRequest request, HttpServletResponse response) {
        // ログインしているユーザーのIDを取得
        long userId = (long) request.getSession().getAttribute("userId");
        // ツイート内容を取得
        String content = request.getParameter("content");
        // ツイートを投稿
        tweetService.postTweet(userId, content);
        // ホーム画面にリダイレクト
        response.sendRedirect("/");
    }

    public void likeTweet(HttpServletRequest request, HttpServletResponse response) {
        // ログインしているユーザーのIDを取得
        long userId = (long) request.getSession().getAttribute("userId");
        // いいねを付けるツイートのIDを取得
        long tweetId = Long.parseLong(request.getParameter("tweetId"));
        // ツイートにいいねを付ける
        tweetService.likeTweet(userId, tweetId);
        // リクエストから送られてきたURLを取得
        String referer = request.getHeader("Referer");
        // 前の画面にリダイレクト
        response.sendRedirect(referer);
    }
}
