async function editemail(){

    let email = document.getElementById("email").value;

    let response = await fetch('/mailConfirm', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(email)
    });
    if (!response.ok) {
        alert("Ошибка HTTP checkGuestID: " + response.status);
    }else{
        let element = document.getElementById("confirmEmail");
        element.onclick = "";
        element.innerText = "Проверьте почту";
    }
}