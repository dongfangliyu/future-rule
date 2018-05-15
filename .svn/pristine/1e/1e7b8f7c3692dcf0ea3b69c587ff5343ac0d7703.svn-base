	 //弹出框部分

    var dialog = $(".dialog");
	var $mask = $("#mask");
	//自动居中对话框
	function autoCenter(el) {
		var bodyW = $(window).width();
		var bodyH = $(window).height();
		var elW = el.width();
		var elH = el.height();
		el.css({
			"left": (bodyW - elW) / 2 + 'px',
			"top": (bodyH - elH) / 2 + 'px'
		});
	};
	autoCenter(dialog);
	

	//禁止选中对话框内容
	if(document.attachEvent) { //ie的事件监听，拖拽div时禁止选中内容，firefox与chrome已在css中设置过-moz-user-select: none; -webkit-user-select: none;
		g('dialog').attachEvent('onselectstart', function() {
			return false;
		});
	}
	//声明需要用到的变量
	var mx = 0,
		my = 0; //鼠标x、y轴坐标（相对于left，top）
	var dx = 0,
		dy = 0; //对话框坐标（同上）
	var isDraging = false; //不可拖动
	var alertClass;
	//鼠标按下
	$(".move_part").mousedown(function(e) {
		e = e || window.event;
		mx = e.pageX; //点击时鼠标X坐标
		my = e.pageY; //点击时鼠标Y坐标
		dx = $(this).parent().offset().left;
		dy = $(this).parent().offset().top;
		isDraging = true; //标记对话框可拖动
		alertClass=($(this).parent().attr("class").split(" ")[0]);
	});

	//鼠标移动更新窗口位置
	$(document).mousemove(function(e) {
		var e = e || window.event;
		var x = e.pageX; //移动时鼠标X坐标
		var y = e.pageY; //移动时鼠标Y坐标
		if(isDraging) {//判断对话框能否拖动
			console.log("isDraging:"+isDraging);
			var moveX = dx + x - mx; //移动后对话框新的left值
			var moveY = dy + y - my; //移动后对话框新的top值
			//设置拖动范围
			var pageW = $(window).width();
			var pageH = $(window).height();
			var dialogW = dialog.width();
			var dialogH = dialog.height();
			var maxX = pageW - dialogW; //X轴可拖动最大值
			var maxY = pageH - dialogH; //Y轴可拖动最大值
			moveX = Math.min(Math.max(0, moveX), maxX); //X轴可拖动范围
			moveY = Math.min(Math.max(0, moveY), maxY); //Y轴可拖动范围
			console.log($(this).index());
			//重新设置对话框的left、top
			$("."+alertClass).css({
				left: moveX + 'px',
				top: moveY + 'px',
			});
		};
	});

	//鼠标离开
	$(".move_part").mouseup(function() {
		isDraging = false;
		console.log("isDraging:"+isDraging);
	});

	//点击关闭对话框
	$(".close").click(function() {
		$(this).parents(".dialog").hide();
//		$mask.css("display", "none");
	});

	//窗口大小改变时，对话框始终居中
	window.onresize = function() {
		autoCenter(dialog);
	};