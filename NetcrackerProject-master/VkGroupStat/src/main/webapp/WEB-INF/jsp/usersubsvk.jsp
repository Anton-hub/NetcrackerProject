<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
            <th>Count</th>
            <th>id</th>
        </tr>
        <c:forEach  items="${person}" var ="person">
            <tr>
                <td>${person.count}</td>
                <td>${person.items.id}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>

</html>