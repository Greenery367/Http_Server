package ch01;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class Practice1 {
	
	public static void main(String[] args) {
		try {
			HttpServer httpServer=HttpServer.create(new InetSocketAddress(8080),0);
			
			httpServer.createContext("/test",new MyTestHandler());
			httpServer.createContext("/hello",new HelloHandler());
			
			httpServer.start();
			System.out.println(">> My Http Server started on port 8080");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end of main
	
	
	static class MyTestHandler implements HttpHandler{

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String method = exchange.getRequestMethod();
			System.out.println("method : " + method);
			
			if("GET".equalsIgnoreCase(method)) {
				// Get 요청시 이라면 여기 동작
				// System.out.println("여기는 Get 방식으로 호출 됨");
				//GET ->  path: /test 라고 들어오면 어떤 응답 처리를 내려 주면 된다. 
				handleGetRequest(exchange);
				
			} else if("POST".equalsIgnoreCase(method)) {
				// Post 요청시 여기 동작 
				// System.out.println("여기는 Post 방식으로 호출 됨");
				handlePostRequest(exchange);
			} else {
				// 지원하지 않는 메서드에 대한 응답 
				String respnose = "Unsupported Methdo : " + method;
				exchange.sendResponseHeaders(405, respnose.length()); // Method Not Allowed
				
				// 예시 
 				// new OutputStreamWriter(exchange.getResponseBody());
				
				OutputStreamWriter os = new OutputStreamWriter(exchange.getResponseBody());
				os.write(respnose);
				os.flush();
				os.close();
		}
		
	}

	// Get 요청시 동작 만들기
	private void handleGetRequest(HttpExchange exchange) throws IOException{
		String response="""
				<!DOCTYPE html>
				<html lang=ko>
				<head></head>
				<body>
				    <h1 style="background-color:red"> Hello path by /test </h1>
				</body>
				</html>
				""";
		exchange.sendResponseHeaders(200,response.length());
		OutputStreamWriter os = new OutputStreamWriter(exchange.getResponseBody());
		os.write(response);
		os.flush();
		os.close();
	}
	
	private void handlePostRequest(HttpExchange exchange) throws IOException{
		String response="""
				<!DOCTYPE html>
				<html lang=ko>
				<head></head>
				<body>
					<h1 style="background-color:red"> Hello path by /test </h1>
				</body>
				</html>
				""";
		exchange.setAttribute("Content-Type", "text/html; chatset=UTF-");
		exchange.sendResponseHeaders(200, response.length());
		OutputStreamWriter os = new OutputStreamWriter(exchange.getResponseBody());
		os.write(response);
		os.flush();
		os.close();
	} // end of MyTestHandler
	
	static class HelloHandler implements HttpHandler{

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String method=exchange.getRequestMethod();
			System.out.println("hello method : "+method);
			
		}
		 
	} // end of HelloHandler
	
}
