package ca.arsenii.plan4me.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ca.arsenii.plan4me.service.AbstractPlanServiceTest;

import static ca.arsenii.plan4me.Profiles.JDBC;

@ActiveProfiles(JDBC)
class JdbcPlanServiceTest extends AbstractPlanServiceTest {
}