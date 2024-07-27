<%-- 
Document   : viewOrderByTable
Created on : Jul 6, 2024, 1:44:09 PM
Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Your Cart</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #ddd6c5;
                margin: 0;
                padding: 20px;
                color: #fff;
            }
            .container {
                margin: auto;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
                background-color: #282e52;
                max-width: 800px;
                color: #fff;
            }
            h1 {
                text-align: center;
                color: #ddd6c5;
                margin-bottom: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            table, th, td {
                box-shadow:  5px 5px 5px rgba(0, 0, 0, 0.2);
                border: 1px solid #444;
                border-radius: 4px;
            }
            th, td {
                padding: 12px;
                text-align: left;
            }
            th {
                background-color: #ddd6c5;
                color: #282e52;
            }
            td {
                background-color: rgba(1,0,0,0.2);
                color: #fff;
            }
            td img {
                width: 20px;
                height: auto;
                vertical-align: middle;
            }
            .total-money {
                font-weight: bold;
                font-size: 1.2em;
                margin-top: 20px;
                text-align: right;
                color: #a4a4a4;
                font-family: emoij;
            }
            .checkout-btn {
                background-color: #e60000;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                display: block;
                margin: 20px auto;
                text-align: center;
                text-decoration: none;
                font-size: 1em;
                transition: background-color 0.3s;
            }
            .checkout-btn:hover {
                background-color: #c60000;
            }
            .empty-cart {
                text-align: center;
                font-style: italic;
                color: #666;
            }
            .remove-btn {
                background: none;
                border: none;
                padding: 0;
            }
            .remove-btn img {
                width: 20px;
                height: auto;
            }
            .actions a {
                text-decoration: none;
                color: #EE362B;
                padding: 5px;
                border-radius: 5px;
                margin: 0 5px;
            }
            .actions img {
                width: 20px;
                height: auto;
                vertical-align: middle;
            }
            .edit-form {
                display: none;
            }
            .edit-form.active {
                display: block;
            }
            .edit-actions {
                display: none;
            }
            .edit-mode .edit-actions {
                display: inline;
            }
            .edit-button {
                background-color: #e60000;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                display: block;
                margin: 20px auto;
                text-align: center;
                text-decoration: none;
                font-size: 1em;
                transition: background-color 0.3s;
            }
            .edit-button:hover {
                background-color: #c60000;
            }
            
            
            .modal {
            display: none; /* Ẩn hộp thoại khi chưa kích hoạt */
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
        }
        .modal-content {
            background-color: #262626;
            margin: 15% auto;
            padding: 20px;
            border-radius: 8px;
            width: 80%;
            max-width: 500px;
        }
        .modal-header, .modal-footer {
            padding: 10px;
            border-bottom: 1px solid #444;
        }
        .modal-footer {
            border-top: 1px solid #444;
            text-align: right;
        }
        .modal-close {
            color: #fff;
            float: right;
            font-size: 20px;
            font-weight: bold;
            cursor: pointer;
        }
        .modal-close:hover {
            color: #ccc;
        }
        .modal-body {
            padding: 20px;
            color: #fff;
        }
        .modal-body p {
            font-size: 1.2em;
        }
        .modal-body button {
            background-color: #e60000;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1em;
            margin-top: 10px;
        }
        .modal-body button:hover {
            background-color: #c60000;
        }
        #sBill{
            text-align: center;
        }
        #sBill a{
            padding: 2% 5%;
            font-size: 18px;
            border-radius: 4px;
            height: 60px;
            width: 200px;
            text-decoration: none;
            background-color: #ddd6c5;
            color: #282e52;
        }
        
        
        </style>
    </head>
    <body>
        <div class="container <%= request.getParameter("edit") != null ? "edit-mode" : "" %>">
            <h1>Your Cart</h1>
            <c:if test="${not empty showOBC}">
                <table border="1">
                    <tr>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th>Price per Unit</th>
                        <th>Total Price</th>
                    </tr>
                    <c:forEach items="${showOBC}" var="detail">
                        <c:set var="itemName"/>
                        <c:set var="itemPrice" />
                        <tr>                           
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
                            <td>${itemName}</td>
                            <td>${detail.quantity}</td>
                            <td>${itemPrice}</td>
                            <td>${detail.quantity * itemPrice}</td>
                        </tr>
                        <c:set var="totalAmount" value="${totalAmount + detail.quantity * itemPrice}"/>
                    </c:forEach>
                </table>                                                        
                <c:if test="${not empty showOBC}">
                    <p class="total-money">Total Money: ${totalAmount}</p>  
                    <div id="sBill">
                        <a href="${pageContext.request.contextPath}/ShowBill">Print Bill</a>
                    </div>
                    
                </c:if>
            </c:if>
            <c:if test="${empty showOBC}">
                <p class="empty-cart">Your cart is empty.</p>                
            </c:if>
        </div>
        <script>
            function confirmCheckout() {
                var confirmed = confirm("Are you sure you want to checkout?");
                if (confirmed) {
                    document.getElementById('checkoutForm').submit();
                }
            }
        </script>
        
    </body>
</html>



