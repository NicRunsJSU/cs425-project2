<%-- 
    Document   : applicant_skills
    Created on : Nov 27, 2019, 2:43:18 PM
    Author     : amberdawn50
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="applicant" class="edu.jsu.mcis.cs425.project2.BeanApplicant" scope="session" />
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Select Skills</title>
   </head>
   <body>
      <form id="skillsform" name="skillsform" method="post" action="applicant_jobs.jsp">
         <fieldset>
            <legend>Select Your Skills:</legend>
            <jsp:getProperty name="applicant" property="skillsList" />
            <input type="submit" value="Submit" />
         </fieldset>
      </form>
   </body>
</html>
