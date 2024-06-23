function withdrawalFn(){
	let select = confirm("초과근무 신청을 철회하시겠습니까?");
	if(select===true){
		let f = document.createElement('form');
    
		let overtimeNoInput;
		overtimeNoInput = document.createElement('input');
		overtimeNoInput.setAttribute('type', 'hidden');
		overtimeNoInput.setAttribute('name', 'overtimeNo');
		overtimeNoInput.setAttribute('value', value);
		f.appendChild(overtimeNoInput);
		
		f.setAttribute('method', 'post');
		f.setAttribute('action', 'withdrawal.do');
		document.body.appendChild(f);
		f.submit();
	}
}