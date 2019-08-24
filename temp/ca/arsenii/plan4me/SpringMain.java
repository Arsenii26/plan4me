package ca.arsenii.plan4me;

import ca.arsenii.plan4me.repository.UserRepository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "test.xml"); //creates application context spring, which is described in xml, which is located in the classpath
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        //        UserRepository userRepository = (UserRepository) appCtx.getBean("inmemoryUserRepository"); //can take any bins from context
        UserRepository userRepository = appCtx.getBean(UserRepository.class); //can take any bins from context(take access from java EE toward spring)
        userRepository.getAll();
        appCtx.close(); //close all resources which been used during the work + all maps in the context spring will be deleted

    }
}
