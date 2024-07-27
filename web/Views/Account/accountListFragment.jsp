<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty infomation}">
    <c:forEach items="${infomation}" var="i">
        <tr id="infoF">
            <td>${i.email}</td>
            <td>${i.fullName}</td>
            <td>${i.birthday}</td>
            <td>${i.phoneNumber}</td>
            <td>${i.address}</td>
            <td>${i.role}</td>
            <td id="action">

                <form action="${pageContext.request.contextPath}/updateRole" style="display:inline;">
                    <input type="hidden" name="email" value="${i.email}">
                        <button type="submit" class="btn btn-link" value="Update Role">
                            <svg width="24px" height="24px" viewBox="0 0 24.00 24.00" xmlns="http://www.w3.org/2000/svg" fill="#DDD6C5" stroke="#DDD6C5" transform="rotate(0)"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC" stroke-width="0.096"></g><g id="SVGRepo_iconCarrier"> <title></title> <g id="Complete"> <g id="edit"> <g> <path d="M20,16v4a2,2,0,0,1-2,2H4a2,2,0,0,1-2-2V6A2,2,0,0,1,4,4H8" fill="none" stroke="#DDD6C5" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"></path> <polygon fill="none" points="12.5 15.8 22 6.2 17.8 2 8.3 11.5 8 16 12.5 15.8" stroke="#DDD6C5" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"></polygon> </g> </g> </g> </g></svg> </button>
                </form>
                <form id="deleteTableForm" action="${pageContext.request.contextPath}/deleteAccount" method="post" onsubmit="return confirmDelete(this, '${i.email}')">
                     <input type="hidden" name="email" value="${i.email}">
                        <button type="submit" class="btn btn-link" id="buttonD">
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
</c:if>
