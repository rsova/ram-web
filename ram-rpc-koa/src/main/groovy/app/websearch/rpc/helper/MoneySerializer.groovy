package app.websearch.rpc.helper

import redstone.xmlrpc.XmlRpcCustomSerializer
import redstone.xmlrpc.XmlRpcException
import redstone.xmlrpc.XmlRpcSerializer

public class MoneySerializer implements XmlRpcCustomSerializer
  {
      public Class getSupportedClass()
      {          
		  return BigDecimal.class;
      }
  
      public void serialize(
          Object value,
          Writer writer,
          XmlRpcSerializer builtInSerializer )
          throws XmlRpcException, IOException
      {
		    BigDecimal bDecimal = (BigDecimal)value;
		  
            writer.write("<double>");
            writer.write(bDecimal.intValue().toString());
            writer.write("</double>");
      }
  }