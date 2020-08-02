package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.ericsson.masterCraftmanship.tfm.businessControllers.LogoutController;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.GameTest;
import es.ericsson.masterCraftmanship.tfm.utils.Utils;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(MockitoJUnitRunner.class)
public class LogoutResourceTest extends GameTest {

	private MockMvc mockMvc;

	@Mock
	private LogoutController logoutController;

	@InjectMocks
	LogoutResource logoutResource = new LogoutResource(logoutController);

	@Before
	public void setupTest() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(logoutResource).build();
	}

	@Test
	public void givenSession_whenLogout_thenResultOk() throws Exception {
		SessionDto sessionDto = new SessionDto("Test", "testGame");

		mockMvc.perform(post("/logout").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(sessionDto))).andExpect(status().isOk());

	}

	@Test
	public void givenSessionWithoutGameName_whenLogout_thenResultBadRequest() throws Exception {
		SessionDto sessionDto = new SessionDto("Test", null);
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/logout").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(sessionDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));

	}

	@Test
	public void givenSessionWithoutUserName_whenLogout_thenResultBadRequest() throws Exception {
		SessionDto sessionDto = new SessionDto("", "testGame");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/logout").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(sessionDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));

	}

	@Test
	public void givenSession_whenLogoutInWrongPath_thenResultNotFound() throws Exception {
		SessionDto sessionDto = new SessionDto("Test", "testGame");

		mockMvc.perform(post("/logout/player").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(sessionDto))).andExpect(status().isNotFound());
	}

}
