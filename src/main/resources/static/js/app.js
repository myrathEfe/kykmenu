document.addEventListener("DOMContentLoaded", function () {
    const themeToggle = document.getElementById("themeToggle");
    const root = document.documentElement;
    const savedTheme = localStorage.getItem("kyk-theme") || root.getAttribute("data-theme") || "light";

    function applyTheme(theme) {
        root.setAttribute("data-theme", theme);
        localStorage.setItem("kyk-theme", theme);
        if (themeToggle) {
            themeToggle.setAttribute("aria-pressed", String(theme === "dark"));
        }
    }

    applyTheme(savedTheme);

    if (themeToggle) {
        themeToggle.addEventListener("click", function () {
            const nextTheme = root.getAttribute("data-theme") === "dark" ? "light" : "dark";
            applyTheme(nextTheme);
        });
    }

    const alerts = document.querySelectorAll(".alert");
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.classList.add("fade");
        }, 4000);
    });
});
