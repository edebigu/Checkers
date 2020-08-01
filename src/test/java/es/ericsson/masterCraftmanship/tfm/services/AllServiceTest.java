package es.ericsson.masterCraftmanship.tfm.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    GameDaoServiceTest.class,
    PlayerDaoServiceTest.class, 
    SessionDaoServiceTest.class } )
public final class AllServiceTest {
}
