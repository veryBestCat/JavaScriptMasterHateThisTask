
async function delUser(id) {

    let response = await fetch('api/admin/users/'+ id, {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
}

function findUserByIdForDelete(Id) {
    fetch('api/admin/users/' + Id).then(res => {
        res.json().then(data => {
            openDeleteModal(data)
        })
    })
}

function openDeleteModal(userInfo) {
    console.log(userInfo)
    const deleteUser = document.getElementById("modal")
    deleteUser.innerHTML = `
<div class="row">
<div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Delete User</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                                    </div>
        <div class="col-3">
                        </div>
                        <div class="col-6">         
            <div class="form-group text-center">
            <b>ID</b>
            <input type="text"class="form-control text-left" value="${userInfo.id}" disabled>
            <br>
            <b>Username</b>
            <input type="text"class="form-control text-left" value="${userInfo.username}" disabled>
            <br>
            <b>Level</b>
            <input type="text"class="form-control text-left" value="${userInfo.level}" disabled>
            <br>
            <b>Roles</b>
            <input type="text"class="form-control text-left" value="${userInfo.roles.map(role => " " + role.name.substring(5))}" disabled>
            <br>
            </div>
            </div>
</div>

<div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                           <button type="button" class="btn btn-danger"  onclick= "delUser(${userInfo.id}), redirect()" >Delete</button>
                                    </div>
    `
}

function redirect(){window.location = 'http://localhost:8080/admin';}