package com.lec.ex02_board;

public class BoardFactory {
	//실습.싱글톤으로 객체 만들기, BoardDaoImpl을 싱글톤으로 만들기
	
	BoardDAOImpl db =null;
	
	public BoardDAOImpl getBoard() {

		return BoardDAOImpl.getIstance();
	}
}
