<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Beginning of Import class --%>
<%@ page import="DTO.User" %>

<%-- Ending of Import class --%>

<%
	// Get all attribute from login servlet
	String err =  (String)request.getAttribute("err"); // get error response from LoginController
	User user = (User)request.getAttribute("user"); // get user value response from LoginController
%>

<!DOCTYPE html>
<html>
  <head>
    <style>
      
      body {
        background-color:#ccffff;
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
        color:#ff0000;
      }

      .lg-t {
        margin-top: 0px;
        padding-top: 0px;
      }

      .msg-ct {
        text-align: center;
      }

      .lg-ct {
        color: #3366ff;
        font-weight: bold;
      }

      .err-ct{
        color: #ff0000;
      }

      .f-center {
        display: flex;
        flex-direction: column;
        align-items: center;
      }

      .btn-ct {
        display: flex;
        justify-content: center;
      }

      .sts {
        margin-left: 20px;
        margin-right: 20px;
      }

      .btn-top {
        margin-top:20px;
      }

      footer {
        position: absolute;
        bottom: 0;  
        width: 98%;      
      }

    </style>
  </head>
  <body>
    <div>
      <div>
        <h1 class="tn-sl">Training</h1>
        <hr>
        <p class="lg-t">Login</p>
      </div>

      <div class="msg-ct">
        <p class="lg-ct">LOGIN</p>
        <p class="err-ct" id="lb-error"><%= err %></p>      
      </div>

      <div>
        <form class="f-center" action="login" method="POST">
          <div>
            <table>
              <tr>
                <td>User Id:</td>
                <td>
                  <%-- pass userid value, set max length is 8 --%>
                  <input type="text" size="25" value="<%= user.getUserId() %>" name="userId" id="userId" maxlength="8" >
                </td>
              </tr>
              <tr>
                <td>Password:</td>
                <td>
                  <%-- pass password value, set max length is 8 --%>
                  <input type="password" size="25" value="<%= user.getPassword()  %>" name="password" id="password" maxlength="8" >
                </td>
              </tr>            
            </table>
          </div>
          <div class="btn-top">
            <div class="btn-ct">
              <input class="sts" type="submit" id="btn-login" value="Login" >
              <button class="sts" id="btn-clear">Clear</button>
            </div>
          </div>          
        </form>
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
		// Get button clear in HTML
		var btnClear = document.getElementById("btn-clear");
		// Add event click for button clear
		btnClear.addEventListener("click", function(e) {
			// Prevent it auto reload page
			e.preventDefault();
			// clear all data
			document.getElementById("userId").value =
			document.getElementById("password").value =
			document.getElementById("lb-error").innerHTML = "";
		});
	}
  </script>
</html>