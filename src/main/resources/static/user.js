document.addEventListener("DOMContentLoaded", async function () {
    await getUser()
})



async function getUser() {
    let getUser = await fetch('user-info') // посылаем запрос
    const table = document.getElementById("tableUser")
    let json = await getUser.json();

    table.innerHTML = `
        <tr>
            <td>${json.id}</td>
            <td>${json.username}</td>
            <td>${json.roles.map(role => " " + role.name.substring(5))}</td>
            <td>${json.level}</td>
        </tr>
    `
}


