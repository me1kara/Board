package com.lec.ex02_board;

import java.io.FileReader;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDRV() {
		return DRV;
	}

	public void setDRV(String dRV) {
		DRV = dRV;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getUSR() {
		return USR;
	}

	public void setUSR(String uSR) {
		USR = uSR;
	}

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	private String path = ConnectionFactory.class.getResource("jdbc.properties").getPath(); //프로퍼티의 경로 가져오기
	
	private String DRV = null;
	private String URL = null;
	private String USR = null;
	private String PWD = null;
	
	private String insert = null;
	private String select = null;
	private String update = null;
	private String delete = null;
	
	public ConnectionFactory() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// jdbc.properties의 정보를 읽어오는 메서드
	// db접속정보와 sql정보를 가져오는 메서드
	public void setUp() throws Exception {
		
//		System.out.println(path);
		Properties p = new Properties();
//		path = URLDecoder.decode(path, "utf-8"); //인코딩된 경로를 utf8의 방식으로 해독한건데 값이 또같네?
		
//		System.out.println(path);
		p.load(new FileReader(path)); //해당 파일을 가져와서 프로퍼티스 객체에 담음
		
		DRV = p.getProperty("jdbc.drv"); // 키 값의 형태로 돼있기때문에 키를 통해 값을 호출했음
		URL = p.getProperty("jdbc.url");
		USR = p.getProperty("jdbc.usr");
		PWD = p.getProperty("jdbc.pwd");
		
		insert = p.getProperty("insert");
		select = p.getProperty("select");
		update = p.getProperty("update");
		delete = p.getProperty("delete");
		
//		System.out.println(DRV);
//		System.out.println(URL);
//		System.out.println(USR);
//		System.out.println(PWD);
//		System.out.println(insert);
//		System.out.println(select);
//		System.out.println(update);
//		System.out.println(delete);
		
		
	}
	
	public Connection getConnection() throws ClassNotFoundException {
		
		try {
			Class.forName(DRV);
			return DriverManager.getConnection(URL,USR,PWD);
			
			
		} catch (SQLException e) {
			System.out.println("접속실패");
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}

