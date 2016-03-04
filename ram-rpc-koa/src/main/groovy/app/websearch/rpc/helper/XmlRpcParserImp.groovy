package app.websearch.rpc.helper

import redstone.xmlrpc.XmlRpcArray
import redstone.xmlrpc.XmlRpcParser
import redstone.xmlrpc.XmlRpcStruct

class XmlRpcParserImp  extends XmlRpcParser{
//		XmlRpcStruct map = null
//		XmlRpcArray array = null
		def collection
		
		@Override
		protected void handleParsedValue( result) {
			this.collection = result
//			println (arg instanceof XmlRpcStruct)
//			println (arg instanceof XmlRpcArray)
			
//			if(arg instanceof XmlRpcStruct){
//				map = arg
//				//return
//			}
//			
//			if(arg instanceof XmlRpcArray){
//				array = arg
//			}
		}
	
//		protected Object getCollection( arg0) {
//			return (map!=null)? map as Map: array as ArrayList
//		};

}
