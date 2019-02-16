<%@ page language="java" pageEncoding="gb2312"%>

<!DOCTYPE html>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html charset=gb2312">
    <title>Hello</title>
</head>
<body>
<h2>文件上传示例</h2>
<hr/>
<form id="uploadForm" enctype="multipart/form-data"
      method="post">
    <input type="file" name="file" id="file_input" />
</form>
<button id="upload">上传文件</button>

</body>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
    $(function () {
        $("#upload").click(function () {
            var fileName = $('#file_input').val();
            if (fileName === '') {
                alert('请选择文件');
                return false;
            }
            var fileType = (fileName.substring(fileName
                .lastIndexOf(".") + 1, fileName.length))
                .toLowerCase();
            if (fileType !== 'xls' && fileType !== 'xlsx') {
                alert('文件格式不正确，excel文件！');
                return false;
            }
            alert('start')
            var formData = new FormData($('#uploadForm')[0]);
            $.ajax({
                type: 'post',
                url: "/accountImport",
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
            }).success(function (data) {
                alert(data);
            }).error(function(){
                alert("上传失败")

            });
        });
    });
</script>
</html>