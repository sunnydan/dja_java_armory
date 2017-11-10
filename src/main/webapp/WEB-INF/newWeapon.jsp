<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <a href="/weapons">Return to main page</a>
        <form:form method="POST" action="/weapons/new" modelAttribute="weapon">
            <form:label path="name">Name
                <form:errors path="name" />
                <form:input path="name" /></form:label>
            <br>
            <form:label path="description">Description
                <form:errors path="description" />
                <form:textarea path="description" /></form:label>
            <br>
            <form:label path="category">Category
                <form:errors path="category" />
                <form:select path="category">
                    <c:forEach items="${categories}" var="category" varStatus="loop">
                        <form:option value="${category.id}">${category.name}</form:option>
                    </c:forEach>
                </form:select>
            </form:label>
            <br>
            <input type="submit" value="Submit" />
        </form:form>