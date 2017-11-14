<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href="/weapons">Return to main page</a>
<h1>${currentUser.username}'s User Dashboard</h1>
<table border="1">
<tr>
    <td>Permission Level</td>
    <td>${currentUser.permissionLevel}</td>
</tr>
</table>