<!-- 
Document : Export page to excel
Created on : Dec 16, 2015
Author	 : Diana

edited for change from lucene to elasticsearch - 29th Nov, 2016
 -->
 <html><head>
<%@page import="java.util.List"%>
<%@page import="com.FG.company.CompanyInfo" %>
<script>
	$( document ).ready(function() {
    <%
    List<CompanyInfo> selectedExportCompanyList = (List<CompanyInfo>)request.getAttribute("selectedCompanyList4Export");
	if(selectedExportCompanyList==null) 
	{%>
		alert("Select companies to export");
	<%}%> 
</script>
<head>
<body>	
<% response.setContentType("application/vnd.ms-excel"); %>    
     <TABLE BORDER="1">
            <TR>
                <TH>Name</TH>
                <TH>Location</TH>
                <TH>Industry</TH>
                <TH>HeadCount</TH>
                <TH>Revenue</TH>
                <TH>Investors</TH>
            </TR>
			<%for(int i =0;i<selectedExportCompanyList.size();i++) 
			{%>
	            <TR>
	                <TD> <%=selectedExportCompanyList.get(i).getName()%></td>
	                <TD> <%=selectedExportCompanyList.get(i).getLocation()%></td>
	                <TD> <%=selectedExportCompanyList.get(i).getIndustry()%></td>
	                <TD> <%=selectedExportCompanyList.get(i).getHeadcount()%></td>
	                <TD> <%=selectedExportCompanyList.get(i).getRevenue()%></td>
	                <TD> <%=selectedExportCompanyList.get(i).getInvestors()%></td>
	            </TR>
            <% } %>
        </TABLE>
       </body>
       </html>
       