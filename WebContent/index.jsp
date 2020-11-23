<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mancala</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/mancala.css">
</head>
<body>
<section class="Mancala">
	<div class="game-information">
		<h1 class="title">Mancala</h1>
		<c:choose>
			<c:when test="${game.getDisable() == 'B'}">
				<h3 class="game-player">Current Turn: Player 2</h3>
			</c:when>
			<c:when test="${game.getDisable() == 'A'}">
				<h3 class="game-player">Current Turn: Player 1</h3>
			</c:when>
			<c:otherwise>
				<h3 class=game-player>Start a new game!</h3>
			</c:otherwise>
		</c:choose>
		<c:if test="${not empty game.getMessage()}">
			<h3 class="game-message">${game.getMessage()}</h3>
		</c:if>
	</div>
	<form  class="game-controls" action="Mancala" method="post">
		<input class="button" type="submit" name="newgame" value="New Game">
	</form>
	<div class="game-area">
		<c:forEach items="${game.getBoard()}" var="pocket" varStatus="position">
			<c:choose>
				<c:when test="${position.index == 0}">
					<div class="store">
						<p>${pocket}</p>
					</div>
				</c:when>
				<c:when test="${position.index == 13}">
					<div class="store">
						<p>${pocket}</p>
					</div>
				</c:when>
				<c:when test="${position.index < 7}">
					<c:if test="${position.index == 1}">
						<div class="play-area">
						<div class="row">
					</c:if>
					<c:choose>
					<c:when test="${game.getDisable() != 'B' && !game.isGameWon()}">
						<form action="Mancala" method="post">
							<input class= "b-hidden" type="hidden" name="location" value="${position.index}" style="display: none">
							<c:choose>
								<c:when test="${pocket == 0}">
									<input class= "b-disabled" type="submit" name="b" value="${pocket}" disabled>
								</c:when>
								<c:otherwise>
										<input class= "b" type="submit" name="b" value="${pocket}">
								</c:otherwise>
							</c:choose>
						</form>
					</c:when>
					<c:otherwise>
						<form action="Mancala" method="post">
							<input class= "b-hidden" type="hidden" name="location" value="${position.index}" style="display: none" disabled>
							<input class= "b" type="submit" name="b" value="${pocket}" disabled>
						</form>
					</c:otherwise>
					</c:choose>
					<c:if test="${position.index == 6}">
						</div>
					</c:if>
				</c:when>
				<c:when test="${position.index > 6}">
					<c:if test="${position.index == 7}">
						<div class="row">
					</c:if>
					<c:choose>
					<c:when test="${game.getDisable() != 'A' && !game.isGameWon()}">
						<form action="Mancala" method="post">
							<input class= "a-hidden" type="hidden" name="location" value="${position.index}" style="display: none">
							<c:choose>
								<c:when test="${pocket == 0}">
									<input class= "a-disabled" type="submit" name="a" value="${pocket}" disabled>
								</c:when>
								<c:otherwise>
										<input class= "a" type="submit" name="a" value="${pocket}">
								</c:otherwise>
							</c:choose>
						</form>
					</c:when>
					<c:otherwise>
						<form action="Mancala" method="post">
							<input class= "a-hidden" type="hidden" name="location" value="${position.index}" style="display: none" disabled>
							<input class= "a" type="submit" name="a" value="${pocket}" disabled>
						</form>
					</c:otherwise>
					</c:choose>
					<c:if test="${position.index == 12}">
						</div>
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
				   (${position.index}) ${pocket} <br>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<c:if test="${not empty request.Error}">
		<p class="error">${request.Error}</p>
	</c:if>
</section>
</body>
</html>