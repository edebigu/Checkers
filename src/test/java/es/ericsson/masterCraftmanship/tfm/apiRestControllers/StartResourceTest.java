package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.ericsson.masterCraftmanship.tfm.businessControllers.StartController;

@RunWith(MockitoJUnitRunner.class)
public class StartResourceTest {

	private MockMvc mockMvc;

	@Mock
	private StartController startController;

	@InjectMocks
	StartResource startResource = new StartResource(startController);

	@Before
	public void setupTest() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(startResource).build();
	}

	@Test
	public void givenSystem_whenStartGame_thenResultOk() throws Exception {
		mockMvc.perform(get("/start").accept("application/json")).andExpect(status().isOk());

	}

	@Test
	public void givenSystem_whenStartGameWithWrongPath_thenResultNotFound() throws Exception {
		mockMvc.perform(get("/startgame").accept("application/json")).andExpect(status().isNotFound());

	}

}
