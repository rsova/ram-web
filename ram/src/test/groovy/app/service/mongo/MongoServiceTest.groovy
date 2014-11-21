package app.service.mongo;
import static org.junit.Assert.*

import org.json.JSONObject
import org.junit.BeforeClass
import org.junit.Test

import app.service.mongo.MongoService

import com.mongodb.DB
import com.mongodb.MongoClient
import com.mongodb.util.JSON

class MongoServiceTest {
	
	static MongoService service;
	
	@BeforeClass
	public static void testBefore(){
		ConfigObject config = new ConfigSlurper().parse(new File("./src/ratpack/Config.groovy").getText()).app
		println config.mongo.host
		println config.mongo.port
		MongoClient client = new MongoClient(config.mongo.host as String, config.mongo.port)
		DB db = client.getDB(config.mongo.db)
		db.authenticate(config.mongo.user, config.mongo.pass as char[])
		service = new MongoService()
		service.db = db
		service.collection = 'websearch'		
		println 'Set up -------->'	
	}
	
	//@Test
	public void test() {
	 def dbo = service.findOne()
	 //String jsonStr = JSON.serialize(dbo)
	 JSONObject json = new JSONObject(JSON.serialize(dbo))
	 println URLDecoder.decode(json.get('xml'))
	 //println json.get('xml')

   }
	
	//@Test
	public void testSave() {
		def out = service.save("{'method':'lists', 'param':'district', 'other':'stuff'}")
		println out
	}
	@Test
	public void testFindOneByProp() {
		//"$oid": "545ffe06036436590e79eb8f"
		def out = service.findOne(['method':'lists', 'param':'district'])
		println out
		
	}

}
