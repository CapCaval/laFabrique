package org.capcaval.lafabrique.lang;

import org.capcaval.lafabrique.factory.FactoryTools;
import org.capcaval.lafabrique.lang._impl.VersionImpl;

public interface Version {
	
	static VersionFactory factory = FactoryTools.newFactory(VersionFactory.class, new VersionFactoryImpl());
	
	public interface VersionFactory{
		Version newVersion(String versionStr);
		Version newVersion(int... versionIntArray);
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
	public boolean isThisVersionValueHigherThan(Version version);
	public boolean isThisVersionValueLowerThan(Version version);
	public boolean isThisVersionHigherThan(Version version);
	public boolean isThisVersionLowerThan(Version version);
	public boolean isThisVersionSameThan(Version version);
	public long getLongValue();

}
