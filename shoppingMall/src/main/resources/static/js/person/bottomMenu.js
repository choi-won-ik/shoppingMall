const csrfToken = document.getElementById('csrf-token').value;
const csrfParameterName = document.getElementById('csrf-parameter-name').value;

// 상품 tap
productList.forEach((item) => {
	let str = `
	<a href='http://localhost:8080/product/product/${item.id}' class='productItem'>
    	<img src='${item.img}' alt=''>
    	<h5>${item.productName}</h5>
    	<div><strong>${item.price}원</strong><hr></div>
    	<div>${item.productStreetaddress}</div>
	</a>`;

	$('#product').append(str);
});

// likey tap
if(likeyList!==null){
	likeyList.forEach((item)=>{
		let str =`
			<div class="productItem">
				<img src="${item.img}" alt="">
				<h5>${item.productName}</h5>
				<div>
					<strong>${item.price}원</strong>
					<hr>
				</div>
				<div class="likey">
					<div>경기도 화성시 향남읍</div>
					<form method="post" action="/person/likey">
						<input type="hidden" value="${csrfToken}" name="${csrfParameterName}"/>
						<button class="likeyBTN" id="likey-BTN">
						<input type="hidden" name="userid" value="${userID}">
							<input type="number" class="hidden" name="productCode" value="${item.productNo}">
							<svg t="1689815540548" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2271">
								<path class="likey icon plus" d="M742.4 101.12A249.6 249.6 0 0 0 512 256a249.6 249.6 0 0 0-230.72-154.88C143.68 101.12 32 238.4 32 376.32c0 301.44 416 546.56 480 546.56s480-245.12 480-546.56c0-137.92-111.68-275.2-249.6-275.2z" p-id="2272" id="heart"></path>
							</svg>
						</button>
					</form>
				</div>
			</div>
		`;
		
		$("#likey-products").append(str);
	});
}



// 팔로우 tap
followers.forEach((item) => {
	let imgSrc = item.img ? item.img : '/img/user.png';
	let formAC = "";
	
	let followStr = "";
	if(item.userid===loginID){
		followStr ="";
	}
	else{
		if(item.follow===true){
			followStr =`
				<button type="submit" class='followBTN'>
					팔로잉
				</button>
			`;
			
			formAC = "/unfollow";
		}
		
		else{
			followStr =`
				<button type="submit" class='followBTN'>
					<img src='/img/팔로우.png' alt=''>팔로우
				</button>
			`;
			
			formAC = "/follow";
		}
	}
	
	let str = `
		<form action="${formAC}" method="post" class='follow-profile'>
			<a href="http://localhost:8080/person/mypage/${item.userid}"><img src='${imgSrc}' alt=''></a>
			<div>${item.user}</div>
			<div class='secound-line'>
				<p>상품</p>
				<p>${item.productNUM}</p>
			</div>
			<div>${followStr}</div>
			<input type="hidden" name="userID" value="${item.userid}">
			<input type="hidden" value="${csrfToken}" name="${csrfParameterName}"/>
		</form>`;

	$('#all-follower').append(str);
});

// 팔로잉 tap
followings.forEach((item)=>{
	let imgSrc = item.profileImg ? item.profileImg : '/img/user.png';
	let formAC = "";
	
	let followStr = "";
	if(item.follow===true){
		followStr =`
			<button class='followBTN'>
				팔로잉
			</button>
		`;
		formAC = "/unfollow";
	}
	else if(item.userid===loginID){
		followStr ="";
	}
	else{
		followStr =`
			<button class='followBTN'>
				<img src='/img/팔로우.png' alt=''>팔로우
			</button>
		`;
		formAC = "/follow";
	}
	
	let str = `
		<span class="user">
			<form action="${formAC}" method="post" class="following-user">
				<a href="http://localhost:8080/person/mypage/${item.userid}"><img class="following-profile-img" src="${imgSrc}" alt=""></a>
				<strong>${item.user}</strong>
				<div class="following-user-btn">
					<div class="etc">
						<div>상품 </div>
						<div>${item.productNUM}</div>
					</div>
					<div class="etc">
						<div>팔로워 </div>
						<div>${item.followerNUM}</div>
					</div>
				</div>
				<div>${followStr}</div>
				<input type="hidden" name="userID" value="${item.userid}">
				<input type="hidden" value="${csrfToken}" name="${csrfParameterName}"/>
			</form>
	`;
	
	if(item.productImg!==null){
		item.productImg.forEach((index)=>{
			str+=`
				<div class="user-product">
					<a href="http://localhost:8080/product/product/${index.id}" class="following-product-ex">
						<img src="${index.img}" alt="">
					</a>
				</div>
			`;
		});
		
		str+="</span>";
	}else{
		str+="</span>";
	}
	
	$("#all-following").append(str);
});



