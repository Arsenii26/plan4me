package ca.arsenii.plan4me.web;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.repository.InMemoryPlanRepository;
import ca.arsenii.plan4me.repository.PlanRepository;
import ca.arsenii.plan4me.util.PlansUtil;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class PlanServlet extends HttpServlet {
//    private static final Logger log = LoggerFactory.getLogger(PlanServlet.class);

    private PlanRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryPlanRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Plan plan = new Plan(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("localDateTime")),
                request.getParameter("plan")
        );

//        log.info(plan.isNew() ? "Create {}" : "Update {}", plan);
        repository.save(plan);
        response.sendRedirect("plans");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
//                log.info("Delete {}", id);
                repository.delete(id);
                response.sendRedirect("plans");
                break;
            case "create":
            case "update":
                final Plan plan = "create".equals(action) ?
                        new Plan(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "") :
                        repository.get(getId(request));
                request.setAttribute("plan", plan);
                request.getRequestDispatcher("/planForm.jsp").forward(request, response);
                break;
            case "all":
            default:
//                log.info("getAll");
                request.setAttribute("plans",
                        PlansUtil.getPlans(repository.getAll()));
//                        repository.getAll());
//                request.getRequestDispatcher("/plans.jsp").forward(request, response);
                request.getRequestDispatcher("/plans.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
