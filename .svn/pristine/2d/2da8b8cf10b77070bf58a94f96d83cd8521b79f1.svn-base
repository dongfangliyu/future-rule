function fieldsetting(){
    debugger
    layer.closeAll('page');
    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['500px', '400px'], //宽高
        content:'<div class="field-bounced-content">'+
        '<div class="field-bounced-section">'+
        '</div>'+
        '<div class="field-bounced-table">'+
        '<table>'+
        '<tr>'+
        '<td>区间</td>'+
        '<td>值</td>'+
        '<td>操作</td>'+
        '</tr>'+
        '</table>'+
        '</div>'+
        '<div class="field-bounced-first">'+
        '<div class="c-bounced-grid">'+
        '<input class="c-inputOne" type="text"/>'+
        '</div>'+
        '<div class="c-bounced-grid">'+
        '<input class="c-inputThree" type="text"/>'+
        '</div>'+
        '<div class="c-bounced-grid">'+
        '<img src="img/add.png" style="margin-left: 40px;"/>'+
        '<img src="img/delete.png" style="margin-left: 16px;" />'+
        '</div>'+
        '</div>'+
        '<div class="field-bounced-first">'+
        '<div class="c-bounced-grid">'+
        '<input class="c-inputOne" type="text"/>'+
        '</div>'+
        '<div class="c-bounced-grid">'+
        '<input class="c-inputThree" type="text"/>'+
        '</div>'+
        '<div class="c-bounced-grid">'+
        '<img src="img/add.png" style="margin-left: 40px;"/>'+
        '<img src="img/delete.png" style="margin-left: 16px;" />'+
        '</div>'+
        '</div>'+
        '<div class="field-bounced-first">'+
        '<div class="c-bounced-grid">'+
        '<input class="c-inputOne" type="text"/>'+
        '</div>'+
        '<div class="c-bounced-grid">'+
        '<input class="c-inputThree" type="text"/>'+
        '</div>'+
        '<div class="c-bounced-grid">'+
        '<img src="img/add.png" style="margin-left: 40px;"/>'+
        '<img src="img/delete.png" style="margin-left: 16px;" />'+
        '</div>'+
        '</div>'+
        '</div>'+
        '<div align="center" style="margin-top:30px;" class="areaButton">'+
        '<button>确定</button>'+
        '<button style="margin-left:20px;">取消</button>'+
        '</div>'

    });

}
$(function(){
    $.fn.atwho.debug = true;
    var names1 = ["Jacob","Isabella","Ethan","Emma","Michael","Olivia","Alexander","Sophia","William","Ava","Joshua","Emily","Daniel","Madison","Jayden","Abigail"];
    var names2 = $.map(names1,function(value,i) {
        return {'id':i,'name':value,'email':value+"@email.com"};
});
var at_config = {
        at: ":",
        data: names2,
        headerTpl: '<div class="atwho-header">Field List<small>↑&nbsp;↓&nbsp;</small></div>',
        limit: 200
    }
    $inputor = $('#inputor').atwho(at_config);
    $(document).on("focus",".inputor",function() {
        $(".inputor").removeAttr("id");
        $(this).attr("id","inputor");
        $inputor = $('#inputor').atwho(at_config);
    });
});