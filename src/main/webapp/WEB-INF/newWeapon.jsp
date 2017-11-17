<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <a href="/weapons">Return to main page</a>
        <table>
            <form:form method="POST" action="/weapons/new" modelAttribute="weapon">
                <tr>
                    <td>
                        <form:label path="name">Name</form:label>
                        <form:errors path="name" />
                    </td>
                    <td>
                        <form:input path="name" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="description">Description</form:label>
                        <form:errors path="description" />
                    </td>
                    <td>
                        <form:textarea path="description" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="category">Category</form:label>
                        <form:errors path="category" />
                    </td>
                    <td>
                        <form:select path="category">
                            <c:forEach items="${categories}" var="category" varStatus="loop">
                                <form:option value="${category.id}">${category.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Submit" />
                    </td>
                </tr>
            </form:form>
        </table>