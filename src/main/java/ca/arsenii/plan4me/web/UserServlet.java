package ca.arsenii.plan4me.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class UserServlet extends HttpServlet {

//    public static final Logger log = Logger.getLogger(UserServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("forward to users");
        req.getRequestDispatcher("/users.jsp").forward(req, resp);
    }
}
