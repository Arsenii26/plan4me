package ca.arsenii.plan4me;

import ca.arsenii.plan4me.repository.UserRepository;
import ca.arsenii.plan4me.to.PlanTo;
import ca.arsenii.plan4me.web.plan.PlanRestController;
import ca.arsenii.plan4me.web.user.AdminRestController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/inmemory.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.getAll();
            System.out.println();

            PlanRestController mealController = appCtx.getBean(PlanRestController.class);
            List<PlanTo> filteredPlansWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2019, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2019, Month.MAY, 31), LocalTime.of(11, 0));
            filteredPlansWithExcess.forEach(System.out::println);
        }
    }
}
