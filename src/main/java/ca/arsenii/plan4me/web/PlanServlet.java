package ca.arsenii.plan4me.web;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ca.arsenii.plan4me.Profiles;
import org.springframework.util.StringUtils;
import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.web.plan.PlanRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ca.arsenii.plan4me.util.DateTimeUtil.parseLocalDate;
import static ca.arsenii.plan4me.util.DateTimeUtil.parseLocalTime;

public class PlanServlet extends HttpServlet {

//    private ConfigurableApplicationContext springContext;
    private ClassPathXmlApplicationContext springContext;
    private PlanRestController planController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
//       springContext.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
        springContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        springContext.refresh();
        planController = springContext.getBean(PlanRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
//        String id = request.getParameter("id");

//        Plan plan = new Plan(id.isEmpty() ? null : Integer.valueOf(id),
        Plan plan = new Plan(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("plan")
        );

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            planController.create(plan);
        } else {
            planController.update(plan, getId(request));
        }
        response.sendRedirect("plans");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                planController.delete(id);
                response.sendRedirect("plans");
                break;
            case "create":
            case "update":
                final Plan plan = "create".equals(action) ?
                        new Plan(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "") :
                        planController.get(getId(request));
                request.setAttribute("plan", plan);
                request.getRequestDispatcher("/planForm.jsp").forward(request, response);
                break;
            case "filter":
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
                request.setAttribute("plans", planController.getBetween(startDate, startTime, endDate, endTime));
                request.getRequestDispatcher("/plans.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("plans", planController.getAll());
                request.getRequestDispatcher("/plans.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
