package com.blinkfox.fenix.config;

import com.blinkfox.fenix.exception.FenixException;
import com.blinkfox.fenix.helper.XmlNodeHelper;
import org.junit.Test;

/**
 * FenixConfigManager Test.
 *
 * @author blinkfox on 2019-09-01.
 */
public class FenixConfigManagerTest {

    /**
     * 测试 FenixConfig 为空的情况.
     */
    @Test(expected = FenixException.class)
    public void initLoadWithException() {
        FenixConfigManager.getInstance().initLoad(null);
    }

    /**
     * 测试 FenixConfig 为空的情况.
     */
    @Test
    public void initLoad2() {
        FenixConfigManager.getInstance().initLoad(new FenixConfig().setDebug(true));
    }

    @Test
    public void testParseFenixXmlWithXmlns() {
        FenixConfigManager.getInstance().initLoad(new FenixConfig()
                .setDebug(false)
                .setPrintSqlInfo(true)
                .setXmlLocations("others")
                .setHandlerLocations("com.blinkfox.fenix.handler"));
        assert XmlNodeHelper.getNodeBySpaceAndId("myFenixXml", "queryAllMyUsers") != null;
        assert XmlNodeHelper.getNodeBySpaceAndId("myFenixXml", "queryAllUsers") != null;


        FenixConfigManager.getInstance().initLoad(new FenixConfig()
                .setDebug(true)
                .setPrintSqlInfo(true)
                .setXmlLocations("others")
                .setHandlerLocations("com.blinkfox.fenix.handler"));
        assert XmlNodeHelper.getNodeBySpaceAndId("myFenixXml", "queryAllMyUsers") != null;
        assert XmlNodeHelper.getNodeBySpaceAndId("myFenixXml", "queryAllUsers") != null;
    }

}
