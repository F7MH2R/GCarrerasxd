<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gráfico de Líneas</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
        <%@ include file="nav.jsp" %>

    <h1>Gráfico de Líneas: Evolución del Interés</h1>
    <canvas id="graficoLineas" width="800" height="400"></canvas>

    <script>
        const etiquetas = JSON.parse('<%= request.getAttribute("fechas") %>');
        const datos = JSON.parse('<%= request.getAttribute("datos") %>');

        // Organizar los datos por carreras
        const carreras = [...new Set(datos.map(d => d.carrera))];
        const datasets = carreras.map(carrera => {
            return {
                label: carrera,
                data: etiquetas.map(fecha => {
                    const entrada = datos.find(d => d.fecha === fecha && d.carrera === carrera);
                    return entrada ? entrada.total : 0;
                }),
                borderColor: `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, 1)`,
                borderWidth: 2,
                fill: false
            };
        });

        new Chart(document.getElementById('graficoLineas'), {
            type: 'line',
            data: {
                labels: etiquetas,
                datasets: datasets
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Fechas'
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Cantidad de Intereses'
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>
