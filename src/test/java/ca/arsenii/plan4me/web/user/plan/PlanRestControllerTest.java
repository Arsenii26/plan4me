package ca.arsenii.plan4me.web.user.plan;


import ca.arsenii.plan4me.web.plan.PlanRestController;
import ca.arsenii.plan4me.web.user.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.service.PlanService;
import ca.arsenii.plan4me.web.json.JsonUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static ca.arsenii.plan4me.PlanTestData.PLANS;
import static ca.arsenii.plan4me.TestUtil.*;
import static ca.arsenii.plan4me.UserTestData.*;
import static ca.arsenii.plan4me.util.PlansUtil.*;
import static ca.arsenii.plan4me.util.exception.ErrorType.VALIDATION_ERROR;
import static ca.arsenii.plan4me.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_DATETIME;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ca.arsenii.plan4me.PlanTestData.*;
import static ca.arsenii.plan4me.model.AbstractBaseEntity.START_SEQ;

class PlanRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = PlanRestController.REST_URL + '/';

    @Autowired
    private PlanService service;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_PLAN_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Plan.class), ADMIN_PLAN1));
    }

    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + PLAN1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_PLAN_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + PLAN1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(START_SEQ), PLAN6, PLAN5, PLAN4, PLAN3, PLAN2);
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + ADMIN_PLAN_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Plan updated = getUpdated();

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + PLAN1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());

        assertMatch(service.get(PLAN1_ID, START_SEQ), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Plan created = getCreated();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)));

        Plan returned = readFromJson(action, Plan.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_PLAN2, created, ADMIN_PLAN1);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getPlans(PLANS)));
    }

    @Test
    void filter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2019-05-30").param("startTime", "07:00")
                .param("endDate", "2019-05-31").param("endTime", "11:00")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(createWithExcess(PLAN4), createWithExcess(PLAN1)));
    }

    @Test
    void filterAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endTime=")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(contentJson(getPlans(PLANS)));
    }

    @Test
    void createInvalid() throws Exception {
        Plan invalid = new Plan(null, null, "Dummy");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        Plan invalid = new Plan(PLAN1_ID, null, null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + PLAN1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Plan invalid = new Plan(PLAN1_ID, PLAN2.getDateTime(), "Dummy");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + PLAN1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DATETIME));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Plan invalid = new Plan(null, ADMIN_PLAN1.getDateTime(), "Dummy");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DATETIME));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Plan invalid = new Plan(PLAN1_ID, LocalDateTime.now(), "<script>alert(123)</script>");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + PLAN1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }
}