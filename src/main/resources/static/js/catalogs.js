const TABLE_VALUES = [
    {
        id: "article_0",
        name: "--Seleccione un Catálogo--",
        selected: false,
    },
    {
        id: "article_table_items",
        name: "Cátalogo de Items",
        selected: true,
    },
    {
        id: "article_table_items2",
        name: "Cátalogo de Roles",
        selected: false,
    },
    {
        id: "article_table_items3",
        name: "Cátalogo de Estados",
        selected: false,
    },
    {
        id: "article_table_items4",
        name: "Cátalogo de Examenes",
        selected: false,
    },
    {
        id: "article_table_items5",
        name: "Cátalogo de Muestras",
        selected: false,
    },
    {
        id: "article_table_items6",
        name: "Cátalogo de Unidades de Medida",
        selected: false,
    },
]

const CONFIG_TABLE = {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        { formatter: "responsiveCollapse", width: 30, minWidth: 30, hozAlign: "center", resizable: false, headerSort: false },
        { title: "ID", hozAlign: "left", minWidth: 50, width: 75 },
        { title: "NOMBRE", hozAlign: "center", minWidth: 50 },
        { title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 300 },
        { title: "FECHA CREACIÓN", hozAlign: "right", minWidth: 300 },
        { title: "FECHA ACTUALIZACIÓN", hozAlign: "right", minWidth: 300 },
    ]
};


document.addEventListener("DOMContentLoaded", ()=> {
    const select = document.querySelector("#select_Catalogos");
//se crean las etiquetas option para los catalogos

    const select_items = TABLE_VALUES.map(t=>{
        const option = document.createElement("option");
        option.value = t.id;
        option.textContent = t.name;
        option.selected = t.selected;
        return option;
    });
//renderiza los elementos creados anteriormente

    select.append(...select_items);

    //tabla

    new Tabulator("#table_items", CONFIG_TABLE);

    new Tabulator("#table_items2", CONFIG_TABLE);

    new Tabulator("#table_items3", CONFIG_TABLE);

    new Tabulator("#table_items4", CONFIG_TABLE);

    new Tabulator("#table_items5", CONFIG_TABLE);

    new Tabulator("#table_items6", CONFIG_TABLE);

    //evento de select, cambiar de tablas.

    select.addEventListener("input", (e)=>{
        const selected = e.target.value;
        const table = document.querySelector(`#${selected}`) || null;
        table != null && table.classList.remove("d-none");

        TABLE_VALUES.filter(t=> t.id !== selected).forEach(t=>{
            const table = document.querySelector(`#${t.id}`) || null;
            table != null && table.classList.add("d-none");
        });
    })
});