
getCart();

async function deleteFunc(id){
    //console.log("Delete id:" +id);
    console.log('/deleteCartItem/'+id);
    let response = await fetch('/deleteCartItem/'+id, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(getCookie("guestId"))
    });
    if (!response.ok) {
        alert("Ошибка HTTP checkGuestID: " + response.status);
    }else{
        console.log(response.json());
        let element = document.getElementById('row'+id);
        element.remove();
    }
}
async function getCart() {
    if(getCookie("guestId")!= undefined) {
        let response = await fetch('/getCart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(getCookie("guestId"))
        });
        if (!response.ok) {
            alert("Ошибка HTTP checkGuestID: " + response.status);
        }else{
            let json = await response.json();
            //console.log(json);
            //json.forEach()
            let elementToInsert = document.querySelector('div.cart_items');
            for (let i=0; i<json.length; i++){
                console.log(json[i].name);
                let headElement = document.createElement('div');
                headElement.classList.add('row');
                headElement.id="row"+json[i].id;
               // headElement.classList.add('row'+json[i].id);

                let nameElement = document.createElement('div');
                nameElement.className='col-lg-3 col-sm-3 col-xs-12 mob-fix';
                nameElement.innerText=json[i].name;
                headElement.append(nameElement);

                let priceElement = document.createElement('div');
                priceElement.className='col-lg-2 col-sm-2 col-xs-12 mob-fix';
                priceElement.innerText=json[i].price;
                headElement.append(priceElement);

                let countElement = document.createElement('div');
                countElement.className='col-lg-1 col-sm-2 col-xs-12 mob-fix';
                countElement.innerText="Кол-во:" + json[i].count;
                headElement.append(countElement);

                let deleteElement = document.createElement('div');
                deleteElement.className='col-lg-3 col-sm-2 col-xs-12 mob-fix btn btn-primary';
                deleteElement.innerText="Удалить";
                deleteElement.onclick = function (){deleteFunc(json[i].id)};
                headElement.append(deleteElement);

                elementToInsert.append(headElement);
            }
        }
    }
}


function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function setCookie(name, value, options = {}) {

    options = {
        path: '/'
    };

    if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString();
    }

    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);

    for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue;
        }
    }

    document.cookie = updatedCookie;
}
