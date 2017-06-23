<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="p" uri="http://pager.my.com/pager"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta charset="utf-8">
<title>Image Viewer</title>
<base href="<%=basePath%>">
<script type='text/javascript'
	src='3rd/unitegallery/unitegallery/js/jquery-11.0.min.js'></script>
<script type='text/javascript'
	src='3rd/unitegallery/unitegallery/js/unitegallery.min.js'></script>
<link rel='stylesheet'
	href='3rd/unitegallery/unitegallery/css/unite-gallery.css'
	type='text/css' />
<script type='text/javascript'
	src='3rd/unitegallery/unitegallery/themes/compact/ug-theme-compact.js'></script>
<style type="text/css">
	#contentTable, #contentTable tr {
	border-left: 1px solid #B0C4DE;
	border-top: 1px solid #B0C4DE;;
}
 #contentTable td {
	border-right: 1px solid #B0C4DE;;
	border-bottom: 1px solid #B0C4DE;;
}

#contentTable a {
	text-decoration: none;
}
</style>
</head>
<script type="text/javascript">
	var currentSelectedItemIndex = 0;
	var api;
	$(document).ready(function() {
		
		// Select image
		$(".itemclass").bind("click", function() {
			showpic($(this));
		});
		
		// Change star
		$(".starclass").bind("change", function(){
			var item = $(this).attr("id").split("_");
			var category = item[0];
			var id = item[1];
			var star = $(this).val();
			$.post("updateStar", {'category': category, 'id' : id, 'star' : star}, function(data, status){
				if (data != "1") {
					alert("Failed to change star.");
				}
		    });
		});
		
		// Arrow key event
		$(document).keydown(function(event) {
			// Down
			if (event.which == 40) {
				$("#itemtr_" + (parseInt(currentSelectedItemIndex) + 1) + " a:first").click();
			}
			// Up
			else if (event.which == 38) {
				$("#itemtr_" + (parseInt(currentSelectedItemIndex) - 1) + " a:first").click();
			} 
			// Left
			else if (event.which == 37) {
				api.prevItem();
			} 
			// Right
			else if (event.which == 39) {
				api.nextItem();
			}
		});
		
		// Full screen event, Ctrl + q
		$(document).keypress(function(event){
			if (event.ctrlKey && (event.which == 113 || event.which == 17)) {
				api.toggleFullscreen();
			}
		});
		
		// Click the first element defaultly
		if ($("#contentTable a").size() != 0) {
			$("#contentTable a:first").click();
		} else {
			showNoImage();
		}
		
		// Change the div height according to screen height
		$("#contentDiv").css("height", parseInt($(document).height()) - 90);
	});
	
	function showpic(item) {
		// Change CSS style
		$(".changeBackground").css("backgroundColor", "white");
		var selectedItemId = item.attr("id");
		currentSelectedItemIndex = selectedItemId.split("_")[1];
		var parentTr = $("#itemtr_" + currentSelectedItemIndex); 
		parentTr.css("backgroundColor", "#C6E2FF");
		
		// Show PIC
		if (item.attr("data") != "") {
			var images = item.attr("data").split(";");
			var html = "";
			for (var i = 0; i < images.length; i++) {
				var image = encodeURI(images[i]);
				if (image != "") {
					html += "<img alt=\"Preview Image 6\" src= " + image + " data-image=" + image + " data-description=\"Preview Image 6 Description\"> ";
				}
			}
			$("#gallery").html(html);
			api = $("#gallery").unitegallery({
				theme_panel_position : "bottom"
			});
		} else {
			showNoImage();
		}
	}
	
	function showNoImage() {
		$("#gallery").html("<div style='font-size: 38px; text-align: center; margin-top: 110px; width: 100%; font-weight: bold; font-family: Arial,Helvetica,sans-serif'>No Images!</div>");
		$("#gallery").show();
	}
