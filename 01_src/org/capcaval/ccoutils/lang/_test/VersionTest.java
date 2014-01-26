package org.capcaval.ccoutils.lang._test;

import static org.junit.Assert.*;

import org.capcaval.ccoutils.lang.Version;
import org.junit.Assert;
import org.junit.Test;

public class VersionTest {

	@Test
	public void testGetInArrayTest() {
		Version version = Version.factory.newVersion("1.2.3");
		Assert.assertArrayEquals(new int[]{1,2,3}, version.getVersionIntArray());
	}
	
	@Test
	public void testGetStringTest() {
		Version version = Version.factory.newVersion(new int[]{1,2,3});
		Assert.assertEquals("1.2.3", version.getVersionString());
	}

	@Test
	public void IsMoreRecentTest() {
		Version v1 = Version.factory.newVersion("1.2.3");
		Version v2 = Version.factory.newVersion("1.2.4");
		
		Assert.assertTrue(v2.isMoreRecentThan(v1));
		Assert.assertFalse(v1.isMoreRecentThan(v2));
	}
	
	@Test
	public void IsMoreRecentTest2() {
		Version v1 = Version.factory.newVersion("1.2");
		Version v2 = Version.factory.newVersion("1.2.1");
		
		Assert.assertTrue(v2.isMoreRecentThan(v1));
		Assert.assertFalse(v1.isMoreRecentThan(v2));
	}
	
	@Test
	public void IsMoreRecentTest3(){
		Version v1 = Version.factory.newVersion("1.2.1_10");
		Version v2 = Version.factory.newVersion("1.3.1_15");
		
		Assert.assertTrue(v2.isMoreRecentThan(v1));
		Assert.assertFalse(v1.isMoreRecentThan(v2));

	}

}
