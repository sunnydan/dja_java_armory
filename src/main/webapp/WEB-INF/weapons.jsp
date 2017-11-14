<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <head>
        <link rel="stylesheet" type="text/css" href="/main.css">
    </head>

    <body>
        <div style="margin-bottom: 20px;">
            <h1 style="margin-bottom: 0; text-align: center;">Welcome to the Armory, ${currentUser.username}</h1>
            <nav style="display: flex; justify-content:space-between;">
                <span>
                    <a href="/categories">View Categories</a>
                    <a href="/attacks">View Attacks</a>
                </span>
                <span>
                    <a href="/users/dashboard">User Dashboard</a>
                    <c:if test="${currentUser.permissionLevel > 1}"><a href="/admin">Admin Page</a></c:if>
                    <a href="/login?logout">Logout</a>
                </span>
            </nav>
        </div>
        <div style="display: flex; justify-content: space-between;">
            <form action="">
                <input type="search" placeholder="Search Term">
                <input type="submit" value="Search">
            </form>
            <a href="/weapons/new">
                <button>Add Weapon</button>
            </a>
        </div>
        <table border="1" style="width: 100%;">
            <tr>
                <th>Weapon
                    <i class="arrow down"></i>&nbsp;</th>
                <th>Category
                    <i class="arrow down"></i>&nbsp;</th>
                <th>Attacks</th>
            </tr>
            <c:forEach items="${weapons.content}" var="weapon" varStatus="loop">
                <tr>
                    <td>
                        <a href="/weapons/${weapon.id}">
                            ${weapon.name}
                        </a>
                    </td>
                    <td>
                        <a href="/categories/${category.id}">
                            ${weapon.category.name}
                        </a>
                    </td>
                    <td>
                        <select style="width: 100%;" onchange="location = this.value;">
                            <option value="">---Attacks---</option>
                            <c:forEach items="${weapon.attacks}" var="attack" varStatus="loop">
                                <option value="/attacks/${attack.id}">${attack.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="/weapons/delete/${weapon.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        Page:
        <c:forEach begin="1" end="${totalPages}" var="index">
            <a href="/weapons/page/${index}">${index}</a>
        </c:forEach>
    </body>