
let greatButton = document.getElementById("greatUserButton")

greatButton.onclick = async function () {
    grUser()
    redirect()
    //Очистка таблицы
    getTableUser()
}

async function grUser() {
    let user = greatUser()
    let response = await fetch('api/admin/users/new', {
        method: "POST",
        body: JSON.stringify(user),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
}

function arrayBoost() {
    let selectedRoles = []
    Array.from(document.getElementById("role").selectedOptions).forEach(el => {
        selectedRoles.push({
            id : el.index + 1 ,
            name : "ROLE_" + el.text,
           authority : "ROLE_"+ el.outerText
        })
    })
    return selectedRoles;
}

function greatUser() {
    let user = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        level: document.getElementById("level").value,
        roles: arrayBoost()
    }
    return user;
}