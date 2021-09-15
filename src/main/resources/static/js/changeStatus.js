
async function changeStatus(id){
    let input = document.getElementById("inputGroup").value;
    console.log(input.value);
    let response = await fetch('/admin/changeStatus/'+id, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(input)
    });
    if (!response.ok) {
        alert("Ошибка HTTP checkGuestID: " + response.status);
    }else{
        let element = document.getElementById("status");
        switch(input) {
            case 'new': {
                element.innerText = "Новый";
                break;
            }
            case 'inprogress': {
                element.innerText = "В работе";
                break;
            }
            case 'ended': {
                element.innerText = "Завершен";
                break;
            }
        }
    }
}