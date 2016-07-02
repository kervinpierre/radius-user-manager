package com.sludev;

import com.sludev.propsystem.radiususermanager.main.RadiusUserManagerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiusUserManagerApplication.class)
@WebAppConfiguration
public class RadiusUserManagerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
