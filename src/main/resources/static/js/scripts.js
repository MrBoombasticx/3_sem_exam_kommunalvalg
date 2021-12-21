fetch("http://localhost:8080/candidates")
    .then(response => response.json())
    .then(result => {

        result.map(createTable)
    })

function createTable(element) {
    console.log(element)
    const tableRowElement = document.createElement("tr")
    tableRowElement.innerHTML = `
            <td>${element.id}</td>
            <td>${element.name}</td>
            <td>${element.municipality}</td>
            <td>${element.party}</td>
            <td><button id="button-2" onclick="deleteCandidate(${element.id})">Delete</button></td>
            `
    document.getElementById("candidates-table").appendChild(tableRowElement);

}

document.getElementById(`button-1`).addEventListener("click", () => {
    const object = {
        "name": document.getElementById("fname").value,
        "municipality": document.getElementById("municipality").value,
    }
    console.log(object)

    const party = document.getElementById("party").value
    fetch(`http://localhost:8080/candidate/` + party, {
        method: "POST",
        headers: {
            "Content-Type": "application/json;"
        },
        body: JSON.stringify(object)
    }).then(response => response.json())
        .then(result => {
            createTable(result)
        })
    window.location.replace(window.location.href);
});

function deleteCandidate(id) {
    fetch("http://localhost:8080/candidate/" + id, {
        method: "DELETE"
    }).then(response => {
        if (response.status === 200) {
            window.location.replace(window.location.href);
            document.getElementById(id).remove();
        } else {
            console.log(response.status);
        }
    });

}

