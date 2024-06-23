const sec = document.querySelector("#sec");
let i = 5;
function getClock() {
   sec.innerText = i;
   if(i===0){
	   location.href = path;
   }
   i--;
}
getClock();
setInterval(getClock, 1000);