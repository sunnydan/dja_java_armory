<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <head>
        <link rel="stylesheet" type="text/css" href="dropdown.css">
    </head>

    <body>
        <h1>WELCOME TO THE ARMORY</h1>
        <nav>
            <a href="/weapons/new">New Weapon</a>
            <a href="/categories/new">New Category</a>
            <a href="/attacks/new">New Attack</a>
        </nav>
        <table border="1">
            <tr>
                <th>Weapon Name</th>
                <th>Category</th>
                <th>Attacks</th>
            </tr>
            <c:forEach items="${weapons}" var="weapon" varStatus="loop">
                <tr>
                    <td>
                        <a href="/weapons/${weapon.id}">
                            ${weapon.name}
                        </a>
                    </td>
                    <td>${weapon.category.name}</td>
                    <td>
                        <select style="width: 100%;">
                            <c:forEach items="${weapon.attacks}" var="attack" varStatus="loop">
                                <option>${attack.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="/weapons/delete/${weapon.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>