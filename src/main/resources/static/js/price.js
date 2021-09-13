console.log("Hello!")
//transferGuestCartToUser();

async function addToList(productId) {
    if(getCookie("guestId")!= undefined) {
        let response = await fetch('/addToCart/'+productId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(getCookie("guestId"))
        });
        if (!response.ok) {
            alert("Ошибка HTTP checkGuestID: " + response.status);
        } else{
            let json = response.json();
            console.log("Ответ:" + json);
            let addproduct = document.getElementById("add"+productId);
            addproduct.innerText = "Добавлен";
            addproduct.style.backgroundColor = 'red';
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
