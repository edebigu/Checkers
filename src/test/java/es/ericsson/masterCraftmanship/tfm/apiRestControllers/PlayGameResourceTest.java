package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import es.ericsson.masterCraftmanship.tfm.businessControllers.PlayGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.MoveDto;
import es.ericsson.masterCraftmanship.tfm.utils.Utils;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(MockitoJUnitRunner.class)
public class PlayGameResourceTest {
	private MockMvc mockMvc;

	@Mock
	private PlayGameController playGameController;

	@InjectMocks
	PlayGameResource playGameResource = new PlayGameResource(playGameController);

	@Before
	public void setupTest() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(playGameResource).build();
	}

	@Test
	public void givenUsername_whenGetStatus_thenResultOk() throws Exception {
		mockMvc.perform(get("/game/UserTest/getStatus").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk());

	}

	@Test
	public void givenUsername_whenGetStatusWithoutUsernameInPath_thenResultOk() throws Exception {
		mockMvc.perform(get("/game/getStatus").contentType("application/json").accept("application/json"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void givenPlayer_whenGetGames_thenResultOk() throws Exception {
		mockMvc.perform(get("/game/getGames/UserTest").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk());

	}

	@Test
	public void givenPlayer_whenGetGamesWithoutUsernameInPath_thenResultOk() throws Exception {
		mockMvc.perform(get("/game/getGames").contentType("application/json").accept("application/json"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void givenPlayer_whenMove_thenResultOk() throws Exception {
		MoveDto moveDto = new MoveDto("4", "3", "5", "2");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.OK);
		responseJson.setUsername("Test");

		mockMvc.perform(post("/game/move/UserTest").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(moveDto))).andExpect(status().isOk());

	}

	@Test
	public void givenPlayer_whenMoveWithCoordinateEmpty_thenResultBadRequest() throws Exception {
		MoveDto moveDto = new MoveDto("", "3", "5", "2");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/game/move/UserTest").contentType("application/json")
				.accept("application/json").content(Utils.asJsonString(moveDto))).andExpect(status().isBadRequest())
				.andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));

	}

	@Test
	public void givenPlayer_whenMoveWithouPlayerInPath_thenResultNotFound() throws Exception {
		MoveDto moveDto = new MoveDto("4", "3", "5", "2");

		mockMvc.perform(post("/game/move/").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(moveDto))).andExpect(status().isNotFound());

	}

}
