
const formatButton = (cell, formatterParams, onRendered) => {
    const data = cell.getRow().getData();
    const position = cell.getRow().getPosition();

    const button = document.createElement("div");
    button.classList = "btn-group";
    button.setAttribute("role", "group");
    button.setAttribute("aria-label", "Basic mixed styles example");
    button.innerHTML = `<a href="/Muestra/Asociar/${data["id"]}" class="btn btn-primary"><i class="fas fa-pencil"></i>
                        </a>
                        <a type="button" class="btn btn-danger"> 
                          <i class="fas fa-trash-alt"
                         ></i>
                        </a>`;
    return button;
}

const table = new Tabulator("#table_muestras", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        {formatter: "responsiveCollapse", minWidth: 30, hozAlign: "center", resizable: true, headerSort: true},
        {title: "ID", field: "id", hozAlign: "center", sorter: "number", resizable: true, headerSort: true},
        {title: "CO. MUESTRA", field: "codigoMuestra", hozAlign: "center", sorter: "number", resizable: true, headerSort: true},
        {title: "TIPO MUESTRA", field: "tipoMuestra", hozAlign: "center", sorter: "string", resizable: true, headerSort: true},
        {title: "CO. SOLICITUD", field: "codigoSolicitud", hozAlign: "center", sorter: "number", resizable: true, headerSort: true},
        {title: "NO. EXPEDIENTE", field: "numeroExpediente", hozAlign: "center", sorter: "string", resizable: true, headerSort: true},
        {title: "NIT", field: "nit", hozAlign: "center", sorter: "string", resizable: true, headerSort: true},
        {title: "PRESENTACION", field: "presentacion", hozAlign: "center", sorter: "string", resizable: true, headerSort: true},
        {title: "FECHA SOLICITUD", field: "fechaSolicitud", hozAlign: "center", sorter: "string", resizable: true, headerSort: true},
        {title: "FECHA", field: "fecha", hozAlign: "center", sorter: "string", resizable: true, headerSort: true},
        {title: "U MEDIDA", field: "unidadMedida", hozAlign: "center", sorter: "string", resizable: true, headerSort: true},
        {title: "ITEMS", field: "items", hozAlign: "center", sorter: "string", resizable: true, headerSort: true},
        {title: "ACCIONES", field: "ID", formatter: formatButton, hozAlign: "center", minWidth: 120}
    ]
});

