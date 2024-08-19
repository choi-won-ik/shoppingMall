const followerBTN = () => {
	const $more = document.getElementById('more');
	const $body = document.getElementById('more-body');
	const $title = document.getElementById('more-follower-title');

	$more.classList.add('show');
	$title.classList.add('show');
	document.body.style.overflow = 'hidden';
	
	followers.forEach((item)=>{
		let imgSrc = item.img ? item.img : '/img/user.png';
		let str =`
			<a href="http://localhost:8080/person/mypage/${item.userid}" class="item-user">
				<img src="${imgSrc}" alt="">
				<div>
					<h5>${item.user}</h5>
				</div>
			</a>
		`;
		
		$('#item-users').append(str);
	});

	if ($more.classList.contains('show')) {
		$more.addEventListener('click', (e) => {
			if (!$body.contains(e.target)) {
				document.getElementById('item-users').innerHTML="";
				document.body.style.overflow = '';
				$title.classList.remove('show');
				$more.classList.remove('show');
			}
		}, { once: true });

		window.addEventListener('keydown', (event) => {
			if (event.keyCode === 27) {
				document.getElementById('item-users').innerHTML="";
				document.body.style.overflow = '';
				$title.classList.remove('show');
				$more.classList.remove('show');
			}
		});
	}

};

const followingBTN = () => {
	const $more = document.getElementById('more');
	const $body = document.getElementById('more-body');
	const $title = document.getElementById('more-following-title');

	$more.classList.add('show');
	$title.classList.add('show');
	document.body.style.overflow = 'hidden';
	followings.forEach((item)=>{
		let imgSrc = item.profileImg ? item.profileImg : '/img/user.png';
		let str =`
			<a href="http://localhost:8080/person/mypage/${item.userid}" class="item-user">
				<img src="${imgSrc}" alt="">
				<div>
					<h5>${item.user}</h5>
				</div>
			</a>
		`;
		
		$('#item-users').append(str);
	});

	if ($more.classList.contains('show')) {
		$more.addEventListener('click', (e) => {
			
			if (!$body.contains(e.target)) {
				document.getElementById('item-users').innerHTML="";
				document.body.style.overflow = '';
				$title.classList.remove('show');
				$more.classList.remove('show');
			}
		}, { once: true });

		window.addEventListener('keydown', (event) => {
			if (event.keyCode === 27) {
				document.getElementById('item-users').innerHTML="";
				document.body.style.overflow = '';
				$title.classList.remove('show');
				$more.classList.remove('show');
			}
		});
	}
};