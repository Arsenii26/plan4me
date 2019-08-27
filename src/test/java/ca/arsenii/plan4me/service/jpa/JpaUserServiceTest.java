package ca.arsenii.plan4me.service.jpa;

import ca.arsenii.plan4me.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;


import static ca.arsenii.plan4me.Profiles.JPA;

@ActiveProfiles(JPA)
class JpaUserServiceTest extends AbstractUserServiceTest {
}