</script>
<body>
	<div id="main">
		<div id="head"
			style="border: 1px solid none; width: auto; height: 40px; font-weight:bold ; color: white; font-size: 30px; text-align: center; background-color: #473C8B;">
			Welcome to Image Viewer
		</div>
		<div id="content"
			style="border: 1px solid #99ccff; width: auto; margin-top: 2px;">
			<table style="width: 100%">
				<tr>
					<td width="39%" style="vertical-align:top; padding: 2px;">
						<div id="gallery" style="display: none; border: 1px solid none; height: 500px;"></div>
					</td>
					<td width="60%" style="vertical-align:top">
						<div id="contentDiv" style="overflow-y: auto; height: 100px;">
							<table style="width: 100%;">
								<tr style="height: 40px;">
									<td style="padding-top: 20px;">
										<form method="post" action="main">
											<table>
												<tr>
												</tr>
												<tr>
												</tr>
											</table>
											Category: <select name="category" style="width: 150px;">
												<c:forEach items="${categories}" var="cat">
													<option value="${cat.itemTableName}"
														<c:if test='${cat.itemTableName eq category}'>
													selected='selected'
												</c:if>>${cat.name}</option>
												</c:forEach>
											</select> Star: <select name="star" style="width: 150px;">
												<option value="100"
													<c:if test='${star eq 100}'>
												selected='selected'
											</c:if>>All</option>
												<option value="1"
													<c:if test='${star eq 1}'>
												selected='selected'
											</c:if>>Perfect</option>
												<option value="2"
													<c:if test='${star eq 2}'>
												selected='selected'
											</c:if>>Good</option>
												<option value="3"
													<c:if test='${star eq 3}'>
												selected='selected'
											</c:if>>Common</option>
												<option value="4"
													<c:if test='${star eq 4}'>
												selected='selected'
											</c:if>>Bad</option>
												<option value="5"
													<c:if test='${star eq 5}'>
												selected='selected'
											</c:if>>Very Bad</option>
											<option value="0"
													<c:if test='${star eq 0}'>
												selected='selected'
											</c:if>>N/A</option>
											</select>
											Page Size: <select name="pageSize" style="width: 50px;">
												<option value="10"
													<c:if test='${pageSize eq 10}'>
														selected='selected'
													</c:if>
												>10</option>
												<option value="15"
													<c:if test='${pageSize eq 15}'>
														selected='selected'
													</c:if>
												>15</option>
												<option value="20"
													<c:if test='${pageSize eq 20}'>
														selected='selected'
													</c:if>
												>20</option>
												<option value="25"
													<c:if test='${pageSize eq 25}'>
														selected='selected'
													</c:if>
												>25</option>
												<option value="30"
													<c:if test='${pageSize eq 30}'>
														selected='selected'
													</c:if>
												>30</option>
												<option value="35"
													<c:if test='${pageSize eq 35}'>
														selected='selected'
													</c:if>
												>35</option>
												<option value="40"
													<c:if test='${pageSize eq 40}'>
														selected='selected'
													</c:if>
												>40</option>
											</select>
											<input type="submit" value="Submit" style="width: 100px;"/>
										</form>
									</td>
								</tr>
								<tr>
									<td>
										<table id="contentTable" style="width: 100%; font-size: 14px;" cellpadding="2" cellspacing="0" >
											<tbody>
												<tr style="font-weight: bold;">
													<td style="width: 65%;">Title</td>
													<td style="width: 7%;">Count</td>
													<td style="width: 17%;">CreateTime</td>
													<td style="width: 11%;">Star</td>
												</tr>
												<c:forEach items="${categoryItems}" var="categoryItem" varStatus="loop">
													<tr id="itemtr_${loop.index}" class="changeBackground" style="height: 25px;">
														<td>
															<a id="a_${loop.index}" class="itemclass" href="javascript:void(0);" data="${categoryItem.items}"
																<c:if test='${categoryItem.star eq 1}'>
																	style="color: red"
																</c:if>
																<c:if test='${categoryItem.star eq 2}'>
																	style="color: #ef7904"
																</c:if>
																<c:if test='${categoryItem.star eq 3}'>
																	style="color: #6A5ACD"
																</c:if>
																<c:if test='${categoryItem.star eq 4}'>
																	style="color: #71C671"
																</c:if>
																<c:if test='${categoryItem.star eq 5}'>
																	style="color: #6B8E23"
																</c:if>
																<c:if test='${categoryItem.star eq 0}'>
																	style="color: black"
																</c:if>
															>
																${categoryItem.name}
															</a>
														</td>
														<td>${categoryItem.account}</td>
														<td><fmt:formatDate value="${categoryItem.createTime}"
																pattern="yyyy-MM-dd HH:mm:ss" /></td>
														<td>
															<select id="${categoryItem.category}_${categoryItem.id}" class="starclass" name="star" style="width: 100px;">
																<option value="1"
																	<c:if test='${categoryItem.star eq 1}'>
																selected='selected'
															</c:if>>Perfect</option>
																<option value="2"
																	<c:if test='${categoryItem.star eq 2}'>
																selected='selected'
															</c:if>>Good</option>
																<option value="3"
																	<c:if test='${categoryItem.star eq 3}'>
																selected='selected'
															</c:if>>Common</option>
																<option value="4"
																	<c:if test='${categoryItem.star eq 4}'>
																selected='selected'
															</c:if>>Bad</option>
																<option value="5"
																	<c:if test='${categoryItem.star eq 5}'>
																selected='selected'
															</c:if>>Very Bad</option>
															<option value="0"
																	<c:if test='${categoryItem.star eq 0}'>
																selected='selected'
															</c:if>>N/A</option>
															</select>
														</td>
													</tr>
												</c:forEach>
											</tbody>
											<tr class="pageStyle" height="30">
												<td colspan="6" align="center" style="font-size: 16px;"><span> <p:pager
															curPageVar="targetPage" url="main" pages="10" />
												</span></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="bottom"
			style="border: 1px solid none; background-color: #B4CDCD; width: auto; height: 20px; margin-top: 2px; font-size: 14px; text-align: center;">
			Version 1.0.0, 2017
		</div>
	</div>
</body>
</html>
