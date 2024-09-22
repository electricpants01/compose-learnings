const mysql = require('mysql');

const connection = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});

exports.handler = async (event) => {
    return new Promise((resolve, reject) => {
        const taskId = event.queryStringParameters && event.queryStringParameters.id;
        let sql = 'SELECT * FROM todos';
        if (taskId) {
            sql += ' WHERE id = ?';
        }
        connection.query(sql, [taskId], (error, results) => {
            if (error) {
                reject({
                    statusCode: 500,
                    body: JSON.stringify({ message: 'Error al leer las tareas', error }),
                });
            }
            resolve({
                statusCode: 200,
                body: JSON.stringify(results),
            });
        });
    });
};