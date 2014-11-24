package app.websearch.transformer

import org.springframework.stereotype.Service

import app.service.odata.Mappings

import com.google.gson.Gson
@Service
class TransformationService {
	Map<String,Object> mappings

	public TransformationService() {
		mappings = new Gson().fromJson( Mappings.maping, Map.class).get("mappings");
	}

	public Map transform(String json){
		//start from value, parse json to list of
		List oData = new Gson().fromJson(json, Map.class).get('value')

		def propertyList = [:]
		for (Map listing in oData){
			def property = [:]
			for(pair in mappings){
				if(pair.value instanceof Collection){
					property.putAll(handleCollection(pair, listing))
				}else if(pair.value instanceof String){ //number in a value, means no match found
					property.put(
						pair.key,
						listing.get(pair.value).toString()
					)
				}
			}
			propertyList.putAll(property)
		}
		return propertyList
		//return ['data':[propertyList,'&lt;div style=&quot;font-size:10px&quot;&gt;This' ]]
	}
//	public Map findAllFromParams(String json){
//		//start from value, parse json to list of
//		List oData = new Gson().fromJson(json, Map.class).get('value')
//				
//				def propertyList = []
//						for (Map listing in oData){
//							def property = []
//									for(pair in mappings){
//										if(pair.value instanceof Collection){
//											property.add(handleCollection(pair, listing))
//										}else if(pair.value instanceof String){ //number in a value, means no match found
//											property.add([
//											              pair.key,
//											              listing.get(pair.value).toString()
//											              ])
//										}
//									}
//							propertyList.add(property)
//						}
//		return ['data':propertyList]
//	}

	protected Map handleCollection( pair,  listing){
	//protected Map handleCollection(Map pair, Map listing){
		def all = []
		Map collEntity = [:]
		for (val in pair.value){
			all.add(listing.get(val).toString())
		}
		//phone
		if(all?.first() && all?.last()){
			//[a:b]
			//collEntity.addAll([pair.key,  "("+all.first()+") "+all.last()])
			def key = pair.key
			def val = "("+all.first()+") "+all.last()
			collEntity = [key:val]
		}
		return collEntity
	}

	public Map fromXmlrpcToOdata(Map webSearchParams){
		def oParams = [:]
		for (pair in webSearchParams) {
			def lookupKey = pair.key.toString().replaceFirst('What','')
			def matchPair = mappings.get(lookupKey)
			if(matchPair){
				oParams.put(matchPair, pair.value)
			}
		}
		if(webSearchParams.containsKey('WhatPropType')) {oParams.put('PropertyType',webSearchParams.get('WhatPropType'))}
		return oParams
	}
}
