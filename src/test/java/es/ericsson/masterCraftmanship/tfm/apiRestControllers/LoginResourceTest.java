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

import es.ericsson.masterCraftmanship.tfm.businessControllers.LoginController;
import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.utils.Utils;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(MockitoJUnitRunner.class)
public class LoginResourceTest {

	private MockMvc mockMvc;

	@Mock
	private LoginController loginController;

	@InjectMocks
	LoginResource logingResource = new LoginResource(loginController);

	@Before
	public void setupTest() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(logingResource).build();
	}

	@Test
	public void givenPlayer_whenLogin_thenResultOk() throws Exception {

		PlayerDto playerDto = new PlayerDto("TestLogin", "testLogin");

		mockMvc.perform(post("/login").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(playerDto))).andExpect(status().isOk());

	}

	@Test
	public void givenPlayerWithoutUsername_whenLogin_thenResultBadRequest() throws Exception {
		PlayerDto playerDto = new PlayerDto("", "testLogin");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/login").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(playerDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));
	}

	@Test
	public void givenPlayerWithoutPassword_whenLogin_thenResultBadRequest() throws Exception {
		PlayerDto playerDto = new PlayerDto("TestLogin", "");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/login").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(playerDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));
	}

	@Test
	public void givenPlayer_whenLoginInWrongPath_thenResultNotFound() throws Exception {
		PlayerDto playerDto = new PlayerDto("TestLogin", "testLogin");

		mockMvc.perform(post("/loginUser").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(playerDto))).andExpect(status().isNotFound());
	}

}
