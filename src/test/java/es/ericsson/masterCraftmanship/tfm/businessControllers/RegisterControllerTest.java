package es.ericsson.masterCraftmanship.tfm.businessControllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.services.PlayerDaoService;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegisterController.class)
public class RegisterControllerTest {

	@Autowired
	private RegisterController registerController;

	@MockBean
	private PlayerDaoService playerDaoService;

	@Before
	public void setup() {
		Mockito.when(playerDaoService.savePlayer("Test", "test")).thenReturn(new Player("Test", "test"));
		Mockito.when(playerDaoService.savePlayer("TestExist", "testExist")).thenReturn(null);

	}

	@Test
	public void givenNonExistPlayer_whenRegister_thenResultOk() {
		PlayerDto playerDto = new PlayerDto("Test", "test");
		ResponseJson resultRegister = new ResponseJson();
		resultRegister.setResult(Result.OK);
		resultRegister.setUsername(playerDto.getUsername());
		assertEquals(this.registerController.register(playerDto).toString(), resultRegister.toString());
	}

	@Test
	public void givenExistPlayer_whenRegister_thenResultConflict() {
		PlayerDto playerDto = new PlayerDto("TestExist", "testExist");
		ResponseJson resultRegister = new ResponseJson();
		resultRegister.setResult(Result.CONFLICT);
		resultRegister.setUsername(playerDto.getUsername());
		assertEquals(this.registerController.register(playerDto).toString(), resultRegister.toString());
	}

}
