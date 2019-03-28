<%@ page import="Utils.CookieHelper" %>
<%@ page import="DTO.Customer" %>
<%@ page import="Common.MessageConstant" %>
<%@ page import="Common.UrlConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%
  String userNameCookie = CookieHelper.getCookie("userName", request);
  Customer customer = (Customer) request.getAttribute("customer");
  String nextId = (String) request.getAttribute("nextCusId");
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
      position: absolute;
      bottom: 0;
      left: 0;
      width: 99%;
    }

    .top-msg {
      display: flex;
      justify-content: space-between;
    }

    .frm {
      padding-left: 20px;
      display: flex;
      align-items: center;
      background-color: #3366ff;
      height: 30px;
    }

    .frm-ct {
      display: flex;
      align-items: center;
      justify-content: center;
      flex-direction: column;
    }

    .frm-ct > div > button {
      margin-left: 20px;
      margin-right: 20px;
    }

    .frm-ct > div > input {
      margin-left: 20px;
      margin-right: 20px;
    }

    .lblErrorMessage {
      color: red;
    }

    .msg-ct {
      display: flex;
      align-items: center;
      justify-content: center;
    }
  </style>
</head>
<%
  String screenName = (String) request.getAttribute("screenName");
%>
<body>
  <div>

    <div>
      <h1 class="tn-sl">Training</h1>
        <hr>
        <p class="lg-t">Login > Search Customer > Edit Customer Infor</p>
    </div>
    <br>
    <div class="top-msg">
      <div id="lblUserName">Welcome <%=userNameCookie%></div>
      <div><a id="llblLogout" href="<%= request.getContextPath() %>/logout">Logout</a></div>
    </div>
    <br>

    <div class="frm">
      <div id="lblScreenName">
        <%= screenName %>
      </div>
    </div>

    <br>

    <div class="msg-ct">
      <p class="lblErrorMessage" id="lblErrorMessage"></p>
    </div>

    <form class="frm-ct" method="POST">
      <table>
        <tr>
          <td>
            <p >Customer Id</p>
          </td>
          <td>
            <%if(customer != null) {%>
              <label id="lblCustomerID"><%= customer.getCustomerId()%></label>
            <% } if(nextId != null) {%>
              <label id="lblCustomerID"><%= nextId%></label>
            <% } %>
          </td>
        </tr>
        <tr>
          <td>
            <p>Customer Name</p>
          </td>
          <td>
            <input id="txtCustomerName" maxlength="50" value="<%=customer != null ? customer.getCustomerName() : ""%>" type="text">
          </td>
        </tr>
        <tr>
          <td>
            <p>Sex</p>
          </td>
          <td>
            <select id="cboSex">
              <option value=""></option>
              <option <%= customer != null && customer.getSex().equals("0") ? "selected" : ""%> value="0">Male</option>
              <option <%= customer != null && customer.getSex().equals("1") ? "selected" : ""%> value="1">Female</option>
            </select>
          </td>
        </tr>
        <tr>
          <td>
            <p>Birthday</p>
          </td>
          <td>
            <input id="txtBirthday" maxlength="10" value="<%= customer != null ? customer.getBirthday() : ""%>" type="text">
          </td>
        </tr>
        <tr>
          <td>
            <p>Email</p>
          </td>
          <td>
            <input id="txtEmail" maxlength="40" value="<%= customer != null ? customer.getEmail() : ""%>" type="text">
          </td>
        </tr>
        <tr>
          <td>
            <p>Address</p>
          </td>
          <td>
            <textarea id="txaAddress" maxlength="256" rows="3" cols="50"><%= customer != null ? customer.getAddress() : ""%></textarea>
          </td>
        </tr>
      </table>
      <div>
        <input type="button" id="btnSave" value="Save" >
        <button id="btnSave" onclick="buttonClearClick(this)">Clear</button>
      </div>
    </form>


    <footer>
      <div>
        <hr>
        <p class="lg-t">Copyright (c) 2000-2008 FUJINET. All Rights Reserved.</p>
      </div>
    </footer>
  </div>
</body>
<script type="text/javascript">
    window.onload = function() {

      var btnSave = document.getElementById("btnSave");

      btnSave.addEventListener("click", function (ev) {
        ev.preventDefault();



        var lblErrorMessage = document.getElementById("lblErrorMessage");
        var txtBirthday = document.getElementById("txtBirthday").value;
          if(!validateDatetime(txtBirthday) || txtBirthday.length == 0) {
              lblErrorMessage.innerText = "<%= MessageConstant.VALIDATE_DATETIME %>";
              return;
          }

          var txtEmail = document.getElementById("txtEmail").value;
          if(!validateEmail(txtEmail) || txtEmail.length == 0){
            lblErrorMessage.innerText = "<%= MessageConstant.VALIDATE_MAIL %>";
            return;
          }

          var txtCustomerName = document.getElementById("txtCustomerName").value;
          if(txtCustomerName.length == "") {
            return;
          }

          var txaAddress = document.getElementById("txaAddress").value;
          if(txaAddress.length == "") {
            return;
          }

          var selector = document.getElementById("cboSex");
          var cboSex = selector[selector.selectedIndex].value;
          if(cboSex.length == "") {
            return;
          }

          var type = "<%= request.getAttribute("type") %>";
          console.log(type);
          var lblCustomerID = document.getElementById("lblCustomerID").textContent;
          var txtCustomerName = document.getElementById("txtCustomerName").value;
          if(type == "edit") {
              var parameter = "type=edit&bd=" + txtBirthday + "&email="+ txtEmail + "&sex=" + cboSex + "&address=" + txaAddress + "&id=" + lblCustomerID + "&name=" + txtCustomerName;
              ajaxCustomer("POST", "<%= request.getContextPath() + UrlConstant.URL_EDIT %>", parameter);
          } else {
              var parameter = "type=insert&bd=" + txtBirthday + "&email="+ txtEmail + "&sex=" + cboSex + "&address=" + txaAddress + "&id=" + lblCustomerID + "&name=" + txtCustomerName;
              ajaxCustomer("POST", "<%= request.getContextPath() + UrlConstant.URL_EDIT %>", parameter);
          }
      });
    }

    function buttonClearClick(e) {
        e.preventDefault();
        document.getElementById("lblErrorMessage").innerText = "";
        document.getElementById("txtCustomerName").value = "";
        document.getElementById("txtBirthday").value = "";
        document.getElementById("txtEmail").value = "";
        document.getElementById("txaAddress").value = "";

    }

    function validateDatetime(str) {
        var regex = /([12]\d{3}\/(0[1-9]|1[0-2])\/(0[1-9]|[12]\d|3[01]))/;
        return str.match(regex);
    }

    function validateEmail(email) {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return email.match(re);
    }

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

    function ajaxCustomer(method, url, parameters) {
        var http = new getXMLHttpRequestObject();
        http.open(method, url, true);
        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        http.send(parameters);
        http.onreadystatechange = function (e) {
            if(http.readyState == 4 && http.status == 200) {
                alert(http.responseText);
                // console.log(e)	;
                // document.getElementById("response-text").innerHTML = http.responseText;
            }
        }
    }

</script>
</html>