<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <style>
            .container {
                margin-top: 2%;
                margin-right: 2%;
                margin-left: 20%;
                width: 70%;
                padding:2%;
                border-radius: 8px;
                background-color: #fff6ea;
                color: #fff;
                height: 70vh;
                text-align: center;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                background-color: #333;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
                border-radius: 8px;
                color: #ddd6c5;
            }
            th, td {
                border: 1px solid #444;
                padding: 12px;
                text-align: left;
                border-radius: 8px;
            }
            th {
                background-color: #282e52;
                color: #fff;
            }
            tr:nth-child(even) {
                background-color: #444;
            }
            tr:nth-child(odd) {
                background-color: #555;
            }
            tr:hover {
                background-color: #666;
            }
            .total {
                text-align: center;
                font-size: 20px;
                font-weight: bold;
                color: #EE362B;
                margin-top: 20px;
            }
            .info {
                font-size: 20px;
                color: #282e52;
                background-color: #ddd6c5;
                padding: 10px;
                border-radius: 5px;
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
                margin-bottom: 20px;
            }
        </style>
    </head>
   <body>
    <div class="container">
        <c:if test="${not empty orderDetails}">
            <c:set var="firstOrder" value="${orderDetails[0]}"/>
            <div class="info">
                <p>Phone Number: ${orderModel.phoneNumber}</p>
                <p>Table ID: ${firstOrder.tableID}</p>
                <p>Order Date: <fmt:formatDate value="${orderModel.orderDate}" pattern="dd/MM/YYYY HH:mm:ss"/></p>
            </div>
        </c:if>

        <c:set var="totalAmount" value="0"/>
        <c:if test="${not empty orderDetails}">
            <table>
                <tr>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Total</th>
                </tr>
                <c:forEach items="${orderDetails}" var="detail">
                    <c:set var="itemName"/>
                    <c:set var="itemPrice" />
                    <c:choose>
                        <c:when test="${not empty detail.foodID}">
                            <c:forEach items="${foods}" var="food">
                                <c:if test="${food.foodID == detail.foodID}">
                                    <c:set var="itemName" value="${food.foodName}"/>
                                    <c:set var="itemPrice" value="${food.price}"/>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:when test="${not empty detail.drinkID}">
                            <c:forEach items="${drinks}" var="drink">
                                <c:if test="${drink.drinkID == detail.drinkID}">
                                    <c:set var="itemName" value="${drink.drinkName}"/>
                                    <c:set var="itemPrice" value="${drink.price}"/>
                                </c:if>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                    <tr>
                        <td>${itemName}</td>
                        <td>${detail.quantity}</td>
                        <td>${itemPrice}</td>
                        <td>${detail.quantity * itemPrice}</td>
                    </tr>
                    <c:set var="totalAmount" value="${totalAmount + detail.quantity * itemPrice}"/>
                </c:forEach>
            </table>
            <p class="total">Total Amount: ${totalAmount}</p>
        </c:if>
        <c:if test="${empty orderDetails}">
            <p>No details found.</p>
        </c:if>
    </div>
</body>
</html>





