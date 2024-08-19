products.forEach((index)=>{
	let str = "<div class='product'>";
	str += "<a href='http://localhost:8080/product/product/"+index.id+"'>";
	str += "<img src='"+index.img+"' alt=''></a></div>";
	
	$("#products-list").prepend(str);
});