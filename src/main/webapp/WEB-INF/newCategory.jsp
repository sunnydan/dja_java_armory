<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <a href="/weapons">Return to main page</a>
    <form:form method="POST" action="/categories/new" modelAttribute="category">
        <form:label path="name">Name
            <form:errors path="name" />
            <form:input path="name" /></form:label>
        <br>
        <form:label path="description">Description
            <form:errors path="description" />
            <form:textarea path="description" /></form:label>
        <br>
        <input type="submit" value="Submit" />
    </form:form>