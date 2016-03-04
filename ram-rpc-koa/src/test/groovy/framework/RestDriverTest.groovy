package framework;

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.ContentType.XML
import static org.junit.Assert.*
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import static groovyx.net.http.Method.*

import org.junit.Test


class RestDriverTest {
	static final String DBID = 'dbid1422054536'
	
	@Test
	void testHttpBuilder() {
	def reqBody = '''
<methodCall>
  <methodName>lists</methodName>
  <params>
    <param>
      <value>
        <struct>
          <member>
            <name>dbid</name>
            <value>
              <string>dbid1422054536</string>
            </value>
          </member>
          <member>
            <name>getlist</name>
            <value>
              <string>condo</string>
            </value>
          </member>
        </struct>
      </value>
    </param>
  </params>
</methodCall>
'''
		
		def http = new HTTPBuilder('http://websearch.ramidx.com')
		def headerMap = [
			'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:41.0) Gecko/20100101 Firefox/41.0',
			'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
			'Accept-Language': 'en-US,en;q=0.5',
			'Accept-Encoding': 'gzip, deflate',
			'Referer': 'http://websearch.ramidx.com/',
			'Cookie': 'D_SID=199.4.43.66:b+SYOfowW2A04loAVKfta/77odzLu9G3EaZpjvLSgOM; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=DD60966D-3ACE-374D-8EA7-6B914BE291AE; D_UID=49868970-029F-35DD-92F9-534289F43540; D_HID=7IAIVBWoA+pwbhE3oxPGEtoCboeav6yWFVyjDc6y8HA; RAMID=2for6qnmb4oqs5ufm5kv6819a5',
			'Connection': 'keep-alive'
		]
		 
		
		http.request(POST, XML) { req ->
			uri.path = '/ramxml.php' // overrides any path in the default URL
			headers.'User-Agent' = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:41.0) Gecko/20100101 Firefox/41.0'
			headers.'Accept-Language' = 'en-US,en;q=0.5'
//			headers.'Accept-Encoding' = 'gzip, deflate'
			headers.'Referer' = 'http://websearch.ramidx.com/'
			headers.'Connection' = 'keep-alive'
			headers.'Cookie' = 'D_SID=199.4.43.66:b+SYOfowW2A04loAVKfta/77odzLu9G3EaZpjvLSgOM; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=DD60966D-3ACE-374D-8EA7-6B914BE291AE; D_UID=49868970-029F-35DD-92F9-534289F43540; D_HID=7IAIVBWoA+pwbhE3oxPGEtoCboeav6yWFVyjDc6y8HA; RAMID=2for6qnmb4oqs5ufm5kv6819a5'
			headers.'Accept' = 'application/xml'
			body = reqBody
			response.success = { resp, xml ->
				assert resp.status == 200
				println "My response handler got response: ${resp.statusLine}"
				println xml.methodResponse.text
			}

			// called only for a 404 (not found) status code:
			response.'404' = { resp ->
				println 'Not found'
			}
		}
		
	}
		
		
}