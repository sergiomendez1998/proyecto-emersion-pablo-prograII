
const formatButton = (cell, formatterParams, onRendered) => {
    const data = cell.getRow().getData();
    const position = cell.getRow().getPosition();

    const button = document.createElement("div");
    button.classList = "d-flex justify-contet-center";
    button.innerHTML = `<div>
                          <a href="/Muestra/Asociar/${data["id"]}" class="btn btn-primary"><i class="fas fa-pencil"></i></a>
                       </div>
                       <form method="post" action="/eliminarMuestra/${data["id"]}">
                             <button type="submit" class="btn btn-danger"><i class="fas fa-trash"></i></button>
                       </form>
                       <div>
                            <button class="btn btn-success detalle-muestra" data-id="${data["id"]}"><i class="fas fa-eye" data-id="${data["id"]}"></i></button>
                       </div>`;
    return button;
}

var table = new Tabulator("#table_muestras", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        {formatter: "responsiveCollapse", minWidth: 30, hozAlign: "center", resizable: true, headerSort: true},
        {title: "ID", field: "id", hozAlign: "center", sorter: "number", resizable: true, headerSort: true, minWidth: 30},
        {title: "CODIGO", field: "codigoMuestra", hozAlign: "center", sorter: "number", resizable: true, headerSort: true, minWidth: 100},
        {title: "TIPO MUESTRA", field: "tipoMuestra", hozAlign: "center", sorter: "string", resizable: true, headerSort: true, minWidth: 100},
        {title: "NO. SOLICITUD", field: "codigoSolicitud", hozAlign: "center", sorter: "number", resizable: true, headerSort: true, minWidth: 100},
        {title: "PRESENTACION", field: "presentacion", hozAlign: "center", sorter: "string", resizable: true, headerSort: true, minWidth: 150},
        {title: "U.M.", field: "unidadMedida", hozAlign: "center", sorter: "string", resizable: true, headerSort: true, minWidth: 100},
        {title: "ITEMS", field: "items", hozAlign: "center", sorter: "string", resizable: true, headerSort: true, minWidth: 50},
        {title: "ACCIONES", field: "ID", formatter: formatButton, hozAlign: "center", minWidth: 130}
    ]
});


document.getElementById("download-xlsx").addEventListener("click", function(){
    table.download("xlsx", "muestras.xlsx", {sheetName:"muestras"});
});

//trigger download of data.pdf file
document.getElementById("download-pdf").addEventListener("click", function(){
    table.download("pdf", "muestras.pdf", {
        orientation:"portrait", //set page orientation to portrait
        title:"muestras", //add title to report
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

const getDetails = async (id) => {
    const url = `/informacionGeneralMuestra/${id}`;
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

document.addEventListener("click", (e) => {
    if (e.target.matches(".detalle-muestra")) {
        const id = e.target.dataset.id;
        getDetails(id);
    }

    if (e.target.matches(".detalle-muestra i")) {
        const id = e.target.dataset.id;
        getDetails(id);
    }
});

