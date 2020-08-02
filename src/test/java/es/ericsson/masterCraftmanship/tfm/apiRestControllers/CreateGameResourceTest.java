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

import es.ericsson.masterCraftmanship.tfm.businessControllers.CreateGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.utils.Utils;
import es.ericsson.masterCraftmanship.tfm.views.CreateGameJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(MockitoJUnitRunner.class)
public class CreateGameResourceTest {

	private MockMvc mockMvc;

	@Mock
	private CreateGameController createGameController;

	@InjectMocks
	CreateGameResource createGameResource = new CreateGameResource(createGameController);

	@Before
	public void setupTest() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(createGameResource).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenPlayerInSession_whenCreateGame_thenResultOk() throws Exception {
		SessionDto sessionDto = new SessionDto("Test", "testGame");

		mockMvc.perform(post("/createGame").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(sessionDto))).andExpect(status().isOk());
	}

	@Test
	public void givenSessionWithGamenameNull_whenCreateGame_thenResultBadRequest() throws Exception {
		SessionDto sessionDto = new SessionDto("Test", null);
		CreateGameJson responseJson = new CreateGameJson();
		responseJson.setResult(Result.BAD_REQUEST);
		responseJson.setUsername("Test");

		MvcResult result = mockMvc.perform(post("/createGame").contentType("application/json")
				.accept("application/json").content(Utils.asJsonString(sessionDto))).andExpect(status().isBadRequest())
				.andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));

	}

	@Test
	public void givenSessionWithoutUserName_whenCreateGAme_thenResultBadRequest() throws Exception {
		SessionDto sessionDto = new SessionDto("", "testGame");
		CreateGameJson responseJson = new CreateGameJson();
		responseJson.setResult(Result.BAD_REQUEST);
		responseJson.setUsername("");

		MvcResult result = mockMvc.perform(post("/createGame").contentType("application/json")
				.accept("application/json").content(Utils.asJsonString(sessionDto))).andExpect(status().isBadRequest())
				.andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));

	}

	@Test
	public void givenSession_whenCreateGameInWrongPath_thenResultNotFound() throws Exception {
		SessionDto sessionDto = new SessionDto("Test", "testGame");

		mockMvc.perform(post("/createGame/gamer").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(sessionDto))).andExpect(status().isNotFound());
	}

}
