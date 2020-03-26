<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Person List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<h1>Users Subscriptions</h1>

<br/><br/>
<div>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Screen Name</th>
        </tr>
        <c:forEach  items="${person}" var ="person">
            <tr>
                <td>${person.items.name}</td>
                <td>${person.items.screen_name}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>

</html>