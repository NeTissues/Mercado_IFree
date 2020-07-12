<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cadastro</title>
	</head>
	<body>
		 <h1>Cadastro de Usuário</h1>
          <c:if test="${mensagens.existeErros}">
              <div id="erro">
                  <ul>
                      <c:forEach var="erro" items="${mensagens.erros}">
                          <li> ${erro} </li>
                       </c:forEach>
                  </ul>
              </div>
          </c:if>
          <form method="post" action="index.jsp">
              <table>
                  <tr>
                      <th>Login: </th>
                      <td><input type="text" name="login"
                                 value="${param.login}"/></td>
                  </tr>
                  <tr>
                      <th>Senha: </th>
                      <td><input type="password" name="senha" /></td>
                  </tr>
                  <tr>
                      <td colspan="2"> 
                          <input type="submit" name="bOK" value="Entrar"/>
                      </td>
                  </tr>
              </table>
          </form>
	</body>
</html>