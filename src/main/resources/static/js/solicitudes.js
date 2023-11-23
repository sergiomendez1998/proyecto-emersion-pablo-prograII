const renderActionsForRequest = (data, position) => {
    const actions = document.querySelector("#render-actions");
    actions.innerHTML = "";
    const actions_items = document.createElement("div");
    actions_items.innerHTML = `<ul class="list-group">
                                  <li class="list-group-item">Solicitud No. ${data["codigo"]}</li>
                                  <li class="list-group-item">
                                    <button class="btn btn-outline-primary details" data-id="${data["id"]}">Ver Detalles</button>
                                  </li>
                                  <li class="list-group-item">
                                      <a href="/Muestra/Crear/${data["id"]}" class="btn btn-outline-success">Crear Muestras</a>
                                  </li>
                                   <li class="list-group-item">
                                     <button class="btn btn-outline-success contribuyente" data-id="${data["id"]}">Expediente</a>
                                  </li>
                                      <li class="list-group-item">
                                    <input type="button" value="Eliminar Solicitud" data-position="${position}" data-id="${data["id"]}" class="btn btn-outline-danger delete">
                                  </li>
                                </ul>`;
    actions.append(actions_items);
}

const formatButton = (cell, formatterParams, onRendered) => {
    const data = cell.getRow().getData();
    const position = cell.getRow().getPosition();
    const button = document.createElement("button");
    button.textContent = "Ver Acciones";
    button.classList = "btn btn-primary";
    button.setAttribute("data-bs-toggle", "modal");
    button.setAttribute("data-bs-target", "#exampleModal");
    button.addEventListener("click", () => renderActionsForRequest(data, position));
    return button;
}


var table = new Tabulator("#table_soli", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        {formatter: "responsiveCollapse", minWidth: 30, hozAlign: "center", resizable: true, headerSort: true},
        {title: "ID", hozAlign: "center", minWidth: 50},
        {title: "CODIGO", hozAlign: "center", minWidth: 75},
        {title: "NO SOPORTE", hozAlign: "center", minWidth: 50},
        {title: "CORREO", hozAlign: "center", minWidth: 50},
        {title: "OBSERVACIÓN", hozAlign: "center", minWidth: 50, width: 400},
        {title: "FECHA DE RECEPCIÓN", hozAlign: "center", minWidth: 150},
        {title: "ACCIONES", field: "ID", formatter: formatButton, hozAlign: "center", minWidth: 120}
    ]
});

document.getElementById("download-xlsx").addEventListener("click", function(){
    table.download("xlsx", "solicitudes.xlsx", {sheetName:"solicitudes"});
});

//trigger download of data.pdf file
document.getElementById("download-pdf").addEventListener("click", function(){
    table.download("pdf", "solicitudes.pdf", {
        orientation:"portrait", //set page orientation to portrait
        title:"Solicitudes", //add title to report
    });
});

const generalInformation = (data) => {
    const cardBody = Object.keys(data).map((key, idx) => {
        const li = document.createElement("li");
        li.classList = "text-start";
        li.innerHTML = `<span class="fw-bold">${key}</span>: ${data[key]}`;
        return li;
    })

    const card = document.createElement("div");
    card.classList = "card";
    card.append(...cardBody);
    return card;
}

const informacionExpediente = (data) => {
    const requiredFields = ["numeroExpediente", "nombre", "Nit", "observacion"];
    const cardBody = requiredFields.map((key) => {
        const li = document.createElement("li");
        li.classList = "text-start";
        li.innerHTML = `<span class="fw-bold">${key}</span>: ${data[key]}`;
        return li;
    });

    const card = document.createElement("div");
    card.classList = "card";
    card.append(...cardBody);
    return card;
}


const deleteRequest = async (id) => {
    const url = `/Solicitud/eliminar/${id}`;
    try {
        swal.fire({
            title: "Espera un momento",
            text: "Cargando",
            icon: "info",
            confirmButtonText: "Aceptar",
            didOpen: async () => {
                Swal.showLoading();
                const response = await fetch(url, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                const data = await response.json();
                table.deleteRow(id);
                Swal.hideLoading();
                swal.fire({
                    title: "Solicitud Eliminada",
                    text: "Solicitud eliminada con exito",
                    icon: "success",
                    confirmButtonText: "Aceptar",
                    html: generalInformation(data)
                })
            }
        });
    } catch (e) {
        swal.fire({
            title: "Error",
            text: "Error al eliminar la solicitud",
            icon: "error",
            confirmButtonText: "Aceptar"
        })
    }
};

const getDetails = async (id) => {
    const url = `/Solicitud/Informacion/${id}`;
    try {
        swal.fire({
            title: "Espera un momento",
            text: "Cargando",
            icon: "info",
            confirmButtonText: "Aceptar",
            didOpen: async () => {
                Swal.showLoading();
                const response = await fetch(url, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                const data = await response.json();
                Swal.hideLoading();
                swal.fire({
                    title: "Datos Generales",
                    icon: "success",
                    confirmButtonText: "Aceptar",
                    html: generalInformation(data)
                });
            }
        });

    } catch (ex) {

        swal.fire({
            title: "Error",
            text: ex.toString(),
            icon: "error",
            confirmButtonText: "Aceptar"
        })
    }
};

const getInforContribuyente = async (id) => {
    const url = `/Solicitud/Informacion/${id}`;
    try {
        swal.fire({
            title: "Espera un momento",
            text: "Cargando",
            icon: "info",
            confirmButtonText: "Aceptar",
            didOpen: async () => {
                Swal.showLoading();
                const response = await fetch(url, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                const data = await response.json();
                Swal.hideLoading();
                swal.fire({
                    title: "Información Expediente",
                    icon: "success",
                    confirmButtonText: "Aceptar",
                    html: informacionExpediente(data)
                });
            }
        });

    } catch (ex) {

        swal.fire({
            title: "Error",
            text: ex.toString(),
            icon: "error",
            confirmButtonText: "Aceptar"
        })
    }
};

document.addEventListener("click", async (e) => {

    if (e.target.matches(".delete")) {
        const id = e.target.dataset.id;
        await deleteRequest(id);
    }
    if (e.target.matches(".details")) {
        const id = e.target.dataset.id;
        await getDetails(id);
    }
    if (e.target.matches(".contribuyente")) {
        const id = e.target.dataset.id;
        await getInforContribuyente(id);
    }
});





