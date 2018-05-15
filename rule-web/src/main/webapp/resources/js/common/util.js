/**
 *jQuery 插件扩展
 *
 *@author jason 
 *@date 2016-11-07
 */
jQuery.extend({
	/**
	 * 获取checkbox中选中的值 用逗号隔开
	 *
	 *@param name
	 *
	 */	
	getChkValueArr: function(name) {
		var arr = $("input[type='checkbox'][name='" + name + "']").serializeArray();
		var selectArr = [];
		$.each(arr, function(i, item) {
			selectArr.push(item.value);
		});
		return selectArr;
	}
});