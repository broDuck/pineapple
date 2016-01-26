<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../include/header.jsp" %>

<!-- Main content -->
<section class="content">
    <div class="row">
        <!-- left column -->


        <div class="col-md-12">
            <!-- general form elements -->
            <div class='box'>
                <div class="box-header with-border">
                    <h3 class="box-title">파인애플 차트</h3>
                </div>

                <div class='box-body'>

                    <select name="searchType">
                        <option value="tc"
                                <c:out value="${cri.searchType == null?'selected':''}"/>>
                            전체
                        </option>
                        <option value="t"
                                <c:out value="${cri.searchType eq 't'?'selected':''}"/>>
                            제목
                        </option>
                        <option value="c"
                                <c:out value="${cri.searchType eq 'c'?'selected':''}"/>>
                            가수
                        </option>
                        <option value="tc"
                                <c:out value="${cri.searchType eq 'tc'?'selected':''}"/>>
                            전체
                        </option>
                    </select> <input type="text" name='keyword' id="keywordInput"
                                     value='${cri.keyword }'>
                    <button id='searchBtn'>검색</button>
                </div>
            </div>

            <div class="box">
                <div class="box-body">
                    <table class="table table-bordered">
                        <tr>
                            <th style="width: 10%">순위</th>
                            <th style="width: 45%">곡명</th>
                            <th style="width: 10%">아티스트</th>
                            <th style="width: 5%"></th>
                            <th style="width: 5%"></th>
                            <th style="width: 10%">재생횟수</th>
                        </tr>

                        <c:forEach items="${list}" var="musicVO">

                            <tr>
                                <td>${musicVO.mno}</td>
                                <td>${musicVO.title}</td>
                                <td>${musicVO.artist}</td>
                                <td><a href="javascript:;" onclick="playMusic(${musicVO.mno});"><img
                                        src="/resources/dist/img/playvideo3-34.png"></a></td>
                                <td><img src="/resources/dist/img/add139-34.png"></td>
                                <td><span class="badge bg-red">${musicVO.viewcnt }</span></td>
                            </tr>

                        </c:forEach>

                    </table>
                </div>
                <!-- /.box-body -->

                <div class="box-footer">
                    <div class="text-center">
                        <ul class="pagination">

                            <c:if test="${pageMaker.prev}">
                                <li><a
                                        href="list${pageMaker.makeSearch(pageMaker.startPage - 1) }">&laquo;</a></li>
                            </c:if>

                            <c:forEach begin="${pageMaker.startPage }"
                                       end="${pageMaker.endPage }" var="idx">
                                <li
                                        <c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>>
                                    <a href="list${pageMaker.makeSearch(idx)}">${idx}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
                                <li><a
                                        href="list${pageMaker.makeSearch(pageMaker.endPage +1) }">&raquo;</a></li>
                            </c:if>

                        </ul>
                    </div>

                </div>
                <!-- /.box-footer-->
            </div>
        </div>
        <!--/.col (left) -->

    </div>
    <!-- /.row -->
</section>
<!-- /.content -->


<script>
    var result = '${msg}';

    if (result == 'SUCCESS') {
        alert("처리가 완료되었습니다.");
    }
</script>

<script>
    $(document).ready(function () {
        $('#searchBtn').on("click", function (event) {
            self.location = "list"
                    + '${pageMaker.makeQuery(1)}'
                    + "&searchType="
                    + $("select option:selected").val()
                    + "&keyword=" + $('#keywordInput').val();
        });

        $('#newBtn').on("click", function (evt) {
            self.location = "register";
        });
    });

    var clicked;

    function playMusic(mno) {
        if (clicked == null) {
            var winl = (screen.width) / 6;
            var wint = (screen.height) / 6;
            if (winl < 0) winl = 0;
            if (wint < 0) wint = 0;
            var settings = 'height=700,';
            settings += 'width=440,';
            settings += 'top=' + wint + ',';
            settings += 'left=' + winl;
            clicked = window.open("player", "_blank", settings);
            clicked.window.focus();
        }

        $.ajax({
            type: 'post',
            url: 'player',
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            dataType: 'text',
            data: JSON.stringify({mno: mno, session: '${login.id}'}),
            success: function (result) {

            }
        });
    }



</script>

<%@include file="../include/footer.jsp" %>
