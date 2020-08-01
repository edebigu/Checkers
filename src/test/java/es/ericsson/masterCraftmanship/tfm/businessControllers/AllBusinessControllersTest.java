package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    CloseGameControllerTest.class, 
    CreateGameControllerTest.class, 
    LoginControllerTest.class,
    LogoutControllerTest.class,
    OpenGameControllerTest.class,
    PlayGameControllerTest.class,
    RegisterControllerTest.class,
    SaveGameControllerTest.class} )
public final class AllBusinessControllersTest {
}
