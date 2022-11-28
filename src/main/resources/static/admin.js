let allUsers = "";
getTableUser()

function getTableUser() { //
    fetch('admin-all').then(res => {
        res.json().then(
            data => {
                data.forEach(json => {
                    allUsers = allUsers + `
            <tr class="table-light align-middle table-striped">
                <td>${json.id}</td>
                <td>${json.username}</td>
                <td>${json.level}</td>
                <td>${json.roles.map(role => " " + role.name.substring(5))}</td>
               
                <td>
                    <button class="btn btn-primary"  onclick="findUserById(${json.id})" data-bs-toggle="modal" data-bs-target="#myModal2"  type="button" >Update</button>
                </td>
                <td>
                    <button class="btn btn-danger"  onclick="openModalByUserId2(${json.id})" data-bs-toggle="modal" data-bs-target="#myModal" type="button" >Delete</button>
                </td>
            </tr>
            `
                })
                document.getElementById("tableAdmin").innerHTML = allUsers

            })
    })
}






