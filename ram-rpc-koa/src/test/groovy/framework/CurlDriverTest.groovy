package framework;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

class CurlDriverTest {
	
	static final def URL = 'http://websearch.ramidx.com/smartframe/ramxml.php'
//			static final def URL = 'http://websearch.ramidx.com/ramxml.php'
	
//	static final def DBID = 'dbid1422054536'
//	static final def DBID = 'dbid1139259934'
	
	static final def HEADERS = [
		'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
		'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:41.0) Gecko/20100101 Firefox/41.0',
		'Accept-Language': 'en-US,en;q=0.5',
//		'Accept-Encoding': 'gzip, deflate',
		'Referer': 'http://websearch.ramidx.com/',
		'Connection': 'keep-alive',
		'Cookie': 'D_SID=199.4.43.66:b+SYOfowW2A04loAVKfta/77odzLu9G3EaZpjvLSgOM; D_PID=3119DF0B-3C06-308A-88B4-6118E4B86D16; D_IID=DD60966D-3ACE-374D-8EA7-6B914BE291AE; D_UID=49868970-029F-35DD-92F9-534289F43540; D_HID=7IAIVBWoA+pwbhE3oxPGEtoCboeav6yWFVyjDc6y8HA; RAMID=2for6qnmb4oqs5ufm5kv6819a5',
		'Cache-Control': 'no-cache'
	]
	
	static final def CURL_CMD = 'curl'
	

	@Test
	public void testSmartFrame() {

		def rpcXml =
'''
<methodCall>
  <methodName>lists</methodName>
  <params>
    <param>
      <value>
        <struct>
          <member>
            <name>dbid</name>
            <value>
              <string>dbid1422054460</string>
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

		def websearch_raidx = [
			'curl',
			//'-H',"X-Forwarded-For: localhost",
			//'-H', 'Server: nginx',
			'-H',
			'DNT: 1' ,
			//'-H', 'Accept-Encoding: gzip, deflate, sdch' ,'-H',
			//'Accept-Language: en-US,en;q=0.8,ru;q=0.6' ,
			'-H',
			'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36',
			'-H',
			'Accept: text/html ,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' ,
			//'-H', 'Referer: http://www.itsmaui.com/index.htm' ,
			'-H',
			'Cookie: WebSearchID=cl1nv175sugck09he39fsegks7; D_SID=74.123.217.146:3rK08PPRKVcCOWmibSyxVs9T5+e0ovpplUhtdFEsY+4; RAMID=01gi1v10idj6p6li4qpr4ssm45; D_PID=1E1F7BE5-C578-31A0-A45A-42D248887EEA; D_IID=84BC35A0-17CA-37E9-8A26-230D6C6E2936; D_UID=5C0D6FA3-04F3-359C-A74D-7BCB2D1584BB; D_HID=1TRSRu3Nd1d8Ns0kfOGEFbg6J3B/liDyThOKNoVokHE',
			'-H',
			'Connection: keep-alive',
			//'--compressed',
			//'-e localhost',
			'http://websearch.ramidx.com/smartframe/ramxml.php'
		]
		websearch_raidx.add("-d " + rpcXml)
		println  websearch_raidx.execute().text
	}

	@Test
	public void testIdx() {

		def rpcXml =
'''
<methodCall>
<methodName>lists</methodName>
<params>
<param>
<value>
<struct>
<member>
<name>dbid</name>
<value>
<string>dbid1422054460</string>
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
		def websearch_raidx = [
			CURL_CMD,
			'-X', 'POST'
		]
		for ( e in HEADERS) {
			websearch_raidx.add('-H')
			websearch_raidx.add("${e.key}: ${e.value}")
		}
		websearch_raidx.add('-d ' + rpcXml)
		websearch_raidx.add(URL)
		
		println websearch_raidx.execute().text
	}

}
