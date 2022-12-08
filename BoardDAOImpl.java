package com.lec.ex02_board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BoardDAOImpl implements BoardDAOService {
	
	private Connection conn=null;
	private PreparedStatement pstmt =null;
	private ResultSet rs =null;
	
	final static private BoardDAOImpl insntance = new BoardDAOImpl();
	
	//객체를 하나만 만들려고하는 거잖아요
	
	//1.생성자를 막는다
	//2.
	
	
	private BoardDAOImpl() {
		// s
	}
	
	static public BoardDAOImpl getIstance() {
		return insntance;
	}
	
	private BoardVO inputBoard() {
		
		BoardVO bd = new BoardVO();
		
		String subject = JOptionPane.showInputDialog("글제목을 입력하세요!");
		System.out.println(subject);
		String writer = JOptionPane.showInputDialog("작성자를 입력하세요");
		System.out.println(writer);
		String content = JOptionPane.showInputDialog("내용을 입력하세요!");
		System.out.println(content);
		
		bd.setSubject(subject);
		bd.setWriter(writer);
		bd.setContent(content);
		
		return bd;
				
	}
	
	@Override
	public void makeBoard() {
		System.out.println("게시글 진입 성공");
		BoardVO bd = inputBoard();
		
		
		ConnectionFactory cf = new ConnectionFactory();
		String insert = cf.getInsert();
		System.out.println(insert);
		
		try {
			conn = cf.getConnection();
			pstmt=conn.prepareCall(insert);		
			pstmt.setString(1, bd.getSubject());
			pstmt.setString(2, bd.getWriter());
			pstmt.setString(3, bd.getContent());
			
			System.out.println(bd.getSubject());
			System.out.println(bd.getWriter());
			System.out.println(bd.getContent());
			
			int row = pstmt.executeUpdate();
			
			System.out.println("게시글"+ row+"건이 성공적으로 등록됐습니다");
			
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

	}
	
	
	

	@Override
	public BoardVO contentView(int bno) {
			BoardVO bd = new BoardVO();
			ConnectionFactory cf = new ConnectionFactory();
			String sql = cf.getSelect()+" where bno="+bno;
			
			try {
				conn = cf.getConnection();
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					bd.setBno(rs.getInt(1));
					bd.setSubject(rs.getString("subject"));
					bd.setWriter(rs.getString("writer"));
					bd.setContent(rs.getString("content"));
				}else {
					System.out.println(bno+"가 없습니다");
				}
			
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		return bd;
	}

	@Override
	public BoardVO updateBoard(int bno) {
		BoardVO bd = new BoardVO();
		ConnectionFactory cf = new ConnectionFactory();
		String sql = cf.getUpdate();
		try {
			conn = cf.getConnection();
			bd.setBno(bno);
			bd.setSubject(JOptionPane.showInputDialog("변경할 제목!"));
			bd.setContent(JOptionPane.showInputDialog("변경할 내용!"));
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bd.getSubject());
			pstmt.setString(2, bd.getContent());
			pstmt.setInt(3, bno);
			int i = pstmt.executeUpdate();
			System.out.println("글번호"+bno+"게시글이 수정됐습니다");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if(pstmt!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
//		update = update board set subject=?, content=? where bno=?
		
		
		return bd;

	}

	@Override
	public BoardVO deleteBoard(int bno) {
		BoardVO bd = new BoardVO();
		ConnectionFactory cf = new ConnectionFactory();
		try {
			conn = cf.getConnection();
			String sql = cf.getDelete();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			
			int i = pstmt.executeUpdate();
			
			if(i>0) {System.out.println(bno+"가 삭제됐습니다!");}
			else {System.out.println("해당하는 글번호가 없습니다!");}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(pstmt!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		

		return bd;
	}

	@Override
	public ArrayList<BoardVO> listBoard()  {
		
		ConnectionFactory cf = new ConnectionFactory(); //properties 텍스트를 읽어옴
		
		ArrayList<BoardVO> boardList = new ArrayList(); //
		
		
		try {
			conn=cf.getConnection();
			String select = cf.getSelect();
			pstmt = conn.prepareStatement(select);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {			
				BoardVO bd = new BoardVO();
				bd.setBno(rs.getInt(1));
				bd.setSubject(rs.getString("subject"));
				bd.setWriter(rs.getString("writer"));
				bd.setContent(rs.getString("content")); //1줄을 boardVO
				boardList.add(bd); // 
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
		
		return boardList;
	}

	@Override
	public ArrayList<BoardVO> findBySubject(String subject) {
		ArrayList<BoardVO> bdl = new ArrayList(); 
		ConnectionFactory cf = new ConnectionFactory();

		try {
			conn = cf.getConnection();
			String sql = cf.getSelect()+" where subject like '%"+subject+"%' order by bno desc"; // 프로퍼티스의 value 값을 가져오고 거기에 스트링을 덧붙였음
			pstmt = conn.prepareStatement(sql); //덧붙인 sql문을 실행
			rs = pstmt.executeQuery();
				
//			if(rs.next()) {
//				BoardVO bd = new BoardVO(); 
//				bd.setBno(rs.getInt(1));
//				bd.setSubject(rs.getString("subject"));
//				bd.setWriter(rs.getString("writer"));
//				bd.setContent(rs.getString("content"));
//				bdl.add(bd);
//				
//			}else {
//				
//			}

			while(rs.next()) {	
				BoardVO bd = new BoardVO(); 
				bd.setBno(rs.getInt(1));
				bd.setSubject(rs.getString("subject"));
				bd.setWriter(rs.getString("writer"));
				bd.setContent(rs.getString("content"));
				bdl.add(bd);
			}			
		
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return bdl;
	}

	@Override
	public ArrayList<BoardVO> findByWriter(String writer) {
		// TODO Auto-generated method stub
		ArrayList<BoardVO> bdl = new ArrayList();
		ConnectionFactory cf = new ConnectionFactory();

		try {
			conn = cf.getConnection();
			String sql = cf.getSelect()+" where writer like '%"+writer+"%' order by bno desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
				
		

			while(rs.next()) {	
				BoardVO bd = new BoardVO();
				bd.setBno(rs.getInt(1));
				bd.setSubject(rs.getString("subject"));
				bd.setWriter(rs.getString("writer"));
				bd.setContent(rs.getString("content"));
				bdl.add(bd);
			}
			
		
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return bdl;
	}

}
