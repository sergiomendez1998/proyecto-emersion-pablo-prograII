
const renderActionsForRequest = (data) =>{
    const actions = document.querySelector("#render-actions");
    actions.innerHTML = "";
    const actions_items = document.createElement("div");
    actions_items.innerHTML = `<ul class="list-group">
                                  <li class="list-group-item">Solicitud No. ${data["codigo"]}</li>
                                  <li class="list-group-item">
                                    <a href="#" class="btn btn-outline-primary">Ver Detalles</a>
                                  </li>
                                  <li class="list-group-item">
                                      <a href="#" class="btn btn-outline-success">Crear Muestras</a>
                                  </li>
                                  <li class="list-group-item">
                                     <button type="button" class="btn btn-outline-danger" onclick="eliminarSolicitud(${data['id']})">Eliminar Solicitud</button>
                                  </li>
                                   <li class="list-group-item">
                                      <a href="#" class="btn btn-outline-success">Contribuyente</a>
                                  </li>
                                   <li class="list-group-item">
                                      <a href="#" class="btn btn-outline-info">Trazabilidad</a>
                                  </li>
                                   <li class="list-group-item">
                                      <a href="#" class="btn btn-outline-warning">Estados</a>
                                  </li>
                                </ul>`;
    actions.append(actions_items);
}

const formatButton = (cell, formatterParams, onRendered) => {
    const data = cell.getRow().getData();
    console.log(data);
    const button = document.createElement("button");
    button.textContent = "Ver Acciones";
    button.classList = "btn btn-primary";
    button.setAttribute("data-bs-toggle", "modal");
    button.setAttribute("data-bs-target", "#exampleModal");
    button.addEventListener("click", ()=> renderActionsForRequest(data));
    return button;
}


new Tabulator("#table_soli" , {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        {formatter:"responsiveCollapse", minWidth: 30, hozAlign:"center", resizable: true, headerSort: true},
        {title: "ID", hozAlign: "center", minWidth: 50 },
        {title: "CODIGO", hozAlign: "center", minWidth: 75 },
        {title: "NO SOPORTE", hozAlign: "center", minWidth: 50 },
        {title: "CORREO", hozAlign: "center", minWidth: 50 },
        {title: "OBSERVACIÓN", hozAlign: "center", minWidth: 50, width: 400},
        {title: "FECHA DE RECEPCIÓN", hozAlign: "center", minWidth: 150},
        {title: "ACCIONES", field: "ID", formatter: formatButton, hozAlign: "center", minWidth: 120 }
    ]
})

function eliminarSolicitud(id) {
    fetch(`/Solicitud/eliminar/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (response.ok) {
                return response.json().then(data => {
                    if (data.error) {
                        throw new Error(data.error);
                    }
                    return data;
                });
            } else {
                throw new Error(`Error al eliminar la solicitud: ${response.statusText}`);
            }
        })
        .then(data => {
            window.location.href = '/Solicitud';
        })
        .catch(error => {
            console.error('Error al eliminar la solicitud:', error);
            window.location.href = '/Solicitud';
        });
}



