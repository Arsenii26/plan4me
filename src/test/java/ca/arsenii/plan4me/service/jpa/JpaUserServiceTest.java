package ca.arsenii.plan4me.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ca.arsenii.plan4me.service.AbstractJpaUserServiceTest;

import static ca.arsenii.plan4me.Profiles.JPA;

@ActiveProfiles(JPA)
class JpaUserServiceTest extends AbstractJpaUserServiceTest {
}