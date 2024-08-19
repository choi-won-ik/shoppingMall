function execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			var addr = '';
			var extraAddr = '';

			if (data.userSelectedType === 'R') {
				addr = data.roadAddress;
			} else {
				addr = data.jibunAddress;
			}

			if (data.userSelectedType === 'R') {
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
			}

			if (data.zonecode) {
				document.getElementById('productAddress').value = data.zonecode;
				document.getElementById("productStreetaddress").value = addr + extraAddr;
				document.getElementById("productDetailaddress").focus();
			} else {
				console.error("Zonecode is missing in the data object.");
			}
		}
	}).open();
}