<%-- 
    Document   : addCart
    Created on : Jun 6, 2024, 3:30:29 PM
    Author     : LENOVO
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="DAO.TableDAO" %>
<%@ page import="Model.TableModel" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <title>Cart</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <style>

            img{
                border-radius: 5px;
                width: 150px;
                height: 150px;
            }
            body{
                color: white;
                background-color: #ddd6c5;
            }

            #body{
                display: flex;
                padding-right: 24px;
                align-items: flex-end;
                gap: 11px;
            }


            #sidebar {
                width: 10%;
                background-color: #ddd6c5;
                height: 100vh;
                padding: 0% 2%;
                padding-top: 2px;
                box-shadow: 2px 0 5px rgba(0,0,0,0.1);
                border-radius: 24px 0 0 24px;
                position: fixed;
                top: 0;
                left: 0;
                overflow-y: auto;
            }

            .container {
                width:70%;
                margin-right: 2%;
                margin-left: 20%;
                padding: 2%;
                background-color: #C4BCAA;
            }

            #gim{
                background-color: #ddd6c5;
                position: fixed;
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            #gim h1 {
                width: 80%;
                color: red;
                margin-bottom: 20px;
                font-family: cursive;
            }

            h1{
                color: #ddd6c5;
            }
            .sidebar-button {
                font-family: fantasy;
                background-color: #ddd6c5;
                padding: 14px 20px;
                color: #282e52;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 18px;
                text-align: left;
            }

            h2{
                color: #282e52;
            }

            .sidebar-button:hover {
                background-color: rgba(255, 0, 0, 0.5);
                color: black;
            }
            h1 {
                font-size: 24px;
                color: #ffffff;
                margin-bottom: 20px;
                width: 100%;
            }
            .selected-table {
                font-size: 18px;
                margin: 10px;
                text-align: center;
                margin-bottom: 20px;
                padding: 10px;
                width: 15%;
                border-radius: 10px;
                border-bottom: 1px solid darkred;
                background-color: darkred;
                color: #ddd6c5;
            }
            .left-column {
                margin-left: 8%;
                width: 85%;
                background-color: #282e52;
                border-radius: 10px;
            }

            .right-column {
                width: 12%;
                position:  fixed;
                bottom: 20px;
                right: 30px;
                display: flex;
                flex-direction: column;
                align-items: stretch;
                flex-direction: column;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                background-color: transparent;
            }
            table th, table td {
                padding: 10px;
                text-align: left;
                background-color: transparent;
                color: #ddd6c5;
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
            .total-products {
                text-align: left;
                font-size: 18px;
                color: #EE362B;
                margin-top: 20px;
                align-self: flex-start;
            }
            .total-money {
                font-size: 18px;
                color: #ffffff;
                margin-top: auto;
            }
            .checkout-btn {
                width: 100%;
                padding: 10px;
                background-color: #EE362B;
                color: #000000;
                text-align: center;
                border: none;
                border-radius: 5px;
                font-size: 18px;
                cursor: pointer;
                margin-top: 10px;
            }
            .checkout-btn:hover {
                background-color: #FF4500;
            }
            .back-to-menu {
                display: block;
                text-align: center;
                margin-top: 20px;
                color: #EE362B;
                text-decoration: none;
                width: 100%;
            }
            .back-to-menu:hover {
                text-decoration: underline;
            }
            .empty-cart {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100%;
                flex-direction: column;
                color: #FFFFFF;
                text-align: center;
            }
            .empty-cart-message {
                margin-top: 20px;
                font-size: 18px;
                color: #CCCCCC;
            }
            .primary-button {
                margin-top: 20px;
                margin-bottom: 10%;
                padding: 10px 20px;
                background-color: #EE362B;
                color: #000000;
                text-align: center;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            .add-to-cart {
                position: relative;
                line-height: 24px;
                font-weight: 600;
                display: inline-block;
                min-width: 89px;
                color: #FFFFFF;
                text-decoration: none;
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
                background-color: #373535;
                margin: 5% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 400px;
                border-radius: 10px;
                text-align: center;
            }
            .productName{
                position: relative;
                line-height: 24px;
                font-weight: 600;
            }
            .modal-buttons {
                display: flex;
                justify-content: space-around;
                margin-top: 20px;
            }
            .modal-buttons button {
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .modal-buttons .confirm-btn {
                background-color: #EE362B;
                color: #000000;
            }
            .modal-buttons .cancel-btn {
                background-color: #CCCCCC;
                color: #000000;
            }

            table{
                margin:  2%;
            }

            .logout-button{
                background-color: #ddd6c5;
                border: none;
                margin-top: 130%;
                margin-left: -10%;
            }

            #confirmOrderForm button{
                height: 40px;
                font-size: 15px;
                background-color: darkred;
                color: #282e52;
                border-radius: 5px;
                border-color: #fff6ea;
                box-shadow: 2px 2px 2px rgba(255,0,0,0.2);
                color: #ddd6c5;
            }

            #confirmOrderForm button:hover{
                background-color: #fff6ea;
            }
        </style>
    </head>
    <body>
        <div id="sidebar">
            <h2><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 48 48" fill="none">
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
            <div id="gim">
                <button class="sidebar-button" onclick="showOrder()">
                    <svg fill="#282e52" width="32px" height="32px" viewBox="0 -3.84 122.88 122.88" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" style="enable-background:new 0 0 122.88 115.21" xml:space="preserve" transform="matrix(1, 0, 0, 1, 0, 0)rotate(0)"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC" stroke-width="1.9660799999999998"></g><g id="SVGRepo_iconCarrier"> <g> <path d="M29.03,100.46l20.79-25.21l9.51,12.13L41,110.69C33.98,119.61,20.99,110.21,29.03,100.46L29.03,100.46z M53.31,43.05 c1.98-6.46,1.07-11.98-6.37-20.18L28.76,1c-2.58-3.03-8.66,1.42-6.12,5.09L37.18,24c2.75,3.34-2.36,7.76-5.2,4.32L16.94,9.8 c-2.8-3.21-8.59,1.03-5.66,4.7c4.24,5.1,10.8,13.43,15.04,18.53c2.94,2.99-1.53,7.42-4.43,3.69L6.96,18.32 c-2.19-2.38-5.77-0.9-6.72,1.88c-1.02,2.97,1.49,5.14,3.2,7.34L20.1,49.06c5.17,5.99,10.95,9.54,17.67,7.53 c1.03-0.31,2.29-0.94,3.64-1.77l44.76,57.78c2.41,3.11,7.06,3.44,10.08,0.93l0.69-0.57c3.4-2.83,3.95-8,1.04-11.34L50.58,47.16 C51.96,45.62,52.97,44.16,53.31,43.05L53.31,43.05z M65.98,55.65l7.37-8.94C63.87,23.21,99-8.11,116.03,6.29 C136.72,23.8,105.97,66,84.36,55.57l-8.73,11.09L65.98,55.65L65.98,55.65z"></path> </g> </g></svg> 
                    Order     
                </button>
                <button class="sidebar-button" onclick="showMenu()">
                    <svg version="1.1" id="Icons" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 32 32" xml:space="preserve" width="32px" height="32px" fill="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <style type="text/css"> .st0{
                            fill:none;
                            stroke:#282E52;
                            stroke-width:2;
                            stroke-linecap:round;
                            stroke-linejoin:round;
                            stroke-miterlimit:10;
                        } </style> <path class="st0" d="M8,9h15c0.6,0,1,0.4,1,1v18c0,0.6-0.4,1-1,1H9c-0.6,0-1-0.4-1-1V9l11.6-5.3C20.2,3.3,21,3.8,21,4.6V9"></path> <path class="st0" d="M17.1,29l-0.4-3.3c-0.1-1.3,0.3-2.6,1.2-3.5c0.7-0.7,1.1-2,1.1-3.7V13"></path> <path class="st0" d="M13,13v5.5c0,1.7,0.4,2.9,1.1,3.7c0.9,1,1.3,2.2,1.2,3.5L14.9,29"></path> <line class="st0" x1="16" y1="19" x2="16" y2="13"></line> </g></svg>
                    Menu
                </button>
                <button class="sidebar-button" onclick="showBill()"><svg width="32px" height="32px" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg" fill="#282e52" stroke="#282e52"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>currency-revenue</title> <g id="Layer_2" data-name="Layer 2"> <g id="invisible_box" data-name="invisible box"> <rect width="48" height="48" fill="none"></rect> </g> <g id="Q3_icons" data-name="Q3 icons"> <path d="M44,7.1V14a2,2,0,0,1-2,2H35a2,2,0,0,1-2-2.3A2.1,2.1,0,0,1,35.1,12h2.3A18,18,0,0,0,6.1,22.2a2,2,0,0,1-2,1.8h0a2,2,0,0,1-2-2.2A22,22,0,0,1,40,8.9V7a2,2,0,0,1,2.3-2A2.1,2.1,0,0,1,44,7.1Z"></path> <path d="M4,40.9V34a2,2,0,0,1,2-2h7a2,2,0,0,1,2,2.3A2.1,2.1,0,0,1,12.9,36H10.6A18,18,0,0,0,41.9,25.8a2,2,0,0,1,2-1.8h0a2,2,0,0,1,2,2.2A22,22,0,0,1,8,39.1V41a2,2,0,0,1-2.3,2A2.1,2.1,0,0,1,4,40.9Z"></path> <path d="M24.7,22c-3.5-.7-3.5-1.3-3.5-1.8s.2-.6.5-.9a3.4,3.4,0,0,1,1.8-.4,6.3,6.3,0,0,1,3.3.9,1.8,1.8,0,0,0,2.7-.5,1.9,1.9,0,0,0-.4-2.8A9.1,9.1,0,0,0,26,15.3V13a2,2,0,0,0-4,0v2.2c-3,.5-5,2.5-5,5.2s3.3,4.9,6.5,5.5,3.3,1.3,3.3,1.8-1.1,1.4-2.5,1.4h0a6.7,6.7,0,0,1-4.1-1.3,2,2,0,0,0-2.8.6,1.8,1.8,0,0,0,.3,2.6A10.9,10.9,0,0,0,22,32.8V35a2,2,0,0,0,4,0V32.8a6.3,6.3,0,0,0,3-1.3,4.9,4.9,0,0,0,2-4h0C31,23.8,27.6,22.6,24.7,22Z"></path> </g> </g> </g></svg> 
                    Bill Detail 
                </button>

                <button class="sidebar-button" onclick="showProfile()">
                    <svg width="32px" height="32px" viewBox="0 0 20 20" version="1.1" xmlns="http://www.w3.org/2000/svg" fill="#282e52"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <g id="layer1"> <path d="M 10 0 C 4.4830748 0 0 4.4830748 0 10 C 0 15.516925 4.4830748 20 10 20 C 10.686108 20 11.353096 19.923761 12 19.791016 L 12 18.767578 C 11.357372 18.916361 10.689085 19 10 19 C 7.7805094 19 5.7554759 18.195439 4.1875 16.867188 L 4.2871094 16.835938 L 5.0878906 16.615234 L 5.9003906 16.427734 L 6.7167969 16.273438 L 6.9472656 16.216797 L 7.1699219 16.123047 L 7.3710938 15.996094 L 7.5507812 15.835938 L 7.7011719 15.652344 L 7.8222656 15.443359 L 7.9101562 15.220703 L 7.9570312 14.986328 L 7.9707031 14.748047 L 7.9433594 14.509766 L 7.8789062 14.279297 L 7.7792969 14.0625 L 7.6484375 13.865234 L 7.4902344 13.644531 L 7.3535156 13.416016 L 7.0742188 12.855469 L 6.8261719 12.279297 L 6.609375 11.693359 L 6.4238281 11.09375 L 6.2734375 10.486328 L 6.1542969 9.8710938 L 6.0683594 9.2519531 L 6.015625 8.6289062 L 6 8.0019531 L 6.0195312 7.609375 L 6.078125 7.2207031 L 6.171875 6.8398438 L 6.3046875 6.4707031 L 6.4726562 6.1152344 L 6.6738281 5.7792969 L 6.9082031 5.4628906 L 7.1699219 5.171875 L 7.4628906 4.9082031 L 7.7773438 4.6738281 L 8.1152344 4.4746094 L 8.4707031 4.3046875 L 8.8398438 4.1738281 L 9.21875 4.078125 L 9.6074219 4.0195312 L 10 4 L 10.392578 4.0195312 L 10.78125 4.078125 L 11.160156 4.1738281 L 11.529297 4.3046875 L 11.886719 4.4746094 L 12.222656 4.6738281 L 12.537109 4.9082031 L 12.830078 5.171875 L 13.091797 5.4628906 L 13.326172 5.7792969 L 13.527344 6.1152344 L 13.695312 6.4707031 L 13.828125 6.8398438 L 13.923828 7.2207031 L 13.982422 7.609375 L 14 8.0019531 L 13.984375 8.6289062 L 13.931641 9.2519531 L 13.845703 9.8710938 L 13.728516 10.486328 L 13.576172 11.09375 L 13.390625 11.693359 L 13.234375 12.117188 C 13.541423 11.400474 14.085761 10.814969 14.757812 10.429688 L 14.833984 10.035156 L 14.925781 9.359375 L 14.982422 8.6816406 L 15 8.0019531 L 14.982422 7.5664062 L 14.923828 7.1328125 L 14.830078 6.7070312 L 14.697266 6.2910156 L 14.53125 5.8886719 L 14.330078 5.5 L 14.097656 5.1328125 L 13.830078 4.7871094 L 13.537109 4.4648438 L 13.212891 4.171875 L 12.869141 3.90625 L 12.5 3.6699219 L 12.113281 3.46875 L 11.710938 3.3027344 L 11.294922 3.1699219 L 10.867188 3.0761719 L 10.435547 3.0195312 L 10 3 L 9.5644531 3.0195312 L 9.1328125 3.0761719 L 8.7050781 3.1699219 L 8.2890625 3.3027344 L 7.8867188 3.46875 L 7.5 3.6699219 L 7.1328125 3.90625 L 6.7871094 4.171875 L 6.4628906 4.4648438 L 6.1699219 4.7871094 L 5.9042969 5.1328125 L 5.6699219 5.5 L 5.46875 5.8886719 L 5.3027344 6.2910156 L 5.1699219 6.7070312 L 5.0761719 7.1328125 L 5.0195312 7.5664062 L 5 8.0019531 L 5.0175781 8.6816406 L 5.0742188 9.359375 L 5.1660156 10.035156 L 5.2949219 10.703125 L 5.4589844 11.361328 L 5.6601562 12.013672 L 5.8984375 12.652344 L 6.1660156 13.275391 L 6.4707031 13.884766 L 6.6523438 14.193359 L 6.8632812 14.484375 L 6.9296875 14.595703 L 6.9648438 14.720703 L 6.96875 14.847656 L 6.9375 14.974609 L 6.875 15.087891 L 6.7871094 15.181641 L 6.6777344 15.25 L 6.5527344 15.287109 L 5.6953125 15.449219 L 4.84375 15.646484 L 4.0019531 15.878906 L 3.375 16.080078 C 1.9045777 14.478558 1 12.349397 1 10 C 1 5.0235149 5.0235149 1 10 1 C 14.976485 1 19 5.0235149 19 10 C 19 10.320097 18.979986 10.634169 18.947266 10.945312 C 19.294443 11.278158 19.578734 11.675211 19.769531 12.123047 C 19.918106 11.438148 20 10.728954 20 10 C 20 4.4830748 15.516925 0 10 0 z M 16.5 11 C 15.12521 11 14 12.12521 14 13.5 L 14 15 L 13 15 L 13 20 L 20 20 L 20 15 L 19 15 L 19 13.5 C 19 12.12521 17.87479 11 16.5 11 z M 16.5 12 C 17.334349 12 18 12.665651 18 13.5 L 18 15 L 15 15 L 15 13.5 C 15 12.665651 15.665651 12 16.5 12 z M 14 16 L 19 16 L 19 19 L 14 19 L 14 16 z " style="fill:#282e52; fill-opacity:1; stroke:none; stroke-width:0px;"></path> </g> </g></svg> 
                    Profile
                </button>
                <button class="sidebar-button" onclick="showTable()"><svg fill="#282e52" width="32px" height="32px" viewBox="0 0 50 50" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M10.585938 11L0.5859375 21L49.414062 21L39.414062 11L10.585938 11 z M 0 22L0 28L50 28L50 22L0 22 z M 3 29L3 50L9 50L9 29L3 29 z M 11 29L11 43L17 43L17 29L11 29 z M 33 29L33 43L39 43L39 29L33 29 z M 41 29L41 50L47 50L47 29L41 29 z"></path></g></svg> 
                    Table
                </button>

                <div id="logout">
                    <button class="logout-button" onclick="Logout()"><svg width="40px" height="40px" viewBox="0 0 24 24" fill="#282e52" xmlns="http://www.w3.org/2000/svg" stroke="white"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M16.125 12C16.125 11.5858 15.7892 11.25 15.375 11.25L4.40244 11.25L6.36309 9.56944C6.67759 9.29988 6.71401 8.8264 6.44444 8.51191C6.17488 8.19741 5.7014 8.16099 5.38691 8.43056L1.88691 11.4306C1.72067 11.573 1.625 11.7811 1.625 12C1.625 12.2189 1.72067 12.427 1.88691 12.5694L5.38691 15.5694C5.7014 15.839 6.17488 15.8026 6.44444 15.4881C6.71401 15.1736 6.67759 14.7001 6.36309 14.4306L4.40244 12.75L15.375 12.75C15.7892 12.75 16.125 12.4142 16.125 12Z" fill="#282e52"></path> <path d="M9.375 8C9.375 8.70219 9.375 9.05329 9.54351 9.3055C9.61648 9.41471 9.71025 9.50848 9.81946 9.58145C10.0717 9.74996 10.4228 9.74996 11.125 9.74996L15.375 9.74996C16.6176 9.74996 17.625 10.7573 17.625 12C17.625 13.2426 16.6176 14.25 15.375 14.25L11.125 14.25C10.4228 14.25 10.0716 14.25 9.8194 14.4185C9.71023 14.4915 9.6165 14.5852 9.54355 14.6944C9.375 14.9466 9.375 15.2977 9.375 16C9.375 18.8284 9.375 20.2426 10.2537 21.1213C11.1324 22 12.5464 22 15.3748 22L16.3748 22C19.2032 22 20.6174 22 21.4961 21.1213C22.3748 20.2426 22.3748 18.8284 22.3748 16L22.3748 8C22.3748 5.17158 22.3748 3.75736 21.4961 2.87868C20.6174 2 19.2032 2 16.3748 2L15.3748 2C12.5464 2 11.1324 2 10.2537 2.87868C9.375 3.75736 9.375 5.17157 9.375 8Z" fill="#282e52"></path> </g></svg></button>
                </div>
            </div>
        </div>
        <div class="container">     
            <div class="left-column">             
                <div id="idT">
                    <%
                        TableModel selectedTable = (TableModel) session.getAttribute("selectedTable");
                        if (selectedTable != null) {
                            String tableNumber = selectedTable.getTableNumber();
                            String floorID = selectedTable.getFloorID();
                    %>
                    <p class="selected-table"><%= tableNumber %> - <%= floorID %></p>
                    <%
                        } else {
                    %>
                    <p class="selected-table">No table selected.</p>
                    <%
                        }
                    %>
                </div>
                <c:set var="cart" value="${sessionScope.tableCarts[sessionScope.selectedTable.tableID]}"/>
                <c:if test="${cart != null && !cart.items.isEmpty()}">               
                    <table>
                        <tr>
                            <th></th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Action</th>
                        </tr>
                        <c:forEach items="${cart.items}" var="item" varStatus="status">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.foodModel != null}">
                                            <img src="${pageContext.request.contextPath}/image/${item.foodModel.foodImg}" alt="${item.foodModel.foodImg}" width="50" height="50">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/image/${item.drinkModel.drinkImg}" alt="${item.drinkModel.drinkName}" width="50" height="50">
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.foodModel != null}">
                                            ${item.foodModel.foodName}

                                        </c:when>
                                        <c:otherwise>
                                            ${item.drinkModel.drinkName}

                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/process?num=-1&id=${item.foodModel != null ? item.foodModel.foodID : item.drinkModel.drinkID}">-</a>
                                    ${item.quantity}
                                    <a href="${pageContext.request.contextPath}/process?num=1&id=${item.foodModel != null ? item.foodModel.foodID : item.drinkModel.drinkID}">+</a>
                                </td>
                                <td>${item.price}</td>
                                <td>${item.quantity * item.price}</td>
                                <td class="actions">
                                    <form action="${pageContext.request.contextPath}/process" method="post" style="display: inline;">
                                        <input type="hidden" name="foodID" value="${item.foodModel != null ? item.foodModel.foodID : ''}"/>
                                        <input type="hidden" name="drinkID" value="${item.drinkModel != null ? item.drinkModel.drinkID : ''}"/>
                                        <button type="submit" style="background: none; border: none; padding: 0;">
                                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                            <g id="SVGRepo_iconCarrier">
                                            <path d="M10 11V17" stroke="#DDD6C5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                            <path d="M14 11V17" stroke="#DDD6C5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                            <path d="M4 7H20" stroke="#DDD6C5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                            <path d="M6 7H12H18V18C18 19.6569 16.6569 21 15 21H9C7.34315 21 6 19.6569 6 18V7Z" stroke="#DDD6C5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                            <path d="M9 5C9 3.89543 9.89543 3 11 3H13C14.1046 3 15 3.89543 15 5V7H9V5Z" stroke="#DDD6C5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                            </g>
                                            </svg>
                                        </button>
                                    </form>
                                </td>
                            </tr>                
                        </c:forEach>
                    </table>
                </c:if>
                <c:if test="${cart == null || cart.items.isEmpty()}">
                    <div class="empty-cart">
                        <div class="empty-cart-content">
                            <div class="empty-cart-message">
                                <div class="empty-cart-icon">   
                                </div>
                                <div class="you-have-no">
                                    You have no selections in your cart yet
                                </div>
                            </div>
                            <div class="primary-button">                              
                                <div><a class="add-to-cart" href="${pageContext.request.contextPath}/showM">Add Dishes</a></div>

                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="right-column">                  
                <form id="confirmOrderForm" action="${pageContext.request.contextPath}/confirmOrder" method="post">
                    <button type="button" onclick="showModal()">Confirm Order</button>
                </form> 
            </div>
        </div>

        <!-- Modal dialog -->
        <div id="confirmModal" class="modal">
            <div class="modal-content">
                <p>After confirmation, it cannot be edited!!!!</p>
                <div class="modal-buttons">
                    <button class="confirm-btn" onclick="submitForm()">Yes</button>
                    <button class="cancel-btn" onclick="closeModal()">Cancel</button>
                </div>
            </div>
        </div>
        <script>
            // Show the modal
            function showModal() {
                document.getElementById('confirmModal').style.display = 'block';
            }

            // Close the modal
            function closeModal() {
                document.getElementById('confirmModal').style.display = 'none';
            }

            // Submit the form
            function submitForm() {
                document.getElementById('confirmOrderForm').submit();
            }

            // Close the modal if the user clicks outside of it
            window.onclick = function (event) {
                var modal = document.getElementById('confirmModal');
                if (event.target == modal) {
                    modal.style.display = 'none';
                }
            }
        </script>
        <script>
            function showMenu() {
                window.location.href = '${pageContext.request.contextPath}/showM';
            }
            function showOrder() {
                window.location.href = '${pageContext.request.contextPath}/Views/Order/AddCart.jsp';
            }
            function showProfile() {
                window.location.href = '${pageContext.request.contextPath}/Views/Serve/ServeProfile.jsp';
            }
            function showBill() {
                window.location.href = '${pageContext.request.contextPath}/ShowOrder';
            }
            function showTable() {
                window.location.href = '${pageContext.request.contextPath}/Views/Serve/ViewServe.jsp';
            }

            function Logout() {
                window.location.href = '${pageContext.request.contextPath}/logout';
            }
        </script>
    </body>
</html>





