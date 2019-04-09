<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="oracle.ucp.jdbc.*"%>




<html>
<head>
<title>test</title>
</head>
<body>
	Current time is <%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%> 
	</br>
	
	<%

		String jndi = request.getParameter("jndi");

		Connection connection = null;
		InitialContext ctx = new InitialContext();

		DataSource ods = (DataSource) ctx.lookup(jndi);

		if(ods !=  null){
			connection = ods.getConnection();
			System.out.println(connection);
		}
		
		

	%>
	connection=<%= connection %>	

	
	
   
</body>
</html>