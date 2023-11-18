var table =  new Tabulator("#table_soli" , {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        {formatter:"responsiveCollapse", width:30, minWidth: 30, hozAlign:"center", resizable: true, headerSort: true},
        {title: "ID", hozAlign: "center", minWidth: 50, width: 75 },
        {title: "CODIGO DE SOLICITUD", hozAlign: "center", minWidth: 50, width: 150 },
        {title: "NÚMERO DE SOPORTE", hozAlign: "center", minWidth: 50, width: 150 },
        {title: "CORREO", hozAlign: "center", minWidth: 50, width: 250 },
        {title: "OBSERVACIÓN", hozAlign: "center", minWidth: 50, width: 400},
        {title: "FECHA DE RECEPCIÓN", hozAlign: "center", minWidth: 50, width: 200 },
    ]
})


