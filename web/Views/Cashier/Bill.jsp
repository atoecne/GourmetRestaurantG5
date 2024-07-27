<%-- 
    Document   : Bill
    Created on : Jul 19, 2024, 6:01:38 AM
    Author     : LENOVO
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <title>Bill</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #DDD6C5;
                color: #282E52;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                width: 60%;
                background-color: #FFF;
                border: 1px solid #282E52;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            th, td {
                border: 1px solid #282E52;
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #282E52;
                color: #FFF;
            }
            .total-money {
                font-size: 1.2em;
                font-weight: bold;
                margin-bottom: 20px;
            }
            .checkout-btn {
                background-color: #282E52;
                color: #FFF;
                border: none;
                padding: 10px 20px;
                font-size: 1em;
                cursor: pointer;
                border-radius: 5px;
            }
            .checkout-btn:hover {
                background-color: #555A77;
            }
            .empty-cart {
                font-size: 1.2em;
                font-weight: bold;
                color: #D9534F;
            }
            a {
                color: #D9534F;
                text-decoration: none;
                font-weight: bold;
            }
            a:hover {
                text-decoration: underline;
            }

            #searchResults {
                display: none;
                position: absolute;
                background-color: black;
                border: 1px solid #ddd;
                z-index: 1000;
                width: 315px;
                margin-left: 113px;
            }

            .customer-row {
                color: #ddd6c5;
                background-color: #282e52;
                cursor: pointer;
                padding: 10px;
                border: 1px solid #ddd;
                margin-bottom: 5px;
                transition: background-color 0.3s;
            }

            .customer-row:hover {
                background-color: gray;
                color: #fff;
            }

            .customer-row img {
                width: 50px;
                height: 50px;
                margin-right: 10px;
                vertical-align: middle;
            }

            .body {
                display: flex;
            }

            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgb(0,0,0);
                background-color: rgba(0,0,0,0.4);
                padding-top: 60px;
            }

            .modal-content {
                background-color: #fefefe;
                margin: 5% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
            }

            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }
            .coupon-row {
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .coupon-row:hover {
                background-color: #f1f1f1;
            }
        </style>
    </head>
    <body>
        <div class="body">
            <div class="container">
                <h2>GOURMET RESTAURANT</h2>
                <p>600, Nguyen Van Cu Street (extended), An Binh Ward, Ninh Kieu District, Can Tho City</p>
                <p>Phone: 0774.033.5782 - 0292.777.3636</p>
                <h2>BILL</h2>
                <h3>${requestScope.tableName}</h3>

                <c:set var="orderDateTimeStr" value="${requestScope.time}"/>
                <p>Date: ${requestScope.orderDate}</p>
                <p>Serve: ${requestScope.serveName}</p>
                <p>Time: ${requestScope.orderTime}</p>
                <p>Customer: ${sessionScope.name}</p>
                <c:if test="${not empty showOB}">
                    <table border="1">
                        <tr>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Price per Unit</th>
                            <th>Total Price</th>
                        </tr>
                        <c:forEach items="${showOB}" var="detail">
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
                    <p class="total-money">Total Money: ${totalAmount}</p>

                    <c:set var="discount" value="${(sessionScope.dis * totalAmount) / 100}"/>
                    <p>Discount Amount: ${discount}</p>
                    <c:set var="real" value="${totalAmount - discount}"/>
                    <p>Real Money: ${real}</p>
                </c:if>
                <c:if test="${empty showOB}">
                    <p class="empty-cart">Your cart is empty.</p>                
                </c:if>
            </div>

            <div>
                <div class="search-ID"> 
                    <form>
                        <label>Search Customer</label>
                        <input oninput="searchByAjax(this)" type="text" id="searchQuery" name="txtSearch" autocomplete="off"/>
                        <div id="searchResults" class="content"></div>
                    </form>
                </div>
                <form action="${pageContext.request.contextPath}/invoice" method="post">
                    <input type="hidden" name="realValue" value="${real}"/>
                    <input type="submit" value="Checkout" class="checkout-btn"/>
                    <p>${message}</p>
                </form>
                <a href="${pageContext.request.contextPath}/ShowT">Cancel</a>    
            </div>
        </div>

        <c:if test="${not empty sessionScope.name}">
            <button class="checkout-btn" onclick="showModal()">Show Coupons</button>              
        </c:if>


        <div id="couponModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h2>Available Coupons</h2>
                <c:if test="${not empty sessionScope.listC}">
                    <table>
                        <thead>
                            <tr id="headerF">
                                <th id="tl">Image</th>
                                <th>Coupon ID</th>
                                <th>Description</th>
                                <th>Discount Type</th>
                                <th>Discount Value</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Active</th>
                            </tr>
                        </thead>
                        <tbody class="content">
                            <c:set var="result" value="${sessionScope.listC}"/>
                            <c:if test="${not empty result}">
                                <c:forEach var="c" items="${result}">
                                    <tr class="coupon-row" data-coupon-id="${c.couponID}">
                                        <td><img src="image/${c.couponImg}" width="50"></td>
                                        <td>${c.couponID}</td>
                                        <td class="description-cell">${c.couponDescription}</td>
                                        <td>${c.discountType}</td>
                                        <td>${c.discountValue}</td>
                                        <td>${c.startDate}</td>
                                        <td>${c.endDate}</td>
                                        <td>${c.active}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty sessionScope.listC}">
                    <p>No coupons available.</p>
                </c:if>
            </div>
        </div>

        <script>
            function showModal() {
                document.getElementById('couponModal').style.display = 'block';
            }

            function closeModal() {
                document.getElementById('couponModal').style.display = 'none';
            }

            // Handle row clicks
            document.addEventListener('DOMContentLoaded', function () {
                var rows = document.querySelectorAll('.coupon-row');
                rows.forEach(function (row) {
                    row.addEventListener('click', function () {
                        var couponId = this.getAttribute('data-coupon-id');
                        // Redirect to the servlet with the coupon ID as a query parameter
                        window.location.href = "${pageContext.request.contextPath}/addPointCustomer?couponID=" + couponId;
                    });
                });
            });
        </script>



        <script>
            function showModal() {
                document.getElementById('couponModal').style.display = 'block';
            }

            function closeModal() {
                document.getElementById('couponModal').style.display = 'none';
            }

        </script>


        <script>
            function searchByAjax(param) {
                var txtSear = param.value;
                if (txtSear.trim() === "") {
                    $("#searchResults").hide();
                    return;
                }
                $.ajax({
                    url: "${pageContext.request.contextPath}/searchCustomer",
                    type: "get",
                    data: {
                        txtSearch: txtSear
                    },
                    success: function (data) {
                        if (data.trim() === "") {
                            $("#searchResults").hide();
                        } else {
                            $("#searchResults").html(data);
                            $("#searchResults").show();
                        }
                    }
                });
            }

            $(document).mouseup(function (e) {
                var container = $("#searchResults");
                if (!container.is(e.target) && container.has(e.target).length === 0) {
                    container.hide();
                }
            });

            var totalAmount = ${totalAmount};
            function addCustomer(phoneNumber) {
                window.location.href = "${pageContext.request.contextPath}/addPointCustomer?phoneNumber=" + phoneNumber + "&totalAmount=" + totalAmount;
            }
        </script>
    </body>
</html>



