$(document).ready(function() {
	$.get("/cart", function(result) {
		let cnt = 1;
		$.each(result.cart, function(productId, productInfo) {
			let tr = ".tr-" + cnt;
			$('<img src="/img/products/' + productId + '.jpg" alt="Picture" onerror="this.src=\'/img/products/default.jpg\'"/>').appendTo(".checkout " + tr + " .product-img");
			$('<div class="fs-5">' + productInfo.name + '</div>').appendTo(".checkout " + tr + " .product-name");
			$('<div class="row text-center fs-5">' +  productInfo.price + ' $</div>').appendTo(".checkout " + tr + " .product-price");
			$('<div class="row text-center fs-5">' + productInfo.count + '</div>').appendTo(".checkout " + tr + " .product-count");
			cnt++;
		});
		
		$(".checkout .total-price").text(result.cartTotalPrice + " $");
	});
	
	$('input:radio[name="delivery-option"]').change(function() {
        if (this.checked && this.value === 'courier') {
			$(".post-office-delivery").hide();
			$(".courier-delivery").show();
		} else {
			$(".courier-delivery").hide();
			$(".post-office-delivery").show();
		}
    });
    
    $('input:radio[name="payment-option"]').change(function() {
        if (this.checked && this.value === 'card') {
			$(".card-payment").show();
		} else {
			$(".card-payment").hide();
		}
    });
});