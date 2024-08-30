 /*秒殺接口隱藏*/
 function getSkPath() {
    var goodsId = $("#goodsId").val();
    g_showLoading();
    $.ajax({
        url: "/seckill/path",
        type: "GET",
        data: {
            goodsId: goodsId,
            verifyCode: $("#verifyCode").val() // 獲取驗證碼的值
        },
        success: function (data) {
            if (data.code == 0) {
                var path = data.data;// 獲取服務端返回的秒殺地址
                doSeckill(path);// 真正做秒殺的接口
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.msg("客戶端請求有誤");
        }
    });
}

/* 秒殺接口壓力測試時，注釋掉：驗證碼和秒殺地址隱藏 */
/*真正做秒殺的接口, path為服務端返回的秒殺接口地址*/
function doSeckill(path) {
    $.ajax({
        url: "/seckill/" + path + "/doSeckill",
        type: "POST",
        data: {
            goodsId: $("#goodsId").val()
        },
        success: function (data) {
            if (data.code == 0) {
                //window.location.href="/order_detail.htm?orderId="+data.data.id;
                getSkResult($("#goodsId").val());
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.msg("客戶端請求有誤");
        }
    });
}

/*獲取秒殺的結果*/
function getSkResult(goodsId) {
    g_showLoading();// 加載中
    $.ajax({
        url: "/seckill/result",
        type: "GET",
        data: {
            goodsId: $("#goodsId").val()
        },
        success: function (data) {
            if (data.code == 0) {
                var result = data.data;
                // console.log(data);
                if (result < 0) {
                    layer.msg("對不起，秒殺失敗");
                } else if (result == 0) {// 繼續輪詢發送秒殺請求
                    setTimeout(function () {
                        getSkResult(goodsId);
                    }, 500);// 輪詢間隔500ms
                } else {
                    layer.confirm("恭喜你，秒殺成功！查看訂單？", {btn: ["確定", "取消"]},
                        // 確定的回調
                        function () {
                            window.location.href = "/order_detail.htm?orderId=" + result;
                        },
                        // 取消的回調
                        function () {
                            layer.closeAll();
                        });
                }
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.msg("客戶端請求有誤");
        }
    });
}

// 使用ajax從服務端請求頁面數據, 跳轉到這個頁面時就會執行(function有$)
$(function () {
    // countDown();
    getDetail();
});

/*獲取商品詳情*/
function getDetail() {
    var goodsId = g_getQueryString("goodsId");// goodsId為goods_list.html中詳情url中的參數
    $.ajax({
        url: "/goods/getDetails/" + goodsId,
        type: "GET",
        success: function (data) {
            if (data.code == 0) {
                render(data.data);
            } else {
                layer.msg(data.msg);
            }
        },
        error: function () {
            layer.msg("客戶端請求有誤");
        }
    });
}

/*渲染頁面*/
function render(detail) {
    var seckillStatus = detail.seckillStatus;
    var remainSeconds = detail.remainSeconds;
    var goods = detail.goods;
    var user = detail.user;
    console.log(detail);
    // console.log(goods);
    // console.log(user);
    if (user != null) {
        // $("#userTip").hide();
        $("#userTip").text(user.nickname+" 你好！");
        console.log(user.nickname);
    }

    $("#goodsName").text(goods.goodsName);
    $("#goodsImg").attr("src", goods.goodsImg);
    $("#startTime").text(new Date(goods.startDate).format("yyyy-MM-dd hh:mm:ss"));
    $("#remainSeconds").val(remainSeconds);
    $("#goodsId").val(goods.id);
    $("#goodsPrice").text(goods.goodsPrice);
    $("#seckillPrice").text(goods.seckillPrice);
    $("#stockCount").text(goods.stockCount);
    countDown();
}

function countDown() {
    var remainSeconds = $("#remainSeconds").val();
    var timeout;
    if (remainSeconds > 0) {// 秒殺還沒開始，倒計時
        $("#buyButton").attr("disabled", true);
        $("#skTip").html("秒殺倒計時：" + remainSeconds + "秒");
        timeout = setTimeout(function () {
            $("#countDown").text(remainSeconds - 1);
            $("#remainSeconds").val(remainSeconds - 1);
            countDown();
        }, 1000);
    } else if (remainSeconds == 0) { // 秒殺進行中
        $("#buyButton").attr("disabled", false);
        if (timeout) {
            clearTimeout(timeout);
        }
        $("#skTip").html("秒殺進行中");
        // 在倒計時結束時獲取驗證碼（使用ajax異步向服務器請求驗證碼圖片）
        $("#verifyCodeImg").attr("src", "/seckill/verifyCode?goodsId=" + $("#goodsId").val());
        $("#verifyCodeImg").show();// 從服務器加載完驗證碼圖片後，顯示出來
        $("#verifyCode").show();
    } else {//秒殺已經結束
        $("#buyButton").attr("disabled", true);
        $("#skTip").html("秒殺已經結束");
        $("#verifyCodeImg").hide();
        $("#verifyCode").hide();
    }
}

/*點擊驗證碼圖片時，從服務器異步獲取另一張驗證碼碼圖片*/
function refreshVerifyCode() {
    console.log("刷新驗證碼");
    $("#verifyCodeImg").attr("src", "/seckill/verifyCode?goodsId=" + $("#goodsId").val() + "&timestamp=" + new Date().getTime());
}