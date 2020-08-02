package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	StartResourceTest.class,
    CloseGameResourceTest.class, 
    CreateGameResourceTest.class, 
    LoginResourceTest.class,
    LogoutResourceTest.class,
    OpenGameResourceTest.class,
    PlayGameResourceTest.class,
    RegisterResourceTest.class,
    SaveGameResourceTest.class} )
public final class AllResourceTest {
}
