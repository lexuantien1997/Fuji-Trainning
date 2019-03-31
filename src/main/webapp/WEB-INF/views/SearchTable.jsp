<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="DTO.Customer" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="static com.sun.activation.registries.LogSupport.log" %>
<%@ page import="Common.UrlConstant" %>

<%
  String disableNext = "", disablePrev = "", disableLast = "", disableFirst = "", disableDelete = "";
  if(session.getAttribute("count") != null && session.getAttribute("current") != null ) {
    int countPage = (Integer) session.getAttribute("count");
    int currentPage = (Integer) session.getAttribute("current");

    log("count page=" + countPage);
    log("current page=" + currentPage);

    if(countPage > 1) {
      disableDelete = "";
      disableNext = "";
      if(currentPage <=1) {
        disablePrev = "disabled";
        disableFirst = "disabled";
      } else if(currentPage > 1 ) {
          if(currentPage < countPage) {
            disablePrev = "";
            disableFirst = "";
          }  else {
            disableNext = "disabled";
            disableLast = "disabled";
          }
      }
    } else {
      disableNext = "disabled";
      disablePrev = "disabled";
      disableLast = "disabled";
      disableFirst = "disabled";
      if(countPage == 0)
        disableDelete = "disabled";
    }

  }
%>
<%--1 2 3 4--%>
<div>
  <br>
  <div class="btn-cl">
    <div>
      <button id="btnFirst" <%= disableFirst %> onclick="buttonFirstClick(this)"><<</button>
      <button id="btnPrevious" <%= disablePrev %> onclick="buttonPrevClick(this)"><</button>
      <p>Previous</p>
    </div>
    <div>
      <p>Next</p>
      <button id="btnNext" <%= disableNext %> onclick="buttonNextClick(this)">></button>
      <button id="btnLast" <%= disableLast %> onclick="buttonLastClick(this)">>></button>
    </div>
  </div>

  <br>
  <div>
    <div class="tbl-header">
      <table cellpadding="0" cellspacing="0" border="0">
        <thead>
        <tr>
          <th><input type="checkbox" id="chkAll" onclick="CheckUncheckAll(this)"/></th>
          <th>Customer ID</th>
          <th>Customer Name</th>
          <th>Sex</th>
          <th>Birthday</th>
          <th>Address</th>
        </tr>
        </thead>
      </table>
    </div>
    <div class="tbl-content">
      <table cellpadding="0" cellspacing="0" border="0" id="tbl-search">
        <tbody>
        <%
          ArrayList<Customer> customers = (ArrayList<Customer>)request.getAttribute("customers");
          if(customers != null) {
          for(int i = 0; i < customers.size() ; i++) { %>
        <tr bgcolor="<%= i % 2 == 0 ? "#ccffff" : "#ccff33" %>">
          <td>
            <input type="checkbox" id="chkDetail" onclick="CheckUncheckHeader()"/>
          </td>
          <td>
            <a href="<%= request.getContextPath() + UrlConstant.URL_EDIT + "?id=" + customers.get(i).getCustomerId()%>"><%= customers.get(i).getCustomerId()%></a>
          </td>
          <td><%= customers.get(i).getCustomerName() %></td>
          <td><%= customers.get(i).getSex() %></td>
          <td><%= customers.get(i).getBirthday() %></td>
          <td><%= customers.get(i).getAddress() %></td>
        </tr>
        <% }
        }%>
        </tbody>
      </table>
    </div>


  </div>

  <div>
    <button id="btnAddnew" onclick="buttonAddNewClick()">Add New</button>
    <button id="btnDelete" <%=disableDelete%> onclick="buttonDeleteClick()">Delete</button>
  </div>

</div>
