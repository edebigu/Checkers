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

import es.ericsson.masterCraftmanship.tfm.businessControllers.SaveGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.SaveGameDto;
import es.ericsson.masterCraftmanship.tfm.utils.Utils;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(MockitoJUnitRunner.class)
public class SaveGameResourceTest {

	private MockMvc mockMvc;

	@Mock
	private SaveGameController saveGameController;

	@InjectMocks
	SaveGameResource saveGameResource = new SaveGameResource(saveGameController);

	@Before
	public void setupTest() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(saveGameResource).build();
	}

	@Test
	public void givenSession_whenSaveGame_thenResultOk() throws Exception {
		SaveGameDto saveSessionDto = new SaveGameDto("Test", "testGame", false);

		mockMvc.perform(post("/saveGame").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(saveSessionDto))).andExpect(status().isOk());

	}

	@Test
	public void givenSessionWithoutGameName_whenLogout_thenResultBadRequest() throws Exception {
		SaveGameDto saveSessionDto = new SaveGameDto("Test", null, false);
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/saveGame").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(saveSessionDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));

	}

	@Test
	public void givenSessionWithoutUserName_whenLogout_thenResultBadRequest() throws Exception {
		SaveGameDto saveSessionDto = new SaveGameDto(null, "testGame", false);
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/saveGame").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(saveSessionDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));

	}

	@Test
	public void givenSession_whenLogoutWithWrongPath_thenResultNotFound() throws Exception {
		SaveGameDto saveSessionDto = new SaveGameDto(null, "testGame", false);

		mockMvc.perform(post("/saveGame/player").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(saveSessionDto))).andExpect(status().isNotFound());

	}

}
