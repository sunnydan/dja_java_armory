<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <a href="/weapons">Return to main page</a>
    <h1>${weapon.name}</h1>
    <span title="${weapon.category.description}">${weapon.category.name}</span>
    <p>${weapon.description}</p>
    <h3>Attacks</h3>
    <ul>
        <c:forEach items="${weapon.attacks}" var="attack" varStatus="loop">
            <li title="${attack.description}">${attack.name}</li>
        </c:forEach>
    </ul>
    <h3>Add Attack</h3>
    <form action="/weapons/${weapon.id}" method="POST">
        <select name="attack">
            <c:forEach items="${attacks}" var="attack" varStatus="loop">
                <option value="${attack.id}">${attack.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Add">
    </form>