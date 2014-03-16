package org.capcaval.ccoutils.lang._test;

import org.capcaval.ccoutils.lang.Version;
import org.junit.Assert;
import org.junit.Test;

public class VersionTest {

	@Test
	public void testGetInArrayTest() {
		Version version = Version.factory.newVersion("1.2.3");
		Assert.assertArrayEquals(new int[]{1,2,3,0,0,0}, version.getVersionIntArray());
	}
	
	@Test
	public void testGetStringTest() {
		Version version = Version.factory.newVersion(new int[]{1,2,3});
		Assert.assertEquals("1.2.3", version.getVersionString());
	}

	@Test
	public void testBadInputStringTest() {
		Version version = Version.factory.newVersion("12.5.3-ab");
		Assert.assertArrayEquals(new int[]{12,5,3,0,0,0}, version.getVersionIntArray());
	}

	
	@Test
	public void IsMoreRecentTest() {
		Version v1 = Version.factory.newVersion("1.2.3");
		Version v2 = Version.factory.newVersion("1.2.4");
		
		Assert.assertTrue(v2.isHigherVersionThan(v1));
		Assert.assertFalse(v1.isHigherVersionThan(v2));
		
		Assert.assertTrue(v2.isHigherVersionThan(v1));
		Assert.assertFalse(v1.isHigherVersionThan(v2));
	}
	
	@Test
	public void IsMoreRecentTest2() {
		Version v1 = Version.factory.newVersion("1.2");
		Version v2 = Version.factory.newVersion("1.2.1");
		
		Assert.assertTrue(v2.isHigherVersionThan(v1));
		Assert.assertFalse(v1.isHigherVersionThan(v2));
		
		Assert.assertTrue(v2.isHigherVersionThan(v1));
		Assert.assertFalse(v1.isHigherVersionThan(v2));
	}
	
	@Test
	public void IsMoreRecentTest3(){
		Version v1 = Version.factory.newVersion("1.2.1_10");
		Version v2 = Version.factory.newVersion("1.3.1_15");
		
		Assert.assertTrue(v2.isHigherVersionThan(v1));
		Assert.assertFalse(v1.isHigherVersionThan(v2));

		Assert.assertFalse(v2.isLowerVersionThan(v1));
		Assert.assertTrue(v1.isLowerVersionThan(v2));
	}

	@Test
	public void IsMoreRecentTest4(){
		Version v1 = Version.factory.newVersion("1.2.1_10");
		Version v2 = Version.factory.newVersion("1.3.1");
		
		Assert.assertTrue(v2.isHigherVersionThan(v1));
		Assert.assertFalse(v1.isHigherVersionThan(v2));

		Assert.assertFalse(v2.isLowerVersionThan(v1));
		Assert.assertTrue(v1.isLowerVersionThan(v2));
	}
	
	
	@Test
	public void getLongValueTest(){
		Version v = Version.factory.newVersion("1.2.1_10");
		long value = v.getLongValue();
		System.out.println("long value : " + value);

		Assert.assertEquals(1_002_001_010_000_000L, value);
	}

}
