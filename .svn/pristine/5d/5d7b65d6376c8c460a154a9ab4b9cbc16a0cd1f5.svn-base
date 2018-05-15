$(document).ready(function(){
	jQuery(function($) {							
			  var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
			  var previousPoint = null;	
				var d1 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.5) {
					d1.push([i, Math.sin(i)]);
				}
			
				var d2 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.5) {
					d2.push([i, Math.cos(i)]);
				}
			
				var d3 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.2) {
					d3.push([i, Math.tan(i)]);
				}
				
				$('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {

					var $source = $(source);
					var $parent = $source.closest('.tab-content')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			
				//Android's default browser somehow is confused when tapping on label which will lead to dragging the task
				//so disable dragging when clicking on label
				var agent = navigator.userAgent.toLowerCase();
				if("ontouchstart" in document && /applewebkit/.test(agent) && /android/.test(agent))
				  $('#tasks').on('touchstart', function(e){
					var li = $(e.target).closest('#tasks li');
					if(li.length == 0)return;
					var label = li.find('label.inline').get(0);
					if(label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation() ;
				});
				
				$('#tasks input:checkbox').removeAttr('checked').on('click', function(){
					if(this.checked) $(this).closest('li').addClass('selected');
					else $(this).closest('li').removeClass('selected');
				});				
			
			});
			//设置样式
function addTab(subtitle, url) {
    if (!$('#tt').tabs('exists', subtitle)) {
        $('#tt').tabs('add', { 
            title: subtitle,
            content: createFrame(url),
            closable: true 
        });
        $('#tt').tabs('getSelected').css('width', 'auto'); 
		$('#tt').tabs('getSelected').height($(window).height());  
    }
    tabClose();
    $('#tt').tabs('getSelected').css('width', 'auto'); 
}
   
function createFrame(url) {
    var heights=$(window).height()-85; 	
    var s = '<iframe id="frame_con" scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%; height:'+ heights+'px"></iframe>';
    return s;
}

function tabClose() {
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function() {
        var subtitle = $(this).children("span").text();
        $('#tt').tabs('close', subtitle);
    });
}		
});