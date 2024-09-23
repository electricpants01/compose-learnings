const { Client } = require('pg');

const client = new Client({
    host: "terraform-20240922181449343900000001.cx0iuaywg46s.us-east-1.rds.amazonaws.com",
    user: "todo_user",
    password: "locoto123",
    database: "todo_db",
    port: 5432,
    ssl: {
            rejectUnauthorized: false, // Optional: Set to true if you have the certificate.
        }
});

// Connect to the database before the handler function is invoked
client.connect()
  .then(() => console.log('Connected to PostgreSQL database'))
  .catch(err => console.error('Error connecting to PostgreSQL database:', err));

async function testConnection() {
    try {
        console.log("conectando")

        // Example query
        const res = await client.query('SELECT * from todos');
        console.log('Server time:', res.rows[0]);

        await client.end();
        console.log("Disconnected from the database");
    } catch (err) {
        client.end();
        console.error("Error connecting to the database", err);
    }
}

testConnection();