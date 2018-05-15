var Comm = Comm || {};

Comm.isIE6 = !window.XMLHttpRequest;	//ie6

/**
 * 获取URL参数值
 * @type {Comm.urlParam}
 */
Comm.getRequest = Comm.urlParam = function () {
    var param, url = location.search, theRequest = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0, len = strs.length; i < len; i++) {
            param = strs[i].split("=");
            theRequest[param[0]] = decodeURIComponent(param[1]);
        }
    }
    return theRequest;
};

/**
 * 通用post请求，返回json对象
 * @param url 请求地址
 * @param params 请求参数，没有传null
 * @param callback 成功时的回调函数
 * @param contentType
 * @param timeout 超时时间，参数可以不传
 * @param async 异步标志，true表示异步，false同步，参数可以不传
 * @param errorCallback 错误时的回调函数，参数可以不传
 * @param completeCallback 结束回调函数，参数可以不传
 */
Comm.ajaxPost = function (url, params, callback, contentType,processData, cache,timeout, async, errorCallback, completeCallback) {
    url = _ctx + "/" + url;
    if (url.indexOf('?') != -1) {
        url += "&r=" + _version;
    } else {
        url += "?r=" + _version;
    }
    if (contentType == null || typeof(contentType) == "undefined") {
        contentType = "application/x-www-form-urlencoded; charset=UTF-8";
    }
    if (timeout == null || typeof(timeout) == "undefined") {
        timeout = 30 * 1000;
    }
    if (async == null || typeof(async) == "undefined") {
        async = true;
    }
    if(processData == null || typeof(processData) == "undefined"){
        processData = true;
    }
    if(cache == null || typeof(cache) == "undefined"){
        cache = true;
    }
    $.ajax({
        type: "POST",
        url: url,
        async: async,
        data: params,
        timeout: timeout,
        contentType: contentType,
        processData:processData,
        cache:cache,
        dataType: "json",
        beforeSend: function () {
            layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
        },
        success: function (data) {
            if (data.code == 0) {
                callback(data);
            } else if (data.code == 1) {
                layer.msg(data.msg);
            } else if (data.code == 3 || data.code == 6) {
                layer.msg("会话失效，请重新登录！", {icon: 2, time: 2000}, function () {
                    Comm.getTopWinow().location = _ctx + '/login';
                });
            } else {
                layer.msg("系统或网络异常,请稍候重试！");
            }
        },
        error: function (err) {
            layer.msg("系统或网络异常,请稍候重试！");
            errorCallback && errorCallback(err);
        },
        complete: function () {
            layer.closeAll('loading');
            completeCallback && completeCallback();
        }
    });
};

/**
 * 通用get请求，返回json对象
 * @param url 请求地址
 * @param params 请求参数，没有传null
 * @param callback 成功时的回调函数
 * @param timeout 超时时间，参数可以不传
 * @param async 异步标志，true表示异步，false同步，参数可以不传
 * @param errorCallback 错误时的回调函数，参数可以不传
 * @param completeCallback 结束回调函数，参数可以不传
 */
Comm.ajaxGet = function (url, params, callback, timeout, async, errorCallback, completeCallback) {
    url = _ctx + "/" + url;
    if (url.indexOf('?') != -1) {
        url += "&r=" + _version;
    } else {
        url += "?r=" + _version;
    }
    if (timeout == null || typeof(timeout) == "undefined") {
        timeout = 30 * 1000;
    }
    if (async == null || typeof(async) == "undefined") {
        async = true;
    }
    $.ajax({
        type: "GET",
        url: url,
        async: async,
        timeout: timeout,
        dataType: "json",
        data: params,
        beforeSend: function () {
            layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
        },
        success: function (data) {
            if (data.code == 0) {
                callback(data);
            } else if (data.code == 1) {
                layer.msg(data.msg);
            } else if (data.code == 3) {
                Comm.getTopWinow().location = '/login?msg=会话失效,请重新登录';
            } else {
                layer.msg("系统或网络异常,请稍候重试！");
            }
        },
        error: function (err) {
            layer.msg("系统或网络异常,请稍候重试！");
            errorCallback && errorCallback(err);
        },
        complete: function () {
            layer.closeAll('loading');
            completeCallback && completeCallback();
        }
    });
};

/**
 * 判断是否为null
 * @param str
 * @returns {boolean}
 */
Comm.isNull = function isNull(str) {
    str = $.trim(str);
    if (str == null || str.length == 0) {
        return true;
    } else {
        return false;
    }
};

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
Comm.getTopWinow = function getTopWinow() {
    var p = window;
    while (p != p.parent) {
        p = p.parent;
    }
    return p;
};

(function ($) {
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);
