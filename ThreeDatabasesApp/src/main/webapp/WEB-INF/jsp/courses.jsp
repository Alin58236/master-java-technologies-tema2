<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Courses</title>
</head>
<body>
<h1>Courses</h1>

<c:if test="${empty courses}">
  <p>No courses found.</p>
</c:if>

<table border="1">
  <tr><th>ID</th><th>Name</th><th>Teacher Name</th></tr>
  <c:forEach var="course" items="${courses}">
    <tr>
      <td>${course.id}</td>
      <td>${course.name}</td>
      <td>${teacherNames[course.teacherId]}</td>
    </tr>
  </c:forEach>
</table>

<h2>Add Course</h2>
<form action="/ThreeDatabasesApp-1.0-SNAPSHOT/api/courses" method="post" onsubmit="handleSubmit(event)">
  Name: <input type="text" name="name">
  Teacher ID: <input type="number" name="teacherId">
  <input type="submit" value="Add">
</form>

<button type="button" onclick="window.location.href='http://localhost:8080/ThreeDatabasesApp-1.0-SNAPSHOT/'">
  Back to Index
</button>

<script>
  function handleSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const data = new FormData(form);

    fetch(form.action, {
      method: form.method,
      body: new URLSearchParams(data)
    })
            .then(response => {
              if (!response.ok) throw new Error('POST failed with ' + response.status);
              // după POST reușit, redirecționează
              window.location.href = '/ThreeDatabasesApp-1.0-SNAPSHOT/page/courses';
            })
            .catch(err => {
              alert('Error posting data: ' + err.message);
            });
  }
</script>


</body>
</html>
