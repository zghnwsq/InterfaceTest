package Test1;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

public class TestSoap {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Soap s = new Soap();
		s.addBody("<?xml version='1.0' encoding='UTF-8'?>"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<soap:auth>"
				+ "</soap:auth>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");
		String res = s.post("http://www.testingedu.com.cn/inter/SOAP?wsdl");
//		System.out.println(res);
		System.out.println(s.getResult(res));
		JSONObject js = new JSONObject(s.getResult(res));
//		System.out.println(js.get("token"));
		
		s.addBody("<?xml version='1.0' encoding='UTF-8'?>"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<soap:login>"
				+ "<arg0>ted1</arg0>"
				+ "<arg1>123456</arg1>"
				+ "</soap:login>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");
		s.addHead("token", js.get("token").toString());
		res = s.post("http://www.testingedu.com.cn/inter/SOAP?wsd");
		System.out.println(s.getResult(res));

		s.addBody("<?xml version='1.0' encoding='UTF-8'?>"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<soap:getUserInfo>"
				+ "<arg0>28</arg0>"
				+ "</soap:getUserInfo>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");
		res = s.post("http://www.testingedu.com.cn/inter/SOAP?wsdl");
		System.out.println(s.getResult(res));
				
		
		s.addBody("<?xml version='1.0' encoding='UTF-8'?>"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.testingedu.com/\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<soap:logout>"
				+ "</soap:logout>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>");
		res = s.post("http://www.testingedu.com.cn/inter/SOAP?wsdl");
		System.out.println(s.getResult(res));
		
		
		
	}

}

