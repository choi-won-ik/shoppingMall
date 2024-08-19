const $kakaoPayBTN = document.getElementById('kakaoPayBTN');

const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

$kakaoPayBTN.addEventListener('click', () => {
	let $productStreetaddress = document.getElementById('productStreetaddress');
	let $productDetailaddress = document.getElementById('productDetailaddress');

	if ($productDetailaddress.value !== "" && $productStreetaddress.value !== "") {
		let $quantity = document.getElementById('quantity');

		$.ajax({
			url: '/kakaoPay',
			type: 'post',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			headers: { 'Content-Type': 'application/json' },
			dataType: 'json',
			data: JSON.stringify({
				partnerOrderId: kakaoPay.partnerOrderId,
				partnerUserId: kakaoPay.partnerUserId,
				itemName: kakaoPay.itemName,
				quantity: $quantity.value,
				totalAmount: kakaoPay.totalAmount*$quantity.value,
				taxFreeAmount: kakaoPay.taxFreeAmount,
				productStreetaddress: $productStreetaddress.value,
				productDetailaddress: $productDetailaddress.value
			}),
			success: (data) => {
				var kakaopay = {
					ref: null,
				};

				kakaopay.ref = window.open('', 'paypopup', 'width=426,height=510,toolbar=no');
				if (kakaopay.ref) {
					setTimeout(function() {
						kakaopay.ref.location.href = data.next_redirect_pc_url;
					}, 0);

					var checkPopupClosed = setInterval(function() {
						if (kakaopay.ref.closed) {
							clearInterval(checkPopupClosed);

							window.location.href="http://localhost:8080/complete"
						}
					}, 1000);
				} else {
					throw new Error("popup을 열 수 없습니다!(cannot open popup)");
				}

				console.log("test")
			}, error: (xhr, status, error) => {
				if (xhr.status === 401) {
					window.location.href = "http://localhost:8080/login/login";
				} else {
					alert("알 수 없는 오류");
				}

			}
		});
	} else {
		alert("주소를 입력해 주십시오.");
	}
});