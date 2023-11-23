const table = new Tabulator("#table_muestras", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        {formatter: "responsiveCollapse", minWidth: 30, hozAlign: "center", resizable: true, headerSort: true},
        {title: "ID", hozAlign: "center", minWidth: 75},
        {title: "NOMBRE", hozAlign: "center", minWidth: 100},
        {title: "FECHA ACTUAL", hozAlign: "center", minWidth: 100},
        {title: "FECHA DE CREACIÓN", hozAlign: "center", minWidth: 100},
        {title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 50, width: 300},
    ]
});