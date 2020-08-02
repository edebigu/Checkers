package es.ericsson.masterCraftmanship.tfm.checkers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import es.ericsson.masterCraftmanship.tfm.apiRestControllers.AllResourceTest;
import es.ericsson.masterCraftmanship.tfm.businessControllers.AllBusinessControllersTest;
import es.ericsson.masterCraftmanship.tfm.models.AllModelTest;
import es.ericsson.masterCraftmanship.tfm.services.AllServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    AllModelTest.class,
    AllResourceTest.class,
    AllBusinessControllersTest.class,
    AllServiceTest.class } )
public final class AllTest {
}
