<%@page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Map"%>

<html>

<head>
<meta charset="utf-8" />
<title>bank系统</title>
<script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>

<body>
	<h1>用户中心</h1>
	<h1>${login_user_flag.username }</h1>
	<a href="###" onclick="openAccount();">开户</a>
	<form id="searchForm" action="/bankCard/query.do">
		<input id="pageNum" type="hidden" name="pageNum" value="1">
	</form>
	<a href="/" >返回</a>
	<table id="xxx">
   	</table>
   	
	    <h1 id="pageInfo"></h1>
	    <a href="###" onclick="firstPage();">首页</a>&nbsp;&nbsp;
	    <a href="###" onclick="prePage();">上一页</a>&nbsp;&nbsp;
	    <a href="###" onclick="nextPage();">下一页</a>&nbsp;&nbsp;
	    <a href="###" onclick="lastPage();">尾页</a>
    
    <script type="text/javascript">
    	var pageNum = 1;
    	var totalNum = 0;
    	
    	listCards();
    	
    	function openAccount() {
    		if (confirm("你确定要开户吗？")) {
    			$.ajax({
                    url:'/card/openAccount.do',
                    type:'POST', //GET
                    async:true,    //或false,是否异步
                    data:{
                    },
                    timeout:5000,    //超时时间
                    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                    beforeSend:function(xhr){
                        console.log(xhr)
                        console.log('发送前')
                    },
                    success:function(data,textStatus,jqXHR){
                        
                        if (!data.success) {
                        	alert(data.message);
                        	return;
                        }
                        
                        alert('开户成功');
                        firstPage();
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
    		} else {
    			alert('开卡失败');
    		}
    	}
    	
    	function deleteCard(cardId) {
    		if (confirm("你确信要删除银行卡吗？")) {
    			$.ajax({
                    url:'/bankCard/deleteCard.do',
                    type:'POST', //GET
                    async:true,    //或false,是否异步
                    data:{
                    	cardId:cardId
                    },
                    timeout:5000,    //超时时间
                    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                    beforeSend:function(xhr){
                        console.log(xhr)
                        console.log('发送前')
                    },
                    success:function(data,textStatus,jqXHR){
                        
                        if (!data.success) {
                        	alert(data.message);
                        	return;
                        }
                        
                        alert('删除成功');
                        firstPage();
                        
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
    		} else {
    			alert('删除失败');
    		}
    	}
    	
    	function listCards() {
    		$.ajax({
                url:'/card/query.do',
                type:'POST', //GET
                async:true,    //或false,是否异步
                data:{
                	pageNum:pageNum
                },
                timeout:5000,    //超时时间
                dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                beforeSend:function(xhr){
                    console.log(xhr)
                    console.log('发送前')
                },
                success:function(data,textStatus,jqXHR){
                    
                    if (!data.success) {
                    	alert(data.message);
                    	return;
                    }
                    
                    var cards = data.data.data;
                    var msg = "<tr><td>ID</td><td>卡号</td><td>余额</td><td>状态</td><td>开户时间</td><td>操作</td></tr>";
                    for (var i=0;i<cards.length;i++) {
                    	var card = cards[i];
                    	msg += '<tr>';
                    	msg += '<td>' + card.id + '</td>';
                    	msg += '<td>' + card.cardNum + '</td>';
                    	msg += '<td>' + card.balance + '</td>';
                    	msg += '<td>' + card.status + '</td>';
                        msg += '<td>' + card.createTime + '</td>';
                    	msg += '<td><a href="/card/toCardDetail.do?cardId='+card.id+'">查看详情</a><a href="###" onclick="deleteCard('+card.id+');">删除</a></td>';
                    	msg += '</tr>';
                    }
                    
                    $('#xxx').html(msg);
                    totalNum = data.data.totalPageNum;
                    pageNum = data.data.currentPageNum;
                    
                    $('#pageInfo').html('当前页'+data.data.currentPageNum+'/'+data.data.totalPageNum);
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
    	
    	function firstPage() {
    		pageNum = 1;
    		listCards();
    	}
    	
    	function lastPage() {
    		pageNum = totalNum;
    		listCards();
    	}
    	
    	function nextPage() {
    		if (pageNum + 1 > totalNum) {
    			return;
    		}
    		
    		pageNum += 1;
    		listCards();
    	}
    	
    	function prePage() {
    		if (pageNum - 1 <= 0) {
    			return;
    		}
    		
    		pageNum -= 1;
    		listCards();
    	}
    
    </script>

</body>

<footer> 1997-2017@copyrightByYoung </footer>

</html>