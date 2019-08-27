package ca.arsenii.plan4me.web.user;


import ca.arsenii.plan4me.util.exception.ErrorType;
import ca.arsenii.plan4me.web.MessageUtil;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ca.arsenii.plan4me.AllActiveProfileResolver;
import ca.arsenii.plan4me.repository.JpaUtil;
import ca.arsenii.plan4me.service.UserService;

import javax.annotation.PostConstruct;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
//@WebAppConfiguration
//@ExtendWith(SpringExtension.class)
@Transactional
@ActiveProfiles(resolver = AllActiveProfileResolver.class)
public abstract class AbstractControllerTest {

    public static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;

    @Autowired
    private CacheManager cacheManager;

    @Autowired(required = false)
    private JpaUtil jpaUtil;

    @Autowired
    protected UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected MessageUtil messageUtil;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }


    @BeforeEach
    void setUp(){
        cacheManager.getCache("users").clear();
        if(jpaUtil != null){
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }
    private String getMessage(String code) {
        return messageUtil.getMessage(code, MessageUtil.FR_LOCALE);
    }

    public ResultMatcher errorType(ErrorType type) {
        return jsonPath("$.type").value(type.name());
    }

    public ResultMatcher detailMessage(String code) {
        return jsonPath("$.details").value(getMessage(code));
    }
}

