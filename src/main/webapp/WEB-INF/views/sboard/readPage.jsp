<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../include/header.jsp" %>
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


<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<!-- Main content -->
<section class="content">
    <div class="row">
        <!-- left column -->
        <div class="col-md-12">
            <!-- general form elements -->
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">게시글 읽기</h3>
                </div>
                <!-- /.box-header -->

                <form role="form" action="modifyPage" method="post">

                    <input type='hidden' name='bno' value="${boardVO.bno}"> <input
                        type='hidden' name='page' value="${cri.page}"> <input
                        type='hidden' name='perPageNum' value="${cri.perPageNum}">
                    <input type='hidden' name='searchType' value="${cri.searchType}">
                    <input type='hidden' name='keyword' value="${cri.keyword}">

                </form>

                <div class="box-body">
                    <div class="form-group">
                        <label for="exampleInputEmail1">제목</label> <input type="text"
                                                                          name='title' class="form-control"
                                                                          value="${boardVO.title}"
                                                                          readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">내용</label>
						<textarea class="form-control" name="content" rows="3"
                                  readonly="readonly">${boardVO.content}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">글쓴이</label> <input type="text"
                                                                           name="writer" class="form-control"
                                                                           value="${boardVO.writer}"
                                                                           readonly="readonly">
                    </div>

                    <label for="exampleInputEmail1">파일 목록</label>

                    <div class="dragAndDropDiv"></div>

                </div>
                <!-- /.box-body -->

                <div class="box-footer">
                    <c:if test="${login.id == boardVO.writer}">
                        <button type="submit" class="btn btn-warning" id="modifyBtn">수정</button>
                        <button type="submit" class="btn btn-danger" id="removeBtn">삭제</button>
                    </c:if>
                    <button type="submit" class="btn btn-primary" id="goListBtn">목록으로</button>
                </div>


            </div>
            <!-- /.box -->
        </div>
        <!--/.col (left) -->

    </div>
    <!-- /.row -->


    <div class="row">
        <div class="col-md-12">

            <div class="box box-success">
                <div class="box-header">
                    <h3 class="box-title">댓글 작성</h3>
                </div>
                <c:if test="${not empty login}">
                    <div class="box-body">
                        <label for="exampleInputEmail1">글쓴이</label>
                        <input class="form-control" type="text" placeholder="USER ID" id="newReplyWriter" value="${login.id}" readonly="readonly">
                        <label for="exampleInputEmail1">댓글 내용</label>
                        <input class="form-control" type="text" placeholder="REPLY TEXT" id="newReplyText">
                    </div>
                    <!-- /.box-body -->
                    <div class="box-footer">
                        <button type="button" class="btn btn-primary" id="replyAddBtn">댓글 달기</button>
                    </div>
                </c:if>

                <c:if test="${empty login}">
                    <div class="box-body">
                        <div><a href="javascript:goLogin();">로그인이 필요한 기능입니다. 로그인 하시려면 클릭하세요.</a> </div>
                    </div>
                </c:if>
            </div>


            <!-- The time line -->
            <ul class="timeline">
                <!-- timeline time label -->
                <li class="time-label" id="repliesDiv">
		  <span class="bg-green">
		    댓글 보기 <small id='replycntSmall'> [ ${boardVO.replycnt} ]</small>
		    </span>
                </li>
            </ul>

            <div class='text-center'>
                <ul id="pagination" class="pagination pagination-sm no-margin ">

                </ul>
            </div>

        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->


    <!-- Modal -->
    <div id="modifyModal" class="modal modal-primary fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body" data-rno>
                    <p><input type="text" id="replytext" class="form-control"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-info" id="replyModBtn">수정</button>
                    <button type="button" class="btn btn-danger" id="replyDelBtn">삭제</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                </div>
            </div>
        </div>
    </div>

    <div class="popup back" style="display: none;"></div>
    <div id="popup_front" class="popup front" style="display: none;">
        <img id="popup_img">
    </div>


</section>
<!-- /.content -->

