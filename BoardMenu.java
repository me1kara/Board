package com.lec.ex02_board;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BoardMenu {

	private double ver;
	
	public BoardMenu(double ver) {
		this.ver=ver;
	}
	
	
	
	public void listBoard(BoardDAOImpl board){
		ArrayList<BoardVO> bds = board.listBoard();
		
		System.out.println("=======================================");
		System.out.println("글번호\t\t제목\t\t작성자\t\t내용\t\t");
		System.out.println("=======================================");
		
		
		for(BoardVO bd:bds) {
			System.out.println(bd.toString());
		}
		
		System.out.println("=============출력종료==================");
		
		//걍 DB연결해서 한 줄씩 읽어서 반환하면 그만 아닌가
		
	}
	

	public int mainMenuUI() {
		
		String menuNo = JOptionPane.showInputDialog("====게시판관리프로그램 V" + this.ver + "====\n\n"+
					"1.새로운 글쓰기\n"+
					"2.전체글 목록조회\n"+
					"3.본문글 보기\n"+
					"4.본문글 수정\n"+
					"5.본문글 삭제\n"+
					"6.제목으로 게시글조회하기\n"+
					"7.작성자로 게시글조회하기\n"+
					"0.종료\n\n"+
					"처리할 작업번호를 입력하세요!"
				);
		if(menuNo==null || menuNo.equals("")) {
			return 8;
		}else {return Integer.parseInt(menuNo);
		}
	
	}


	public void mainBoardMenu() {
		
		BoardDAOImpl board = new BoardFactory().getBoard();
		
		
		while(true) {
			int menuNo = mainMenuUI();
			
			switch(menuNo) {
				case 0: System.out.println("프로그램을 종료합니다"); System.exit(0);
				case 1: makeBoard(board); break ;
				case 2: listBoard(board); break ;
				case 3: contentView(board); break ;
				case 4: update(board); break ;
				case 5: delete(board); break ;
				case 6: findBySubject(board); break ;
				case 7: findByWritter(board); break ;
				case 8: System.out.println("숫자를 입력해주세요"); break ;
			}
			
		}
	
	}

	private void findByWritter(BoardDAOImpl board) {
		String writter = JOptionPane.showInputDialog("찾고자 하는 작성자를 입력하세요!");
		if(writter==null) return;
		else {
			ArrayList<BoardVO> bdl =board.findByWriter(writter);
			System.out.println("====================================================");
			System.out.println("글번호\t\t제목\t\t작성자\t\t내용\t\t");
			System.out.println("====================================================");
			
			
			if(bdl.isEmpty()) {
				System.out.println("해당하는 글이 없습니다");
				
			}else {
			
			for(BoardVO bd:bdl) {
				System.out.println(bd.toString());
				System.out.println(bd.getBno());
				System.out.println(bd.getContent());
				
			}
			}
			
			System.out.println("=================================================");
		}	
	}



	private void findBySubject(BoardDAOImpl board) {
		String subject = JOptionPane.showInputDialog("찾고자 하는 제목을 입력하세요!");
		if(subject==null) return;
		else {
			ArrayList<BoardVO> bdl =board.findBySubject(subject);
			System.out.println("===================================================");
			System.out.println("글번호\t\t제목\t\t작성자\t\t내용\t\t");
			System.out.println("===================================================");
			
			if(bdl.isEmpty()) {
				System.out.println("해당하는 글이 없습니다");
			}else {
			
			for(BoardVO bd:bdl) {
				System.out.println(bd.toString());
			}
			}
			
			System.out.println("=================================================");
		}	
		
	}



	private void delete(BoardDAOImpl board) {
		// TODO Auto-generated method stub
		String bno = JOptionPane.showInputDialog("글번호를 입력하세요!");
		if(bno==null) return;
		else {
			BoardVO bd = board.deleteBoard(Integer.parseInt(bno));
		}
	}



	private void update(BoardDAOImpl board) {
		// 
		String bno = JOptionPane.showInputDialog("글번호를 입력하세요!");
		
		if(bno==null) return;
		else {
			BoardVO bd = board.updateBoard(Integer.parseInt(bno));
		}
		
	}



	private void contentView(BoardDAOImpl board) {
		String bno = JOptionPane.showInputDialog("글번호를 입력하세요!");
		
		if(bno==null||bno.equals("")) return;
		else {
			BoardVO bd = board.contentView(Integer.parseInt(bno));
			System.out.println("글번호:"+bd.getBno());
			System.out.println("글제목:"+bd.getSubject());
			System.out.println("작성자:"+bd.getWriter());
			System.out.println("글내용:"+bd.getContent());
			
		}
	}



	public void makeBoard(BoardDAOImpl board) {
		board.makeBoard();
	}
	
	
}
