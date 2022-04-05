$(document).ready(function () {
    let price = $("#minPrice").val() + '-' + $("#maxPrice").val();
    let index = price.indexOf('-');
    let minPriceBorder = $("#minPrice").attr("min");
    let maxPriceBorder = $("#maxPrice").attr("max");

    $("#priceRange").ionRangeSlider({
        type: "double",
        min: parseInt(minPriceBorder, 10),
        max: parseInt(maxPriceBorder, 10),
        from: parseInt(price.substring(0, index), 10),
        to: parseInt(price.substring(index + 1), 10),
        hide_min_max: true,
        hide_from_to: true,
        skin: "square"
    });

    let priceRange = $("#priceRange").data("ionRangeSlider");

    $("#minPrice").on("change", function () {
        let value = parseInt($(this).val(), 10);
        let maxPriceValue = parseInt($("#maxPrice").val(), 10);

        if (value < minPriceBorder) {
            $(this).val(minPriceBorder);
        } else if (value > maxPriceValue) {
            $(this).val(maxPriceValue);
        }

        priceRange.update({
            from: $(this).val()
        });
    });

    $("#maxPrice").on("change", function () {
        let value = parseInt($(this).val(), 10);
        let minPriceValue = parseInt($("#minPrice").val(), 10);

        if (value > maxPriceBorder) {
            $(this).val(maxPriceBorder);
        } else if (value < minPriceValue) {
            $(this).val(minPriceValue);
        }
        priceRange.update({
            to: $(this).val()
        });
    });

    $("#priceRange").on("change", function () {
        let $this = $(this),
            value = $this.prop("value").split(";");
        let minPrice = value[0];
        let maxPrice = value[1];

        $("#minPrice").val(minPrice);
        $("#maxPrice").val(maxPrice);
    });

    $(".productsFilterForm").submit(function(event) {
        event.preventDefault();

        let minPriceBorder = $("#minPrice").attr("min");
        let maxPriceBorder = $("#maxPrice").attr("max");
        let url = document.location.href;
        let serialize = $(this).serialize();
        let params = new URLSearchParams(serialize);

        let nameParam = params.get("name");
        if (nameParam === '') {
            params.delete("name");
        }

        let minPriceParam = parseInt(params.get("minPrice"), 10);
        let maxPriceParam = parseInt(params.get("maxPrice"), 10);

        params.delete("minPrice");
        params.delete("maxPrice");
        if (minPriceParam != minPriceBorder || maxPriceParam != maxPriceBorder) {
            params.append("price", minPriceParam + '-' + maxPriceParam);
        }

        if (params.get("categoryId") != null) {
            let categories = params.getAll("categoryId");
            params.delete("categoryId");
            params.append("categoryId", categories.join());
        }

        if (params.get("manufacturerId") != null) {
            let manufacturers = params.getAll("manufacturerId");
            params.delete("manufacturerId");
            params.append("manufacturerId", manufacturers.join());
        }

        let index = url.indexOf('?');
        if (index == -1) {
            url = url + '?' + params.toString();
        } else {
            url = url.substring(0, index + 1) + params.toString();
        }

        document.location.href = decodeURIComponent(url);
    });
});