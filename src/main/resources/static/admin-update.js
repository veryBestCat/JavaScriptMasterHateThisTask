let updateButton = document.getElementById("updateUserButtonInModal")

updateButton.onclick = function () {
    let form = document.forms["UpdateForm"];
    updateUser(form.id.value)
    redirect()
}

async function updateUser(id) {
    let form = document.forms["UpdateForm"];
    let response = await fetch('/api/admin/users/update/' + id, {
        method: "PATCH",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        body:JSON.stringify( {
            id: form.id.value,
            username: form.username.value,
            level: form.level.value,
            password: form.password.value,
            roles: arrayBoostUp()
        })

    })
}

function findUserByIdForUpdate(Id) {
    fetch('api/admin/users/' + Id).then(res => {
        res.json().then(data => {
            openUpdateModal(data)
        })
    })
}

function openUpdateModal(userInfo) {

    let form = document.forms["UpdateForm"];

    form.id.value = userInfo.id;
    form.username.value = userInfo.username;
    form.password.value = userInfo.password;
    form.level.value = userInfo.level;
    form.roles.value = userInfo.role;
}

function arrayBoostUp() {
    let selectedRoles = []
    Array.from(document.getElementById("roleUp").selectedOptions).forEach(el => {
        selectedRoles.push({
            id: el.index + 1,
            name: "ROLE_" + el.text,
            authority: "ROLE_" + el.outerText
        })
    })
    return selectedRoles;
}