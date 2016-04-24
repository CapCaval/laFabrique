package org.capcaval.ermine.coves._test;

import org.capcaval.ermine.jfx.JfxApplicationTools;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.StringMultiLine;
import org.junit.Assert;

public class CovesApplicationTest {
	@org.junit.Test
	public void writeLanguageTest() {

		CovesAppExampleMain.main(new String[] { "generateTranslationFile" });

		String propStr = FileTools.readStringfromFile("language.properties");

		StringMultiLine result = new StringMultiLine("#PasswordPaneView", "PasswordPaneView.userlabel=Username:",
				"PasswordPaneView.pswdLabel=Password:", "PasswordPaneView.loginButton=Login", "");

		Assert.assertEquals(result.toString(), propStr);
	}

	@org.junit.Test
	public void readLanguageTest() {
		JfxApplicationTools.runApplication(CovesAppExampleMain.class, new String[] { "" });
	}
}
