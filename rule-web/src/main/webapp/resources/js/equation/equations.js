/**
 *
 */
$(".c-operational-character , .c-operational-characterTwo").on("mouseenter",function(){
    $(this).find(".c-operator-show").hide();
    $(this).find(".c-operator-hide").show();
})
$(".c-operational-character , .c-operational-characterTwo").on("mouseleave",function(){
    $(this).find(".c-operator-show").show();
    $(this).find(".c-operator-hide").hide();
});
var dataId;
//获取光标位置
(function($, undefined) {
    $.fn.getCursorPosition = function() {
        var el = $(this).get(0);
        var pos = 0;
        if ('selectionStart' in el) {
            pos = el.selectionStart;
        } else if ('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    }
})(jQuery);
$(".c-operational-character").on("click",function(){
    dataId=$(this).attr("dataId");
    var strIndex=$("#inputor").getCursorPosition();
    var content=$("#inputor").val();
    var str1=content.slice(0,strIndex);
    var str2=content.slice(strIndex,content.length);
    var txtFocus = document.getElementById("inputor");
    switch (dataId) {
        case '0':
            str1=str1+'+';
            break;
        case '1':
            str1=str1+'-';
            break;
        case '2':
            str1=str1+'*';
            break;
        case '3':
            str1=str1+'/';
            break;
        case '7':
            str1=str1+'()';
            break;
        default:

            break;
    }
    var newContent=str1+str2;
    $("#inputor").val(newContent);
    if(dataId==7){
        var position=str1.length-1;
        txtFocus.setSelectionRange(position,position);//设置光标位置，不适用IE
        $("#inputor").focus();
    }else{
        var position=str1.length;
        txtFocus.setSelectionRange(position,position);
        $("#inputor").focus();
    }


});
$(".c-operational-characterTwo").on("click",function(){
    dataId=$(this).attr("dataId");
    var strIndex=$("#inputor").getCursorPosition();
    var content=$("#inputor").val();
    var str1=content.slice(0,strIndex);
    var str2=content.slice(strIndex,content.length);
    var txtFocus = document.getElementById("inputor");
    switch (dataId) {
        case '4':
            str1=str1+'sqrt()';
            break;
        case '5':
            str1=str1+'ln()';
            break;
        case '6':
            str1=str1+'avg()';
            break;
        case '8':
            str1=str1+'abs()';
            break;
        case '9':
            str1=str1+'max()';
            break;
        case '10':
            str1=str1+'min()';
            break;
        case '11':
            str1=str1+'lg()';
            break;
        case '12':
            str1=str1+'exp()';
            break;
        case '13':
            str1=str1+'ceil()';
            break;
        case '14':
            str1=str1+'floor()';
            break;
        case '15':
            str1=str1+'PI';
            break;
        default:
            break;
    }
    var newContent=str1+str2;
    $("#inputor").val(newContent);
    var position=str1.length;
    txtFocus.setSelectionRange(position,position);
    $("#inputor").focus();
});
