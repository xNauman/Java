package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import setup.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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

            String dbip = request.getParameter("dbip");
            String ccip = request.getParameter("ccip");
            SetupControl ctr = new SetupControl(dbip, ccip);
            int[] mapList = ctr.getMapList();

            int trafFreq = 5;
            int flavour = 0;
            int mapID = 1;

            if (request.getParameter("rbg_Map") != null) {
                mapID = Integer.parseInt(request.getParameter("rbg_Map"));
            }

            int defType = -1;
            int[] busInService = new int[mapList[mapID - 1]];
            int[] dispatchFreq = new int[mapList[mapID - 1]];

            for (int i = 0; i < dispatchFreq.length; i++) {
                dispatchFreq[i] = 5;
            }

            String error = "";

            try {
                if (request.getParameter("but") != null) {
                    error = "";
                }
                if (request.getParameter("error") != null) {
                    error = request.getParameter("error");
                }
                if (request.getParameter("tb_trafLightFreq") != null) {
                    trafFreq = Integer.parseInt(request.getParameter("tb_trafLightFreq"));
                    if (trafFreq <= 0 || trafFreq > 30) {
                        error = "Error: Traffic Light Frequency not within range!";
                    }
                }
                if (request.getParameter("rbg_Flavour") != null) {
                    flavour = Integer.parseInt(request.getParameter("rbg_Flavour"));
                }

                for (int i = 0; i < busInService.length 
                        && i < request.getParameterValues("tb_Bus").length; i++) {
                    if (request.getParameterValues("tb_Bus") != null) {
                        busInService[i] = Integer.parseInt
                                (request.getParameterValues("tb_Bus")[i]);
                        if (busInService[i] < 0 || busInService[i] > 10) {
                            error = "Error: No. of Buses not within range!";
                        }
                    }
                }

                for (int i = 0; i < busInService.length 
                        && i < request.getParameterValues("tb_Bus").length; i++) {
                    if (request.getParameterValues("tb_DispatchFreq") != null) {
                        dispatchFreq[i] = Integer.parseInt
                                ((request.getParameterValues("tb_DispatchFreq"))[i]);
                    }
                    if (dispatchFreq[i] <= 0 || dispatchFreq[i] > 30) {
                        error = "Error: Dispatch Frequency not within range!";
                    }
                }

                if (request.getParameter("but") != null) {
                    if (error.equals("")) {
                        setup.SetupDataObj su = new setup.SetupDataObj();
                        Route[] routeList = new Route[busInService.length];
                        su.setMapID(mapID);
                        su.setFlavour(flavour);
                        su.setTrafFreq(trafFreq);

                        for (int i = 0; i < routeList.length; i++) {
                            routeList[i] = new Route();
                            routeList[i].setDispatchFreq(dispatchFreq[i]);
                            routeList[i].setNoOfBus(busInService[i]);
                        }

                        su.setRouteList(routeList);

                        if (request.getParameter("but").equals("bn_default")) {
                            defType = 0;
                            error = "";
                        } else if (request.getParameter("but").equals("bn_lastSaved")) {
                            defType = 1;
                            error = "";
                        } else if (request.getParameter("but").equals("bn_confirm")) {
                            ctr.sendtoDB(su, mapID);
                            response.sendRedirect("confirmed.html");
                        } else if (request.getParameter("but").equals("bn_save")) {
                            if (error.equals("")) {
                                ctr.setUserConfig(su);
                                defType = 1;
                            }
                        }
                        if (defType == 0) {
                            su = ctr.getSystemConfig(mapID);
                            trafFreq = su.getTrafFreq();
                            flavour = su.getFlavour();
                            for (int i = 0; i < mapList[mapID - 1]; i++) {
                                busInService[i] = su.getRouteList()[i].getNoOfBus();
                                dispatchFreq[i] = su.getRouteList()[i].getDispatchFreq();
                            }
                        }
                        if (defType == 1) {
                            su = ctr.getUserConfig(mapID);
                            trafFreq = su.getTrafFreq();
                            flavour = su.getFlavour();
                            for (int i = 0; i < mapList[mapID - 1]; i++) {
                                busInService[i] = su.getRouteList()[i].getNoOfBus();
                                dispatchFreq[i] = su.getRouteList()[i].getDispatchFreq();
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                error = "Error! Invalid input. Input must be a number.";
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            }
            String[] routeList = ctr.getRouteNameList(mapID);

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Setup</title>\n");
      out.write("        <!-- ***************** Javascript for Tooltip ******************** -->\n");
      out.write("        <script type=\"text/javascript\">            \n");
      out.write("            var dom = (document.getElementById) ? true : false;\n");
      out.write("            var ns5 = (!document.all && dom || window.opera) ? true: false;\n");
      out.write("            var ie5 = ((navigator.userAgent.indexOf(\"MSIE\")>-1) && dom) ? true : false;\n");
      out.write("            var ie4 = (document.all && !dom) ? true : false;\n");
      out.write("            var nodyn = (!ns5 && !ie4 && !ie5 && !dom) ? true : false;\n");
      out.write("            \n");
      out.write("            var origWidth, origHeight;\n");
      out.write("            if (nodyn) { event = \"nope\" }\n");
      out.write("            var tipFollowMouse= true;\n");
      out.write("            var tipWidth= 410;\n");
      out.write("            var offX= 20;\n");
      out.write("            var offY= 12; \n");
      out.write("            var tipFontFamily= \"Verdana, arial, helvetica, sans-serif\";\n");
      out.write("            var tipFontSize= \"8pt\";\n");
      out.write("            var tipFontColor= \"#000000\";\n");
      out.write("            var tipBgColor= \"#DDECFF\"; \n");
      out.write("            var tipBorderColor= \"#000080\";\n");
      out.write("            var tipBorderWidth= 3;\n");
      out.write("            var tipBorderStyle= \"ridge\";\n");
      out.write("            var tipPadding= 4;\n");
      out.write("            \n");
      out.write("            var messages = new Array();\n");
      out.write("            ");
 for (int i = 1; i <= mapList.length; i++) {
      out.write("\n");
      out.write("            messages[");
      out.print("" + (i - 1));
      out.write("] = new Array('image/Map");
      out.print("" + i);
      out.write(".jpg',\n");
      out.write("                'Map ");
      out.print("" + i);
      out.write(' ');
      out.write(':');
      out.write(' ');
      out.print("" + mapList[i - 1]);
      out.write(" Routes',\"black\", \"white\");\n");
      out.write("                ");
 }
      out.write("\n");
      out.write("                \n");
      out.write("                if (document.images) {\n");
      out.write("                    var theImgs = new Array();\n");
      out.write("                    for (var i=0; i<messages.length; i++) {\n");
      out.write("                        theImgs[i] = new Image();\n");
      out.write("                        theImgs[i].src = messages[i][0];\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                var startStr = '<table width=\"' + tipWidth + \n");
      out.write("                    '\"><tr><td align=\"center\" width=\"100%\"><img src=\"';\n");
      out.write("                var midStr = '\" border=\"0\"></td></tr><tr><td valign=\"top\">';\n");
      out.write("                var endStr = '</td></tr></table>';\n");
      out.write("                var tooltip, tipcss;\n");
      out.write("                function initTip() {\n");
      out.write("                    if (nodyn) return;\n");
      out.write("                    tooltip = (ie4)? document.all['tipDiv']: \n");
      out.write("                        (ie5||ns5)? document.getElementById('tipDiv'): null;\n");
      out.write("                    tipcss = tooltip.style;\n");
      out.write("                    if (ie4||ie5||ns5) {\t// ns4 would lose all this on rewrites\n");
      out.write("                        tipcss.width = tipWidth+\"px\";\n");
      out.write("                        tipcss.fontFamily = tipFontFamily;\n");
      out.write("                        tipcss.fontSize = tipFontSize;\n");
      out.write("                        tipcss.color = tipFontColor;\n");
      out.write("                        tipcss.backgroundColor = tipBgColor;\n");
      out.write("                        tipcss.borderColor = tipBorderColor;\n");
      out.write("                        tipcss.borderWidth = tipBorderWidth+\"px\";\n");
      out.write("                        tipcss.padding = tipPadding+\"px\";\n");
      out.write("                        tipcss.borderStyle = tipBorderStyle;\n");
      out.write("                    }\n");
      out.write("                    if (tooltip&&tipFollowMouse) {\n");
      out.write("                        document.onmousemove = trackMouse;\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                window.onload = initTip;\n");
      out.write("               \n");
      out.write("                var t1,t2;\t// for setTimeouts\n");
      out.write("                var tipOn = false;\t// check if over tooltip link\n");
      out.write("                function doTooltip(evt,num) {\n");
      out.write("                    if (!tooltip) return;\n");
      out.write("                    if (t1) clearTimeout(t1);\tif (t2) clearTimeout(t2);\n");
      out.write("                    tipOn = true;\n");
      out.write("                    // set colors if included in messages array\n");
      out.write("                    if (messages[num][2])\tvar curBgColor = messages[num][2];\n");
      out.write("                    else curBgColor = tipBgColor;\n");
      out.write("                    if (messages[num][3])\tvar curFontColor = messages[num][3];\n");
      out.write("                    else curFontColor = tipFontColor;\n");
      out.write("                    if (ie4||ie5||ns5) {\n");
      out.write("                        var tip = startStr + messages[num][0] + midStr + \n");
      out.write("                            '<span style=\"font-family:' + tipFontFamily + \n");
      out.write("                            '; font-size:' + tipFontSize + '; color:' + \n");
      out.write("                            curFontColor + ';\">' + messages[num][1] + '</span>' + endStr;\n");
      out.write("                        tipcss.backgroundColor = curBgColor;\n");
      out.write("                        tooltip.innerHTML = tip;\n");
      out.write("                    }\n");
      out.write("                    if (!tipFollowMouse) positionTip(evt);\n");
      out.write("                    else t1=setTimeout(\"tipcss.visibility='visible'\",100);\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                var mouseX, mouseY;\n");
      out.write("                function trackMouse(evt) {\n");
      out.write("                    standardbody=(document.compatMode==\"CSS1Compat\")? \n");
      out.write("                        document.documentElement : document.body \n");
      out.write("                        \n");
      out.write("                    mouseX = (ns5)? evt.pageX: window.event.clientX \n");
      out.write("                        + standardbody.scrollLeft;\n");
      out.write("                    mouseY = (ns5)? evt.pageY: window.event.clientY \n");
      out.write("                        + standardbody.scrollTop;\n");
      out.write("                    if (tipOn) positionTip(evt);\n");
      out.write("                }\n");
      out.write("     \n");
      out.write("                function positionTip(evt) {\n");
      out.write("                    if (!tipFollowMouse) {\n");
      out.write("                        standardbody=(document.compatMode==\"CSS1Compat\")? \n");
      out.write("                            document.documentElement : document.body\n");
      out.write("                        mouseX = (ns5)? evt.pageX: window.event.clientX \n");
      out.write("                            + standardbody.scrollLeft;\n");
      out.write("                        mouseY = (ns5)? evt.pageY: window.event.clientY \n");
      out.write("                            + standardbody.scrollTop;\n");
      out.write("                    }\n");
      out.write("                    // tooltip width and height\n");
      out.write("                    var tpWd = (ie4||ie5)? tooltip.clientWidth: tooltip.offsetWidth;\n");
      out.write("                    var tpHt = (ie4||ie5)? tooltip.clientHeight: tooltip.offsetHeight;\n");
      out.write("                    // document area in view (subtract scrollbar width for ns)\n");
      out.write("                    var winWd = (ns5)? window.innerWidth-20+window.pageXOffset: \n");
      out.write("                        standardbody.clientWidth+standardbody.scrollLeft;\n");
      out.write("                    var winHt = (ns5)? window.innerHeight-20+window.pageYOffset: \n");
      out.write("                        standardbody.clientHeight+standardbody.scrollTop;\n");
      out.write("                    // check mouse position against tip and window dimensions\n");
      out.write("                    // and position the tooltip \n");
      out.write("                    if ((mouseX+offX+tpWd)>winWd) \n");
      out.write("                        tipcss.left = mouseX-(tpWd+offX)+\"px\";\n");
      out.write("                    else tipcss.left = mouseX+offX+\"px\";\n");
      out.write("                    if ((mouseY+offY+tpHt)>winHt) \n");
      out.write("                        tipcss.top = winHt-(tpHt+offY)+\"px\";\n");
      out.write("                    else tipcss.top = mouseY+offY+\"px\";\n");
      out.write("                    if (!tipFollowMouse) t1=setTimeout(\"tipcss.visibility='visible'\",100);\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                function hideTip() {\n");
      out.write("                    if (!tooltip) return;\n");
      out.write("                    t2=setTimeout(\"tipcss.visibility='hidden'\",100);\n");
      out.write("                    tipOn = false;\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                document.write('<div id=\"tipDiv\" style=\"position:absolute; ' +\n");
      out.write("                    'visibility:hidden; z-index:100\"></div>')\n");
      out.write("                \n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("        <!-- *************** Javascript for Image Popup ****************** -->\n");
      out.write("        <style type=\"text/css\">\n");
      out.write("            \n");
      out.write("            #hintbox{ /*CSS for pop up hint box */\n");
      out.write("                position:absolute;\n");
      out.write("                top: 0;\n");
      out.write("                background-color: lightyellow;\n");
      out.write("                width: 150px; /*Default width of hint.*/ \n");
      out.write("                padding: 3px;\n");
      out.write("                border:1px solid black;\n");
      out.write("                font:normal 11px Verdana;\n");
      out.write("                line-height:18px;\n");
      out.write("                z-index:100;\n");
      out.write("                border-right: 3px solid black;\n");
      out.write("                border-bottom: 3px solid black;\n");
      out.write("                visibility: hidden;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .hintanchor{ /*CSS for link that shows hint onmouseover*/\n");
      out.write("                font-weight: bold;\n");
      out.write("                color: navy;\n");
      out.write("                margin: 3px 8px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("        <script type=\"text/javascript\">\n");
      out.write("            \n");
      out.write("            /***********************************************\n");
      out.write("            * Show Hint script- © Dynamic Drive (www.dynamicdrive.com)\n");
      out.write("            * This notice MUST stay intact for legal use\n");
      out.write("            * Visit http://www.dynamicdrive.com/ for this script and 100s more.\n");
      out.write("            ***********************************************/\n");
      out.write("            \n");
      out.write("            var horizontal_offset=\"9px\" //horizontal offset of hint box from anchor link\n");
      out.write("            \n");
      out.write("            /////No further editting needed\n");
      out.write("            \n");
      out.write("            var vertical_offset=\"0\"\n");
      out.write("            var ie=document.all\n");
      out.write("            var ns6=document.getElementById&&!document.all\n");
      out.write("            \n");
      out.write("            function getposOffset(what, offsettype){\n");
      out.write("                var totaloffset=(offsettype==\"left\")? what.offsetLeft : what.offsetTop;\n");
      out.write("                var parentEl=what.offsetParent;\n");
      out.write("                while (parentEl!=null){\n");
      out.write("                    totaloffset=(offsettype==\"left\")? \n");
      out.write("                    totaloffset+parentEl.offsetLeft : totaloffset+parentEl.offsetTop;\n");
      out.write("                    parentEl=parentEl.offsetParent;\n");
      out.write("                }\n");
      out.write("                return totaloffset;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function iecompattest(){\n");
      out.write("                return (document.compatMode && document.compatMode!=\"BackCompat\")? \n");
      out.write("                    document.documentElement : document.body\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function clearbrowseredge(obj, whichedge){\n");
      out.write("                var edgeoffset=(whichedge==\"rightedge\")? parseInt(horizontal_offset)*-1 \n");
      out.write("                    : parseInt(vertical_offset)*-1\n");
      out.write("                if (whichedge==\"rightedge\"){\n");
      out.write("                    var windowedge=ie && !window.opera? iecompattest().scrollLeft\n");
      out.write("                        + iecompattest().clientWidth-30 : window.pageXOffset\n");
      out.write("                        + window.innerWidth-40\n");
      out.write("                    dropmenuobj.contentmeasure=dropmenuobj.offsetWidth\n");
      out.write("                    if (windowedge-dropmenuobj.x < dropmenuobj.contentmeasure)\n");
      out.write("                        edgeoffset=dropmenuobj.contentmeasure+obj.offsetWidth\n");
      out.write("                            + parseInt(horizontal_offset)\n");
      out.write("                }\n");
      out.write("                else{\n");
      out.write("                    var windowedge=ie && !window.opera? iecompattest().scrollTop\n");
      out.write("                        + iecompattest().clientHeight-15 : window.pageYOffset\n");
      out.write("                        + window.innerHeight-18\n");
      out.write("                    dropmenuobj.contentmeasure=dropmenuobj.offsetHeight\n");
      out.write("                    if (windowedge-dropmenuobj.y < dropmenuobj.contentmeasure)\n");
      out.write("                        edgeoffset=dropmenuobj.contentmeasure-obj.offsetHeight\n");
      out.write("                }\n");
      out.write("                return edgeoffset\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showhint(menucontents, obj, e, tipwidth){\n");
      out.write("                if ((ie||ns6) && document.getElementById(\"hintbox\")){\n");
      out.write("                    dropmenuobj=document.getElementById(\"hintbox\")\n");
      out.write("                    dropmenuobj.innerHTML=menucontents\n");
      out.write("                    dropmenuobj.style.left=dropmenuobj.style.top=-500\n");
      out.write("                    if (tipwidth!=\"\"){\n");
      out.write("                        dropmenuobj.widthobj=dropmenuobj.style\n");
      out.write("                        dropmenuobj.widthobj.width=tipwidth\n");
      out.write("                    }\n");
      out.write("                    dropmenuobj.x=getposOffset(obj, \"left\")\n");
      out.write("                    dropmenuobj.y=getposOffset(obj, \"top\")\n");
      out.write("                    dropmenuobj.style.left=dropmenuobj.x-clearbrowseredge(obj, \n");
      out.write("                        \"rightedge\")+obj.offsetWidth+\"px\"\n");
      out.write("                    dropmenuobj.style.top=dropmenuobj.y-clearbrowseredge(obj, \n");
      out.write("                        \"bottomedge\")+\"px\"\n");
      out.write("                    dropmenuobj.style.visibility=\"visible\"\n");
      out.write("                    obj.onmouseout=hidetip\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function hidetip(e){\n");
      out.write("                dropmenuobj.style.visibility=\"hidden\"\n");
      out.write("                dropmenuobj.style.left=\"-500px\"\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function createhintbox(){\n");
      out.write("                var divblock=document.createElement(\"div\")\n");
      out.write("                divblock.setAttribute(\"id\", \"hintbox\")\n");
      out.write("                document.body.appendChild(divblock)\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            if (window.addEventListener)\n");
      out.write("                window.addEventListener(\"load\", createhintbox, false)\n");
      out.write("            else if (window.attachEvent)\n");
      out.write("                window.attachEvent(\"onload\", createhintbox)\n");
      out.write("            else if (document.getElementById)\n");
      out.write("                window.onload=createhintbox\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div style=\"text-align:center;\">\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            <img src=\"image/header.jpg\"/>\n");
      out.write("            <form action=\"index.jsp\" method=\"GET\" name=\"myform\">\n");
      out.write("                <b>Map: </b><a href=\"#\" class=\"hintanchor\" onMouseover=\"showhint\n");
      out.write("                    ('Mouseover the map thumbnails to see actual size. Map can ' \n");
      out.write("                        + 'be selected by clicking on the thumbnail. Currently '\n");
      out.write("                        + 'selected Map is Map ");
      out.print("" + mapID);
      out.write(".', this, event, \n");
      out.write("                        '300px')\">[?]</a>\n");
      out.write("                <input type=\"hidden\" name=\"rbg_Map\" value=\"");
      out.print("" + mapID);
      out.write("\"/>\n");
      out.write("                <input type=\"hidden\" name=\"dbip\" value=\"");
      out.print(dbip);
      out.write("\"/>\n");
      out.write("                <input type=\"hidden\" name=\"ccip\" value=\"");
      out.print(ccip);
      out.write("\"/>\n");
      out.write("                ");
 for (int i = 1; i <= mapList.length; i++) {
      out.write("\n");
      out.write("                ");
 if (mapID != i) {
      out.write(" <A HREF=\"javascript:document.myform.submit()\"\n");
      out.write("                    onmouseover=\"doTooltip(event,");
      out.print("" + (i - 1));
      out.write(")\" \n");
      out.write("                    onmouseout=\"hideTip()\" onclick=\"rbg_Map.value='");
      out.print("" + i);
      out.write("';\">\n");
      out.write("                    <img src=\"image/Map");
      out.print("" + i);
      out.write(".jpg\" alt=\"Map ");
      out.print("" + i);
      out.write("\" \n");
      out.write("                        border=\"0px\" width=\"100px\" align=\"middle\"/></a>\n");
      out.write("                ");
 } else {
      out.write("\n");
      out.write("                <img onmouseover=\"doTooltip(event,");
      out.print("" + (i - 1));
      out.write(")\" \n");
      out.write("                    onmouseout=\"hideTip()\" width=\"100px\" \n");
      out.write("                    src=\"image/Map");
      out.print("" + i);
      out.write(".jpg\" border=\"5px\" \n");
      out.write("                    alt=\"Map ");
      out.print("" + i);
      out.write(" Currently Selected\" align=\"middle\"/>\n");
      out.write("                ");
 }
            }
      out.write("\n");
      out.write("                <br/>\n");
      out.write("                <b>Flavour: <a href=\"#\" class=\"hintanchor\" onMouseover=\"showhint\n");
      out.write("                    ('Please choose a flavour for your bus simulation. ' + \n");
      out.write("                'Currently selected Flavour is ");
 if (flavour == 0) { out.print("Winter.");  
                } else { out.print("Urban."); } 
      out.write("', this, event, '150px')\">\n");
      out.write("                        [?]</a></b>\n");
      out.write("                <input type=\"hidden\" name=\"rbg_Flavour\" value=\"");
      out.print("" + flavour);
      out.write("\"/>\n");
      out.write("                <A HREF=\"javascript:document.myform.submit()\" \n");
      out.write("                    onclick=\"rbg_Flavour.value='0';\">\n");
      out.write("                    <img border=\"0\" src=\"image/Winter");
 if (flavour == 0) {
                out.print("Pressed"); }
      out.write(".jpg\" align=\"middle\" alt=\"Day\" width=\"200px\"></a>\n");
      out.write("                &nbsp;&nbsp;&nbsp;\n");
      out.write("                <A HREF=\"javascript:document.myform.submit()\" \n");
      out.write("                    onclick=\"rbg_Flavour.value='1';\"><img border=\"0\" \n");
      out.write("                    src=\"image/Urban");
 if (flavour == 1) {out.print("Pressed");}
      out.write(".jpg\"\n");
      out.write("                    align=\"middle\" alt=\"Urban\" width=\"200px\"></a>\n");
      out.write("                <br/>\n");
      out.write("                <b>Traffic Light Frequency:</b>\n");
      out.write("                <a href=\"#\" class=\"hintanchor\" onMouseover=\"showhint(\n");
      out.write("                'Please input a number between 1 - 10 for traffic light frequency.', \n");
      out.write("                this, event, '150px')\">[?]</a>\n");
      out.write("                <input type=\"text\" name=\"tb_trafLightFreq\" \n");
      out.write("                    value=\"");
      out.print("" + trafFreq);
      out.write("\" size=\"2\" />\n");
      out.write("                <p/>\n");
      out.write("                <font color=\"red\">");
      out.print(error);
      out.write("</font>\n");
      out.write("                <table border=\"1\" bordercolor=\"black\" width=\"400px\">\n");
      out.write("                    <thead>\n");
      out.write("                        <tr>\n");
      out.write("                            <th>Route</th>\n");
      out.write("                            <th>No. of Buses</th>\n");
      out.write("                            <th>Dispatch Frequency</th>\n");
      out.write("                        </tr>\n");
      out.write("                    </thead>\n");
      out.write("                    <tbody>\n");
      out.write("                        ");
 for (int i = 0; i < mapList[mapID - 1]; i++) {
      out.write("\n");
      out.write("                        <tr>                            \n");
      out.write("                            <td>Route ");
      out.print(routeList[i]);
      out.write("</td>\n");
      out.write("                            <input type=\"hidden\" name=\"routeName\" \n");
      out.write("                                value=\"");
      out.print(routeList[i]);
      out.write("\"/>\n");
      out.write("                            <td><input type=\"text\" name=\"tb_Bus\" \n");
      out.write("                                value=\"");
      out.print("" + busInService[i]);
      out.write("\" size=\"2\" />\n");
      out.write("                                <a href=\"#\" class=\"hintanchor\" onMouseover=\"showhint(\n");
      out.write("            'Please input a number between 0 - 10 indicating number of buses for ' \n");
      out.write("            + 'Route ");
      out.print(routeList[i]);
      out.write(".', this, event, '150px')\">[?]</a></td>\n");
      out.write("                            <td><input type=\"text\" name=\"tb_DispatchFreq\" \n");
      out.write("                            value=\"");
      out.print("" + dispatchFreq[i]);
      out.write("\" size=\"2\" />\n");
      out.write("                            <a href=\"#\" class=\"hintanchor\" onMouseover=\"showhint(\n");
      out.write("        'Please input a number between 1 - 30 indicating bus dispatch frequency ' \n");
      out.write("        + 'for Route ");
      out.print(routeList[i]);
      out.write(".', this, event, '150px')\">[?]</a></td>\n");
      out.write("                        </tr>\n");
      out.write("                        ");
 }
      out.write("\n");
      out.write("                    </tbody>\n");
      out.write("                </table>\n");
      out.write("                <p/>\n");
      out.write("                <input type=\"hidden\" name=\"but\"/>\n");
      out.write("                <a href=\"#\" class=\"hintanchor\" onMouseover=\"showhint(\n");
      out.write(" '1) Load system default values for flavour, traffic light frequency, ' \n");
      out.write(" + 'no. of buses and dispatch frequency for Map ");
      out.print("" + mapID);
      out.write(".<br/>' \n");
      out.write(" + '2) Load previously saved values for Map ");
      out.print("" + mapID);
      out.write(".<br/>'\n");
      out.write(" + '3) Save values for flavour, traffic light frequency, no. of buses and '\n");
      out.write(" + 'dispatch frequency for Map ");
      out.print("" + mapID);
      out.write(".', this, event, '400px')\">[?]</a>\n");
      out.write("                \n");
      out.write("                <A HREF=\"javascript:document.myform.submit()\" \n");
      out.write("                    onclick=\"but.value='bn_default';\"><img border=\"0\" \n");
      out.write("                    src=\"image/default.jpg\" alt=\"Default\" align=\"middle\"/></a>\n");
      out.write("                <A HREF=\"javascript:document.myform.submit()\" \n");
      out.write("                onclick=\"but.value='bn_lastSaved';\"><img border=\"0\" \n");
      out.write("                src=\"image/lastSaved.jpg\" alt=\"Last Saved\" align=\"middle\"/></a>\n");
      out.write("                <A HREF=\"javascript:document.myform.submit()\" \n");
      out.write("                onclick=\"but.value='bn_save';\"><img border=\"0\" \n");
      out.write("                src=\"image/save.jpg\" alt=\"Save\" align=\"middle\"/></a>\n");
      out.write("                <a href=\"index.jsp?dbip=");
      out.print(dbip);
      out.write("&ccip=");
      out.print(ccip);
      out.write("\"><img \n");
      out.write("                src=\"image/reset.jpg\" border=\"0\" alt=\"Reset\" align=\"middle\"/></a>\n");
      out.write("                <A HREF=\"javascript:document.myform.submit()\" \n");
      out.write("                onclick=\"but.value='bn_confirm';\"><img border=\"0\" \n");
      out.write("                src=\"image/confirm.jpg\" alt=\"Confirm\" align=\"middle\"/></a>\n");
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
