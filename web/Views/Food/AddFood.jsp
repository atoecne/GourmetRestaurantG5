<%-- 
    Document   : addFood
    Created on : Jun 5, 2024, 8:28:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="DAO.CategoryDAO" %>
<%@ page import="Model.CategoryModel" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Food</title>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            body{
                color: white;
                background-color: #ddd6c5;
            }

            #body{
                display: flex;
                padding-right: 24px;
                align-items: flex-start;
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

            #content {
                margin-right: 2%;
                margin-left: 18%;
                padding:0% 4%;
                background: #ddd6c5;
                width: 100%;
            }

            #gim{
                max-width: 180px;
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

            .logout-button{
                background-color: #ddd6c5;
                border: none;
                margin-top: 15px;
                margin-left: -15px;
            }
            .sidebar-button:hover {
                background-color: rgba(255, 0, 0, 0.5);
                color: black;
            }

            #group{
                display: flex;
                align-items: center;
                width: 80%;
            }
            #bg {
                width: 80%;
                max-width: 900px;
                margin: 30px auto;
                padding: 20px;
                background-color: #282e52;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            #bg h1 {
                text-align: center;
                margin-bottom: 20px;
                color: white;
            }

            #bg label {
                width: 150px;
                display: block;
                margin: 2px 0 2px;
                font-weight: bold;
            }

            #bg input[type="text"],
            #bg input[type="number"],
            #bg input[type="file"],
            #bg select,
            #bg textarea {
                width: calc(100% - 22px);
                padding: 10px;
                border-radius: 5px;
                border: none;
                margin-bottom: 10px;
                background-color: white;
                color: #282e52;
            }


            #bg select {
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                background-color: white;
                border: none;
                border-radius: 5px;
                color: black;
                padding: 10px;
                font-size: 16px;
                outline: none;
            }

            #bg select option {
                background-color: black;
                color: white;
            }
            #bg input[type="submit"],
            #bg button {
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                margin-top: 10px;
            }
            #cancel{
                background-color: #ddd6c5;
                border: none;
            }

            #bg input[type="submit"] {
                background-color: Red;
                color: white;
                margin-right: 10px;
            }

            #bg button {
                background-color: rgba(0,0,0,0);
                color: white;
            }

            #bg input[type="submit"]:hover{
                background-color: white;
                color: black;
            }

            #bg input[type="checkbox"] {
                margin-left: 10px;
            }

            .form-container {
                width: 80%;
                max-width: 900px;
                margin: 30px auto;
                padding: 20px;
                background-color: #444;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .image-container {
                display: flex;
                align-items: center;
                margin-top: 20px;
            }
            #image-preview {

                max-width: 150px;
                max-height: 150px;
                border-radius: 8px;
                display: none;
            }
            .image-upload{
                display: flex;
            }

            #group #foodID{
                background-color: gray;
                color: #ddd6c5;
            }
        </style>
    </head>
    <body>
        <%
            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryModel> categories = categoryDAO.getAllCategory();

        %>
        <div id="body">
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
                    <button class="sidebar-button" onclick="showAccount()"><svg width="32px" height="32px" viewBox="0 0 20 20" version="1.1" xmlns="http://www.w3.org/2000/svg" fill="#282e52"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <g id="layer1"> <path d="M 10 0 C 4.4830748 0 0 4.4830748 0 10 C 0 15.516925 4.4830748 20 10 20 C 10.686108 20 11.353096 19.923761 12 19.791016 L 12 18.767578 C 11.357372 18.916361 10.689085 19 10 19 C 7.7805094 19 5.7554759 18.195439 4.1875 16.867188 L 4.2871094 16.835938 L 5.0878906 16.615234 L 5.9003906 16.427734 L 6.7167969 16.273438 L 6.9472656 16.216797 L 7.1699219 16.123047 L 7.3710938 15.996094 L 7.5507812 15.835938 L 7.7011719 15.652344 L 7.8222656 15.443359 L 7.9101562 15.220703 L 7.9570312 14.986328 L 7.9707031 14.748047 L 7.9433594 14.509766 L 7.8789062 14.279297 L 7.7792969 14.0625 L 7.6484375 13.865234 L 7.4902344 13.644531 L 7.3535156 13.416016 L 7.0742188 12.855469 L 6.8261719 12.279297 L 6.609375 11.693359 L 6.4238281 11.09375 L 6.2734375 10.486328 L 6.1542969 9.8710938 L 6.0683594 9.2519531 L 6.015625 8.6289062 L 6 8.0019531 L 6.0195312 7.609375 L 6.078125 7.2207031 L 6.171875 6.8398438 L 6.3046875 6.4707031 L 6.4726562 6.1152344 L 6.6738281 5.7792969 L 6.9082031 5.4628906 L 7.1699219 5.171875 L 7.4628906 4.9082031 L 7.7773438 4.6738281 L 8.1152344 4.4746094 L 8.4707031 4.3046875 L 8.8398438 4.1738281 L 9.21875 4.078125 L 9.6074219 4.0195312 L 10 4 L 10.392578 4.0195312 L 10.78125 4.078125 L 11.160156 4.1738281 L 11.529297 4.3046875 L 11.886719 4.4746094 L 12.222656 4.6738281 L 12.537109 4.9082031 L 12.830078 5.171875 L 13.091797 5.4628906 L 13.326172 5.7792969 L 13.527344 6.1152344 L 13.695312 6.4707031 L 13.828125 6.8398438 L 13.923828 7.2207031 L 13.982422 7.609375 L 14 8.0019531 L 13.984375 8.6289062 L 13.931641 9.2519531 L 13.845703 9.8710938 L 13.728516 10.486328 L 13.576172 11.09375 L 13.390625 11.693359 L 13.234375 12.117188 C 13.541423 11.400474 14.085761 10.814969 14.757812 10.429688 L 14.833984 10.035156 L 14.925781 9.359375 L 14.982422 8.6816406 L 15 8.0019531 L 14.982422 7.5664062 L 14.923828 7.1328125 L 14.830078 6.7070312 L 14.697266 6.2910156 L 14.53125 5.8886719 L 14.330078 5.5 L 14.097656 5.1328125 L 13.830078 4.7871094 L 13.537109 4.4648438 L 13.212891 4.171875 L 12.869141 3.90625 L 12.5 3.6699219 L 12.113281 3.46875 L 11.710938 3.3027344 L 11.294922 3.1699219 L 10.867188 3.0761719 L 10.435547 3.0195312 L 10 3 L 9.5644531 3.0195312 L 9.1328125 3.0761719 L 8.7050781 3.1699219 L 8.2890625 3.3027344 L 7.8867188 3.46875 L 7.5 3.6699219 L 7.1328125 3.90625 L 6.7871094 4.171875 L 6.4628906 4.4648438 L 6.1699219 4.7871094 L 5.9042969 5.1328125 L 5.6699219 5.5 L 5.46875 5.8886719 L 5.3027344 6.2910156 L 5.1699219 6.7070312 L 5.0761719 7.1328125 L 5.0195312 7.5664062 L 5 8.0019531 L 5.0175781 8.6816406 L 5.0742188 9.359375 L 5.1660156 10.035156 L 5.2949219 10.703125 L 5.4589844 11.361328 L 5.6601562 12.013672 L 5.8984375 12.652344 L 6.1660156 13.275391 L 6.4707031 13.884766 L 6.6523438 14.193359 L 6.8632812 14.484375 L 6.9296875 14.595703 L 6.9648438 14.720703 L 6.96875 14.847656 L 6.9375 14.974609 L 6.875 15.087891 L 6.7871094 15.181641 L 6.6777344 15.25 L 6.5527344 15.287109 L 5.6953125 15.449219 L 4.84375 15.646484 L 4.0019531 15.878906 L 3.375 16.080078 C 1.9045777 14.478558 1 12.349397 1 10 C 1 5.0235149 5.0235149 1 10 1 C 14.976485 1 19 5.0235149 19 10 C 19 10.320097 18.979986 10.634169 18.947266 10.945312 C 19.294443 11.278158 19.578734 11.675211 19.769531 12.123047 C 19.918106 11.438148 20 10.728954 20 10 C 20 4.4830748 15.516925 0 10 0 z M 16.5 11 C 15.12521 11 14 12.12521 14 13.5 L 14 15 L 13 15 L 13 20 L 20 20 L 20 15 L 19 15 L 19 13.5 C 19 12.12521 17.87479 11 16.5 11 z M 16.5 12 C 17.334349 12 18 12.665651 18 13.5 L 18 15 L 15 15 L 15 13.5 C 15 12.665651 15.665651 12 16.5 12 z M 14 16 L 19 16 L 19 19 L 14 19 L 14 16 z " style="fill:#282e52; fill-opacity:1; stroke:none; stroke-width:0px;"></path> </g> </g></svg> Account</button>
                    <button class="sidebar-button" onclick="showFoodMenu()"><svg fill="#282e52" width="32px" height="32px" viewBox="0 -3.84 122.88 122.88" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" style="enable-background:new 0 0 122.88 115.21" xml:space="preserve" transform="matrix(1, 0, 0, 1, 0, 0)rotate(0)"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC" stroke-width="1.9660799999999998"></g><g id="SVGRepo_iconCarrier"> <g> <path d="M29.03,100.46l20.79-25.21l9.51,12.13L41,110.69C33.98,119.61,20.99,110.21,29.03,100.46L29.03,100.46z M53.31,43.05 c1.98-6.46,1.07-11.98-6.37-20.18L28.76,1c-2.58-3.03-8.66,1.42-6.12,5.09L37.18,24c2.75,3.34-2.36,7.76-5.2,4.32L16.94,9.8 c-2.8-3.21-8.59,1.03-5.66,4.7c4.24,5.1,10.8,13.43,15.04,18.53c2.94,2.99-1.53,7.42-4.43,3.69L6.96,18.32 c-2.19-2.38-5.77-0.9-6.72,1.88c-1.02,2.97,1.49,5.14,3.2,7.34L20.1,49.06c5.17,5.99,10.95,9.54,17.67,7.53 c1.03-0.31,2.29-0.94,3.64-1.77l44.76,57.78c2.41,3.11,7.06,3.44,10.08,0.93l0.69-0.57c3.4-2.83,3.95-8,1.04-11.34L50.58,47.16 C51.96,45.62,52.97,44.16,53.31,43.05L53.31,43.05z M65.98,55.65l7.37-8.94C63.87,23.21,99-8.11,116.03,6.29 C136.72,23.8,105.97,66,84.36,55.57l-8.73,11.09L65.98,55.65L65.98,55.65z"></path> </g> </g></svg> Food</button>
                    <button class="sidebar-button" onclick="showDrinkMenu()"><svg width="32px" height="32px" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M11 30.9998H25M18 30.9998V17.9998L29 6.99981H7L16 15.9998M12.2051 4.01351C11.1701 2.21251 9.2261 0.999512 7.0001 0.999512C3.6861 0.999512 1.0001 3.68651 1.0001 6.99951C1.0001 10.3135 3.6861 12.9995 7.0001 12.9995C8.6561 12.9995 10.1561 12.3275 11.2421 11.2425M29 0.999812L23 6.99981" stroke="#282e52" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg> Drink</button>
                    <button class="sidebar-button" onclick="showTableList()"><svg fill="#282e52" width="32px" height="32px" viewBox="0 0 50 50" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M10.585938 11L0.5859375 21L49.414062 21L39.414062 11L10.585938 11 z M 0 22L0 28L50 28L50 22L0 22 z M 3 29L3 50L9 50L9 29L3 29 z M 11 29L11 43L17 43L17 29L11 29 z M 33 29L33 43L39 43L39 29L33 29 z M 41 29L41 50L47 50L47 29L41 29 z"></path></g></svg> Table</button>
                    <button class="sidebar-button" onclick="showCustomerList()"><svg width="32px" height="32px" viewBox="0 -2 1028 1028" fill="#282e52" class="icon" version="1.1" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M91.448447 896c-50.086957 0-91.428571-40.546584-91.428571-91.428571V91.428571C0.019876 41.341615 40.56646 0 91.448447 0h671.006211c50.086957 0 91.428571 40.546584 91.428572 91.428571v337.093168l-3.180124-0.795031c-13.515528-3.975155-26.236025-5.565217-40.546584-5.565217h-0.795031l-0.795031-2.385093h-2.385094V91.428571c0-23.055901-20.670807-43.726708-43.726708-43.726708H91.448447c-23.055901 0-43.726708 20.670807-43.726708 43.726708v713.142858c0 23.055901 20.670807 43.726708 43.726708 43.726708h352.198758l0.795031 0.795031c8.745342 11.925466 3.975155 20.670807 0.795031 27.031056-3.180124 5.565217-4.770186 9.540373 0.795031 15.10559l4.770186 4.770186H91.448447z" fill=""></path><path d="M143.125466 174.906832c-8.745342 0-15.900621-11.130435-15.900621-24.645962 0-13.515528 7.15528-24.645963 15.900621-24.645963h270.310559c8.745342 0 15.900621 11.130435 15.900621 24.645963 0 13.515528-7.15528 24.645963-15.900621 24.645962h-270.310559z" fill=""></path><path d="M413.436025 128h-270.310559c-7.15528 0-13.515528 9.540373-13.515528 22.26087s6.360248 22.26087 13.515528 22.260869h270.310559c7.15528 0 13.515528-9.540373 13.515528-22.260869s-5.565217-22.26087-13.515528-22.26087zM139.945342 302.111801c-7.15528 0-12.720497-10.335404-12.720497-24.645962s5.565217-24.645963 12.720497-24.645963h193.987577c7.15528 0 12.720497 10.335404 12.720497 24.645963s-5.565217 24.645963-12.720497 24.645962H139.945342z" fill=""></path><path d="M333.932919 255.204969H139.945342c-5.565217 0-9.540373 9.540373-9.540373 22.26087s3.975155 22.26087 9.540373 22.260869h193.987577c5.565217 0 9.540373-9.540373 9.540373-22.260869s-4.770186-22.26087-9.540373-22.26087zM734.628571 1024c-27.826087 0-58.037267-1.590062-96.993788-4.770186-56.447205-4.770186-108.124224-31.006211-158.211181-79.503106L253.634783 718.708075c-52.47205-50.881988-54.857143-117.664596-7.950311-168.546584 19.875776-20.670807 50.881988-33.391304 84.273292-33.391305 33.391304 0 63.602484 12.720497 82.68323 34.981367 0.795031 0.795031 2.385093 2.385093 5.565217 3.975155 0.795031 0.795031 2.385093 1.590062 3.180124 2.385093V451.57764v-52.47205c0-40.546584 0-81.888199 0.795031-122.434783 0.795031-60.42236 47.701863-106.534161 109.714286-106.534161h0.795031c59.627329 0 104.944099 43.726708 108.124224 103.354037 0.795031 13.515528 0.795031 27.826087 0 42.136646v18.285714h11.925466c41.341615 0 73.142857 14.310559 96.198757 44.52174 0.795031 1.590062 5.565217 3.180124 11.925466 3.180124 2.385093 0 4.770186 0 6.360249-0.795031 7.15528-0.795031 14.310559-1.590062 20.670807-1.590062 31.801242 0 59.627329 12.720497 83.478261 38.956521 3.975155 3.975155 12.720497 7.15528 20.670807 7.15528h3.180125c5.565217-0.795031 11.925466-1.590062 17.490683-1.590062 59.627329 0 107.329193 42.136646 108.124224 96.993789 2.385093 100.968944 3.975155 200.347826-7.15528 298.931677-13.515528 119.254658-77.118012 182.857143-201.142857 198.757764-23.055901 3.975155-49.291925 5.565217-77.913044 5.565217zM325.982609 562.086957c-16.695652 0-32.596273 6.360248-44.521739 17.490683-14.310559 14.310559-22.26087 31.006211-22.26087 49.291925 0 19.080745 8.745342 38.161491 24.645963 54.062112l30.21118 30.21118c65.987578 65.192547 134.360248 131.975155 202.732919 197.962733 33.391304 31.801242 71.552795 52.47205 113.689441 60.42236 32.596273 6.360248 65.192547 9.540373 96.993789 9.540373 28.621118 0 57.242236-2.385093 85.068323-7.950311 100.968944-18.285714 147.080745-66.782609 156.621118-160.596273 8.745342-89.838509 7.950311-182.062112 6.360248-271.10559v-14.310559c-0.795031-32.596273-23.850932-54.857143-56.447205-54.857143-8.745342 0-16.695652 1.590062-25.440993 4.770187V601.043478c0 11.130435 0 32.596273-22.26087 32.596274h-0.795031c-7.15528 0-12.720497-1.590062-15.900621-5.565218-6.360248-6.360248-7.15528-18.285714-7.15528-27.826087v-4.770186c0-36.571429 0.795031-73.937888 0-111.304348-0.795031-32.596273-23.850932-55.652174-55.652174-55.652174-7.950311 0-15.900621 1.590062-23.0559 3.975155v128.795031c0 11.130435-2.385093 19.875776-7.950311 25.440994-3.975155 3.975155-9.540373 6.360248-16.695652 6.360249h-0.795031c-21.465839-0.795031-21.465839-23.055901-21.465838-31.006211v-52.47205-66.782609c0-15.10559-6.360248-31.006211-18.285715-42.931677-11.130435-11.130435-26.236025-17.490683-41.341615-17.490683-6.360248 0-13.515528 0.795031-19.875776 3.180124V442.832298c0 27.031056 0 55.652174-1.590062 83.478261-0.795031 7.15528-7.15528 12.720497-13.515528 18.285714-2.385093 2.385093-5.565217 4.770186-7.950311 7.15528l-2.385093 2.385093-1.590062-3.975155c-1.590062-2.385093-3.975155-4.770186-6.360248-6.360249-4.770186-5.565217-10.335404-11.130435-13.515528-17.490683-2.385093-4.770186-1.590062-10.335404-1.590062-15.10559v-6.360249-69.167701c0-50.881988 0-103.354037-0.795032-155.031056 0-38.161491-24.645963-63.602484-60.42236-64.397516-38.956522 0-65.192547 27.826087-65.192546 68.372671v374.459627l-10.335404 6.360249-0.795031-1.590062c-7.15528-7.950311-15.10559-15.900621-22.26087-23.850932-16.695652-17.490683-34.186335-36.571429-51.677018-54.062112-15.900621-15.10559-35.776398-23.850932-56.447205-23.850931z" fill=""></path></g></svg>Customer</button>
                    <button class="sidebar-button" onclick="showRevenueReport()"><svg width="32px" height="32px" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg" fill="#282e52" stroke="#282e52"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>currency-revenue</title> <g id="Layer_2" data-name="Layer 2"> <g id="invisible_box" data-name="invisible box"> <rect width="48" height="48" fill="none"></rect> </g> <g id="Q3_icons" data-name="Q3 icons"> <path d="M44,7.1V14a2,2,0,0,1-2,2H35a2,2,0,0,1-2-2.3A2.1,2.1,0,0,1,35.1,12h2.3A18,18,0,0,0,6.1,22.2a2,2,0,0,1-2,1.8h0a2,2,0,0,1-2-2.2A22,22,0,0,1,40,8.9V7a2,2,0,0,1,2.3-2A2.1,2.1,0,0,1,44,7.1Z"></path> <path d="M4,40.9V34a2,2,0,0,1,2-2h7a2,2,0,0,1,2,2.3A2.1,2.1,0,0,1,12.9,36H10.6A18,18,0,0,0,41.9,25.8a2,2,0,0,1,2-1.8h0a2,2,0,0,1,2,2.2A22,22,0,0,1,8,39.1V41a2,2,0,0,1-2.3,2A2.1,2.1,0,0,1,4,40.9Z"></path> <path d="M24.7,22c-3.5-.7-3.5-1.3-3.5-1.8s.2-.6.5-.9a3.4,3.4,0,0,1,1.8-.4,6.3,6.3,0,0,1,3.3.9,1.8,1.8,0,0,0,2.7-.5,1.9,1.9,0,0,0-.4-2.8A9.1,9.1,0,0,0,26,15.3V13a2,2,0,0,0-4,0v2.2c-3,.5-5,2.5-5,5.2s3.3,4.9,6.5,5.5,3.3,1.3,3.3,1.8-1.1,1.4-2.5,1.4h0a6.7,6.7,0,0,1-4.1-1.3,2,2,0,0,0-2.8.6,1.8,1.8,0,0,0,.3,2.6A10.9,10.9,0,0,0,22,32.8V35a2,2,0,0,0,4,0V32.8a6.3,6.3,0,0,0,3-1.3,4.9,4.9,0,0,0,2-4h0C31,23.8,27.6,22.6,24.7,22Z"></path> </g> </g> </g></svg> Revenue</button>
                    <button class="sidebar-button" onclick="showCouponList()"><svg width="32px" height="32px" viewBox="0 0 1024 1024" fill="#282e52" class="icon" version="1.1" xmlns="http://www.w3.org/2000/svg" stroke="#282e52"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><path d="M99.2 883.2c-41.6 0-75.2-34.4-75.2-76.8V648c32.8-7.2 59.2-24 73.6-47.2 8-13.6 22.4-39.2 22.4-65.6 0-24.8-12.8-47.2-21.6-62.4-15.2-24-44-44-74.4-50.4V264c0-42.4 33.6-76.8 75.2-76.8h825.6c41.6 0 75.2 34.4 75.2 76.8v158.4c-29.6 7.2-55.2 25.6-71.2 52l-2.4 4.8c-10.4 16.8-21.6 35.2-21.6 55.2s11.2 39.2 21.6 55.2l3.2 4.8c16 26.4 41.6 45.6 71.2 52v159.2c0 42.4-33.6 76.8-75.2 76.8H99.2z m249.6-48h604.8V692h-0.8c-62.4-39.2-98.4-96.8-98.4-156.8 0-60 36-117.6 98.4-156.8h0.8V236H348.8v72.8h-55.2v-72.8H70.4v135.2h0.8c47.2 29.6 98.4 88 98.4 164S118.4 669.6 71.2 699.2h-0.8v136h223.2v-72.8h55.2v72.8z" fill=""></path><path d="M721.6 559.2h-75.2c-3.2 0-5.6 0.8-8.8 2.4-2.4 1.6-4 0-4 3.2s0 6.4 0.8 10.4 0.8 3.2 0.8 5.6c0 4.8 4 6.4 11.2 4.8H720c7.2 0 11.2 4 11.2 11.2v24c0 8-5.6 12-16 12h-22.4c-5.6 0-12 0-18.4 0.8-6.4 0.8-12.8 0.8-17.6 0.8h-10.4c-6.4 0-10.4 4-10.4 12v55.2c0 4-3.2 6.4-8.8 6.4h-26.4c-9.6 0-14.4-4-14.4-12.8 0-3.2 0-10.4 0.8-20.8s0-25.6-0.8-29.6c0-4-0.8-6.4-0.8-8-0.8-0.8-3.2-1.6-6.4-1.6H504c-9.6 0-15.2-4.8-15.2-16 0-2.4 0-5.6-0.8-8.8-0.8-3.2-0.8-4.8 0-8.8 0.8-4 2.4-7.2 3.2-11.2 1.6-3.2 4.8-4 8.8-3.2h81.6c1.6 0 2.4-1.6 2.4-6.4v-16c0-4-4.8-5.6-13.6-5.6h-71.2c-3.2 0-5.6 0-8-0.8-2.4 0-3.2-2.4-3.2-6.4v-12c0-6.4 0-11.2-0.8-14.4-1.6-4.8 0-8.8 3.2-12 4-3.2 8.8-4.8 15.2-4.8 6.4 0.8 12.8 0.8 20 0.8 7.2-0.8 8-0.8 12-0.8h13.6c4.8 0 6.4-1.6 4.8-4.8-1.6-1.6-2.4-8-9.6-17.6-8-10.4-19.2-27.2-28-38.4-9.6-13.6-21.6-28.8-34.4-44-5.6-8.8-4-16 3.2-21.6 4-2.4 8-5.6 12-8.8 4-3.2 8.8-7.2 14.4-10.4 7.2-4.8 14.4-0.8 21.6 12.8 2.4 3.2 6.4 8.8 12.8 17.6s12.8 16.8 20 27.2c6.4 9.6 13.6 18.4 19.2 25.6 6.4 8 9.6 12 10.4 13.6 1.6 3.2 4 4.8 8 5.6 4 0.8 7.2-0.8 8.8-4 0.8-1.6 4-6.4 9.6-15.2 5.6-8 12-17.6 19.2-28 7.2-10.4 13.6-20 19.2-29.6 6.4-8.8 9.6-15.2 11.2-17.6 4-5.6 7.2-8.8 9.6-10.4 2.4-1.6 7.2-0.8 13.6 0.8 4 1.6 8 4 12 7.2 4.8 3.2 8 5.6 8.8 7.2 8 8.8 10.4 16 7.2 22.4-1.6 2.4-5.6 8.8-12.8 18.4-7.2 9.6-14.4 20-22.4 30.4-8 10.4-15.2 27.2-21.6 36.8-6.4 9.6-10.4 15.2-11.2 16.8-2.4 4-1.6 6.4 3.2 7.2 4 0.8 8 1.6 12 1.6h46.4c4 0 7.2 1.6 9.6 4.8 2.4 3.2 4 6.4 4 9.6v26.4c0.8 6.4-3.2 8.8-10.4 8.8zM292.8 387.2h56.8v121.6h-56.8V387.2zM349.6 705.6h-56.8V562.4h56.8v143.2z" fill=""></path></g></svg> Coupon</button>
                    <button class="sidebar-button" onclick="showLogList()"><svg width="32px" height="32px" viewBox="0 0 512 512" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>log</title> <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"> <g id="log-white" fill="#282e52" transform="translate(85.572501, 42.666667)"> <path d="M236.349632,7.10542736e-15 L1.68296533,7.10542736e-15 L1.68296533,234.666667 L44.349632,234.666667 L44.349632,192 L44.349632,169.6 L44.349632,42.6666667 L218.642965,42.6666667 L300.349632,124.373333 L300.349632,169.6 L300.349632,192 L300.349632,234.666667 L343.016299,234.666667 L343.016299,106.666667 L236.349632,7.10542736e-15 L236.349632,7.10542736e-15 Z M4.26325641e-14,405.333333 L4.26325641e-14,277.360521 L28.8096875,277.360521 L28.8096875,382.755208 L83.81,382.755208 L83.81,405.333333 L4.26325641e-14,405.333333 Z M153.17,275.102708 C173.279583,275.102708 188.692917,281.484792 199.41,294.248958 C209.705625,306.47125 214.853437,322.185625 214.853437,341.392083 C214.853437,362.404792 208.772396,379.112604 196.610312,391.515521 C186.134062,402.232604 171.653958,407.591146 153.17,407.591146 C133.060417,407.591146 117.647083,401.209062 106.93,388.444896 C96.634375,376.222604 91.4865625,360.267396 91.4865625,340.579271 C91.4865625,319.988021 97.5676042,303.490937 109.729687,291.088021 C120.266146,280.431146 134.74625,275.102708 153.17,275.102708 Z M153.079687,297.680833 C142.663646,297.680833 134.625833,302.015833 128.96625,310.685833 C123.848542,318.512917 121.289687,328.567708 121.289687,340.850208 C121.289687,355.059375 124.330208,366.0775 130.41125,373.904583 C136.131042,381.310208 143.717292,385.013021 153.17,385.013021 C163.525833,385.013021 171.59375,380.647917 177.37375,371.917708 C182.491458,364.211042 185.050312,354.035833 185.050312,341.392083 C185.050312,327.483958 182.009792,316.616354 175.92875,308.789271 C170.208958,301.383646 162.592604,297.680833 153.079687,297.680833 Z M343.91,333.715521 L343.91,399.011458 C336.564583,401.48 331.386667,403.105625 328.37625,403.888333 C319.043958,406.356875 309.019271,407.591146 298.302187,407.591146 C277.229271,407.591146 261.18375,402.292812 250.165625,391.696146 C237.943333,380.015729 231.832187,363.729375 231.832187,342.837083 C231.832187,318.813958 239.418437,300.69125 254.590937,288.468958 C265.609062,279.558125 280.480521,275.102708 299.205312,275.102708 C315.220729,275.102708 330.122292,278.022812 343.91,283.863021 L334.065937,306.350833 C327.563437,303.099583 321.87375,300.826719 316.996875,299.53224 C312.12,298.23776 306.761458,297.590521 300.92125,297.590521 C286.952917,297.590521 276.657292,302.13625 270.034375,311.227708 C264.435,318.934375 261.635312,329.079479 261.635312,341.663021 C261.635312,356.775312 265.849896,368.154687 274.279062,375.801146 C281.022396,381.942396 289.391354,385.013021 299.385937,385.013021 C305.226146,385.013021 310.765312,384.019583 316.003437,382.032708 L316.003437,356.293646 L293.967187,356.293646 L293.967187,333.715521 L343.91,333.715521 Z" id="XLS"> </path> </g> </g> </g></svg> Log</button>

                    <div id="logout">
                        <button class="logout-button" onclick="Logout()"><svg width="40px" height="40px" viewBox="0 0 24 24" fill="#282e52" xmlns="http://www.w3.org/2000/svg" stroke="white"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M16.125 12C16.125 11.5858 15.7892 11.25 15.375 11.25L4.40244 11.25L6.36309 9.56944C6.67759 9.29988 6.71401 8.8264 6.44444 8.51191C6.17488 8.19741 5.7014 8.16099 5.38691 8.43056L1.88691 11.4306C1.72067 11.573 1.625 11.7811 1.625 12C1.625 12.2189 1.72067 12.427 1.88691 12.5694L5.38691 15.5694C5.7014 15.839 6.17488 15.8026 6.44444 15.4881C6.71401 15.1736 6.67759 14.7001 6.36309 14.4306L4.40244 12.75L15.375 12.75C15.7892 12.75 16.125 12.4142 16.125 12Z" fill="#282e52"></path> <path d="M9.375 8C9.375 8.70219 9.375 9.05329 9.54351 9.3055C9.61648 9.41471 9.71025 9.50848 9.81946 9.58145C10.0717 9.74996 10.4228 9.74996 11.125 9.74996L15.375 9.74996C16.6176 9.74996 17.625 10.7573 17.625 12C17.625 13.2426 16.6176 14.25 15.375 14.25L11.125 14.25C10.4228 14.25 10.0716 14.25 9.8194 14.4185C9.71023 14.4915 9.6165 14.5852 9.54355 14.6944C9.375 14.9466 9.375 15.2977 9.375 16C9.375 18.8284 9.375 20.2426 10.2537 21.1213C11.1324 22 12.5464 22 15.3748 22L16.3748 22C19.2032 22 20.6174 22 21.4961 21.1213C22.3748 20.2426 22.3748 18.8284 22.3748 16L22.3748 8C22.3748 5.17158 22.3748 3.75736 21.4961 2.87868C20.6174 2 19.2032 2 16.3748 2L15.3748 2C12.5464 2 11.1324 2 10.2537 2.87868C9.375 3.75736 9.375 5.17157 9.375 8Z" fill="#282e52"></path> </g></svg></button>
                    </div>
                </div>
            </div>

            <div id="content">
                <form action="${pageContext.request.contextPath}/addF" method="post" enctype="multipart/form-data">
                    <button type="button" onclick="cancelForm()" id="cancel">
                        <svg width="32px" height="32px" viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg" 
                             fill="#000000" stroke="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                        <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                        <g id="SVGRepo_iconCarrier"> <defs> 
                        <style>.cls-1{
                                fill:none;
                                stroke:#282e52;
                                stroke-linecap:round;
                                stroke-linejoin:round;
                                stroke-width:20px;
                            }</style> 
                        </defs> <g data-name="Layer 2" id="Layer_2"> 
                        <g data-name="E421, Back, buttons, multimedia, play, stop" 
                           id="E421_Back_buttons_multimedia_play_stop"> <circle class="cls-1" cx="256" cy="256" r="246"></circle> 
                        <line class="cls-1" x1="352.26" x2="170.43" y1="256" y2="256"></line> 
                        <polyline class="cls-1" points="223.91 202.52 170.44 256 223.91 309.48"></polyline> 
                        </g> </g> </g></svg>
                    </button>

                    <div id="bg">
                        <h1>Add Food</h1>
                        <div id="group">
                            <label for="foodID">Food ID:              </label>
                            <input type="text" id="foodID" name="foodID" value="${newFoodID}" readonly>
                        </div>
                        <br>
                        <div id="group">
                            <label for="foodName">Food Name:</label>
                            <input type="text" id="foodName" name="foodName" required>
                        </div>
                        <br>
                        <div id="group">
                            <label for="categoryID">Category Name:    </label>
                            <select id="categoryID" name="categoryID" required>
                                <%
                                    for (CategoryModel category : categories) {
                                %>
                                <option value="<%= category.getCategoryID()%>"><%= category.getCategoryName()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <br>
                        <div id="group">
                            <label for="description">Food Description:</label>
                            <textarea id="description" name="description" required></textarea>
                        </div>
                        <br>
                        <div id="group">
                            <label for="price">Price:                 </label>
                            <input type="number" id="price" name="price" required>  
                        </div>
                        <br>
                        <div id="group">
                            <label for="quantity">Quantity :                  </label>
                            <input type="number" id="quantity" name="quantity" required> 
                        </div>
                        <br>
                        <div id="group">
                            <label for="unit">Unit :                  </label>
                            <input type="text" id="unit" name="unit" required> 
                        </div>
                        <br>
                        <div id="group">
                            <label for="foodImg">Food Image:</label>
                            <input id="foodImg" type="file" name="foodImg" accept="image/*" required="" onchange="previewImage(event)"/>                            
                        </div>
                        <img id="image-preview" alt="Image Preview"/>
                        <br>
                        <input type="submit" value="Add Food">
                    </div>

                </form>
            </div>     
        </div>
        <script>
            function updateStatus(checkbox) {
                if (checkbox.checked) {
                    checkbox.value = 1;
                }
            }

            function cancelForm() {
                window.location.href = '${pageContext.request.contextPath}/showF';
            }
        </script>
        <script>
            function previewImage(event) {
                var reader = new FileReader();
                reader.onload = function () {
                    var output = document.getElementById('image-preview');
                    output.src = reader.result;
                    output.style.display = 'block';
                };
                reader.readAsDataURL(event.target.files[0]);
            }

            window.onload = function () {
                var add = '<c:out value="${add}"/>';
                if (add.trim() !== '') {
                    var errorMessage = document.getElementById('error-message');
                    errorMessage.textContent = add;
                    errorMessage.style.display = 'block';

                    setTimeout(function () {
                        errorMessage.style.display = 'none';
                    }, 3000);
                }
            }
            ;
        </script>
        <script>
            function cancelForm() {
                window.location.href = '${pageContext.request.contextPath}/showF';
            }
            function showAccount()
            {
                window.location.href = '${pageContext.request.contextPath}/listAccount';
            }


            function showFoodMenu() {
                window.location.href = '${pageContext.request.contextPath}/showF';
            }


            function showDrinkMenu() {
                window.location.href = '${pageContext.request.contextPath}/showD';
            }


            function showTableList() {
                window.location.href = '${pageContext.request.contextPath}/ShowT';
            }


            function showCouponList() {
                window.location.href = '${pageContext.request.contextPath}/showC';
            }


            function showLogList() {
                window.location.href = '${pageContext.request.contextPath}/showL';
            }


            function showCustomerList() {
                window.location.href = '${pageContext.request.contextPath}/listCustomer?action=list';
            }


            function showRevenueReport() {
                window.location.href = '${pageContext.request.contextPath}/revenueReport';
            }

            function Logout() {
                window.location.href = '${pageContext.request.contextPath}/logout';
            }
        </script>

        <div id="error-message" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: rgba(255, 0, 0, 0.8); color: white; padding: 20px; border-radius: 8px; z-index: 1000;"></div>
    </body>
</html>
