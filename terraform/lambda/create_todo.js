const mysql = require('mysql');

const connection = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});

exports.handler = async (event) => {
    const { title, description } = JSON.parse(event.body); // Datos enviados en la solicitud POST
    return new Promise((resolve, reject) => {
        const sql = 'INSERT INTO todos (title, description) VALUES (?, ?)';
        connection.query(sql, [title, description], (error, results) => {
            if (error) {
                reject({
                    statusCode: 500,
                    body: JSON.stringify({ message: 'Error al insertar la tarea', error }),
                });
            }
            resolve({
                statusCode: 200,
                body: JSON.stringify({ message: 'Tarea creada con éxito', taskId: results.insertId }),
            });
        });
    });
};