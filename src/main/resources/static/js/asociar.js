
const formatButton = (cell, formatterParams, onRendered) => {
    const data = cell.getRow().getData();
    console.log(data);
    const button = `<form method="post" action="/desasociarItemMuestra/${data["id_muestra"]}">
                                <input type="hidden" name="itemId" value="${data["id"]}">
                                <button type="submit" class="btn btn-danger"><i class="fas fa-trash"></i></button>
                            </form>`
    return button;
}


var table = new Tabulator("#table_muestras_asociadas", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        {formatter: "responsiveCollapse", minWidth: 30, hozAlign: "center", resizable: true, headerSort: true},
        {title: "ID", hozAlign: "center", minWidth: 50},
        {title: "ITEM ASOCIADO", hozAlign: "center", minWidth: 75},
        {title: "ID MUESTRA", hozAlign: "center", minWidth: 50},
        {title: "SOLICITUD", hozAlign: "center", minWidth: 50},
        {title: "ACCIONES", field: "ID", formatter: formatButton, hozAlign: "center", minWidth: 120}
    ]
});

document.getElementById("download-xlsx").addEventListener("click", function(){
    table.download("xlsx", "items.xlsx", {sheetName:"itemsAsociados"});
});

//trigger download of data.pdf file
document.getElementById("download-pdf").addEventListener("click", function(){
    table.download("pdf", "items.pdf", {
        orientation:"portrait", //set page orientation to portrait
        title:"Items Asociados", //add title to report
    });
});
