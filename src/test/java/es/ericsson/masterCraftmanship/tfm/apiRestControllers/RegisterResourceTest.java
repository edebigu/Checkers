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

import es.ericsson.masterCraftmanship.tfm.businessControllers.RegisterController;
import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.utils.Utils;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(MockitoJUnitRunner.class)
public class RegisterResourceTest {

	private MockMvc mockMvc;

	@Mock
	private RegisterController registerController;

	@InjectMocks
	RegisterResource registerResource = new RegisterResource(registerController);

	@Before
	public void setupTest() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(registerResource).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenPlayer_whenRegister_thenResultOk() throws Exception {
		PlayerDto playerDto = new PlayerDto("Test", "test");

		mockMvc.perform(post("/register").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(playerDto))).andExpect(status().isOk());
	}

	@Test
	public void givenPlayerWithoutPassword_whenRegister_thenResultBadRequest() throws Exception {
		PlayerDto playerDto = new PlayerDto("Test", "");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/register").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(playerDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));
	}

	@Test
	public void givenPlayerWithoutUsername_whenRegister_thenResultBadRequest() throws Exception {
		PlayerDto playerDto = new PlayerDto("", "test");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setResult(Result.BAD_REQUEST);

		MvcResult result = mockMvc.perform(post("/register").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(playerDto))).andExpect(status().isBadRequest()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), Utils.asJsonString(responseJson));
	}

	@Test
	public void givenPlayer_whenRegisterInWrongPath_thenResultNotFound() throws Exception {
		PlayerDto playerDto = new PlayerDto("TestLogin", "testLogin");

		mockMvc.perform(post("/register/user").contentType("application/json").accept("application/json")
				.content(Utils.asJsonString(playerDto))).andExpect(status().isNotFound());
	}

}
