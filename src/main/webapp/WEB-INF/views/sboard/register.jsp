<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="../include/header.jsp" %>

<style>
    .dragAndDropDiv {
        border: 2px dashed #92AAB0;
        width: 650px;
        height: 200px;
        color: #92AAB0;
        text-align: center;
        vertical-align: middle;
        padding: 10px 0px 10px 10px;
        font-size: 200%;
        display: table-cell;
    }

    .progressBar {
        width: 200px;
        height: 22px;
        border: 1px solid #ddd;
        border-radius: 5px;
        overflow: hidden;
        display: inline-block;
        margin: 0px 10px 5px 5px;
        vertical-align: top;
    }

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

    .filesize {
        display: inline-block;
        vertical-align: top;
        color: #30693D;
        width: 100px;
        margin-left: 10px;
        margin-right: 5px;
    }

    .abort {
        background-color: #A8352F;
        -moz-border-radius: 4px;
        -webkit-border-radius: 4px;
        border-radius: 4px;
        display: inline-block;
        color: #fff;
        font-family: arial;
        font-size: 13px;
        font-weight: normal;
        padding: 4px 15px;
        cursor: pointer;
        vertical-align: top
    }
</style>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<script>
    var files = [];

    $(document).ready(function () {
        var objDragAndDrop = $(".dragAndDropDiv");

        $(document).on("dragenter", ".dragAndDropDiv", function (e) {
            e.stopPropagation();
            e.preventDefault();
            $(this).css('border', '2px solid #0B85A1');
        });
        $(document).on("dragover", ".dragAndDropDiv", function (e) {
            e.stopPropagation();
            e.preventDefault();
        });
        $(document).on("drop", ".dragAndDropDiv", function (e) {

            $(this).css('border', '2px dotted #0B85A1');
            e.preventDefault();
            var files = e.originalEvent.dataTransfer.files;

            handleFileUpload(files, objDragAndDrop);
        });

        $(document).on('dragenter', function (e) {
            e.stopPropagation();
            e.preventDefault();
        });
        $(document).on('dragover', function (e) {
            e.stopPropagation();
            e.preventDefault();
            objDragAndDrop.css('border', '2px dotted #0B85A1');
        });
        $(document).on('drop', function (e) {
            e.stopPropagation();
            e.preventDefault();
        });

        function handleFileUpload(files, obj) {
            for (var i = 0; i < files.length; i++) {
                var fd = new FormData();
                fd.append('file', files[i]);

                var status = new createStatusbar(obj); //Using this we can set progress.
                status.setFileNameSize(files[i].name, files[i].size);
                sendFileToServer(fd, status);
            }
        }

        var rowCount = 0;

        function createStatusbar(obj) {

            rowCount++;
            var row = "odd";
            if (rowCount % 2 == 0) row = "even";
            this.statusbar = $("<div class='statusbar " + row + "'></div>");
            this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
            this.size = $("<div class='filesize'></div>").appendTo(this.statusbar);
            this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
            this.abort = $("<div class='abort'>중지</div>").appendTo(this.statusbar);

            obj.after(this.statusbar);

            this.setFileNameSize = function (name, size) {
                var sizeStr = "";
                var sizeKB = size / 1024;
                if (parseInt(sizeKB) > 1024) {
                    var sizeMB = sizeKB / 1024;
                    sizeStr = sizeMB.toFixed(2) + " MB";
                } else {
                    sizeStr = sizeKB.toFixed(2) + " KB";
                }

                this.filename.html(name);
                this.size.html(sizeStr);
            }

            this.setProgress = function (progress) {
                var progressBarWidth = progress * this.progressBar.width() / 100;
                this.progressBar.find('div').animate({width: progressBarWidth}, 10).html(progress + "% ");
                if (parseInt(progress) >= 100) {
                    this.abort.hide();
                }
            }

            this.setAbort = function (jqxhr) {
                var sb = this.statusbar;
                this.abort.click(function () {
                    jqxhr.abort();
                    sb.hide();
                });
            }
        }

        function sendFileToServer(formData, status) {
            var uploadURL = "/fileUpload/post"; //Upload URL
            var extraData = {}; //Extra Data.
            var jqXHR = $.ajax({
                xhr: function () {
                    var xhrobj = $.ajaxSettings.xhr();
                    if (xhrobj.upload) {
                        xhrobj.upload.addEventListener('progress', function (event) {
                            var percent = 0;
                            var position = event.loaded || event.position;
                            var total = event.total;
                            if (event.lengthComputable) {
                                percent = Math.ceil(position / total * 100);
                            }
                            //Set progress
                            status.setProgress(percent);
                        }, false);
                    }
                    return xhrobj;
                },
                url: uploadURL,
                type: "POST",
                contentType: false,
                processData: false,
                cache: false,
                data: formData,
                success: function (data) {
                    status.setProgress(100);
                    files.push(data);

                    console.log(files);
                    console.log(files.length);

                }
            });

            status.setAbort(jqXHR);
        }

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

    });
</script>

<!-- Main content -->
<section class="content">
    <div class="row">
        <!-- left column -->
        <div class="col-md-12">
            <!-- general form elements -->
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">게시글 등록</h3>
                </div>
                <!-- /.box-header -->

                <form id="registerForm" role="form" method="post">
                    <div class="box-body">
                        <div class="form-group">
                            <label for="exampleInputEmail1">제목</label> <input type="text"
                                                                              name='title' class="form-control"
                                                                              placeholder="Enter Title">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">내용</label>
							<textarea class="form-control" name="content" rows="3"
                                      placeholder="Enter ..."></textarea>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">글쓴이</label> <input type="text"
                                                                               name="writer" class="form-control"
                                                                               placeholder="Enter Writer">
                        </div>

                        <label for="exampleInputEmail1">파일 첨부 (최대 20MB까지 업로드 가능합니다.)</label>
                        <div id="fileUpload" class="dragAndDropDiv">Drag & Drop Files Here</div>
                    </div>
                    <!-- /.box-body -->

                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">등록</button>
                    </div>
                </form>


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

<%@include file="../include/footer.jsp" %>
