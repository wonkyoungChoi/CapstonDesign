<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.sql.*"
    pageEncoding="UTF-8"%>
    
    <% request.setCharacterEncoding("UTF-8"); %>
    
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 등록</title>
</head>
<body>
<div>
<h2>닉네임 변경</h2>
<hr>
<form method="post" name="form1">
	<table border="1" cellspacing="1" cellpadding="5">
	
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" size=20></td>
		</tr>
		<tr>
			<td>닉네임</td>
			<td><input type="text" name="nick" size=20></td>
		</tr>

		<tr>
			<td colspan="2" align="center">
				<input type="submit" name="Submit" value="로그인">
				<input type="reset" name="Cancel" value="취소"></td>
		</tr>
	</table>
</form>
<hr>
</div>

<%
	//데이터베이스 연결관련 변수 선언
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	ResultSet rs = null;
	
	String nick, title;

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";

	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
				
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		String sql = "select title, watchnick from group_buying";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		title = request.getParameter("title");
	
		
		while(rs.next()) {
			if(title !=null) {
				if(title.equals(rs.getString("title"))){
					String sql1 = "update group_buying SET watchnick = ? WHERE title = ?";
					pstmt1 = conn.prepareStatement(sql1);
					pstmt1.setString(1, rs.getString("nick") + "| " +request.getParameter("nick"));
					pstmt1.setString(2, request.getParameter("title"));
					pstmt1.executeUpdate();
					pstmt1.close();
				} else {
					out.print("없는게시판");
				}
			}
			
		}
		pstmt.close();
		
			
		
	}
	catch(Exception e) {
		System.out.println(e);
	}
%>

