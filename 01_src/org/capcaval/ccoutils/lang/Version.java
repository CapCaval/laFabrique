package org.capcaval.ccoutils.lang;

import org.capcaval.ccoutils.factory.FactoryTools;

public interface Version {
	
	static VersionFactory factory = FactoryTools.newFactory(VersionFactory.class, new VersionFactoryImpl());
	
	public interface VersionFactory{
		Version newVersion(String versionStr);
		Version newVersion(int[] versionIntArray);
	}
	
	public class VersionFactoryImpl implements VersionFactory{
		@Override
		public Version newVersion(String versionStr) {
			return new VersionImpl(versionStr);
		}

		@Override
		public Version newVersion(int[] versionIntArray) {
			return new VersionImpl(versionIntArray);
		}
	}
	
	public String getVersionString();
	public int[] getVersionIntArray();
	public boolean isMoreRecentThan(Version version);
}
