<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="/main.css">
            <title>Admin Page</title>
        </head>

        <body>
            <div style="margin-bottom: 20px;">
                <h1 style="margin-bottom: 0; text-align: center; margin-top: 0;">Welcome to the Admin Page, ${currentUser.username}</h1>
                <nav style="display: flex; justify-content:space-between;">
                    <a href="/weapons">Return to main page</a>
                    <span>
                        <a href="/users/dashboard">User Dashboard</a>
                        <a href="/login?logout">Logout</a>
                    </span>
                </nav>
            </div>
            <div style="display: flex; justify-content: space-between;">
                <form action="">
                    <input type="search" placeholder="Search Term">
                    <input type="submit" value="Search">
                </form>
            </div>
            <table border="1" style="width: 100%;">
                <thead>
                    <th>Username
                        <i class="arrow down"></i>&nbsp;</th>
                    <th>Permission Level
                        <i class="arrow down"></i>&nbsp;</th>
                    <th>Weapons</th>
                    <th>Actions</th>
                </thead>
                <c:forEach items="${users.content}" var="user" varStatus="loop">
                    <c:if test="${currentUser.permissionLevel > user.permissionLevel}">
                        <tr>
                            <td>
                                <a href="/admin/users/${user.id}">
                                    ${user.username}
                                </a>
                            </td>
                            <td>${user.permissionLevel}</td>
                            <td>
                                <select style="width: 100%;" onchange="location = this.value;">
                                    <option value="">---Weapons---</option>
                                </select>
                            </td>
                            <td>
                                <select style="width: 100%;" onchange="location = this.value;">
                                    <option value="">---Actions---</option>
                                    <c:if test="${user.permissionLevel < 2 && currentUser.permissionLevel == 3}">
                                        <option value="/superadmin/users/promote/${user.id}">Promote to Admin</option>
                                    </c:if>
                                    <c:if test="${user.permissionLevel == 2 && currentUser.permissionLevel == 3}">
                                        <option value="/superadmin/users/demote/${user.id}">Demote from Admin</option>
                                    </c:if>
                                    <option value="/admin/users/delete/${user.id}">Delete</option>
                                </select>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
            Page:
            <c:forEach begin="1" end="${totalPages}" var="index">
                <a href="/admin/users/page/${index}">${index}</a>
            </c:forEach>
        </body>

        </html>