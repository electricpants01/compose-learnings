const mysql = require('mysql');

const connection = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});

exports.handler = async (event) => {
    const { id } = JSON.parse(event.body); // ID de la tarea a eliminar
    return new Promise((resolve, reject) => {
        const sql = 'DELETE FROM todos WHERE id = ?';
        connection.query(sql, [id], (error, results) => {
            if (error) {
                reject({
                    statusCode: 500,
                    body: JSON.stringify({ message: 'Error al eliminar la tarea', error }),
                });
            }
            resolve({
                statusCode: 200,
                body: JSON.stringify({ message: 'Tarea eliminada con éxito', results }),
            });
        });
    });
};