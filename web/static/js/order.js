/**
 * 订单页面 JavaScript
 */
(function() {
    'use strict';

    // 购物车数量输入自动提交
    var qtyInputs = document.querySelectorAll('.cart-item input[type="number"]');
    qtyInputs.forEach(function(input) {
        input.addEventListener('change', function() {
            var form = this.closest('form');
            if (form) form.submit();
        });
    });

    // 支付确认
    var payBtns = document.querySelectorAll('a[href*="order/pay"]');
    payBtns.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            if (!confirm('确定支付该订单？')) {
                e.preventDefault();
            }
        });
    });
})();
