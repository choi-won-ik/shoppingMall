const $list = document.getElementById('seller-product-List');

if(sellerProductList!==null){
	sellerProductList.forEach((item)=>{
		let str = `
			<a href="http://localhost:8080/product/product/1" class="product-seller-otherProduct">
				<img src="${item.img}" alt="">
				<div>
					<h5>
						<strong>${item.price}원</strong>
					</h5>
				</div>
			</a>
		`;
		
		$("#seller-product-List").append(str);
	});
}


