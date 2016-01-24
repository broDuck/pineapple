<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

<style type="text/css">

	.progressBar div {
		height: 100%;
		color: #fff;
		text-align: right;
		line-height: 22px; /* same as #progressBar height if we want text middle aligned */
		width: 0;
		background-color: #0ba1b5;
		border-radius: 3px;
	}

	.statusbar {
		border-top: 1px solid #A9CCD1;
		min-height: 25px;
		width: 99%;
		padding: 10px 10px 0px 10px;
		vertical-align: top;
	}

	.statusbar:nth-child(odd) {
		background: #EBEFF0;
	}

	.filename {
		display: inline-block;
		vertical-align: top;
		width: 250px;
	}
</style>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">게시글 수정</h3>
				</div>
				<!-- /.box-header -->

<form role="form" action="modifyPage" method="post">

	<input type='hidden' name='page' value="${cri.page}"> <input
		type='hidden' name='perPageNum' value="${cri.perPageNum}">
	<input type='hidden' name='searchType' value="${cri.searchType}">
	<input type='hidden' name='keyword' value="${cri.keyword}">

					<div class="box-body">

						<div class="form-group">
							<label for="exampleInputEmail1">번호</label> <input type="text"
								name='bno' class="form-control" value="${boardVO.bno}"
								readonly="readonly">
						</div>

						<div class="form-group">
							<label for="exampleInputEmail1">제목</label> <input type="text"
								name='title' class="form-control" value="${boardVO.title}">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">내용</label>
							<textarea class="form-control" name="content" rows="3">${boardVO.content}</textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputEmail1">글쓴이</label> <input
								type="text" name="writer" class="form-control"
								value="${boardVO.writer}">
						</div>

						<label for="exampleInputEmail1">파일 목록</label>
						<div class="dragAndDropDiv"></div>
					</div>
					<!-- /.box-body -->
				</form>
				<div class="box-footer">
					<button type="submit" class="btn btn-primary">저장</button>
					<button type="submit" class="btn btn-warning">취소</button>
				</div>

<script>
$(document).ready(function() {
	var formObj = $("form[role='form']");

	console.log(formObj);

	$(".btn-warning").on("click",function() {
		self.location = "/sboard/list?page=${cri.page}&perPageNum=${cri.perPageNum}"
						+ "&searchType=${cri.searchType}&keyword=${cri.keyword}";
	});

	$("#registerForm").submit(function (event) {
		event.preventDefault();

		var that = $(this);
		var str = "";

		for (var i = 0; i < files.length; i++) {
			str += "<input type='hidden' name='files[" + i + "]' value='" + files[i] + "'> ";
		}

		that.append(str);
		that.get(0).submit();
	});

	$(".btn-primary").on("click", function() {
		formObj.submit();
	});
});

var obj = $(".dragAndDropDiv");
var bno = ${boardVO.bno};

$.getJSON("/sboard/getAttach/" + bno, function(list) {
	$(list).each(function() {
		var fileName = this;
		var originalName = fileName.split("_");
		handleFileUpload(originalName[1], fileName);
	});
});

function handleFileUpload(fileName, fileLink) {
	var status = new createStatusBar(); //Using this we can set progress.

	status.setFileName(fileName, fileLink);
}

function createStatusBar() {
	this.statusbar = $("<div class='statusbar'></div>");
	this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);

	obj.after(this.statusbar);

	this.setFileName = function (name, link) {
		var fileLink ="<a href='/displayFile?fileName=" + link + "'>" + name + "</a>";
		this.filename.html(fileLink);
	}
}
</script>




			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="../include/footer.jsp"%>
