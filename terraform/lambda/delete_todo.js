const { Client } = require('pg');

const client = new Client({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    port: 5432,
});

exports.handler = async (event) => {
    const { id } = JSON.parse(event.body);

    await client.connect();

    try {
        const sql = 'DELETE FROM todos WHERE id = $1';
        const res = await client.query(sql, [id]);

        if (res.rowCount === 0) {
            return {
                statusCode: 404,
                body: JSON.stringify({ message: 'Tarea no encontrada' }),
            };
        }

        return {
            statusCode: 200,
            body: JSON.stringify({ message: 'Tarea eliminada con éxito' }),
        };
    } catch (error) {
        return {
            statusCode: 500,
            body: JSON.stringify({ message: 'Error al eliminar la tarea', error }),
        };
    } finally {
        await client.end();
    }
};
