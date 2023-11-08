var table = new Tabulator("#table_items", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        { formatter: "responsiveCollapse", width: 30, minWidth: 30, hozAlign: "center", resizable: false, headerSort: false },
        { title: "ID", hozAlign: "left", minWidth: 50, width: 75 },
        { title: "NOMBRE", hozAlign: "center", minWidth: 50 },
        { title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 300 },
        { title: "FECHA", hozAlign: "right", minWidth: 300 },
        { title: "FECHA ACTUAL", hozAlign: "right", minWidth: 300 },
    ]
});