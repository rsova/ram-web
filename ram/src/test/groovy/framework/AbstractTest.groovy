package framework

import org.apache.olingo.client.core.v4.ODataClientImpl
import org.apache.olingo.commons.api.format.ODataFormat
import org.custommonkey.xmlunit.XMLUnit
import org.junit.BeforeClass

public abstract class AbstractTest {

  protected static org.apache.olingo.client.api.v3.ODataClient v3Client;

  protected static org.apache.olingo.client.api.v4.ODataClient v4Client;

 // protected abstract CommonODataClient<?> getClient();

  @BeforeClass
  public static void setUp() {
    XMLUnit.setIgnoreComments(true);
    XMLUnit.setIgnoreAttributeOrder(true);
    XMLUnit.setIgnoreWhitespace(true);
    XMLUnit.setNormalizeWhitespace(true);
    XMLUnit.setCompareUnmatched(false);
  }

  @BeforeClass
  public static void setClientInstances() {
//    v3Client = ODataClientFactory.getV3();
    v4Client = ODataClientImpl.getV4()
	
  }

  protected String getSuffix(final ODataFormat format) {
    return format == ODataFormat.ATOM || format == ODataFormat.XML ? "xml" : "json";
  }
}