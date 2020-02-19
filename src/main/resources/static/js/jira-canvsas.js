if (!('sendAsBinary' in XMLHttpRequest.prototype)) {
  XMLHttpRequest.prototype.sendAsBinary = function(string) {
    var bytes = Array.prototype.map.call(string, function(c) {
      return c.charCodeAt(0) & 0xff;
    });
    this.send(new Uint8Array(bytes).buffer);
  };
}
async function reportWithScreenshot() {
    let screenshot = await captureScreenshot(); // png dataUrl
    let img = q(".screen");
    img.src = screenshot; 
    
    let c = q(".screen-container");
    c.classList.remove('invisible_section')
        
    let box = await getDescription();    
    c.classList.add('invisible_section');
    
    postScreenshot(screenshot, '/upload/screenshot','image','screen.png','image/png', box)

}

let q = s => document.querySelector(s); 
window.reportWithScreenshot = reportWithScreenshot;

async function  captureScreenshot(selector="body") 
{
  return new Promise((resolve, reject) => {  
    let node = document.querySelector(selector);
    
    html2canvas(node, { onrendered: (canvas) => {
        let pngUrl = canvas.toDataURL("image/png");
        resolve(pngUrl);
    }});  
  });
}

async function getDescription(box) {
  return new Promise((resolve, reject) => {
     let b = q(".screen_section");
     let r = q(".screen_shot_region");
     let scr = q(".screen");
     let send = q(".send");
     let start=0;
     let sx,sy,ex,ey=-1;

     b.addEventListener("click", e=>{
       if(start==0) {
         sx=e.offsetX+scr.offsetLeft;
         sy=e.offsetY+b.offsetTop;
         ex=0;
         ey=0;
         r.style.top = sy +'px';
         r.style.left =sx +'px';     
         r.style.width = 0;
         r.style.height = 0; 
       }
       start=(start+1)%3;  		
     });
     
     b.addEventListener("mousemove", e=>{
       if(start==1) {
           ex=e.offsetX+scr.offsetLeft-sx;
           ey=e.offsetY+b.offsetTop-sy
           r.style.width = ex +'px';
           r.style.height = ey +'px'; 
       }
     });
     
     send.addEventListener("click", e=>{
       start=0;
       let a=100/75 //zoom out img 75%       
       resolve({
          x:Math.floor((sx-scr.offsetLeft)*a),
          y:Math.floor((sy-b.offsetTop)*a),
          width:Math.floor(ex*a),
          height:Math.floor(ey*a),
          desc: q('.bug-desc').value
          });
          
     });
  });
}

function postScreenshot(data, url, name, fn, type, description) {
	  data = data.replace('data:' + type + ';base64,', '');
	  var xhr = new XMLHttpRequest();
	  xhr.open('POST', url, true);
	  
	  var boundary = 'ohaiimaboundary';
	  xhr.setRequestHeader(
	    'Content-Type', 'multipart/form-data; boundary=' + boundary);
	  xhr.sendAsBinary([
	    '--' + boundary,
	    'Content-Disposition: form-data; name="' + name + '"; description="' + description +'"; filename="' + fn + '"', 
	    'Content-Type: ' + type,
	    '',
	    atob(data),
	    '--' + boundary + '--'
	  ].join('\r\n'));
	}