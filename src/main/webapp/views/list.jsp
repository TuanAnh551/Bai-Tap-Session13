<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="/users?action=create">Add New User</a>
    </h2>
    <h2>    <a href="http://localhost:8080/users?action=permision">Create User and Permision</a>
    </h2>

    <div class="form-container">
        <form action="/users" style="display: inline-block;">
            <input type="search" value="${keyword}" name="keyword" placeholder="search by country" >
            <input type="submit" value="SEARCH" name="action">
        </form>
        <form action="/users" style="display: inline-block;">
            <input type="submit" value="SortAsc"  name="action">
        </form>
        <form action="/users" style="display: inline-block;">
            <input type="submit" value="SortDesc" name="action">
        </form>
    </div>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.country}"/></td>
                <td>
                   <a href="/users?action=edit&id=${user.id}"><button>Edit</button></a>
<a href="/users?action=delete&id=${user.id}"><button>Delete</button></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>