<script id="template" type="text/x-handlebars-template">
    {{#each .}}
    <li class="replyLi" data-rno={{rno}}>
        <i class="fa fa-comments bg-blue"></i>

        <div class="timeline-item">
  <span class="time">
    <i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
  </span>

            <h3 class="timeline-header">{{replyer}}</h3>

            <div class="timeline-body">{{replytext}}</div>
            <div class="timeline-footer">
                {{#eqReplyer replyer}}
                <a class="btn btn-primary btn-xs"
                   data-toggle="modal" data-target="#modifyModal">수정</a>
                {{/eqReplyer}}
            </div>
        </div>
    </li>
    {{/each}}
</script>

<script>
    Handlebars.registerHelper("prettifyDate", function (timeValue) {
        var dateObj = new Date(timeValue);
        var year = dateObj.getFullYear();
        var month = dateObj.getMonth() + 1;
        var date = dateObj.getDate();
        return year + "/" + month + "/" + date;
    });

    Handlebars.registerHelper("eqReplyer", function(replyer, block) {
        var accum = '';

        if (replyer == '${login.id}') {
            accum += block.fn();
        }

        return accum;
    });

    var printData = function (replyArr, target, templateObject) {

        var template = Handlebars.compile(templateObject.html());

        var html = template(replyArr);
        $(".replyLi").remove();
        target.after(html);

    }

    var bno = ${boardVO.bno};
    var replyPage = 1;

    function getPage(pageInfo) {

        $.getJSON(pageInfo, function (data) {
            printData(data.list, $("#repliesDiv"), $('#template'));
            printPaging(data.pageMaker, $(".pagination"));

            $("#modifyModal").modal('hide');
            $("#replycntSmall").html("[ " + data.pageMaker.totalCount + " ]");

        });
    }

    var printPaging = function (pageMaker, target) {

        var str = "";

        if (pageMaker.prev) {
            str += "<li><a href='" + (pageMaker.startPage - 1)
                    + "'> << </a></li>";
        }

        for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
            var strClass = pageMaker.cri.page == i ? 'class=active' : '';
            str += "<li " + strClass + "><a href='" + i + "'>" + i + "</a></li>";
        }

        if (pageMaker.next) {
            str += "<li><a href='" + (pageMaker.endPage + 1)
                    + "'> >> </a></li>";
        }

        target.html(str);
    };

    $("#repliesDiv").on("click", function () {

        if ($(".timeline li").size() > 1) {
            return;
        }
        getPage("/replies/" + bno + "/1");

    });

    $(".pagination").on("click", "li a", function (event) {

        event.preventDefault();

        replyPage = $(this).attr("href");

        getPage("/replies/" + bno + "/" + replyPage);

    });

    $("#replyAddBtn").on("click", function () {

        var replyerObj = $("#newReplyWriter");
        var replytextObj = $("#newReplyText");
        var replyer = replyerObj.val();
        var replytext = replytextObj.val();


        $.ajax({
            type: 'post',
            url: '/replies/',
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            dataType: 'text',
            data: JSON.stringify({bno: bno, replyer: replyer, replytext: replytext}),
            success: function (result) {
                console.log("result: " + result);
                if (result == 'SUCCESS') {
                    alert("등록 되었습니다.");
                    replyPage = 1;
                    getPage("/replies/" + bno + "/" + replyPage);
                    replyerObj.val("");
                    replytextObj.val("");
                }
            }
        });
    });

    $(".timeline").on("click", ".replyLi", function (event) {

        var reply = $(this);

        $("#replytext").val(reply.find('.timeline-body').text());
        $(".modal-title").html(reply.attr("data-rno"));

    });

    $("#replyModBtn").on("click", function () {

        var rno = $(".modal-title").html();
        var replytext = $("#replytext").val();

        $.ajax({
            type: 'put',
            url: '/replies/' + rno,
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "PUT"
            },
            data: JSON.stringify({replytext: replytext}),
            dataType: 'text',
            success: function (result) {
                console.log("result: " + result);
                if (result == 'SUCCESS') {
                    alert("수정 되었습니다.");
                    getPage("/replies/" + bno + "/" + replyPage);
                }
            }
        });
    });

    $("#replyDelBtn").on("click", function () {

        var rno = $(".modal-title").html();
        var replytext = $("#replytext").val();

        $.ajax({
            type: 'delete',
            url: '/replies/' + rno,
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "DELETE"
            },
            dataType: 'text',
            success: function (result) {
                console.log("result: " + result);
                if (result == 'SUCCESS') {
                    alert("삭제 되었습니다.");
                    getPage("/replies/" + bno + "/" + replyPage);
                }
            }
        });
    });

</script>


<script>
    var obj = $(".dragAndDropDiv");
    var bno = ${boardVO.bno};
    var arr = [];

    $(document).ready(function () {
        var formObj = $("form[role='form']");

        console.log(formObj);

        $("#modifyBtn").on("click", function () {
            formObj.attr("action", "/sboard/modifyPage");
            formObj.attr("method", "get");
            formObj.submit();
        });

        $("#removeBtn").on("click", function () {
            var replyCnt = $("#replycntSmall").html();

            if (replyCnt > 0) {
                alert("댓글이 달린 게시물을 삭제할 수 없습니다.");
                return;
            }

            $.getJSON("/sboard/getAttach/" + bno, function (list) {
                $(list).each(function () {
                    arr.push(this);
                });
            });

            if (arr.length > 0) {
                $.ajax({
                    url: "/deleteAllFiles",
                    type: "POST",
                    data: {files: arr},
                    success: function () {

                    }
                });
            }

            formObj.attr("action", "/sboard/removePage");
            formObj.submit();
        });

        $("#goListBtn ").on("click", function () {
            formObj.attr("method", "get");
            formObj.attr("action", "/sboard/list");
            formObj.submit();
        });

    });

    $.getJSON("/sboard/getAttach/" + bno, function (list) {
        $(list).each(function () {
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
            var fileLink = "<a href='/displayFile?fileName=" + link + "'>" + name + "</a>";
            this.filename.html(fileLink);
        }
    }
</script>


<%@include file="../include/footer.jsp" %>
