package app.websearch.rpc.helper

import redstone.xmlrpc.XmlRpcCustomSerializer
import redstone.xmlrpc.XmlRpcException
import redstone.xmlrpc.XmlRpcSerializer

public class ObjectSerializer implements XmlRpcCustomSerializer
  {
      public Class getSupportedClass()
      {          
		  return Object.class;
      }
  
      public void serialize(
          Object value,
          Writer writer,
          XmlRpcSerializer builtInSerializer )
          throws XmlRpcException, IOException
      {
          writer.write()
//  
//          Object[] array = ( Object[] ) value;
//  
//          for ( int i = 0; i < array.length; ++i )
//          {
//              builtInSerializer.serialize( array[ i ], writer );
//              
//              if ( i < array.length - 1 )
//              {
//                  writer.write( ',' );
//              }
//          }
//  
//          writer.write( ']' );
      }
  }