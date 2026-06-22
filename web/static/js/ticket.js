/**
 * 票务页面 JavaScript
 */
(function() {
    'use strict';

    // 分类标签切换高亮
    var categoryLinks = document.querySelectorAll('.category-tabs a');
    var currentType = getQueryParam('type');
    categoryLinks.forEach(function(link) {
        if (currentType && link.href.indexOf('type=' + currentType) > -1) {
            link.classList.add('active');
        }
    });

    // 加入购物车数量校验
    var addCartForms = document.querySelectorAll('.detail-info form');
    addCartForms.forEach(function(form) {
        form.addEventListener('submit', function(e) {
            var qty = this.querySelector('input[name="quantity"]');
            if (qty && (parseInt(qty.value) < 1)) {
                alert('数量至少为1');
                e.preventDefault();
            }
        });
    });
})();
