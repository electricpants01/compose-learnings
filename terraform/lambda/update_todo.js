const mysql = require('mysql');

const connection = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});

exports.handler = async (event) => {
    const { id, title, description, status } = JSON.parse(event.body); // Datos enviados en la solicitud PUT
    return new Promise((resolve, reject) => {
        const sql = 'UPDATE todos SET title = ?, description = ?, status = ? WHERE id = ?';
        connection.query(sql, [title, description, status, id], (error, results) => {
            if (error) {
                reject({
                    statusCode: 500,
                    body: JSON.stringify({ message: 'Error al actualizar la tarea', error }),
                });
            }
            resolve({
                statusCode: 200,
                body: JSON.stringify({ message: 'Tarea actualizada con éxito', results }),
            });
        });
    });
};