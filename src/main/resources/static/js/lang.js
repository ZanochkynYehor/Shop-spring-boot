$(document).ready(function () {
    $(".lang-select").change(function () {
        let url = document.location.href;
        let params = new URLSearchParams(document.location.search);

        if (params.get("lang") != null) {
            params.delete("lang");
        }
        params.append("lang", $(this).val());

        let index = url.indexOf('?');
        if (index == -1) {
            url = url + '?' + params.toString();
        } else {
            url = url.substring(0, index + 1) + params.toString();
        }

        document.location.href = decodeURIComponent(url);
    });
});