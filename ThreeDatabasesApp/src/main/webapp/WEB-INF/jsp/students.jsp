<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Students</title>
</head>
<body>
<h1>Students</h1>

<c:if test="${empty students}">
  <p>No students found.</p>
</c:if>

<table border="1">
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Email</th>
    <th>Actions</th>
  </tr>
  <c:forEach var="student" items="${students}">
    <tr>
      <td>${student.id}</td>
      <td>${student.name}</td>
      <td>${student.email}</td>
      <td>
        <button onclick="deleteStudent(${student.id})">Delete</button>
      </td>
    </tr>
  </c:forEach>
</table>

<h2>Add Student</h2>
<form action="/ThreeDatabasesApp-1.0-SNAPSHOT/api/students" method="post" onsubmit="handleSubmit(event)">
  Name: <input type="text" name="name">
  Email: <input type="text" name="email">
  <input type="submit" value="Add">
</form>

<button type="button" onclick="window.location.href='http://localhost:8080/ThreeDatabasesApp-1.0-SNAPSHOT/'">
  Back to Index
</button>

<script>
  function handleSubmit(event) {
    event.preventDefault(); // previne navigarea automată
    const form = event.target;
    const data = new FormData(form);

    fetch(form.action, {
      method: form.method,
      body: new URLSearchParams(data)
    }).then(response => {
      if (response.ok) {
        // redirect înapoi la pagina students pentru refresh
        window.location.href = '/ThreeDatabasesApp-1.0-SNAPSHOT/page/students';
      } else {
        alert('Error posting data');
      }
    }).catch(err => alert('Error: ' + err));
  }

  function deleteStudent(id) {

    if (!confirm("Vrei sa stergi studentul "+id+"?")) return;
    fetch("/ThreeDatabasesApp-1.0-SNAPSHOT/api/students/"+id, {
      method: 'DELETE'
    })
            .then(response => {
              if (response.ok) {
                alert("Studentul "+id+" a fost sters.");
                location.reload();
              } else {
                response.text().then(text => alert("Server error: " + text));
              }
            })
            .catch(err => alert("Request failed: " + err));
  }
</script>

</body>
</html>
