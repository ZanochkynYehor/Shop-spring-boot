$(document).ready(function () {
    let nameRegex = /^[a-z ,.'-]+$/i;
    let emailRegex = /^[a-z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-z0-9-]+(?:\.[a-z0-9-]+)*$/i;
    let passRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,20}$/;

    $("#firstName").keyup(function () {
        if ($(this).val().match(nameRegex)) {
            $(".firstNameMsg").hide();
        } else {
            $(".firstNameMsg").show();
        }
    });

    $("#lastName").keyup(function () {
        if ($(this).val().match(nameRegex)) {
            $(".lastNameMsg").hide();
        } else {
            $(".lastNameMsg").show();
        }
    });

    $("#email").keyup(function () {
        if ($(this).val().match(emailRegex)) {
            $(".emailMsg").hide();
        } else {
            $(".emailMsg").show();
        }
    });

    $("#password").keyup(function () {
        if ($(this).val().match(passRegex)) {
            $(".passwordMsg").hide();
        } else {
            $(".passwordMsg").show();
        }
    });

    $("#confirmPassword").keyup(function () {
        if ($(this).val() === $("#password").val()) {
            $(".confirmPasswordMsg").hide();
        } else {
            $(".confirmPasswordMsg").show();
        }
    });

    $(".signupForm").submit(function () {
        let flag = true;

        if (!$("#firstName").val().match(nameRegex)) {
            $(".firstNameMsg").show();
            flag = false;
        }
        if (!$("#lastName").val().match(nameRegex)) {
            $(".lastNameMsg").show();
            flag = false;
        }
        if (!$("#email").val().match(emailRegex)) {
            $(".emailMsg").show();
            flag = false;
        }
        if (!$("#password").val().match(passRegex)) {
            $(".passwordMsg").show();
            flag = false;
        }
        if ($("#confirmPassword").val() !== $("#password").val()) {
            $(".confirmPasswordMsg").show();
            flag = false;
        }
        if ($("#captcha").val() === "") {
            $(".captchaMsg").show();
            flag = false;
        }

        return flag;
    });
});