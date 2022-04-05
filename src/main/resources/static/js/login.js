$(document).ready(function () {
    $(".form-login").submit(function () {
        let flag = true;

        if ($("#email").val() === "") {
            $(".emailMsg").show();
            flag = false;
        }
        if ($("#password").val() === "") {
            $(".passwordMsg").show();
            flag = false;
        }

        return flag;
    });
});