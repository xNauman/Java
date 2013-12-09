package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import setup.*;

public final class busFare_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
      out.write("\"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("\n");

            float[] busFare;
            int[] numPass, numRoute;
            String[] routeName;
            String dbip = request.getParameter("dbip");
            String ccip = request.getParameter("ccip");
            SetupControl ctr = new SetupControl(dbip, ccip);

            //retrieve selected mapID from CC via SetupControl/SetupRMI class
            int mapID = ctr.getMapID();

            /* if bus fare values in the form is not blank, busFare[] = values in form
            else busFare[] = values retrieved from internal database */
            if (request.getParameterValues("route") != null) {
                busFare = new float[request.getParameterValues("route").length];
                for (int i = 0; i < request.getParameterValues("route").length; i++) {
                    busFare[i] = Float.parseFloat(request.getParameterValues("route")[i]);
                }
            } else {
                busFare = ctr.getFare(mapID);
            }

            /* if save button is pressed, save values in internal database via 
            SetupControl/DBConnection 
            else if loadSaved button is pressed, load values from internal
            database via SetupControl/DBConnection */
            if (request.getParameter("but") != null) {
                if (request.getParameter("but").equals("bn_save")) {
                    ctr.setFare(busFare, mapID);
                } else if (request.getParameter("but").equals("bn_loadSaved")) {
                    busFare = ctr.getFare(mapID);
                }
            }

            /* numPass[].length = num of routes, numPass[] contains num of passengers
            per route */
            numPass = ctr.getNumOfPassenger();

            /* numBus[].length = num of map, numBus[] contains num of routes
            per map */
            numRoute = ctr.getMapList();

// retrieves name of each route
            routeName = ctr.getRouteNameList(mapID);

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Bus Fare Calculation</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div style=\"text-align:center\">\n");
      out.write("            <img src=\"image/busFare.jpg\"/>\n");
      out.write("            <form action=\"busFare.jsp\" method=\"GET\" name=\"myform\">\n");
      out.write("                <input type=\"hidden\" name=\"dbip\" value=\"");
      out.print(dbip);
      out.write("\"/>\n");
      out.write("                <input type=\"hidden\" name=\"ccip\" value=\"");
      out.print(ccip);
      out.write("\"/>\n");
      out.write("                Map Selected: \n");
      out.write("                <img src=\"image/Map");
      out.print("" + mapID);
      out.write(".jpg\" align=\"middle\" width=\"200px\"/>\n");
      out.write("                <p/>\n");
      out.write("                <table border=\"1\" bordercolor=\"black\">\n");
      out.write("                    <tr>\n");
      out.write("                        <th>Route</th>\n");
      out.write("                        <th>Bus Fare Per Pax</th>\n");
      out.write("                        <th>No. of Passengers</th>\n");
      out.write("                        <th>Total Fare Per Route</th>\n");
      out.write("                    </tr>\n");
      out.write("                    ");
 for (int i = 0; i < numRoute[mapID - 1]; i++) {
      out.write("\n");
      out.write("                    <tr>\n");
      out.write("                        <td>");
      out.print(routeName[i]);
      out.write("</td>\n");
      out.write("                        <td>$<input style=\"text-align:right;\" type=\"text\" \n");
      out.write("                                    name=\"route\" value=\"");
      out.print("" + busFare[i]);
      out.write("\"/></td>\n");
      out.write("                        <td>");
      out.print("" + numPass[i]);
      out.write("</td>\n");
      out.write("                        <td align=\"right\">$");
      out.print("" + (busFare[i] * numPass[i]));
      out.write("</td>\n");
      out.write("                    </tr>\n");
      out.write("                    ");
 }
      out.write("\n");
      out.write("                </table>\n");
      out.write("                ");

            float total = 0;
            for (int i = 0; i < numPass.length; i++) {
                total += busFare[i] * numPass[i];
            }
                
      out.write("\n");
      out.write("                <h4>Total Revenue: $");
      out.print("" + total);
      out.write("</h4>\n");
      out.write("                \n");
      out.write("                <a href=\"javascript:document.myform.submit()\" \n");
      out.write("                   onclick=\"but.value='bn_loadSaved';\">\n");
      out.write("                    <img border=\"0\" src=\"image/bfLoadSaved.jpg\" alt=\"Load Saved\"/></a>\n");
      out.write("                \n");
      out.write("                <a href=\"javascript:document.myform.submit()\" \n");
      out.write("                   onclick=\"but.value='bn_save';\">\n");
      out.write("                    <img border=\"0\" src=\"image/bfSave.jpg\" alt=\"Save\"/></a>\n");
      out.write("                \n");
      out.write("                <a href=\"javascript:document.myform.submit()\" \n");
      out.write("                   onclick=\"but.value='bn_calculate';\">\n");
      out.write("                    <img border=\"0\" src=\"image/bfCalculate.jpg\" alt=\"Calculate\"/></a>\n");
      out.write("                <input type=\"hidden\" name=\"but\" value=\"\"/>\n");
      out.write("            </form>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
