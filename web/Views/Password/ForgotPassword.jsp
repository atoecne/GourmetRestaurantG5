<%-- 
    Document   : forgotPassword
    Created on : Jun 5, 2024, 10:15:11 AM
    Author     : Thanh Cong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
        <style>
            body{
                background-color: #ddd6c5;
                margin: 0;
                line-height: normal;
            }

            .container {
                width: 92%;
                position: relative;
                border-radius: 10px;
                background-color: #fff6ea;
                height: 550px;
                margin-left: 4%;
                display: flex;
                justify-content: center;
            }

            .content{
                width: 50%;
                position: relative;
                border-radius: 10px;
                background-color: #444;
                height: 360px;
                top: 15%;
            }


            h3{
                position: relative;
                font-size: 35px;
                line-height: 40px;
                display: flex;
                font-family: Inter;
                color: #fa0303;
                text-align: center;
                align-items: center;
                justify-content: center;
                overflow: hidden;
                text-overflow: ellipsis;
                height: 35px;
                margin-bottom: 8%;
                text-shadow: 1px 0 0 #000, 0 1px 0 #000, -1px 0 0 #000, 0 -1px 0 #000;
            }

            .content label{
                margin-left: 21%;
                font-size: 18px;
                color: white;
            }

            .input-I{
                text-align: center;
                margin-bottom: 4%;
            }

            .content form input[type="text"]{
                width:58%;
                padding-top: 1%;
                padding-bottom: 1%;
                margin-bottom: 3%;
                margin-top: 1%;
                border-radius: 5px;
                border: 1px solid #ccc;
            }

            .content form input[type="submit"] {
                width: 125px;
                margin: 10px auto;
                padding: 15px;
                font-size: 16px;
                border: none;
                background-color: #e60000;
                color: white;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .content form input[type="submit"]:hover {
                background-color: #b30000 ;
            }

            .alert{
                text-align: center;
                color: red;
                font-size: 20px;
            }

            .sidebar{
                display: flex;
                justify-content: space-between;
            }
            h2{
                color: #282e52;
                margin-left: 2%;
            }

            .a2{
                color: #282e52;
                text-decoration: none;
                margin-top: 3%;
                margin-right: 2%;
                font-size: 18px;
            }

        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2><svg xmlns="http://www.w3.org/2000/svg" width="50" height="40" viewBox="0 0 48 48" fill="none">
                <path d="M14.1582 45.9016C9.96503 44.0004 6.73379 41.235 4.24252 37.6054C0.69062 32.4449 -0.616677 26.6918 0.271298 20.5189C1.99792 8.71646 12.2343 -0.369959 24.7646 0.0251029C37.3936 0.395473 45.7307 10.1239 47.3093 18.6918C32.8057 19.2844 18.3515 19.7288 3.72454 17.5313C4.24252 18.7165 4.61251 19.8029 5.17983 20.7905C5.32782 21.0621 6.01847 21.0621 6.48713 21.2103C6.48713 21.2103 6.53646 21.2597 6.56112 21.3091C7.00511 23.3091 7.00511 23.3337 9.02772 23.3584C11.0257 23.3831 13.0483 23.3584 15.0956 23.3584C15.0956 24.1733 15.0956 24.7658 15.0956 25.4819C14.4296 25.3831 13.8376 25.3091 13.1963 25.2103C13.1716 26.0498 12.6536 26.0498 12.037 26.0251C10.705 26.0004 9.39771 26.0498 8.06575 26.0251C7.5231 26.0251 7.35043 26.2226 7.35043 26.7412C7.42443 29.6054 6.93111 29.1115 9.6197 29.1362C11.3463 29.1609 13.0729 29.1362 14.9229 29.1362C14.6762 34.7412 14.4296 40.2473 14.1582 45.9016Z" fill="#282e52"/>
                <path d="M18.7214 47.3582C18.7214 46.9632 18.7214 46.6669 18.7214 46.3459C18.8448 40.8891 18.9681 35.457 19.0668 30.0002C19.0668 29.3829 19.2148 29.1113 19.9054 29.1113C26.6145 29.136 33.299 29.136 40.0082 29.136C40.2548 29.136 40.5015 29.1854 40.9455 29.2348C40.9455 30.1237 40.9208 31.0126 40.9455 31.8768C40.9948 34.5928 41.0688 37.2842 41.0935 40.0002C41.0935 40.42 40.9208 40.9632 40.6248 41.2348C34.6803 46.815 27.6752 48.9632 19.6094 47.6052C19.4614 47.5805 19.3134 47.5311 19.1408 47.4817C19.0668 47.457 18.9434 47.4323 18.7214 47.3582Z" fill="#282e52"/>
                <path d="M28.0452 23.6546C28.0452 24.5188 28.0452 25.2101 28.0452 25.9756C25.8499 25.9756 23.6793 26.0003 21.5334 25.9509C21.2867 25.9509 21.0154 25.5558 20.6454 25.2348C20.3001 25.2842 19.7821 25.3583 19.1901 25.4324C19.1901 24.8151 19.1901 24.2719 19.1901 23.6299C22.15 23.6546 25.0606 23.6546 28.0452 23.6546Z" fill="#282e52"/>
                <path d="M40.7729 25.4575C40.1562 25.3588 39.6875 25.2847 39.1942 25.2106C39.2189 25.3094 39.2436 25.4082 39.2682 25.5069C38.7996 25.6798 38.3309 25.9761 37.8623 26.0008C36.259 26.0501 34.6557 26.0254 33.0524 26.0254C32.7071 26.0254 32.3864 26.0008 31.9918 25.9761C31.9918 25.2106 31.9918 24.544 31.9918 23.6798C32.4357 23.6798 32.8797 23.6798 33.2991 23.6798C35.371 23.6551 37.4429 23.6304 39.5149 23.581C40.8222 23.5563 40.8222 23.5316 40.8222 24.8156C40.7975 24.9884 40.7729 25.1612 40.7729 25.4575Z" fill="#282e52"/>
                <path d="M45.2867 34.5927C45.2127 32.8396 45.114 31.0618 45.0154 29.21C45.8294 29.21 46.5693 29.21 47.5313 29.21C46.8653 31.0865 46.224 32.8643 45.6074 34.6421C45.5087 34.6174 45.41 34.5927 45.2867 34.5927Z" fill="#282e52"/>
                <path d="M44.8674 25.482C44.8674 24.7907 44.8674 24.1981 44.8674 23.4573C45.7307 23.4573 46.594 23.4327 47.4573 23.482C47.63 23.482 47.9013 23.7783 47.9506 23.9759C48.0246 24.3709 48.0246 24.7907 47.926 25.1857C47.852 25.482 47.63 25.7536 47.4573 26.0252C47.186 25.8524 46.9146 25.6796 46.6433 25.5067C46.668 25.408 46.6927 25.3092 46.7173 25.2351C46.15 25.2845 45.5827 25.3586 44.8674 25.482Z" fill="#EE362B"/>
                <path d="M16.9948 22.6669C16.6988 22.2719 16.2302 21.9015 16.1315 21.4817C15.9835 20.7904 16.4768 20.3459 17.1675 20.3212C17.8581 20.2966 18.3514 20.7657 18.2528 21.4324C18.1788 21.8768 17.7348 22.2472 17.4388 22.6669C17.2908 22.6669 17.1428 22.6669 16.9948 22.6669Z" fill="#282e52"/>
                <path d="M30.3392 22.5189C29.3525 22.4942 28.9579 22.1485 28.9085 21.4819C28.8592 20.8399 29.3772 20.272 29.9938 20.3214C30.6351 20.3707 31.1531 20.7658 31.0545 21.4078C31.0051 21.8522 30.5365 22.2473 30.3392 22.5189Z" fill="#282e52"/>
                <path d="M42.4501 22.5191C42.2528 22.2475 41.8088 21.8524 41.7348 21.408C41.6362 20.7413 42.1048 20.2969 42.8201 20.2969C43.4861 20.2969 43.8808 20.7413 43.8808 21.3586C43.9054 22.0993 43.4614 22.4697 42.4501 22.5191Z" fill="#282e52"/>
                </svg>Gourmet Restaurant</h2>
            <a class="a2" href="${pageContext.request.contextPath}/login">Login</a>
        </div>
        <div class="container">
            <div class="content">
                <h3>FORGOT PASSWORD</h3>
                <form action="${pageContext.request.contextPath}/fPass" method="post">
                    <label>Email:</label><br/>
                    <div class="input-I"> 
                        <input type="text" name="email" required><br/>
                        <input type="submit" value="Get OTP"><br/>
                    </div>
                </form>
                <c:if test="${not empty mailError}">
                    <div class="alert alert-danger" role="alert">
                        ${mailError}
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>



