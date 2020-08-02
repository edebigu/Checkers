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

import es.ericsson.masterCraftmanship.tfm.businessControllers.CloseGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.CloseGameDto;
import es.ericsson.masterCraftmanship.tfm.utils.Utils;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(MockitoJUnitRunner.class)
public class CloseGameResourceTest {

	private MockMvc mockMvc;

	@Mock
	private CloseGameController closeGameController;

	@InjectMocks
	CloseGameResource closeGameResource = new CloseGameResource(closeGameController);

	@Before
	public void setupTest() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(closeGameResource).build();
	}

	@Test
	public void givenSessionWithGame_whenCloseGame_thenResultOk() throws Exception {
		CloseGameDto closeGameDto = new CloseGameDto("Test", "testGame", false);

		mockMvc.perform(post("/closeGame").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(closeGameDto))).andExpect(status().isOk());
	}

	@Test
	public void givenSessionWithoutGame_whenCloseGame_thenResultBadRequest() throws Exception {
		CloseGameDto closeGameDto = new CloseGameDto("Test", null, false);
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);
		responseJson.setUsername("Test");

		MvcResult result = mockMvc.perform(post("/closeGame").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(closeGameDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));
	}

	@Test
	public void givenSessionWithoutUsername_whenCloseGame_thenResultBadRequest() throws Exception {
		CloseGameDto closeGameDto = new CloseGameDto(null, "testGame", false);
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/closeGame").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(closeGameDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));
	}

	@Test
	public void givenSessionWithGame_whenCloseGameInWrongPath_thenResultNotFound() throws Exception {
		CloseGameDto closeGameDto = new CloseGameDto("Test", "testGame", false);

		mockMvc.perform(post("/close").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(closeGameDto))).andExpect(status().isNotFound());
	}

}
