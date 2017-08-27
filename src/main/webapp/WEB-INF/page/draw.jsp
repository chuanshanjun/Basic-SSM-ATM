<%@page contentType="text/html; charset=utf-8" %>

<html>

<head>
	<meta charset="utf-8" />
	<title>ATM系统</title>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>

<body>
     <h1>取款</h1>
     <form action="/card/draw.do" method="post">
     	卡号：${card.cardNum }<br>
     	余额：${card.balance }<br>
		取款金额：<input type="text" id="amount" name="amount"><br>
		<input type="hidden" id="cardId" name="cardId" value="${card.id }">
		<input type="button" value="确定" onclick="draw();">&nbsp;&nbsp;
     </form>

	 <script type="text/javascript">

         function draw() {
             $.ajax({
                 url:'/card/draw.do',
                 type:'POST', //GET
                 async:true,    //或false,是否异步
                 data:{
                     amount:$('#amount').val(),
                     cardId:$('#cardId').val()
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

                     window.location.href='/card/toCardDetail.do?cardId=${card.id}';

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

<footer>
1997-2017@copy
</footer>

</html>