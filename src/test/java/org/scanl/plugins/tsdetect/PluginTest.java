package org.scanl.plugins.tsdetect;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.psi.xml.XmlFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PsiErrorElementUtil;

/**
 * This was included in the plugin template, we can probably remove this once we have actual tests
 */
@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class PluginTest extends BasePlatformTestCase {

    public void testXMLFile() {
        var psiFile = myFixture.configureByText(XmlFileType.INSTANCE, "<foo>bar</foo>");
        var xmlFile = assertInstanceOf(psiFile, XmlFile.class);

        assertFalse(PsiErrorElementUtil.hasErrors(getProject(), xmlFile.getVirtualFile()));

        assertNotNull(xmlFile.getRootTag());

        assertEquals("foo", xmlFile.getRootTag().getName());
        assertEquals("bar", xmlFile.getRootTag().getValue().getText());
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/rename";
    }

    public void testRename() {
        myFixture.testRename("foo.xml", "foo_after.xml", "a2");
    }
}
