document.addEventListener("DOMContentLoaded", function () {
    const alerts = document.querySelectorAll(".alert");
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.classList.add("fade");
        }, 4000);
    });
});
