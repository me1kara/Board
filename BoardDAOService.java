package com.lec.ex02_board;

import java.util.ArrayList;

public interface BoardDAOService {
	
	//글쓰기
	void makeBoard();
	//본문조회
	BoardVO contentView(int bno);
	//글수정
	BoardVO updateBoard(int bno);
	//글삭제
	BoardVO deleteBoard(int bno);
	//전체글조회
	ArrayList<BoardVO> listBoard();
	//제목별조회
	ArrayList<BoardVO> findBySubject(String subject);
	//작성자별조회
	ArrayList<BoardVO> findByWriter(String writer);
	
	
}
