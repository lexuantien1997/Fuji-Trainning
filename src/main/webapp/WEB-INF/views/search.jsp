<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@ page import="Utils.CookieHelper" %>
<%@ page import="Common.MessageConstant" %>
<%
  String userNameCookie = CookieHelper.getCookie("userName", request);
%>
<head>
  <style>
    body {
      background-color: #ccffff;
      margin-left: 20px;
      margin-right: 20px;
    }

    p {
      padding: 0px;
      margin: 0px;
    }

    hr {
      color: dimgray !important;
      background-color: dimgray !important;
      border: solid 1px dimgray !important;
    }

    .tn-sl {
      color: #ff0000;
    }

    .lg-t {
      margin-top: 0px;
      padding-top: 0px;
    }

    footer {
      position: relative;
      bottom: 0;
      left: 0;
      width: 99%;
    }

    .top-msg {
      display: flex;
      justify-content: space-between;
    }

    .rect {
      height: 25px;
      color: #3366ff !important;
      background-color: #3366ff !important;
      border: solid 1px #3366ff !important;

    }

    .frm {
      display: flex;
      justify-content: space-around;
      align-items: center;
      background-color:#ffff99; 
      height: 50px;
    }

    .frm > div {
      display: flex;
    }

    .btn-cl {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .btn-cl > div > p {
      display: inline;
    }

    .tbl-header{
      background-color: #339966;
    }

    .tbl-header > table > thead > tr > th {
      padding: 20px 15px;
      text-align: left;
      font-weight: 500;
      font-size: 12px;
      text-transform: uppercase;
    }
    .tbl-content > table > tbody > tr > td {
      padding: 15px;
      text-align: left;
      vertical-align:middle;
      font-weight: 300;
      font-size: 12px;
      border-bottom: solid 1px rgba(255,255,255,0.1);
    }

    .tbl-content{
      height:300px;
      overflow-x:auto;
      margin-top: 0px;
      border: 1px solid rgba(255,255,255,0.3);
    }

    .tbl-content > table {
      width:100%;
      table-layout: fixed;
      border: solid 2px #339966;
    }

    .tbl-header > table {
      width:100%;
      table-layout: fixed;
    }

  </style>
</head>

<body>
  <div>

    <div>
      <h1 class="tn-sl">Training</h1>
        <hr>
        <p class="lg-t">Login > Search Customer</p>
    </div>
    <br>
    <div class="top-msg">
      <div id="lblUserName">Welcome <%=userNameCookie%></div>
      <div><a id="llblLogout" href="<%= request.getContextPath() %>/logout">Logout</a></div>
    </div>
    <br>
    <hr class="rect">
    <br>
    <form class="frm">
      <div>
        <div>
          <table>
            <tr>
              <td>
                <p>Customer Name</p>
              </td>
              <td>
                <%--<input id="txtCustomerName" value="<%=cusName%>" type="text" maxlength="50">--%>
                  <input id="txtCustomerName" type="text" maxlength="50">
              </td>
            </tr>
          </table>
        </div>
        <div>
          <table>
            <tr>
              <td>
                <p>Sex</p>
              </td>
              <td>
              	<select id="cboSex">
                  <%--<% if(sex!=null && sex.length() == 0) { %>--%>
                    <%--<option value="" <%= sex != null && sex.length() == 0 ? " selected" : ""%> ></option>--%>
                    <option value=""></option>
                  <%--<% } else { %>--%>
                    <%--<option value=""></option>--%>
                  <%--<% } %>--%>

                  <%--<% if(sex!=null && sex.equals("0")) { %>--%>
                  <%--<option value="0" <%= sex != null && sex.equals("0") ? " selected" : ""%> >Male</option>--%>
                    <option value="0">Male</option>
                  <%--<% } else { %>--%>
                  <%--<option value="0">Male</option>--%>
                  <%--<% } %>--%>

                  <%--<% if(sex!=null && sex.equals("1")) { %>--%>
                  <%--<option value="1" <%= sex != null && sex.equals("1") ? " selected" : ""%> >Female</option>--%>
                    <option value="1">Female</option>
                  <%--<% } else { %>--%>
                  <%--<option value="1">Female</option>--%>
                  <%--<% } %>--%>
				        </select>
              </td>
            </tr>
          </table>
        </div>
        <div>
          <table>
            <tr>
              <td>
                <p>Birthday</p>
              </td>
              <td>
                <%--<input id="txtBirthdayForm" value = "<%=bdFrom%>" type="text" maxlength="10">--%>
                <input id="txtBirthdayForm" type="text" maxlength="10">
              </td>
              <td>
                <p>~</p>
              </td>
              <td>
                <%--<input id="txtBirthdayTo" value = "<%=bdTo%>" type="text" maxlength="10">--%>
                <input id="txtBirthdayTo" type="text" maxlength="10">
              </td>
            </tr>
          </table>
        </div>

      </div>
      <div>
        <button id="btnSearch">Search</button>
      </div>
    </form>

    <div id="response-text">
      <%@ include file="SearchTable.jsp"%>
    </div>



    <footer>
      <div>
        <hr>
        <p class="lg-t">Copyright (c) 2000-2008 FUJINET. All Rights Reserved.</p>
      </div>
    </footer>
  </div>
</body>
  <script type="text/javascript">
	// Waiting window load success
	window.onload = function() {
		console.log("ready");

    ajaxSearchTable("POST", "<%= request.getContextPath() + UrlConstant.URL_SEARCH %>","type=all");

		var btnSearch = document.getElementById("btnSearch");

    btnSearch.addEventListener("click", function (ev) {
      ev.preventDefault();

      var txtBirthdayForm = document.getElementById("txtBirthdayForm").value;
      var txtBirthdayTo = document.getElementById("txtBirthdayTo").value;
      var err = "";
      if(!validateDatetime(txtBirthdayForm) && txtBirthdayForm!= "") {
          err = "<%= MessageConstant.VALIDATE_BDFROM %>";
      }

      if(!validateDatetime(txtBirthdayTo) && txtBirthdayTo!="") {
          err += "<%= MessageConstant.VALIDATE_BDTO %>";
      }

      if(err) { alert(err); return; }

      if(new Date(txtBirthdayForm) > new Date(txtBirthdayTo)) {
          alert("<%= MessageConstant.RANGE_OUTPUT_BIRTHDAY %>");
      }

      var selector = document.getElementById("cboSex");
      var cboSex = selector[selector.selectedIndex].value;
      var txtCustomerName = document.getElementById("txtCustomerName").value;

      var parameters = "type=search&cusName=" + txtCustomerName + "&sex=" + cboSex + "&bdFrom="  + txtBirthdayForm + "&bdTo=" + txtBirthdayTo;
      ajaxSearchTable("POST", "<%= request.getContextPath() + UrlConstant.URL_SEARCH %>",parameters);
      document.getElementById("chkAll").checked = false;
    });

	};

  function CheckUncheckAll(chkAll) {
    //Fetch all rows of the Table.
    var rows = document.getElementById("tbl-search").rows;

    //Execute loop on all rows excluding the Header row.
    for (var i = 0; i < rows.length; i++) {
        rows[i].getElementsByTagName("INPUT")[0].checked = chkAll.checked;
    }
  };

  function CheckUncheckHeader() {
    //Determine the reference CheckBox in Header row.
    var chkAll = document.getElementById("chkAll");

    //By default set to Checked.
    chkAll.checked = true;

    //Fetch all rows of the Table.
    var rows = document.getElementById("tbl-search").rows;

    //Execute loop on all rows excluding the Header row.
    for (var i = 0; i < rows.length; i++) {
      if (!rows[i].getElementsByTagName("INPUT")[0].checked) {
        chkAll.checked = false;
        break;
      }
    }
  };

	function getXMLHttpRequestObject() {
	  var xmlhttp;	
	  if (!xmlhttp && typeof XMLHttpRequest != 'undefined') {
	    try {
	      xmlhttp = new XMLHttpRequest();
	    } catch (e) {
	      xmlhttp = false;
	    }
	  }
	  return xmlhttp;
	}

  function validateDatetime(str) {
      var regex = /([12]\d{3}\/(0[1-9]|1[0-2])\/(0[1-9]|[12]\d|3[01]))/;
      return str.match(regex);
  }

	function ajaxSearchTable(method, url, parameters) {
		var http = new getXMLHttpRequestObject();
		http.open(method, url, true);
		http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		http.send(parameters);
		http.onreadystatechange = function (e) {
		  if(http.readyState == 4 && http.status == 200) {
        console.log(e)	;
		    document.getElementById("response-text").innerHTML = http.responseText;
      }
    }
	}

	function buttonNextClick(e) {
    ajaxSearchTable("POST","<%= request.getContextPath() + UrlConstant.URL_SEARCH %>","type=btnNext");
  }

  function buttonPrevClick(e) {
    ajaxSearchTable("POST", "<%= request.getContextPath() + UrlConstant.URL_SEARCH %>","type=btnPrev");
  }

  function buttonFirstClick(e) {
    ajaxSearchTable("POST", "<%= request.getContextPath() + UrlConstant.URL_SEARCH %>","type=btnFirst");
  }

  function buttonLastClick(e) {
    ajaxSearchTable("POST", "<%= request.getContextPath() + UrlConstant.URL_SEARCH %>","type=btnLast");
  }

  function buttonDeleteClick() {
    var rows = document.getElementById("tbl-search").rows;
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        if(rows[i].getElementsByTagName("INPUT")[0].checked == true)
          ids.push(rows[i].cells[1].getElementsByTagName("a")[0].textContent);
    }

    if(ids.length == 0) {
      alert("<%= MessageConstant.NO_ROW2DELETE%>");
    } else {
      ajaxSearchTable("POST", "<%= request.getContextPath() + UrlConstant.URL_SEARCH %>", "type=delete&ids=" + ids);
    }

  }



  </script>
</html>