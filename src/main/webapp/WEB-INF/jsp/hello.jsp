<%@ page language="java" pageEncoding="gb2312"%>

<!DOCTYPE html>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html charset=gb2312">
    <title>Hello</title>
</head>
<body>
<h2>�ļ��ϴ�ʾ��</h2>
<hr/>
<form id="uploadForm" enctype="multipart/form-data"
      method="post">
    <input type="file" name="file" id="file_input" />
</form>
<button id="upload">�ϴ��ļ�</button>

</body>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
    $(function () {
        $("#upload").click(function () {
            var fileName = $('#file_input').val();
            if (fileName === '') {
                alert('��ѡ���ļ�');
                return false;
            }
            var fileType = (fileName.substring(fileName
                .lastIndexOf(".") + 1, fileName.length))
                .toLowerCase();
            if (fileType !== 'xls' && fileType !== 'xlsx') {
                alert('�ļ���ʽ����ȷ��excel�ļ���');
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
                alert("�ϴ�ʧ��")

            });
        });
    });
</script>
</html>