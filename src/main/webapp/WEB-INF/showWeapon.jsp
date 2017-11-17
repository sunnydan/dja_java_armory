<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <a href="/weapons">Return to main page</a>
        <h1>${weapon.name}</h1>
        <table>
            <tr>
                <td>Creator:</td>
                <td>${weapon.getAuthor().getUsername()}</td>
            </tr>
            <tr>
                <td>Category:</td>
                <td title="${weapon.category.description}">${weapon.category.name}</td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>${weapon.description}</td>
            </tr>
            <tr>
                <td>Attacks:</td>
                <td>
                    <ul>
                        <c:forEach items="${weapon.attacks}" var="attack" varStatus="loop">
                            <li title="${attack.description}">${attack.name}</li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </table>
        <h3>Add Attack</h3>
        <form action="/weapons/${weapon.id}" method="POST">
            <select name="attack">
                <c:forEach items="${attacks}" var="attack" varStatus="loop">
                    <option value="${attack.id}">${attack.name}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="submit" value="Add">
        </form>