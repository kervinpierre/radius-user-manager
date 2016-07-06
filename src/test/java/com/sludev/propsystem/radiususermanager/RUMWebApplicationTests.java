package com.sludev.propsystem.radiususermanager;

import com.sludev.propsystem.radiususermanager.main.RadiusUserManagerApplication;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiusUserManagerApplication.class)
@WebAppConfiguration
public class RUMWebApplicationTests
{

	@Test
	public void A001_contextLoads()
	{
	}

}
