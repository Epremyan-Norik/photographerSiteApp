console.log("Hello!")
submit();

async function submit() {
    let response = await fetch('/check');

    if (response.ok) { // если HTTP-статус в диапазоне 200-299
        // получаем тело ответа (см. про этот метод ниже)
        let json = await response.json();
        alert(json);
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}
