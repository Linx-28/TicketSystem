/**
 * 通用 JavaScript 工具函数
 */

/** 确认对话框 */
function confirmAction(msg, onConfirm) {
    if (confirm(msg || '确定执行此操作吗？')) {
        if (typeof onConfirm === 'function') onConfirm();
        return true;
    }
    return false;
}

/** 格式化金额 */
function formatPrice(price) {
    return '¥' + parseFloat(price).toFixed(2);
}

/** 获取 URL 参数 */
function getQueryParam(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURIComponent(r[2]);
    return null;
}
