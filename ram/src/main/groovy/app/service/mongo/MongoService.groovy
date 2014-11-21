package app.service.mongo
import groovy.xml.*

import org.json.JSONObject

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import com.mongodb.DBObject
import com.mongodb.WriteResult
import com.mongodb.util.JSON
class MongoService {

	//@Inject
	DB db
	
	private String collection //websearch

	public DBObject findOne(){
		//return db.getCollection(this.collection).findOne()
		return db.getCollection('websearch').findOne()
	}

	def findOneByName(String token){
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put('name', token);
		DBCursor cursor = db.getCollection(this.collection).find(whereQuery)
		DBObject dbo = (cursor.hasNext())?cursor.next():null // get one
		return dbo
	}
	
	def Map findOne(Map params){
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.putAll(params);
		def start = Date.getMillisOf(new Date())
		DBCursor cursor = db.getCollection(this.collection).find(whereQuery)
		DBObject dbo = (cursor.hasNext())?cursor.next():null // get one
		double total = (Date.getMillisOf(new Date()) - start)/1000 //seconds
		return dbo?.toMap()?.plus(['total':total])
	}
	
	public WriteResult save(String json){
		DBObject dbObject = (DBObject) JSON.parse(json);
		return db.getCollection(this.collection).save(dbObject)
	}
	
	public WriteResult save(Map map){
		return db.getCollection(this.collection).save(new BasicDBObject(map))
	}

	public String produceResponce(def dbo){
		String jsonStr = JSON.serialize(dbo)
		JSONObject json = new JSONObject(jsonStr)
		return json.get('items')
	}

}

