package vn.store.service;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tito on 3/2/14.
 */
@RunWith(Arquillian.class)
@RunAsClient
public class TestRestService {
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
    	System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "h2");
        return ShrinkWrap.create(WebArchive.class, "arquillianSpring.war")
                .addPackages(true, "vn.store")
//                .addAsWebInfResource("web-h2.xml", "web.xml")
                .setWebXML( new File( "src/main/webapp/WEB-INF/web.xml" ))
                ;
    }
    
    @ArquillianResource
	URL deploymentURL;
    
    @Test
    public void testRestService(){
        RestTemplate template = new RestTemplate();
        String res = template.getForObject(
        		deploymentURL + "api/users", String.class);
        assertNotNull(res);
    }
}
