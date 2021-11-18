!function (e) {
    "use strict";

    function t() {
    }

    t.prototype.createStackedChart = function (e, t, a, i, o, r) {
        Morris.Bar({
            element: e,
            data: t,
            xkey: a,
            ykeys: i,
            stacked: !0,
            labels: o,
            hideHover: "auto",
            resize: !0,
            gridLineColor: "rgba(108, 120, 151, 0.1)",
            barColors: r
        })
    }, t.prototype.createDonutChart = function (e, t, a) {
        Morris.Donut({element: e, data: t, resize: !0, colors: a})
    }, e(".peity-pie").each(function () {
        e(this).peity("pie", e(this).data())
    }), e(".peity-donut").each(function () {
        e(this).peity("donut", e(this).data())
    }), e(".peity-line").each(function () {
        e(this).peity("line", e(this).data())
    }), t.prototype.init = function () {
        this.createStackedChart("morris-bar-stacked", 
        [
            {y: "Tháng 1", a: document.getElementById('thang1').value},
            {y: "Tháng 2", a: document.getElementById('thang2').value},
            {y: "Tháng 3", a: document.getElementById('thang3').value},
            {y: "Tháng 4", a: document.getElementById('thang4').value},
            {y: "Tháng 5", a: document.getElementById('thang5').value},
            {y: "Tháng 6", a: document.getElementById('thang6').value},
            {y: "Tháng 7", a: document.getElementById('thang7').value},
            {y: "Tháng 8", a: document.getElementById('thang8').value},
            {y: "Tháng 9", a: document.getElementById('thang9').value},
            {y: "Tháng 10", a: document.getElementById('thang10').value},
            {y: "Tháng 11", a: document.getElementById('thang11').value},
            {y: "Tháng 12", a: document.getElementById('thang12').value}
    ], "y", ["a"], ["Số lượng tin nhắn"], ["#1699dd"]);

        this.createDonutChart("morris-donut-example", 
        [
            {label: "Online", value: document.getElementById('useronline').value},
            {label: "Offline", value: document.getElementById('useroffline').value
        }], ["#655be6", "#1699dd"])

    }, e.Dashboard = new t, e.Dashboard.Constructor = t
}(window.jQuery), function () {
    "use strict";
    window.jQuery.Dashboard.init()
}();