<%--
  Created by IntelliJ IDEA.
  User: YOUNG
  Date: 2017/8/23
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ATM-SSM卡详细界面</title>
    <script type="text/javascript" src="/js/jquery-mini.js"></script>
</head>
<body>
<h1>用户名:${card.userId}</h1>
<h1>卡号:${card.cardNum}</h1>
<h1>余额:${card.balance}</h1>
<h1>卡ID:${cardId}</h1>
<a href="/card/toDeposit.do?cardId=${card.id}">存款</a>
<a href="/bankCard/toDraw.do?cardId=${card.id}">取款</a>
<a href="/card/toTransfer.do?cardId=${card.id}">转账</a>
<a href="###" onclick="listDetail();">查看流水</a>

<form id="searchForm" action="/card/listDetail.do">
    <input id="pageNum" type="hidden" name="pageNum" value="1">
    <input type="hidden" name="cardId" value="${card.id}">
    <input type="submit" value="查询" onclick="search();"> &nbsp;&nbsp;
    <a href="/bankCard/exportDetail.do?&cardId=${bankCard.id}">导出</a>
    <a href="/" >返回</a>
</form>

<table id="detailTable">
</table>

<h1 id="pageInfo"></h1>
<a href="###" onclick="firstPage();">首页</a>&nbsp;&nbsp;
<a href="###" onclick="prePage();">上一页</a>&nbsp;&nbsp;
<a href="###" onclick="nextPage();">下一页</a>&nbsp;&nbsp;
<a href="###" onclick="lastPage();">尾页</a>&nbsp;&nbsp;


<script type="text/javascript">
    var pageNum = '${data.currentPageNum}';
    var totalNum = '${data.totalPageNum}';


    function listDetail() {
        $.ajax({
            url:'/card/listDetail.do',
            type:'POST', //GET
            async:true,    //或false,是否异步
            data:{
                pageNum:pageNum,
                cardId:${card.id}
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
                var msg = "<tr><td>ID</td><td>卡ID</td><td>交易金额</td><td>操作类型</td><td>备注</td><td>开户时间</td></tr>";
                for (var i=0;i<cards.length;i++) {
                    var card = cards[i];
                    msg += '<tr>';
                    msg += '<td>' + card.id + '</td>';
                    msg += '<td>' + card.cardId + '</td>';
                    msg += '<td>' + card.amount + '</td>';
                    msg += '<td>' + card.optType + '</td>';
                    msg += '<td>' + card.flowDesc + '</td>';
                    msg += '<td>' + card.createTime + '</td>';
                    msg += '</tr>';
                }

                $('#detailTable').html(msg);
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
        listDetail();
    }

    function lastPage() {
        pageNum = totalNum;
        listDetail();
    }

    function nextPage() {
        if (pageNum + 1 > totalNum) {
            return;
        }

        pageNum += 1;
        listDetail();
    }

    function prePage() {
        if (pageNum - 1 <= 0) {
            return;
        }

        pageNum -= 1;
        listDetail();
    }

</script>
</body>
</html>
