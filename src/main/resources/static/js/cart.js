$(document).on("click", "#open-cart-button", function() {
	$.get("/cart", function(result) {
		$(".cart").remove();

		if (result.cartSize == 0) {
			$(".empty-cart-msg").show();
			$(".modal-footer").hide();
		} else {
			$(".empty-cart-msg").hide();

			$("<div>", { class: 'cart' }).appendTo(".modal-body .container-fluid")
				.append($("<table>", { class: 'table table-bordered table-sm cart-table' }));
			$("<tbody>", { class: 'cart-table-tbody' }).appendTo(".cart-table");
			$.each(result.cart, function(productId, productInfo) {
				$("<tr>", { id: productId }).appendTo(".cart-table-tbody")
					.append($("<td>", { class : 'td-' + productId }));
				$("<div>", { class: 'product-info p-1' }).appendTo(".cart .td-" + productId)
					.append($("<div>", { class: "row justify-content-center pb-1 product-top" }));
				$("<div>", { class: 'col align-self-center product-name text-start' }).appendTo(".cart .td-" + productId + " .product-top").text(productInfo.name);
				$("<div>", { class: 'col-lg-1 remove-btn' }).appendTo(".cart .td-" + productId + " .product-top")
					.append($("<button>", { class: 'btn btn-sm remove-product-button', value: productId }));
				$("<i>", { class: 'bi bi-trash-fill' }).appendTo(".cart .td-" + productId + " .remove-product-button");
				$("<div>", { class: 'row align-items-center product-bottom' }).appendTo(".cart .td-" + productId + " .product-info")
					.append($("<div>", { class: 'col-lg-3 product-img' }));
				$("<img>", { src: '/img/products/' + productId + '.jpg', alt: 'Picture', onerror: "this.src='/img/products/default.jpg" }).appendTo(".cart .td-" + productId + " .product-img");
				$("<div>", { class: 'col-lg-3 offset-lg-2 align-self-center' }).appendTo(".cart .td-" + productId + " .product-bottom")
					.append($("<div>", { class: 'input-group input-group-sm' }));
				$("<button>", { id: 'minus-button-' + productId, class: 'btn product-minus-count-button', value: productId }).appendTo(".cart .td-" + productId + " .input-group")
					.append($("<i>", { class: 'bi bi-dash-circle-fill' }));
				$("<input>", { id: 'count-' + productId, class: 'form-control product-count', value: productInfo.count, readonly: true }).appendTo(".cart .td-" + productId + " .input-group");
				$("<button>", { class: 'btn product-plus-count-button', value: productId }).appendTo(".cart .td-" + productId + " .input-group")
					.append($("<i>", { class: 'bi bi-plus-circle-fill' }));
				$("<div>", { class: 'col-lg-2 offset-lg-2' }).appendTo(".cart .td-" + productId + " .product-bottom").text(productInfo.price + ' $');

				if (productInfo.count == 1) {
					$("#minus-button-" + productId).prop('disabled', true);
				}
			});

			$(".modal-footer .total-price").text(result.cartTotalPrice + " $");
			$(".modal-footer").show();
		}
	});
});

$(document).on("click", ".add-to-cart-button", function() {
	$.ajax({
		url: "/cart",
		type: "POST",
		data: jQuery.param({ productId: $(this).val() }),
		headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
		success: function (result) {
			$(".cart-size-badge").text(result.cartSize);
			if (result.cartSize > 0) {
				$(".cart-size-badge").show();
			}/* else {
				$(".cart-size-badge").hide();
			}*/
		}
	});

	/*$.post("/cart", { productId: $(this).val() }, function(result) {
		$(".cart-size-badge").text(result.cartSize);
		if (result.cartSize > 0) {
			$(".cart-size-badge").show();
		} else {
			$(".cart-size-badge").hide();
		}
	});*/
});

$(document).on("click", ".product-minus-count-button", function() {
	let productId = $(this).val();
	let productCount = parseInt($("#count-" + productId).val()) - 1;
	
	if (productCount == 1) {
		$(this).prop('disabled', true);
	}
	
	ajaxSetProductCount(productId, productCount);
});

$(document).on("click", ".product-plus-count-button", function() {
	let productId = $(this).val();
	let productCount = parseInt($("#count-" + productId).val()) + 1;
	
	if (productCount > 1) {
		$("#minus-button-" + productId).prop('disabled', false);
	}
	
	ajaxSetProductCount(productId, productCount);
});

function ajaxSetProductCount(productId, productCount) {
	$.ajax({
		url: "/cart?" + $.param({ "productId": productId, "productCount": productCount }),
		type: "PUT",
		headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
		success: function(result) {
			$("#count-" + productId).val(productCount);
			$(".modal-footer .total-price").text(result.cartTotalPrice + " $");
		}
	});
}

$(document).on("click", ".clear-cart-button", function() {
	$.ajax({
		url: "/cart",
		type: "DELETE",
		headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
		success: function() {
			clearCart();
		}
	});
});

$(document).on("click", ".remove-product-button", function() {
	let productId = $(this).val();
	$.ajax({
		url: "/cart?" + $.param({ "productId": productId }),
		type: "DELETE",
		headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
		success: function(result) {
			if (result.cartSize == 0) {
				clearCart();
			} else {
				$("tr#" + productId).remove();
				$(".modal-footer .total-price").text(result.cartTotalPrice + " $");
				$(".cart-size-badge").text(result.cartSize);
			}
		}
	});
});

function clearCart() {
	$(".cart").remove();
	$(".modal-footer").hide();
	$(".cart-size-badge").hide();
	$(".empty-cart-msg").show();
}