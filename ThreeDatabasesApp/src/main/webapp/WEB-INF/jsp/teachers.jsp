<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teachers</title>
</head>
<body>
<h1>Teachers</h1>

<c:if test="${empty teachers}">
    <p>No teachers found.</p>
</c:if>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Subject</th>
    </tr>
    <c:forEach var="teacher" items="${teachers}">
        <tr>
            <td>${teacher.id}</td>
            <td>${teacher.name}</td>
            <td>${teacher.department}</td>
        </tr>
    </c:forEach>
</table>

<h2>Add Teacher</h2>
<form action="/ThreeDatabasesApp-1.0-SNAPSHOT/api/teachers" method="post" onsubmit="handleSubmit(event)">
    Name: <input type="text" name="name">
    Subject: <input type="text" name="department">
    <input type="submit" value="Add">
</form>

<button type="button" onclick="window.location.href='/ThreeDatabasesApp-1.0-SNAPSHOT/'">
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
                // redirect înapoi la pagina teachers pentru refresh
                window.location.href = '/ThreeDatabasesApp-1.0-SNAPSHOT/page/teachers';
            } else {
                alert('Error posting data');
            }
        }).catch(err => alert('Error: ' + err));
    }
</script>

</body>
</html>
