<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix= "fmt" uri= "http://java.sun.com/jsp/jstl/fmt" %>
<c:import url= "/WEB-INF/views/layout/app.jsp">
	<c:param name="content">
		<c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
		<c:choose>
			<c:when test= "${report != null }">
				<h2>日報　詳細ページ</h2>
				
				<table>
					<tbody>
						<tr>
							<td>氏名</td>
							<td><c:out value="${report.employee.name }" /></td>
						</tr>
						<tr>
							<td>日付</td>
							<td><fmt:formatDate value= "${report.report_date }" pattern= "yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td>内容</td>
							<td>
								<pre><c:out value= "${report.content }" /></pre>
							</td>
						</tr>
						<tr>
							<td>登録日時</td>
							<td>
								<fmt:formatDate value= "${report.created_at}" pattern="yyyy-MM-dd" />
							</td>
						</tr>
						<tr>
							<td>変更日時</td>
							<td>
								<fmt:formatDate value="${report.created_at }" pattern= "yyyy-MM-dd"/>
							</td>
						</tr>
					</tbody>
				</table>
				<c:choose>
					<c:when test= "${sessionScope.login_employee.id == report.employee.id }" >
						<p><a href= "<c:url value= "/reports/edit?id=${report.id }" />">この日報を編集する</a></p>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test = "${following_count == 0 }">
								<p><a href= "<c:url value= '/follow?follower_id=${sessionScope.login_employee.id}&followed_id=${report.employee.id}'/>">フォローする</a></p>
							</c:when>
							<c:otherwise>
								<p>フォロー中(<a href= "<c:url value= '/followdelete?follower_id=${sessionScope.login_employee.id}&followed_id=${report.employee.id}'/>">フォローを解除する</a>)</p>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<h2>お探しのデータは見つかりませんでした。</h2>
			</c:otherwise>
		</c:choose>
		
		<p><a href= "<c:url value= "/reports/index" />">一覧に戻る</a></p>
	</c:param>
</c:import>