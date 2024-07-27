<%-- 
    Document   : Login
    Created on : 03-06-2024, 15:19:30
    Author     : Nguyen Van Canh -  CE170300
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="initial-scale=1, width=device-width">
        <link rel="stylesheet" href="./Css/index.css" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400&display=swap" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inria+Serif:wght@400&display=swap" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inder:wght@400&display=swap" />
        <style>
            .error {
                color: red;
            }
            .background-image {
                position: absolute; /* Đặt phần tử ảnh nền ở vị trí tuyệt đối bên trong phần tử cha */
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-image: url('./image/loginbg.jpg');
                background-size: cover; /* Đảm bảo ảnh nền được đổ đầy toàn bộ phần tử */
                filter: blur(5px); /* Áp dụng hiệu ứng mờ cho ảnh nền */
                z-index: -1; /* Đặt z-index để phần tử ảnh nền nằm dưới các phần tử con */
            }
        </style>
        <title>Login - Gourment Restaurant</title>
    </head>
    <body>
        <div class="frame-main">
            <div class="background-image"></div>
            <div class="logo-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 48 48" fill="none">
                <path d="M14.1583 45.9012C9.96506 44 6.73382 41.2346 4.24255 37.6049C0.690651 32.4444 -0.616646 26.6913 0.271329 20.5185C1.99795 8.71603 12.2343 -0.370386 24.7646 0.0246756C37.3936 0.395046 45.7307 10.1234 47.3094 18.6913C32.8058 19.2839 18.3515 19.7284 3.72457 17.5308C4.24255 18.716 4.61254 19.8025 5.17986 20.7901C5.32785 21.0617 6.0185 21.0617 6.48716 21.2099C6.48716 21.2099 6.53649 21.2592 6.56115 21.3086C7.00514 23.3086 7.00514 23.3333 9.02775 23.358C11.0257 23.3827 13.0483 23.358 15.0956 23.358C15.0956 24.1728 15.0956 24.7654 15.0956 25.4815C14.4296 25.3827 13.8376 25.3086 13.1963 25.2099C13.1716 26.0494 12.6537 26.0494 12.037 26.0247C10.705 26 9.39774 26.0494 8.06578 26.0247C7.52313 26.0247 7.35047 26.2222 7.35047 26.7407C7.42446 29.6049 6.93114 29.1111 9.61974 29.1358C11.3464 29.1605 13.073 29.1358 14.9229 29.1358C14.6763 34.7407 14.4296 40.2469 14.1583 45.9012Z" fill="#282E52"/>
                <path d="M18.7215 47.358C18.7215 46.963 18.7215 46.6667 18.7215 46.3457C18.8448 40.8889 18.9682 35.4568 19.0668 30C19.0668 29.3827 19.2148 29.1111 19.9055 29.1111C26.6146 29.1358 33.2991 29.1358 40.0082 29.1358C40.2549 29.1358 40.5016 29.1852 40.9455 29.2346C40.9455 30.1235 40.9209 31.0124 40.9455 31.8765C40.9949 34.5926 41.0689 37.284 41.0935 40C41.0935 40.4198 40.9209 40.963 40.6249 41.2346C34.6804 46.8148 27.6752 48.963 19.6095 47.6049C19.4615 47.5803 19.3135 47.5309 19.1408 47.4815C19.0668 47.4568 18.9435 47.4321 18.7215 47.358Z" fill="#282E52"/>
                <path d="M28.0452 23.6543C28.0452 24.5185 28.0452 25.2099 28.0452 25.9753C25.8499 25.9753 23.6793 26 21.5334 25.9506C21.2867 25.9506 21.0154 25.5555 20.6454 25.2346C20.3001 25.2839 19.7821 25.358 19.1901 25.4321C19.1901 24.8148 19.1901 24.2716 19.1901 23.6296C22.15 23.6543 25.0606 23.6543 28.0452 23.6543Z" fill="#282E52"/>
                <path d="M40.7729 25.4568C40.1562 25.358 39.6876 25.2839 39.1943 25.2099C39.2189 25.3086 39.2436 25.4074 39.2683 25.5062C38.7996 25.679 38.3309 25.9753 37.8623 26C36.259 26.0494 34.6557 26.0247 33.0524 26.0247C32.7071 26.0247 32.3864 26 31.9918 25.9753C31.9918 25.2099 31.9918 24.5432 31.9918 23.679C32.4358 23.679 32.8798 23.679 33.2991 23.679C35.371 23.6543 37.443 23.6296 39.5149 23.5802C40.8222 23.5555 40.8222 23.5309 40.8222 24.8148C40.7975 24.9876 40.7729 25.1605 40.7729 25.4568Z" fill="#282E52"/>
                <path d="M45.2867 34.5926C45.2127 32.8395 45.1141 31.0617 45.0154 29.2099C45.8294 29.2099 46.5694 29.2099 47.5313 29.2099C46.8654 31.0864 46.224 32.8642 45.6074 34.642C45.5087 34.6173 45.4101 34.5926 45.2867 34.5926Z" fill="#282E52"/>
                <path d="M44.8674 25.4815C44.8674 24.7901 44.8674 24.1975 44.8674 23.4568C45.7307 23.4568 46.594 23.4321 47.4574 23.4815C47.63 23.4815 47.9013 23.7778 47.9507 23.9753C48.0247 24.3704 48.0247 24.7901 47.926 25.1852C47.852 25.4815 47.63 25.7531 47.4574 26.0247C47.186 25.8518 46.9147 25.679 46.6434 25.5062C46.6681 25.4074 46.6927 25.3086 46.7174 25.2346C46.1501 25.2839 45.5827 25.358 44.8674 25.4815Z" fill="#282E52"/>
                <path d="M16.9949 22.6667C16.6989 22.2716 16.2302 21.9012 16.1316 21.4815C15.9836 20.7901 16.4769 20.3457 17.1675 20.321C17.8582 20.2963 18.3515 20.7654 18.2528 21.4321C18.1788 21.8765 17.7349 22.2469 17.4389 22.6667C17.2909 22.6667 17.1429 22.6667 16.9949 22.6667Z" fill="#282E52"/>
                <path d="M30.3392 22.5185C29.3525 22.4938 28.9579 22.1482 28.9085 21.4815C28.8592 20.8395 29.3772 20.2716 29.9938 20.321C30.6351 20.3704 31.1531 20.7654 31.0545 21.4074C31.0051 21.8519 30.5365 22.2469 30.3392 22.5185Z" fill="#282E52"/>
                <path d="M42.4501 22.5185C42.2528 22.2469 41.8088 21.8519 41.7348 21.4074C41.6362 20.7407 42.1048 20.2963 42.8201 20.2963C43.4861 20.2963 43.8808 20.7407 43.8808 21.358C43.9054 22.0988 43.4614 22.4691 42.4501 22.5185Z" fill="#282E52"/>
                </svg>
            </div>
            <div class="gourment-login">Gourmet - Restaurant</div>
            <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
                <div class="input-container">
                    <input type="text" name="email" placeholder="Email" required/>

                </div>
                <div class="input-container">
                    <input type="password" name="pass" placeholder="Password" required/>
                </div>
                <c:if test="${not empty errorMessage}">
                    <p class="error">${errorMessage}</p>
                </c:if>
                <div class="input-container select-role">
                    <select name="role">
                        <option value="Serve">Serve</option>
                        <option value="Cashier">Cashier</option>
                        <option value="Manager">Manager</option>
                    </select>
                </div>
                <button type="submit" class="login-button">LOGIN</button>
                <div>
                    <c:if test="${not empty requestScope.Error}">
                        <h3 class="error">${requestScope.Error}</h3>
                    </c:if>
                    <c:if test="${not empty errorEmail}">
                        <p class="error">${errorEmail}</p>
                    </c:if>

                </div>

            </form>
            <div class="forgot-password">
                <a href="${pageContext.request.contextPath}/fPass">Forgot Password</a>
            </div>

        </div>
    </body>
</html>
