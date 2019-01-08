package in.strollup.fb.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CurrencyConvert
 */
public class CurrencyConvert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrencyConvert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String fromCur = request.getParameter("from");
		String toCur = request.getParameter("to");
		System.out.println("converting currency from : "+fromCur+" to "+toCur);
		double result = getCurrency(fromCur, toCur);
		response.getWriter().println("converting currency from : "+fromCur+" to "+toCur);
		if(result == -1)
			response.getWriter().println("Invalid Currency Code");
		else
			response.getWriter().println(result);
	}
	
	public double getCurrency(String fromCur, String toCur) throws IOException {
		System.out.println("inside getCurrency :"+fromCur+toCur);
		fromCur = fromCur.trim();
		toCur = toCur.trim();
		String output = "test";
		try {
            URL url = new URL("https://api.exchangeratesapi.io/latest");//your url i.e fetch data from .
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            output = br.readLine();
//            System.out.println(output);
            conn.disconnect();
            
            String fromTmp = output.substring(output.indexOf(fromCur)+4);
    		String from = fromTmp.substring(1, fromTmp.indexOf(",")-1);
    		

    		String toTmp = output.substring(output.indexOf(toCur)+4);
    		String to = toTmp.substring(1, toTmp.indexOf(",")-1);
    		
    		
    		System.out.println(from);
    		System.out.println(to);
    		
    		double toInt = Double.parseDouble(to);
    		double fromInt = Double.parseDouble(from);
    		return toInt/fromInt;

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
            return -1;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
