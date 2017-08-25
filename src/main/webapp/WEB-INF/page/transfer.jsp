<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
<meta charset="utf-8" />
<title>ATM-SSM系统</title>
<script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>

<body>
	<h1>转账页面</h1>
	<h1>用户名：${card.userId}</h1>
	<h1>卡号：${card.cardNum}</h1>
	<h1>余额：${card.balance}</h1>
		<form >
			转账金额：<input type="text" id="amount" name="amount"><br><br>
			<input type="hidden" id="cardId" name="cardId" value="${card.id}">
			<input type="text" id="inCardNum" name="inCardNum">
			<input type="button" value="转账" onclick="transfer();"> &nbsp;&nbsp;
			<a href="/" >返回</a>
		</form>
		
	<script type="text/javascript">
    
    function transfer() {
    	$.ajax({
            url:'/card/transfer.do',
            type:'POST', //GET
            async:true,    //或false,是否异步
            data:{
                amount:$('#amount').val(),
                cardId:$('#cardId').val(),
                inCardNum:$('#inCardNum').val()
            },
            timeout:5000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend:function(xhr){
                console.log(xhr)
                console.log('发送前')
            },
            success:function(data,textStatus,jqXHR){
                var obj = data;
                alert(obj.success);
                
                if (!data.success) {
                	alert(data.message);
                	return;
                }

                window.location.href='/card/toCardDetail.do?cardId=${card.id }';

            },
            error:function(xhr,textStatus){
                console.log('错误')
                console.log(xhr)
                console.log(textStatus)
            },
            complete:function(){
                console.log('结束')
            }
        });
    }
    
    </script>
</body>

<footer> 1997-2017@copyrightByYoung </footer>

</html>