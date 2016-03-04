package app.websearch.rpc.helper

import org.apache.commons.lang.StringEscapeUtils

import redstone.xmlrpc.XmlRpcSerializer
//import groovy.util.*
class XmlRpcHelper {

	static final String REQUEST_HEADER = "<methodCall><methodName>METHOD_NAME</methodName><params><param>"
	static final String REQUEST_FOOTER = "</param></params></methodCall>"

	static 	public String toXmlRpcRequest(String methodName, params) {
		Writer writer = new StringWriter();
		XmlRpcSerializer x = new XmlRpcSerializer()
		x.serialize(params, writer)
		return writeRequestHeader(methodName) + writer.toString() + writeRequestFooter()
	}

	static 	public String toXmlRpcRequest1(String methodName, Map params) {
		XmlRpcSerializer serializer = new XmlRpcSerializer()
		Writer writer = new StringWriter();
		def map = ['methodCall1':methodName]
		map.putAll(params)
		serializer.serialize(map, writer)
		return  writer.toString()
	}

	static 	public String toXmlResponse(Object params) {
		XmlRpcSerializer serializer = new XmlRpcSerializer()
		serializer.addCustomSerializer(new MoneySerializer()) 
		serializer.addCustomSerializer(new ObjectSerializer()) 
		Writer writer = new StringWriter();
		serializer.writeEnvelopeHeader([], writer)
		serializer.serialize(params, writer)
		serializer.writeEnvelopeFooter([],writer)
		def xml =  writer.toString()
		//serializer puts in integers as i4, replace with int to match
		return xml.replace('i4>', 'int>')
	}

	static public Object xmlRpcToCollection(String xmlRpc){
		XmlRpcParserImp parser = new XmlRpcParserImp()
		parser.parse(new ByteArrayInputStream(xmlRpc.getBytes()))
		return parser.collection
	}
	
	static public String xmlRpcRequestMethodName(String xmlRpc){
		return new XmlSlurper().parseText(xmlRpc).methodName.text()
	}
	
	static public Map xmlRpcToCollection2(String xmlRpc){
		
		XmlRpcSerializer serializer = new XmlRpcSerializer()
		def response = new XmlSlurper().parseText(xmlRpc)
		def list = response.params.param.value.struct.member.depthFirst().findAll { it.name() in ['name', 'value']} unique { a, b -> a <=> b }
		def collection = list.collate(2)
		def data = [:]
		for (element in collection) {
			data.put(element.first().text(), element.last().text())
		}
		return data
	}
	
	static public Map xmlRpcToCollection1(String xmlRpc, path){
		XmlRpcSerializer serializer = new XmlRpcSerializer()
		def response = new XmlSlurper().parseText(xmlRpc)
		def list = response.params.param.value.array.data.value[4].array.data.value[0].struct.member.depthFirst().findAll { it.name() in ['name', 'value']}
		def collection = list.collate(2)
		def data = [:]
		for (el in collection) {
			data.put(el.first().text(), el.last().text())
		}
		return data
	}
	
	static public String writeRequestHeader(String methodName){
		return REQUEST_HEADER.replaceFirst('METHOD_NAME', methodName)
	}
	
	static public String writeRequestFooter(){
		return REQUEST_FOOTER
	}
	
	static public List<Map<String,Object>> escapeXml (List<Map<String,Object>> list) {
		for (Map<String,Object> row : list) {
			for (Map.Entry<String,Object> entry : row) {
				if (entry.getValue() instanceof String) {
					String escaped = StringEscapeUtils.escapeXml(entry.getValue());
					entry.setValue(escaped);
				}
			}
		}
		return list;
	}

}
