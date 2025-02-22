package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private Statement statement;
	
    public Register() {
        super();
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try{
			Class.forName(config.getInitParameter("databaseDriver"));
			connection = (Connection) DriverManager.getConnection(
					config.getInitParameter("databaseName"),
					config.getInitParameter("username"),
					config.getInitParameter("password"));
			statement = (Statement)connection.createStatement();
		}catch(Exception exception){
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try{
				statement.close();
				connection.close();
		}catch(SQLException sqlException){
			sqlException.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter saida = response.getWriter();
		doGet(request, response);
		
		saida.println("<html>");
		saida.println("<head>");
		int value = Integer.parseInt(request.getParameter("animal"));
		String sql;
		try{
			sql="UPDATE surveyresults SET votes = votes + 1" + "WHERE id = " + value;
			ResultSet totalRS = statement.executeQuery(sql);
			totalRS.next();
			int total = totalRS.getInt(1);
			
			sql = "SELECT surveyoption, votes, id FROM surveyresults " + "ORDER BY id";
			ResultSet resultRS = statement.executeQuery(sql);
			
			saida.println("<title>Obrigado!!!</title>");
			saida.println("</head>");
			saida.println("<body>");
			saida.println("<p>Obrigado pela párticiapação!</p>");
			saida.println("<br/>Resultados:</p><pre>");
			
			int votes;
			while(resultRS.next()){
				saida.print(resultRS.getString(1));
				saida.print(": ");
				votes = resultRS.getInt(2);
				saida.printf("%.2f", (double)votes/total *100);
				saida.print("% respostas: ");
				saida.println(votes);
			}
			
			resultRS.close();
			
			saida.print("Total de respostas: ");
			saida.print(total);
			
			saida.println("</pre></body></html>");
			saida.close();
		}catch(SQLException sqlException){
			sqlException.printStackTrace();
			saida.println("<title> Erro! </title>");
			saida.println("</head>");
			saida.println("<body><p>Ocorreu um erro no banco de dados. ");
			saida.println("Tente novamente mais tarde.</p></body></html>");
			saida.close();
		}
		}
		
	}
