public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void handleSignUp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            userService.signUp(username, password);
            response.sendRedirect("/");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "アカウントの登録に失敗しました");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }
    }

   public void handleSignIn(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            User user = userService.signIn(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                response.sendRedirect("/");
            } else {
                request.setAttribute("errorMessage", "ユーザー名かパスワードが間違っています");
                request.getRequestDispatcher("/signin.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "サインインに失敗しました");
            request.getRequestDispatcher("/signin.jsp").forward(request, response);
        }
    }
}
